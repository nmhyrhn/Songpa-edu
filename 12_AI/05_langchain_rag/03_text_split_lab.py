from pathlib import Path
from rag_core import load_documents, split_text

DATA_DIR = Path(__file__).resolve().parent / "data"

def print_chunks(text: str, chunk_size: int, overlap_sentences: int) -> None:
    """한 설정의 chunk개수와 내용을 출력"""
    chunks = split_text(
        text,
        chunk_size=chunk_size,
        overlap_sentences=overlap_sentences
    )

    print("\n" + "=" * 80)
    print(
        f"chunk_size={chunk_size}, overlap_sentences={overlap_sentences} "
        f"-> chunk {len(chunks)}개"
    )

    for number, chunk in enumerate(chunks, start=1):
        print(f"\n[chunk {number} / {len(chunk)}자]")
        print(chunk)

def main() -> None:
    documents = load_documents(DATA_DIR)
    course_document = next(
        document for document in documents if document["source"] == "course_guide.txt"
    )

    # 같은 문서를 서로 다른 설정으로 분할해서 경계와 문맥 변화를 확인
    settings = [
        (120, 0),
        (120, 1),
        (260, 1),
        (50,0),
        (50,1)
    ]

    for chunk_size, overlap_sentences in settings:
        print_chunks(course_document["text"], chunk_size, overlap_sentences)

if __name__ == "__main__":
    main()