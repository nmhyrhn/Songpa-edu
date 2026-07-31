import argparse
import os 

from dotenv import load_dotenv
from langsmith import Client

from workflow_components import (
    LANGSMITH_REQUIRED,
    langsmith_setting_errors
)

def masked(value: str | None) -> str:
    """API Key원문을 노출하지 않고 설정 여부만 확인할 문자열 생성"""
    if not value:
        return "MISSING"
    if len(value) < 8:
        return "SET"
    return value[:4] + "..." + value[-4:]

def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--run"
                        , action="store_true"
                        , help="LangSmith 서버 연결을 확인합니다.")
    args = parser.parse_args()
    load_dotenv()
    print("[환경 변수]")
    for name in LANGSMITH_REQUIRED:
        value = os.getenv(name)
        display = value if name != "LANGSMITH_API_KEY" else masked(value)
        print(f"{name}: {display or 'MISSING'}")

    errors = langsmith_setting_errors()
    if errors:
        print("\n설정 문제:")
        for error in errors:
            print("-", error)
        print(".env 값을 확인하세요.")
        return

    if args.run:
        try:
            Client().info
        except Exception as e:
            raise SystemExit(
                f"LangSmith API 연결 실패: {type(e).__name__}: {e}"
            ) from e
        else:
            print("LangSmith API 연결: OK")
    else:
        print("--run을 붙이면 실제 LangSmith API 연결도 확인합니다.")    

if __name__ == "__main__":
    main()