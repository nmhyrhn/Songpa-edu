import argparse

from dotenv import load_dotenv
from langsmith import tracing_context

from workflow_components import (
    baseline_target,
    require_langsmith_settings,
    run_workflow
)

def main() -> None:
    """실패 버전을 실행하고 실제 LangSmith에 Trace를 기록"""
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--runtime-error",
        action="store_true",
        help="compose Task 누락으로 Executor Run의 예외를 재현한다."
    )
    args = parser.parse_args()
    load_dotenv()

    require_langsmith_settings()

    inputs = {
        "goal": "비 오는 날 아이와 갈 송파 하루 일정을 만들어줘.",
        "weather": "rain",
        "people": 3,
        "budget": 200_000,
        "children": True
    }
    if args.runtime_error:
        try:
            with tracing_context(enabled=True):
                run_workflow(
                    inputs,
                    version="runtime_error",
                    langsmith_extra={
                        "metadata": {"workflow_version": "runtime_error"},
                        "tags": ["travel-workflow", "runtime-error"]
                    }
                )
        except ValueError as exc:
            print("[Error]")
            print(type(exc).__name__ + " : ", exc)
        raise RuntimeError("runtime_error Plan이 예상한 ValueError를 만들지 않았습니다.")

    with tracing_context(enabled=True):
        result = baseline_target(inputs)

    print("[수정 전 실행]")
    print("Plan:", result["plan_tasks"])
    print("Path:", " -> ".join(result["path"]))
    print("선택 장소:", result["draft"]["selected_place"])
    print("Validation:", result["validation"])

if __name__ == "__main__":
    main()