from typing import Literal

from langgraph.graph import END, START, StateGraph

from l_01_plan import generate_plan
from component1 import validate_execution
from l_02_executor import execute_plan
from component2 import (
    WorkflowState,
    create_replanner_model,
    require_api_key
)
from travel_domain import TripRequest

def build_graph():
    """Planner, Executor, Validator, Replanner를 연결"""
    replanner_model = create_replanner_model()

    def planner(state: WorkflowState) -> dict:
        """Goal과 요청 조건을 실제 LLM Plan으로 변환"""
        prompt = f"{state['goal']}\n조건: {state['request'].model_dump()}"
        return {
            "plan": generate_plan(prompt),
            "path": state["path"] + ["planner"]
        }

    def executor(state: WorkflowState) -> dict:
        """ l_02_executor.py에서 만든 execute_plan()으로 Plan을 실행"""
        context, draft, tasks = execute_plan(state["request"], state["plan"])
        return {
            "context": context,
            "draft": draft,
            "path": state["path"] + ["executor", *tasks]
        }

    def validator(state: WorkflowState) -> dict:
        """검증 함수로 실패 원인을 만든다."""
        report = validate_execution(
            state["plan"],
            state["request"],
            state["context"],
            state["draft"]
        )
        return {
            "validation": report,
            "path": state["path"] + ["validator"]
        }

    def replanner(state: WorkflowState) -> dict:
        """실제 LLM이 실패 원인으로 retry or stop을 판단"""
        decision = replanner_model.invoke(
            "누락 Task로 해결할 수 있으면 retry와 수정 Plan을 반환하고, "
            "예산처럼 Plan으로 해결할 수 없으면 stop을 반환하세요\n"
            f"Plan: {state['plan']}\n"
            f"실패: {state['validation'].failed_conditions}"
        )
        return {
            "plan": decision.plan or state["plan"],
            "action": decision.action,
            "reason": decision.reason,
            "retries": state["retries"] + int(decision.action == "retry"),
            "path": state["path"] + ["replanner"]
        }
    # PASS/FAIL과 retry/stop 경로를 작성
    def after_validation(state: WorkflowState) -> Literal["replanner", "__end__"]:
        """PASS 또는 반복 상한이면 종료하고 나머지 FAIL은 재계획으로"""
        if state["validation"].passed:
            return END
        if state["retries"] >= state["max_retries"]:
            return END
        return "replanner"

    def after_replan(state: WorkflowState) -> Literal["executor", "__end__"]:
        """retry는 Executor로 보내고 stop은 종료"""
        return "executor" if state["action"] == "retry" else END

    builder = StateGraph(WorkflowState)
    builder.add_node("planner", planner)
    builder.add_node("executor", executor)
    builder.add_node("validator", validator)
    builder.add_node("replanner", replanner)

    builder.add_edge(START, "planner")
    builder.add_edge("planner", "executor")
    builder.add_edge("executor", "validator")
    builder.add_conditional_edges("validator", after_validation)
    builder.add_conditional_edges("replanner", after_replan)
    return builder.compile()

def main() -> None:
    require_api_key()
    request = TripRequest(weather="rain", people=3, budget=50_000, children=True)
    result = build_graph().invoke(
        {
            "goal": "아이와 갈 송파 하루 일정을 만들어줘.",
            "request": request,
            "retries": 0,
            "max_retries": 2,
            "path": []
        },
        config={"recursion_limit": 20}
    )
    print("[경로]", " -> ".join(result["path"]))
    print("[검증]", result["validation"])
    print("[재계획]", result.get("action"), result.get("reason"))

if __name__ == "__main__":
    main()