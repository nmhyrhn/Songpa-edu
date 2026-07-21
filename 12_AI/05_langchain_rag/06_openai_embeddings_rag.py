# OpenAI Embeddings API로 RAG

import argparse
import math
import os
from pathlib import Path
from dotenv import load_dotenv
load_dotenv()


BASE_DIR = Path(__file__).resolve().parent
DEFAULT_DOCUMENT = BASE_DIR / "data" / "rag_sample_notes.txt"
DEFAULT_QUESTION = "FastAPI에서 Swagger는 왜 필요한가?"

def split_text(text: str, max_chars: int = 180) -> list[str]:
    """긴 문서를 작은 chunk로 나눈다."""

    paragraphs = [paragraph.strip() for paragraph in text.split("\n\n") if paragraph.strip()]
    chunks: list[str] = []
    current = ""

    for paragraph in paragraphs:
        if len(current) + len(paragraph) + 1 <= max_chars:
            current = f"{current}\n{paragraph}".strip()
        else:
            if current:
                chunks.append(current)
            current = paragraph

    if current:
        chunks.append(current)

    return chunks

def cosine_similarity(left: list[float], right: list[float]) -> float:

    dot = sum(a * b for a, b in zip(left, right))
    left_size = math.sqrt(sum(a * a for a in left))
    right_size = math.sqrt(sum(b * b for b in right))
    if left_size == 0 or right_size == 0:
        return 0.0
    return dot / (left_size * right_size)

def create_embeddings(texts: list[str]) -> list[list[float]]:
    """OpenAI Embeddings API로 문장을 vector로 바꾼다."""
    from openai import OpenAI

    client = OpenAI()

    response = client.embeddings.create(
        model = "text-embedding-3-small",
        input=texts,
    )
    return [item.embedding for item in response.data]

def retrieve(question: str, chunks: list[str], top_k: int) -> list[tuple[float, str]]:

    """질문과 가장 가까운 chunk를 찾는다."""
    print("🎉", question)
    print("-" * 80)
    print("🥝", chunks)
    embeddings = create_embeddings([question] + chunks)
    print("*" * 90)
    print(embeddings)
    print("*" * 90)
    question_embedding = embeddings[0]
    chunk_embeddings = embeddings[1:]

    scored_chunks = [
        (cosine_similarity(question_embedding, chunk_embedding), chunk)
        for chunk, chunk_embedding in zip(chunks, chunk_embeddings)
    ]

    return sorted(scored_chunks, reverse=True)[:top_k]


def main() -> None:
    parser = argparse.ArgumentParser(description="OpenAI embedding RAG 실습")
    parser.add_argument("--run", action="store_true", help="실제 OpenAI API를 호출한다.")
    parser.add_argument("--document", default=str(DEFAULT_DOCUMENT), help="검색할 문서")
    parser.add_argument("--question", default=DEFAULT_QUESTION, help="검색 질문")
    parser.add_argument("--top-k", type=int, default=2, help="가져올 chunk개수")
    args = parser.parse_args()

    document_path = Path(args.document)
    if not document_path.exists():
        raise SystemExit(f"문서 파일이 없습니다: {document_path}")
    
    if not os.getenv("OPENAI_API_KEY"):
        raise SystemExit("OPENAI_API_KEY가 없습니다. 환경변수를 설정을 다시한번 확인해보세요.")
    
    chunks = split_text(document_path.read_text(encoding="utf-8"))
    for rank, (score, chunk) in enumerate(retrieve(args.question, chunks, args.top_k), start=1):
        print(f"[{rank}] score={score:.4f}")
        print(chunk)
        print()

if __name__ == "__main__":
    main()