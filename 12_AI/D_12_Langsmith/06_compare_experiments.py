from dotenv import load_dotenv
from langsmith import Client

from workflow_components import (
    fixed_target,
    require_langsmith_settings,
    workflow_evaluator
)

DATASET_NAME = "workflow-v1"
BASELINE_EXPERIMENT_PREFIX="workflow-baseline"
FIXED_EXPERIMENT_PREFIX="workflow-fixed"

def experiment_names(client: Client, name_contains: str) -> list[str]:
    """현재 Dataset에서 이름이 일치하는 Experiment를 조회"""
    return [
        project.name
        for project in client.list_projects(
            name_contains=name_contains,
            reference_dataset_name=DATASET_NAME,
            limit=10
        )
    ]

def main() -> None:
    load_dotenv()
    print(f"Dataset: {DATASET_NAME}")
    print(f"baseline Experiment: {BASELINE_EXPERIMENT_PREFIX}-*")

    require_langsmith_settings()
    client = Client()
    if not client.has_dataset(dataset_name=DATASET_NAME):
        raise SystemExit(
            f"Dataset '{DATASET_NAME}'이 없습니다."
        )

    baselines = experiment_names(client, BASELINE_EXPERIMENT_PREFIX)
    if not baselines:
        raise SystemExit(
            "baseline Experiment가 없습니다."
        )

    fixed_experiments = experiment_names(client, FIXED_EXPERIMENT_PREFIX)
    if fixed_experiments:
        print("이미 fixed Experiment가 있습니다.")
        for name in fixed_experiments:
            print("-", name)
    else:
        client.evaluate(
            fixed_target,
            data=DATASET_NAME,
            evaluators=[workflow_evaluator],
            experiment_prefix=FIXED_EXPERIMENT_PREFIX,
            max_concurrency=1,
            metadata={"version": "fixed", "lesson": "fix-test-evalution"}
        )
        print("LangSmith에 fixed Experiment를 기록했습니다.")

if __name__ == "__main__":
    main()