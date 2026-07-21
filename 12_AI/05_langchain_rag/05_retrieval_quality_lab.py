from pathlib import Path

from rag_core import build_index, load_documents, retrieve

DATA_DIR = Path(__file__).resolve().parent / "data"

TEST_CASES = [
    ("수강 취소 환불 기준은 무엇인가요?", "center_policy.txt"),
    ("토요일 실습실 운영 시간은 언제인가요?", "center_policy.txt"),
    ("Text Splitter와 chunk overlap은 왜 필요한가요?", "course_guide.txt"),
    ("VectorStore는 어떤 역할을 하나요?", "course_guide.txt"),
    ("OpenAI API 키는 어디에 저장하나요?", "lab_manual.txt"),
    ("와이파이 연결 문제는 어떻게 해결하나요?", "lab_manual.txt"),
    ("강의를 포기하면 낸 돈을 돌려받는 조건은?", "center_policy.txt"),
    ("무선 인터넷이 먹통이면 어떤 조치를 하나요?", "lab_manual.txt")
]

SETTINGS = [
    {"chunk_size": 100, "overlap": 0, "top_k":1},
    {"chunk_size": 180, "overlap": 1, "top_k":1},
    {"chunk_size": 260, "overlap": 1, "top_k":2},
]


def evaluate(documents: list[dict], setting: dict) -> None:
    """한 검색 설정으로 모든 질문을 실행하여 Hit@k를 계산"""
    index = build_index(
        documents,
        chunk_size=setting["chunk_size"],
        overlap_sentences=setting["overlap"]
    )
    hit_count = 0

    print("\n" + "=" * 80)
    print("설정: ", setting, "/ chunk 수: ", len(index))

    for question, expected_source in TEST_CASES:
        results = retrieve(index, question, top_k=setting["top_k"])
        # 유사도 0인 결과는 우연히 목록에 들어왔을 뿐 검색 성공으로 보지 않는다.
        found_sources = {
            result["source"] for result in results if result["score"] > 0
        }

        is_hit = expected_source in found_sources
        hit_count += int(is_hit)

        top_result = results[0]
        mark = "성공" if is_hit else "실패"
        print(
            f"[{mark}] {question}\n"
            f"            기대={expected_source}, 검색={top_result['source']}, "
            f"score={top_result['score']:.3f}"
        )
    
    hit_rate = hit_count / len(TEST_CASES)
    print(f"Hit@{setting['top_k']}: {hit_count}/{len(TEST_CASES)} = {hit_rate:.0%}")

def main() -> None:
    documents = load_documents(DATA_DIR)
    print(len(documents))
    for setting in SETTINGS:
        evaluate(documents, setting)

if __name__ == "__main__":
    main()