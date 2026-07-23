import os
import math
from pathlib import Path

from dotenv import load_dotenv
from langchain_core.documents import Document
from langchain_core.vectorstores import InMemoryVectorStore
from langchain_openai import OpenAIEmbeddings


BASE_DIR = Path(__file__).resolve().parent
DATA_PATH = BASE_DIR / "data" / "chatbot_knowledge_base.txt"
EMBEDDING_MODEL = os.getenv("OPENAI_EMBEDDING_MODEL", "text-embedding-3-small")


def load_environment() -> None:
    """.env 파일을 읽습니다."""
    for env_path in (BASE_DIR.parent / ".env", BASE_DIR / ".env"):
        if env_path.exists():
            load_dotenv(env_path)
            break

    if not os.getenv("OPENAI_API_KEY"):
        raise SystemExit(
            "OPENAI_API_KEY가 없습니다. 프로젝트 루트의 .env 파일을 확인하세요."
        )


def create_embeddings() -> OpenAIEmbeddings:
    """동일한 실제 OpenAI Embedding 모델을 사용합니다."""
    load_environment()
    return OpenAIEmbeddings(model=EMBEDDING_MODEL)


def topic_for_text(text: str) -> str:
    """metadata filter를 위해 문장의 topic을 지정합니다."""
    lowered = text.lower()
    if any(word in lowered for word in ("memory", "기억", "대화")):
        return "memory"
    if any(
        word in lowered
        for word in ("rag", "loader", "splitter", "embedding", "vectorstore", "retrieval")
    ):
        return "retrieval"
    if "day9" in lowered or "과제" in lowered:
        return "course"
    return "llm"


def load_documents(path: Path = DATA_PATH) -> list[Document]:
    """텍스트 파일의 각 문장을 실제 LangChain Document로 변환합니다."""
    documents = []
    for line_number, line in enumerate(
        path.read_text(encoding="utf-8").splitlines(), start=1
    ):
        text = line.strip()
        if not text:
            continue

        documents.append(
            Document(
                id=f"class-line-{line_number:03d}",
                page_content=text,
                metadata={
                    "source": path.name,
                    "line": line_number,
                    "topic": topic_for_text(text),
                },
            )
        )
    return documents


def build_inmemory_store(
    documents: list[Document] | None = None,
) -> tuple[InMemoryVectorStore, list[str]]:
    """실제 OpenAI Embedding을 사용하는 LangChain VectorStore를 만듭니다."""
    docs = documents or load_documents()
    store = InMemoryVectorStore(create_embeddings())
    ids = store.add_documents(docs, ids=[str(doc.id) for doc in docs])
    return store, ids


def format_results(results: list[tuple[Document, float]]) -> str:
    """InMemoryVectorStore 검색 결과를 비교하기 쉽게 출력합니다."""
    if not results:
        return "검색 결과 없음"

    lines = []
    for rank, (document, score) in enumerate(results, start=1):
        source = document.metadata.get("source")
        line = document.metadata.get("line")
        source_label = f"{source}:{line}" if line is not None else str(source)
        lines.append(
            f"{rank}. similarity={score:.3f} / id={document.id} / "
            f"topic={document.metadata.get('topic')} / "
            f"source={source_label}\n"
            f"   {document.page_content}"
        )
    return "\n".join(lines)

def cosine_similarity(vector_a: list[float], vector_b: list[float]) -> float:
    """Embedding Vector 두개의 방향 유사도를 계산"""
    dot = sum(a * b for a, b in zip(vector_a, vector_b))
    norm_a = math.sqrt(sum(value * value for value in vector_a))
    norm_b = math.sqrt(sum(value * value for value in vector_b))
    return dot / (norm_a * norm_b)