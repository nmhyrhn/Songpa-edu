from typing import Literal

from dotenv import load_dotenv
from langsmith import tracing_context

from workflow_components import(
    baseline_target,
    fixed_target,
    require_langsmith_settings
)

# WORKFLOW_VERSION: Literal["baseline", "fixed"] = "baseline"
WORKFLOW_VERSION: Literal["baseline", "fixed"] = "fixed"

def print_result(result: dict) -> None:
    """현재 선택한 버전의 Plan, 실행 경로와 검증 결과를 출력"""
    print(f"\n[{WORKFLOW_VERSION} 실행 결과]")
    print("Plan: ", result["plan_tasks"])
    print("Path: ", " -> ".join(result["path"]))
    print("선택 장소:", result["draft"]["selected_place"])
    print("Validation:", result["validation"])

def main() -> None:
    load_dotenv()
    require_langsmith_settings()

    inputs = {
        "goal": "비 오는 날 아이와 갈 송파 하루 일정을 만들어줘.",
        "weather": "rain",
        "people": 3,
        "budget": 200_000,
        "children": True
    }
    target = baseline_target if WORKFLOW_VERSION == "baseline" else fixed_target
    with tracing_context(enabled=True):
        result = target(inputs)

    print_result(result)
    passed = bool(result["validation"]["passed"])
    print("\n[판정]", "PASS" if passed else "FAIL")
    print("LangSmith 업로드: YES")

if __name__ == "__main__":
    main()