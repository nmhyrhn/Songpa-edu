# OpenAI Embeddings API로 RAG

import argparse
import math
import os
from pathlib import Path

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