from typing import Any, NotRequired, TypedDict

from travel_domain import (
    ItineraryDraft,
    Plan,
    TripRequest,
    ValidationReport,
    analyze_weather,
    calculate_budget_options,
    compose_draft,
    search_destinations,
    validate_draft,
)


Context = dict[str, Any]


class LessonState(TypedDict):
    """Executor와 Validator가 공유하는 수업용 State입니다."""

    request: TripRequest
    plan: Plan
    path: list[str]
    context: NotRequired[Context]
    draft: NotRequired[ItineraryDraft]
    validation: NotRequired[ValidationReport]


def execute_requirements(request: TripRequest, context: Context) -> None:
    """사용자 요구사항을 이후 Task가 읽을 Context에 저장합니다."""
    context["requirements"] = request.model_dump()


def execute_weather(request: TripRequest, context: Context) -> None:
    """날씨를 실내외 장소 선택 정책으로 변환합니다."""
    context["weather"] = analyze_weather(request)


def execute_destination(request: TripRequest, context: Context) -> None:
    """날씨와 아이 동반 조건으로 장소 후보를 검색합니다."""
    context["destinations"] = search_destinations(request)


def execute_budget(request: TripRequest, context: Context) -> None:
    """검색된 장소별 예상 비용과 예산 충족 여부를 계산합니다."""
    places = context.get("destinations", [])
    context["budget_options"] = calculate_budget_options(request, places)


def execute_compose(request: TripRequest, context: Context) -> None:
    """장소와 비용 결과로 검증 가능한 일정 Draft를 만듭니다."""
    places = context.get("destinations", [])
    options = context.get("budget_options", [])
    context["draft"] = compose_draft(request, places, options)


def validate_execution(
    plan: Plan,
    request: TripRequest,
    context: Context,
    draft: ItineraryDraft,
) -> ValidationReport:
    """Plan의 Task 결과와 전체 일정 조건을 검사합니다."""
    result_keys = {
        "requirements": "requirements",
        "weather": "weather",
        "destination": "destinations",
        "budget": "budget_options",
    }
    failures = [
        f"{task} Task의 실행 결과가 없습니다."
        for task, key in result_keys.items()
        if task in plan.tasks and not context.get(key)
    ]
    if "compose" in plan.tasks:
        failures.extend(validate_draft(request, draft).failed_conditions)

    return ValidationReport(
        passed=not failures,
        failed_conditions=failures,
    )
