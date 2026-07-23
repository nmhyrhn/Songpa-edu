# Vector 스토어를 왜 사용하는가?
import math

from langchain_core.documents import Document
from langchain_core.vectorstores import InMemoryVectorStore

from vectorstore_common import create_embeddings, format_results, cosine_similarity

DOCUMENTS = [
    Document(
        id="policy-refund",
        page_content="수업 시작 24시간 전까지 취소하면 전액 환불됩니다.",
        metadata={"source": "center_policy.txt", "topic": "policy"}
    ),
    Document(
        id="policy-hours",
        page_content="토요일 실습실은 오전 10시부터 오후 5시까지 운영합니다.",
        metadata={"source": "center_policy.txt", "topic": "policy"}
    ),
    Document(
        id="lesson-rag",
        page_content="RAG는 외부 문서를 검색해 LLM Context에 넣는 방식이다.",
        metadata={"source": "course_guid.txt", "topic": "lesson"}
    ),
]
QUESTION = "강의를 취소하면 결제한 돈을 돌려받을 수 있나요?"

def manual_search(question: str) -> list[tuple[Document, float]]:
    """VectorStore없이 개발자가 직접 책임져야 하는 검색 흐름 확인"""
    embeddings = create_embeddings()
    document_vectors = embeddings.embed_documents(
        [document.page_content for document in DOCUMENTS]
    )
    question_vector = embeddings.embed_query(question)

    # 같은 index의 Document와 Vector가 어긋나지 않도록 직접 관리
    results = [
        (document, cosine_similarity(question_vector, vector))
        for document, vector in zip(DOCUMENTS, document_vectors)
    ]
    return sorted(results, key=lambda item: item[1], reverse=True)[:2]

def vectorstore_search(question: str) -> list[tuple[Document, float]]:
    """실제 LangChain VectorStore가 변환·저장·검색을 묶어 처리"""
    store = InMemoryVectorStore(create_embeddings())
    store.add_documents(DOCUMENTS, ids=[str(document.id) for document in DOCUMENTS])
    return store.similarity_search_with_score(question, k=2)

def main() -> None:
    print("Embedding Vector만 있으면 되는데 왜 VectorStore까지 사용할까")
    print("같은 질문을 수동 검색과 실제 VectorStore검색으로 비교")

    print("질문:", QUESTION)
    print("\n[1. VectorStore 없이 직접 검색]")
    print(format_results(manual_search(QUESTION)))
    print("개발자 책임 : Vector 생성, 원문연결, 전체 비교, 정렬, 상위 결과 선택")

    print("\n[2. LangChain InMemoryVectorStore 검색]")
    print(format_results(vectorstore_search(QUESTION)))
    print("VectorStore 책임: Embedding 호출, Vector와 Document 연결, 유사도 검색")

if __name__ == "__main__":
    main()