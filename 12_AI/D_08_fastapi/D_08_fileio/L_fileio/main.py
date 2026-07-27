from pathlib import Path

BASE_DIR = Path(__file__).resolve().parent
DATA_DIR = BASE_DIR / "data"

def safe_data_path(filename: str) -> Path:
    candidate = (DATA_DIR / filename).resolve()
    print("--->>",candidate)
    if DATA_DIR.resolve() not in candidate.parents:
        raise ValueError("data폴더 밖의 경로는 사용할 수 없습니다.")
    return candidate

def read_text(filename: str) -> str:
    return safe_data_path(filename).read_text(encoding="utf-8")

def write_text(filename: str, content: str) -> Path:
    DATA_DIR.mkdir(parents=True, exist_ok=True)
    path = safe_data_path(filename)
    path.write_text(content, encoding="utf-8")
    return path

def copy_binary(source_name:str, target_name: str) -> Path:
    source = safe_data_path(source_name)
    target = safe_data_path(target_name)
    target.write_bytes(source.read_bytes())
    return target

def main() -> None:
    print("🍩[기준 폴더]",BASE_DIR)
    print("🍩sample.txt 내용:")
    print(read_text("sample.txt"))

    output = write_text("class_note.txt", "FileIO는 API밖의 영속 저장을 담당")
    print("🍩 텍스트 저장", output)

    copied = copy_binary("sample.txt", "sample_copy.bin")
    print("🍩 binary 복사:", copied, copied.stat().st.size, "bytes")

if __name__ == "__main__":
    main()