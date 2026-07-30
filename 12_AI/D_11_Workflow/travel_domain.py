from typing import Literal

from pydantic import BaseModel, Field, field_validator

TaskName = Literal[
    "requirements",
    "weather",
    "destination",
    "budget",
    "compose",
]

TASK_DESCRIPTIONS: dict[TaskName, str] = {
    "requirements": "인원, 날씨, 아이 동반, 예산 조건 확인",
    "weather": "날씨를 실내외 장소 선택 정책으로 변환",
    "destination": "조건에 맞는 장소 후보 검색",
    "budget": "후보별 인원 기준 예상 비용 계산",
    "compose": "조사와 계산 결과로 일정 초안 작성",
}


class Plan(BaseModel):
    """Planner와 Executor가 공유하는 실행 계약입니다."""

    goal: str = Field(min_length=5)
    tasks: list[TaskName] = Field(min_length=1, max_length=5)
    success_criteria: list[str] = Field(min_length=1)

    @field_validator("tasks")
    @classmethod
    def require_unique_tasks(cls, tasks: list[TaskName]) -> list[TaskName]:
        """같은 Task가 반복되어 불필요한 실행을 만들지 않게 검사합니다."""
        if len(tasks) != len(set(tasks)):
            raise ValueError("Plan에는 같은 Task를 중복할 수 없습니다.")
        return tasks


class TripRequest(BaseModel):
    """여행 조사 Node들이 공통으로 읽는 사용자 요구사항입니다."""

    weather: Literal["rain", "clear"] = "rain"
    people: int = Field(default=3, ge=1, le=10)
    budget: int = Field(default=200_000, ge=10_000)
    children: bool = True


class Place(BaseModel):
    """필터링과 비용 계산에 필요한 최소 장소 데이터입니다."""

    name: str
    indoor: bool
    child_friendly: bool
    admission_per_person: int


class ItineraryDraft(BaseModel):
    """Executor가 조사 결과로 만든 검증 가능한 일정 초안입니다."""

    selected_place: str | None
    indoor: bool
    child_friendly: bool
    estimated_cost: int = Field(ge=0)
    summary: str


class ValidationReport(BaseModel):
    """Validator가 완료 기준과 Draft를 비교한 결과입니다."""

    passed: bool
    failed_conditions: list[str]


PLACES = [
    Place(
        name="롯데월드 아쿠아리움",
        indoor=True,
        child_friendly=True,
        admission_per_person=35_000,
    ),
    Place(
        name="서울스카이",
        indoor=True,
        child_friendly=True,
        admission_per_person=31_000,
    ),
    Place(
        name="서울책보고",
        indoor=True,
        child_friendly=True,
        admission_per_person=0,
    ),
    Place(
        name="석촌호수",
        indoor=False,
        child_friendly=True,
        admission_per_person=0,
    ),
    Place(
        name="올림픽공원",
        indoor=False,
        child_friendly=True,
        admission_per_person=0,
    ),
]


def search_destinations(request: TripRequest) -> list[Place]:
    """날씨와 아이 동반 조건으로 실제 장소 목록을 필터링합니다.

    비가 오면 실내 장소만 남기고, children이 True면 아이 친화 장소만
    남깁니다. 따라서 요청값을 바꾸면 결과 개수와 장소가 달라집니다.
    """
    candidates = PLACES
    if request.weather == "rain":
        candidates = [place for place in candidates if place.indoor]
    if request.children:
        candidates = [place for place in candidates if place.child_friendly]
    return candidates


def analyze_weather(request: TripRequest) -> dict[str, str | bool]:
    """날씨 입력을 일정 작성에서 사용할 실내외 정책으로 변환합니다."""
    if request.weather == "rain":
        return {
            "condition": "비",
            "indoor_required": True,
            "advice": "실내 장소를 우선하고 야외 장소는 제외합니다.",
        }
    return {
        "condition": "맑음",
        "indoor_required": False,
        "advice": "실내와 야외 장소를 모두 후보로 사용할 수 있습니다.",
    }


def calculate_budget_options(
    request: TripRequest,
    places: list[Place],
) -> list[dict[str, int | str | bool]]:
    """후보 장소마다 인원수 기반 예상 총비용과 예산 충족 여부를 계산합니다.

    총비용은 입장료와 수업용 식비·교통비 추정치의 합계입니다. 실제 가격으로
    오해하지 않도록 각 결과에 estimate라는 성격을 유지합니다.
    """
    meal = 20_000 * request.people
    transport = 5_000 * request.people
    options = []
    for place in places:
        admission = place.admission_per_person * request.people
        total = admission + meal + transport
        options.append(
            {
                "place": place.name,
                "admission": admission,
                "meal": meal,
                "transport": transport,
                "total": total,
                "within_budget": total <= request.budget,
            }
        )
    return options


def build_evidence(
    request: TripRequest,
    places: list[Place],
    weather: dict[str, str | bool],
    budget_options: list[dict[str, int | str | bool]],
) -> list[str]:
    """구조화된 조사 결과를 Writer Prompt에 넣을 근거 문장으로 바꿉니다."""
    evidence = [
        (
            f"요청 조건: {request.people}명, 예산 {request.budget:,}원, "
            f"아이 동반 {request.children}"
        ),
        f"날씨 판단: {weather['condition']} / {weather['advice']}",
    ]
    place_by_name = {place.name: place for place in places}
    for option in budget_options:
        place = place_by_name[str(option["place"])]
        evidence.append(
            f"{place.name}: {'실내' if place.indoor else '야외'}, "
            f"아이 친화 {place.child_friendly}, 예상 총비용 {option['total']:,}원, "
            f"예산 충족 {option['within_budget']}"
        )
    return evidence


def compose_draft(
    request: TripRequest,
    places: list[Place],
    budget_options: list[dict[str, int | str | bool]],
) -> ItineraryDraft:
    """예산을 충족하는 후보 중 가장 저렴한 장소로 일정 초안을 만듭니다.

    만족하는 후보가 없으면 장소를 임의로 고르지 않고 selected_place=None으로
    남깁니다. 이 값은 Validator가 실패 원인을 명확하게 판단하게 합니다.
    """
    affordable = [
        option for option in budget_options if bool(option["within_budget"])
    ]
    if not affordable:
        return ItineraryDraft(
            selected_place=None,
            indoor=False,
            child_friendly=False,
            estimated_cost=0,
            summary="현재 조건과 예산을 모두 만족하는 장소를 선택하지 못했습니다.",
        )

    selected = min(affordable, key=lambda option: int(option["total"]))
    place = next(place for place in places if place.name == selected["place"])
    return ItineraryDraft(
        selected_place=place.name,
        indoor=place.indoor,
        child_friendly=place.child_friendly,
        estimated_cost=int(selected["total"]),
        summary=(
            f"선택 장소는 {place.name}입니다. "
            f"{request.people}명 예상 비용은 {int(selected['total']):,}원입니다."
        ),
    )


def validate_draft(
    request: TripRequest,
    draft: ItineraryDraft,
) -> ValidationReport:
    """Draft를 장소 존재, 날씨, 아이 동반, 예산 기준으로 검증합니다."""
    failures = []
    if draft.selected_place is None:
        failures.append("조건을 만족하는 장소가 선택되지 않았습니다.")
    if request.weather == "rain" and not draft.indoor:
        failures.append("비 오는 날에는 실내 장소가 필요합니다.")
    if request.children and not draft.child_friendly:
        failures.append("아이 동반 가능 장소가 필요합니다.")
    if draft.estimated_cost > request.budget:
        failures.append(
            f"예상 비용 {draft.estimated_cost:,}원이 예산 {request.budget:,}원을 초과합니다."
        )
    return ValidationReport(
        passed=not failures,
        failed_conditions=failures,
    )