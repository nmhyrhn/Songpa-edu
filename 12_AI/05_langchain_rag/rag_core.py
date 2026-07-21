# 공통 RAG 검색 함수

from collections import Counter
from pathlib import Path
import math
import re

# 질문의 의도를 구분하는데 도움이 적은 조사와 자주 쓰는 표현
STOPWORDS = {
    "은", "는", "이", "가", "을", "를", "의", "와", "과","도", "로"
    ,"으로", "에","에서", "에게", "한", "하다", "하는", "무엇", "무엇인가",
    "어떤", "왜", "알려줘", "알려주세요", "인가", "있나요", "되나요"
}

def load_documents(data_dir: Path) -> list[dict]:
    """ 폴더의 txt파일을 읽어 RAG 문서 목록으로 변환한다."""
    documents = []

    for path in sorted(data_dir.glob("*.txt")):
        text = path.read_text(encoding="utf-8").strip()
        if not text:
            continue

        documents.append({
            "source": path.name,
            "text": text
        })
    return documents

def split_sentences(text: str) -> list[str]:
    pieces = re.split(r"(?<=[.!?])\s+|\n+", text)
    return [piece.strip() for piece in pieces if piece.strip()]

def split_text(
    text: str,
    chunk_size: int = 220,
    overlap_sentences: int = 1,
) -> list[str]:
    """문장을 묶어 chunk를 만들고 이전 문장 일부를 다음 chunk에 겹친다."""
    if chunk_size <= 0:
        raise ValueError("chunk_size는 1이상어야 합니다.")
    if overlap_sentences < 0:
        raise ValueError("overlap_sentences는 0 이상이어야 합니다.")
    
    sentences = split_sentences(text)
    chunks = []
    current = []

    for sentence in sentences:
        candidate = " ".join(current + [sentence])

        if current and len(candidate) > chunk_size:
            chunks.append(" ".join(current))
            current = current[-overlap_sentences:] if overlap_sentences else []

        current.append(sentence)
    
    if current:
        chunks.append(" ".join(current))
    return chunks

def tokenize(text: str) -> list[str]:
    words = re.findall(r"[A-Za-z0-9]+|[가-힣]+", text.lower())
    return [word for word in words if word not in STOPWORDS]

def vectorize(text: str) -> Counter:
    """각 단어가 몇 번 나왔는지를 벡터로 사용합니다."""
    return Counter(tokenize(text))

def consine_similarity(vector_a: Counter, vector_b: Counter) -> float:
    """두 벡터의 방향이 얼마나 비슷한지 0~1사이 점수로 계산"""
    common_words = set(vector_a) & set(vector_b)
    dot_product = sum(vector_a[word] * vector_b[word] for word in common_words)

    norm_a = math.sqrt(sum(value * value for value in vector_a.values()))
    norm_b = math.sqrt(sum(value * value for value in vector_b.values()))

    if norm_a == 0 or norm_b == 0:
        return 0.0
    
    return dot_product/ (norm_a * norm_b)


def build_index(
    documents: list[dict],
    chunk_size: int = 220,
    overlap_sentences: int = 1
) -> list[dict]:
    """모든 문서를 chunk로 나눈뒤 검색 가능한 벡터를 붙인다."""
    index = []

    for document in documents:
        chunks = split_text(document["text"], chunk_size, overlap_sentences)
        for chunk_number, chunk_text in enumerate(chunks, start=1):
            index.append({
                "source": document["source"],
                "chunk_number": chunk_number,
                "text": chunk_text,
                "vector": vectorize(chunk_text)
            })
    return index

def retrieve(index: list[dict], question: str, top_k: int = 3) -> list[dict]:
    """질문과 유사한 chunk를 점수가 높은 순서대로 반환한다."""
    if top_k <= 0:
        raise ValueError("top_k는 1이상이어야 합니다.")
    
    question_vector = vectorize(question)
    results = []

    for item in index:
        score = consine_similarity(question_vector, item["vector"])
        results.append({"score": score, **item})
    
    results.sort(key=lambda item: item["score"], reverse=True)
    return results[:top_k]

def format_context(results: list[dict]) -> str:
    """검색 결과를 LLM프롬프트에 넣을 Context 문자열로 변환"""
    blocks = []
    for result in results:
        blocks.append(
            f"[출처: {result['source']}/ chunk {result['chunk_number']}]\n"
            f"{result['text']}"
        )
    return "\n\n".join(blocks)