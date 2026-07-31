import argparse
import os

from dotenv import load_dotenv
from langsmith import tracing_context

from workflow_components import(
    fixed_target,
    require_langsmith_settings,
    run_workflow
)

def main() -> None:
    """Workflow를 실행하고 실제 LangSmith에 Trace를 기록"""
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--llm",
        action="store_true",
        help="고정 Plan 대신에 실제 OpenAI Planner를 사용"
    )
    args = parser.parse_args()
    load_dotenv()

    require_langsmith_settings()
    if args.llm and not os.getenv("OPENAI_API_KEY"):
        raise SystemExit(".env에 OPENAI_API_KEY를 설정하세요.")

    inputs = {
        "goal": "비 오는 날 아이와 갈 송파 하루 일정을 만들어줘.",
        "weather": "rain",
        "people": 3,
        "budget": 200_000,
        "children": True
    }

    with tracing_context(enabled=True):
        result = (
            run_workflow(
                inputs,
                version="llm",
                langsmith_extra={
                    "metadata": {"workflow_version": "llm"},
                    "tags": ["travel-workflow", "llm"]
                }
            )
            if args.llm
            else fixed_target(inputs)
        )

    print("[실행 결과]")
    print("Plan:", result["plan_tasks"])
    print("Path:", " -> ".join(result["path"]))
    print("Draft:", result["draft"])
    print("Validation:", result["validation"])

if __name__ == "__main__":
    main()