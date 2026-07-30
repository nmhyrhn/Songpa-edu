from langgraph.graph import END, START, StateGraph

from l_01_plan import generate_plan
from component1 import (
    Context,
    LessonState,
    execute_budget,
    execute_compose,
    execute_destination,
    execute_requirements,
    execute_weather,
    validate_execution
)
from travel_domain import ItineraryDraft, Plan, TripRequest

# 1. 제공받은 함수를 Planner가 선택할 Task이름과 연결
TASK_HANDLERS = {
    "requirements": execute_requirements,
    "weather": execute_weather,
    "destination": execute_destination,
    "budget": execute_budget,
    "compose": execute_compose
}

def execute_plan(
        request: TripRequest,
        plan: Plan
) -> tuple[Context, ItineraryDraft, list[str]]:
    """Plan 순서대로 등록된 실행함수를 호출한다."""
    context: Context = {}
    path = []

    for task in plan.tasks:
        TASK_HANDLERS[task](request, context)
        path.append(task)

    if "draft" not in context:
        raise ValueError("전체 일정 Plan에는 compose Task가 필요합니다.")
    return context, context["draft"], path

# 2. Executor와 Validator Node 생성
def executor(state: LessonState) -> dict:
    """Plan을 실행하고 중간 결과와 Draft를 State에 저장"""
    context, draft, tasks = execute_plan(state["request"], state["plan"])
    return {
        "context": context,
        "draft": draft,
        "path": state["path"] + ["executor", *tasks]
    }

def validator(state: LessonState) -> dict:
    """제공된 검증 함수로 실행 결과의 PASS 또는 FAIL을 만든다"""
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

# 3. 위의 노드를 이용해서 Graph 생성 및 연결
def build_graph():
    """START -> executor -> validator -> END Graph로 생성"""
    builder = StateGraph(LessonState)
    builder.add_node("executor", executor)
    builder.add_node("validator", validator)
    builder.add_edge(START, "executor")
    builder.add_edge("executor", "validator")
    builder.add_edge("validator", END)
    return builder.compile()

def main() -> None:
    """Graph를 실행"""
    request = TripRequest(weather="rain", people=3, budget=200_000, children=True)
    goal = f"아이와 갈 송파 하루 일정을 만들어줘.\n조건: {request.model_dump()}"

    plan = generate_plan(goal)
    result = build_graph().invoke({"request": request, "plan": plan, "path": []})

    print("[Plan]", plan.tasks)
    print("[경로]", " -> ".join(result["path"]))
    print("[Draft]", result["draft"])
    print("[검증]", result["validation"])

if __name__ == "__main__":
    main()