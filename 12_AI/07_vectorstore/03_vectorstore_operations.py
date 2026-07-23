# LangChain VectorStore 작업 API 

import argparse

from langchain_core.documents import Document
from vectorstore_common import build_inmemory_store, format_results, load_documents

def parse_args() -> argparse.Namespace:
    parser = argparse.ArgumentParser(
        description="실제 VectorStore의 검색·filter·삭제 결과를 비교"
    )
    parser.add_argument(
        "--question",
        default="VectorStore는 왜 사용하나요?",
        help="Embedding으로 변환해 검색할 질문"
    )
    parser.add_argument("--top-k", type=int, default=3, help="반환할 상위 결과 개수")
    parser.add_argument(
        "--topic",
        choices=("retrieval", "memory", "course", "llm"),
        help="해당 metadata topic의 Document만 검색"
    )
    parser.add_argument(
        "--delete-first",
        action="store_true",
        help="첫 검색 결과의 ID를 삭제한 뒤 동일 질문으로 재검색"
    )
    args = parser.parse_args()
    if args.top_k < 1:
        parser.error("--top-k는 1 이상이어야 합니다.")
    return args

def make_filter(topic: str | None):
    """InMemoryVectorStore가 요구하는 Document filter 함수를 생성"""
    if topic is None:
        return None
    return lambda document: document.metadata.get("topic") == topic

def search(store, question: str, top_k: int, topic: str | None):
    return store.similarity_search_with_score(
        question,
        k=top_k,
        filter=make_filter(topic)
    )

def print_document_example(document: Document) -> None:
    print("Document type:", type(document).__name__)
    print("id:", document.id)
    print("page_content:", document.page_content)
    print("metadata:", document.metadata)

def main() -> None:
    args = parse_args()
    documents = load_documents()
    store, stored_ids = build_inmemory_store(documents)

    print("🍋‍🟩" * 80)
    print("[1. 실제 LangChain Document 입력]")
    print_document_example(documents[0])
    print("🍉" * 80)
    print("\n[2. add_documents 결과]")
    print("저장 개수:", len(stored_ids))
    print("앞의 ID 3개:", stored_ids[:3])

    print("=" * 80)
    print("\n[3. similarity_search_with_score]")
    print("질문:", args.question)
    print("top_k:", args.top_k)
    print("metadata filter:", args.topic or "사용 안함")
    results = search(store, args.question, args.top_k, args.topic)
    print(format_results(results))

    if args.delete_first and results:
        deleted_id = str(results[0][0].id)
        store.delete(ids=[deleted_id])
        print(f"\n[4. delete 후 재검색: {deleted_id} 삭제]")
        print(format_results(search(store, args.question, args.top_k, args.topic)))


if __name__ == "__main__":
    main()

