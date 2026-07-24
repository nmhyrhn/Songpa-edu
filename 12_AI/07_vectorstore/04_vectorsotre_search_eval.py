import argparse
from vectorstore_common import build_inmemory_store

EVAL_CASES = [
    ("외부 자료를 찾아 답변에 사용하는 방법은?", "RAG는"),
    ("파일이나 웹 페이지를 읽어오는 구성 요소는?", "Loader는"),
    ("긴 문서를 검색 가능한 작은 조각으로 나누는 이유는", "TextSplitter는"),
    ("벡터와 원문을 함께 보관하는 검색 저장소는?", "VectorStore는"),
    ("긴 대화를 짧게 압축해서 기억하는 방법은?", "Summary memory는"),
    ("모델의 출력 형식과 말투를 원하는 대로 맞추는 방법은?", "Fine-tuning은")
]

def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser()
    parser.add_argument("--top-k", type=int, default=2)
    args = parser.parse_args()
    if args.top_k < 1:
        parser.error("--top-k는 1 이상이어야 합니다.")
    return args

def evaluate_retriever(top_k: int) -> None:
    store, _ = build_inmemory_store()

    # Retriever는 VectorStore위에 검색 정책(k, filter, search type)을 얹는다.
    retriever = store.as_retriever(
        search_type="similarity",
        search_kwargs={"k": top_k}
    )

    passed = 0
    print(f"[Retriever 평가: top_k={top_k}]")
    for question, expected_text in EVAL_CASES:
        documents = retriever.invoke(question)
        matched = any(expected_text in document.page_content for document in documents)
        passed += int(matched)

        print(f"{'PASS' if matched else 'CHECK'} | {question}")
        for rank, document in enumerate(documents, start=1):
            print(
                f" {rank}. {document.id} / topic={document.metadata.get('topic')} /"
                f"{document.page_content}"
            )
    print(f"\n검색 성공: {passed}/{len(EVAL_CASES)}")

    memory_retriever = store.as_retriever(
        search_kwargs={
            "k": 3,
            "filter": lambda document: document.metadata.get("topic") == "memory"
        }
    )
    filtered_docs = memory_retriever.invoke("대화를 기억하는 방법은?")
    filter_passed = bool(filtered_docs) and all(
        document.metadata.get("topic") == "memory" for document in filtered_docs
    )
    print("Metadata filter:", "PASS" if filter_passed else "CHECK")
    print("Fitler 결과 topic:", [doc.metadata.get("topic") for doc in filtered_docs])


def main() -> None:
    args = parse_args()

    print("Retriever의 top-k와 filter를 테스트")
    evaluate_retriever(args.top_k)

if __name__ == "__main__":
    main()