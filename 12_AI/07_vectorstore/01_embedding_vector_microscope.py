# Embedding vector 내부 흐름 확인
import math

from vectorstore_common import EMBEDDING_MODEL, create_embeddings

DOCUMENTS = [
    "수강 신청을 취소하면 결제 금액을 환불받을 수 있습니다.",
    "강의를 철회하면 지불한 돈을 돌려드립니다.",
    "오늘 점심 메뉴는 김치찌개입니다."
]
QUESTION = "수업을 취소하면 돈을 돌려받을 수 있나요?"

def cosine_similarity(vector_a: list[float], vector_b: list[float]) -> float:
    """Embedding Vector 두개의 방향 유사도를 계산"""
    dot = sum(a * b for a, b in zip(vector_a, vector_b))
    norm_a = math.sqrt(sum(value * value for value in vector_a))
    norm_b = math.sqrt(sum(value * value for value in vector_b))
    return dot / (norm_a * norm_b)

def main() -> None:
    print("[오늘의 문제]")
    print("표현은 달라도 의미가 비슷한 문장을 컴퓨터는 어떻게 비교할까요?\n")

    embeddings = create_embeddings()

    # 문서와 질문을 반드시 같은 Embedding 모델로 변환해야 비교할 수 있다.
    document_vectors = embeddings.embed_documents(DOCUMENTS)
    question_vector = embeddings.embed_query(QUESTION)

    print("Embedding 모델 :", EMBEDDING_MODEL)
    print("문서 Vector 개수: ", len(document_vectors))
    print("Vector 차원: ", len(question_vector))
    print("질문 Vector 앞 5개 값", [round(value, 6) for value in question_vector[:5]])

    print("\n[질문과 각 문서의 Cosine Similarity]")
    scored_documents = []
    for text, vector in zip(DOCUMENTS, document_vectors):
        score = cosine_similarity(question_vector, vector)
        scored_documents.append((score, text))

    for rank, (score, text) in enumerate(
        sorted(scored_documents, reverse=True), start=1
    ):
        print(f"{rank}, similarity={score:.3f} / {text}")

if __name__ == "__main__":
    main()