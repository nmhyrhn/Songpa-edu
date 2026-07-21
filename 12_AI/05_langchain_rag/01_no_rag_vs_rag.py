import argparse
import os
from pathlib import Path

from rag_core import build_index, format_context, load_documents, retrieve

DATA_DIR = Path(__file__).resolve().parent / "data"
print(DATA_DIR)
DEFAULT_QUESTION = "수업 시작 24시간 이내에 취소하면 얼마나 환불되나요?"

def build_prompts(question: str, context: str) -> tuple[str, str]:
    """검색 없는 프롬프트와 검색 근거가 포함된 프롬프트 생성"""
    no_rag_prompt = f"다음 질문에 아는 내용을 사용해 답하세요.\n\n질문: {question}"

    rag_prompt = f"""
    아래 Context에 있는 내용만 사용해 질문에 답하세요.
    근거가 없으면 '문서에서 관련 근거를 찾을 수 없습니다.'라고 답하세요.
    답변 마지막에는 사용한 출처를 [파일명]형식으로 표시하세요.

    [Context]
    {context or "검색된 근거 없음"}

    [Question]
    {question}
    """
    return no_rag_prompt, rag_prompt

def call_openai(no_rag_prompt: str, rag_prompt: str) -> None:
    """같은 모델에 두 프롬프트를 보내 검색 근거의 효과를 비교"""
    from dotenv import load_dotenv
    from langchain_openai import ChatOpenAI

    load_dotenv()
    if not os.getenv("OPENAI_API_KEY"):
        raise ValueError("OPENAI_API_KEY가 없습니다. .env파일을 확인하세요.")
    
    model = ChatOpenAI(
        model="gpt-4o-mini",
        temperature=0.0,
        max_tokens=300
    )

    print("\n" + "=" * 80)
    print("[일반 LLM 답변]")
    print(model.invoke(no_rag_prompt).content)

    print("\n" + "=" * 80)
    print("[RAG 답변]")
    print(model.invoke(rag_prompt).content)

def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--question", default=DEFAULT_QUESTION)
    parser.add_argument(
        "--run",
        action="store_true",
        help="실제 OpenAI API를 호출합니다."
    )

    args = parser.parse_args()

    documents = load_documents(DATA_DIR)
    index = build_index(documents, chunk_size=220, overlap_sentences=1)
    results = retrieve(index, args.question, top_k=3)

    relevant_results = [result for result in results if result["score"] > 0]
    context = format_context(relevant_results)
    no_rag_prompt, rag_prompt = build_prompts(args.question, context)

    print("질문:", args.question)
    print("\n[검색 결과]")
    if not relevant_results:
        print("관련 문서를 찾지 못했습니다.")
    for result in relevant_results:
        print(
            f"- score={result['score']:.3f}/"
            f"{result['score']} #{result['chunk_number']}"
        )

        if args.run:
            call_openai(no_rag_prompt, rag_prompt)
        else:
            print("\n[DRY RUN] OpenAI API를 호출하지 않았습니다.")
            print("아래 RAG프롬프트가 모델에 전달됩니다.")
            print(rag_prompt)
            print("실제 비교 실행: python ./no_rag_vs_rag.py --run")

if __name__ == "__main__":
    main()