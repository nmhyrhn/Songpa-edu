import argparse

from dotenv import load_dotenv
from langsmith import Client

from workflow_components import (
    EVALUATION_CASES,
    baseline_target,
    require_langsmith_settings,
    workflow_evaluator
)

DATASET_NAME = "workflow-v1"
BASELINE_EXPERMIMENT_PREFIX = "workflow-baseline"

def show_evaluation_cases() -> None:
    """Workflow를 실행하기 전에 평가 입력과 기대결과를 보여주기"""
    print("[평가문자를 확인]")
    for number, case in enumerate(EVALUATION_CASES, start=1):
        print("\n사례 {number}")
        print("입력: ", case["inputs"])
        print("기대 결과:", case["outputs"])
        print("-" * 80)
    """
    Evaluation가 확인하는 기준
    1. Validator의 실제 PASS/FAIL이 기대 결과와 같은가?
    2. PASS 사례는 조건에 맞는 장소를 선택했는가?
    3. FAIL 사례는 기대한 실패 이유를 반환했는가?
    4. 이전 Task 누락 때문에 우연히 FAIL한 것은 아닌가? 
    """
def ensure_dataset(client: Client) -> None:
    """Dataset이 없을 때 생성하고 생성 여부를 출력"""
    if client.has_dataset(dataset_name=DATASET_NAME):
        print(f"기존 Dataset을 사용합니다: {DATASET_NAME}")
        return
    client.create_dataset(DATASET_NAME, description="여행 workflow 평가 테스트용")
    client.create_examples(dataset_name=DATASET_NAME, examples=EVALUATION_CASES)
    print(f"Dataset을 생성했습니다: {DATASET_NAME}")

def baseline_experiment_names(client: Client) -> list[str]:
    """Dataset에 이미 기록된 baseline Experiment 이름을 조회"""
    return [
        project.name
        for project in client.list_projects(
            name_contains=BASELINE_EXPERMIMENT_PREFIX,
            reference_dataset_name=DATASET_NAME,
            limit=10
        )
    ]
    


def main() -> None:
    """LangSmith Dataset 생성과 baseline 평가 중에 하나를 실행"""
    parser = argparse.ArgumentParser()
    actions = parser.add_mutually_exclusive_group(required=True)
    actions.add_argument(
        "--create-dataset",
        action="store_true",
        help="평가 사례만 LangSmith Dataset으로 생성한다."
    )
    actions.add_argument(
        "--evaluate-baseline",
        action="store_true",
        help="기존 Dataset으로 수정 전 baseline만 평가한다."
    )
    args = parser.parse_args()
    load_dotenv()

    show_evaluation_cases()
    require_langsmith_settings()
    client = Client()  # LangSmith 서버와 통신할 클라이언트 객체를 생성
    if args.create_dataset:
        ensure_dataset(client)
        print("\n[LangSmith에서 Dataset 확인]")
        print(f"Datasets & Experiments -> {DATASET_NAME} -> Examples")
        return

    if not client.has_dataset(dataset_name=DATASET_NAME):
        raise SystemExit(
            f"Dataset '{DATASET_NAME}'이 없습니다. "
            "먼저 --create-dataset을 실행하세요."
        )
    existing = baseline_experiment_names(client)    
    if existing:
        print("이미 baseline Experiment가 있습니다.")
        for name in existing:
            print("-", name)
        return

    client.evaluate(
        baseline_target,
        data=DATASET_NAME,
        evaluators=[workflow_evaluator],
        experiment_prefix=BASELINE_EXPERMIMENT_PREFIX,
        max_concurrency=1,
        metadata={"version": "baseline", "lesson": "test-evaluation"}
    )

if __name__ == "__main__":
    main()