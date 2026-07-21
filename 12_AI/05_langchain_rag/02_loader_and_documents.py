from pathlib import Path
from rag_core import load_documents

BASE_DIR = Path(__file__).resolve().parent
DATA_DIR = BASE_DIR / "data"

print("*" * 80)
print(DATA_DIR)
print("*" * 80)

def main() -> None:
  documents = load_documents(DATA_DIR)

  print(f"데이터 폴더: {DATA_DIR}")
  print(f"로드한 문서 수: {len(documents)}")

  for number, document in enumerate(documents, start=1):
    preview = document["text"][:100].replace("\n", " ")
    print("\n" + "=" * 80)
    print(f"문서 {number}")
    print(f"metadata.source: {document['source']}")
    print(f"글자수 : {len(document['text'])}")
    print(f"미리보기: {preview}...")


if __name__ == "__main__":
  main()