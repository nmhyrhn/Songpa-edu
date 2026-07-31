from __future__ import annotations

import os
import sys
from pathlib import Path
from typing import Any, Callable, Literal, NotRequired, TypedDict

from dotenv import load_dotenv
from langgraph.graph import END, START, StateGraph
from langsmith import traceable


# 단독 실행해도 형제 폴더인 D_11_Workflow의 제공 모듈을 import할 수 있게 합니다.
D_11_DIR = Path(__file__).resolve().parents[1] / "D_11_Workflow"
if str(D_11_DIR) not in sys.path:
    sys.path.insert(0, str(D_11_DIR))

from l_01_plan import generate_plan  # noqa: E402
from component1 import (  # noqa: E402
    Context,
    execute_budget,
    execute_compose,
    execute_destination,
    execute_requirements,
    execute_weather,
    validate_execution,
)
from travel_domain import (  # noqa: E402
    ItineraryDraft,
    Plan,
    TripRequest,
    ValidationReport,
)


PlanVersion = Literal["baseline", "fixed", "llm", "runtime_error"]
TaskHandler = Callable[[TripRequest, Context], dict[str, Any]]
LANGSMITH_REQUIRED = (
    "LANGSMITH_API_KEY",
    "LANGSMITH_TRACING",
    "LANGSMITH_PROJECT",
)


def langsmith_setting_errors() -> list[str]:
    """LangSmith 업로드에 필요한 설정의 누락과 잘못된 값을 찾습니다."""
    load_dotenv()
    errors = [
        f"{name} 누락"
        for name in LANGSMITH_REQUIRED
        if not os.getenv(name)
    ]
    tracing = os.getenv("LANGSMITH_TRACING", "").strip().lower()
    if tracing and tracing not in {"true", "1", "yes", "on"}:
        errors.append("LANGSMITH_TRACING은 true여야 합니다.")

    api_key = os.getenv("LANGSMITH_API_KEY", "").strip().lower()
    if api_key.startswith("your_"):
        errors.append("LANGSMITH_API_KEY를 실제 Key로 변경하세요.")
    return errors


def require_langsmith_settings() -> None:
    """잘못된 LangSmith 설정을 실제 업로드 전에 친절하게 차단합니다."""
    errors = langsmith_setting_errors()
    if errors:
        raise SystemExit("LangSmith 설정 오류: " + " / ".join(errors))


class TraceState(TypedDict):
    """Planner, Executor, Validator가 공유하는 State입니다."""

    goal: str
    request: TripRequest
    plan: NotRequired[Plan]
    context: NotRequired[Context]
    draft: NotRequired[ItineraryDraft]
    validation: NotRequired[ValidationReport]
    path: list[str]


@traceable(name="task-requirements", run_type="tool")
def traced_requirements(request: TripRequest, context: Context) -> dict[str, Any]:
    """요구사항 처리 함수를 Tool Run으로 추적합니다."""
    execute_requirements(request, context)
    return {"requirements": context["requirements"]}


@traceable(name="task-weather", run_type="tool")
def traced_weather(request: TripRequest, context: Context) -> dict[str, Any]:
    """날씨 분석 함수를 Tool Run으로 추적합니다."""
    execute_weather(request, context)
    return {"weather": context["weather"]}


@traceable(name="task-destination", run_type="tool")
def traced_destination(request: TripRequest, context: Context) -> dict[str, Any]:
    """장소 검색 함수를 Tool Run으로 추적합니다."""
    execute_destination(request, context)
    return {
        "destinations": [
            place.model_dump() for place in context["destinations"]
        ]
    }


@traceable(name="task-budget", run_type="tool")
def traced_budget(request: TripRequest, context: Context) -> dict[str, Any]:
    """예산 계산 함수를 Tool Run으로 추적합니다."""
    execute_budget(request, context)
    return {"budget_options": context["budget_options"]}


@traceable(name="task-compose", run_type="tool")
def traced_compose(request: TripRequest, context: Context) -> dict[str, Any]:
    """일정 작성 함수를 Tool Run으로 추적합니다."""
    execute_compose(request, context)
    return {"draft": context["draft"].model_dump()}


TASK_HANDLERS: dict[str, TaskHandler] = {
    "requirements": traced_requirements,
    "weather": traced_weather,
    "destination": traced_destination,
    "budget": traced_budget,
    "compose": traced_compose,
}


def make_plan(goal: str, version: PlanVersion) -> Plan:
    """기준 Plan, 수정 Plan 또는 실제 LLM Plan을 만듭니다."""
    if version == "llm":
        return generate_plan(goal)

    if version == "baseline":
        tasks = ["requirements", "weather", "budget", "compose"]
    elif version == "runtime_error":
        tasks = ["requirements", "weather", "destination", "budget"]
    else:
        tasks = ["requirements", "weather", "destination", "budget", "compose"]
    return Plan(
        goal=goal,
        tasks=tasks,
        success_criteria=[
            "요청 조건에 맞는 장소를 선택한다.",
            "예상 비용이 사용자 예산 이내인지 확인한다.",
        ],
    )


def execute_plan(
    request: TripRequest,
    plan: Plan,
) -> tuple[Context, ItineraryDraft, list[str]]:
    """Plan 순서대로 제공 함수를 실행하고 Draft를 반환합니다."""
    context: Context = {}
    executed_tasks = []
    for task in plan.tasks:
        TASK_HANDLERS[task](request, context)
        executed_tasks.append(task)

    draft = context.get("draft")
    if draft is None:
        raise ValueError("실행 결과에 Draft가 없습니다. compose Task를 확인하세요.")
    return context, draft, executed_tasks


def build_workflow(version: PlanVersion = "fixed"):
    """책임을 유지한 Planner -> Executor -> Validator Graph를 만듭니다."""

    def planner(state: TraceState) -> dict:
        """Goal을 선택한 버전의 Plan으로 변환합니다."""
        return {
            "plan": make_plan(state["goal"], version),
            "path": state["path"] + ["planner"],
        }

    def executor(state: TraceState) -> dict:
        """Plan의 Task를 실제 함수로 실행합니다."""
        context, draft, tasks = execute_plan(state["request"], state["plan"])
        return {
            "context": context,
            "draft": draft,
            "path": state["path"] + ["executor", *tasks],
        }

    def validator(state: TraceState) -> dict:
        """실행 결과가 요청 조건과 Plan 계약을 만족하는지 검사합니다."""
        report = validate_execution(
            state["plan"],
            state["request"],
            state["context"],
            state["draft"],
        )
        budget_options = state["context"].get("budget_options", [])
        if budget_options and not any(
            bool(option["within_budget"]) for option in budget_options
        ):
            cheapest = min(int(option["total"]) for option in budget_options)
            report = ValidationReport(
                passed=False,
                failed_conditions=[
                    (
                        f"최저 예상 비용 {cheapest:,}원이 "
                        f"예산 {state['request'].budget:,}원을 초과합니다."
                    )
                ],
            )
        return {
            "validation": report,
            "path": state["path"] + ["validator"],
        }

    builder = StateGraph(TraceState)
    builder.add_node("planner", planner)
    builder.add_node("executor", executor)
    builder.add_node("validator", validator)
    builder.add_edge(START, "planner")
    builder.add_edge("planner", "executor")
    builder.add_edge("executor", "validator")
    builder.add_edge("validator", END)
    return builder.compile()


@traceable(name="d-travel-workflow", run_type="chain")
def run_workflow(inputs: dict, version: PlanVersion = "fixed") -> dict:
    """평가 가능한 dict 입력으로 Workflow를 실행합니다."""
    request = TripRequest(
        weather=inputs.get("weather", "rain"),
        people=inputs.get("people", 3),
        budget=inputs.get("budget", 200_000),
        children=inputs.get("children", True),
    )
    goal = inputs.get("goal", "조건에 맞는 송파 하루 일정을 만들어줘.")
    result = build_workflow(version).invoke(
        {"goal": goal, "request": request, "path": []},
        config={"run_name": f"d-{version}-workflow"},
    )
    return {
        "goal": goal,
        "request": request.model_dump(),
        "plan_tasks": list(result["plan"].tasks),
        "path": result["path"],
        "draft": result["draft"].model_dump(),
        "validation": result["validation"].model_dump(),
    }


def baseline_target(inputs: dict) -> dict:
    """수정 전 Plan으로 Workflow를 실행하는 LangSmith Target입니다."""
    return run_workflow(
        inputs,
        version="baseline",
        langsmith_extra={
            "metadata": {"workflow_version": "baseline"},
            "tags": ["travel-workflow", "baseline"],
        },
    )


def fixed_target(inputs: dict) -> dict:
    """수정 후 Plan으로 Workflow를 실행하는 LangSmith Target입니다."""
    return run_workflow(
        inputs,
        version="fixed",
        langsmith_extra={
            "metadata": {"workflow_version": "fixed"},
            "tags": ["travel-workflow", "fixed"],
        },
    )


EVALUATION_CASES = [
    {
        "inputs": {
            "goal": "비 오는 날 아이와 갈 송파 하루 일정을 만들어줘.",
            "weather": "rain",
            "people": 3,
            "budget": 200_000,
            "children": True,
        },
        "outputs": {"expected_pass": True},
    },
    {
        "inputs": {
            "goal": "맑은 날 2명이 갈 송파 하루 일정을 만들어줘.",
            "weather": "clear",
            "people": 2,
            "budget": 100_000,
            "children": False,
        },
        "outputs": {"expected_pass": True},
    },
    {
        "inputs": {
            "goal": "비 오는 날 3명이 5만원으로 갈 일정을 만들어줘.",
            "weather": "rain",
            "people": 3,
            "budget": 50_000,
            "children": True,
        },
        "outputs": {
            "expected_pass": False,
            "expected_failure_keyword": "예산 50,000원을 초과",
        },
    },
]


def workflow_evaluator(outputs: dict, reference_outputs: dict) -> dict:
    """PASS/FAIL뿐 아니라 실패 이유가 기대 조건과 같은지도 평가합니다."""
    actual_pass = bool(outputs["validation"]["passed"])
    expected_pass = bool(reference_outputs["expected_pass"])
    selected_place = outputs["draft"]["selected_place"]
    failures = list(outputs["validation"]["failed_conditions"])
    has_missing_task = any(
        "Task의 실행 결과가 없습니다." in failure for failure in failures
    )
    passed = actual_pass == expected_pass
    if expected_pass:
        passed = passed and selected_place is not None and not failures
    else:
        expected_failure = reference_outputs.get("expected_failure_keyword", "")
        passed = passed and any(
            expected_failure in failure for failure in failures
        ) and not has_missing_task
    return {
        "key": "workflow_requirement_match",
        "score": int(passed),
        "comment": (
            f"expected_pass={expected_pass}, actual_pass={actual_pass}, "
            f"selected_place={selected_place}, failures={failures}"
        ),
    }