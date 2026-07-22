import argparse
import os

from memory_core import ChatMemory, format_recent_messages

DEMO_MESSAGES = [
    "내 수준은 Python 초보자야",
    "관심사는 RAG 챗봇이야",
    "Memory Window를 쉽게 설명해줘",
    "내 수준에 맞게 다시 설명해줘"
]

def build_prompt(memory: ChatMemory, user_message: str) -> str:
    """Profile, Summary, 최근대화, 현재 질문을 하나의 프롬프트로 생성"""
    context = memory.context()
    return f"""
    너는 AI 수업 도우미다.
    사용자의 학습 수준과 선호를 반영하되, 기억에 없는 정보는 추측하지 마라.

    [Profile]
    {context['profile'] or '저장된 profile 없음'}

    [Summary]
    {context['summary'] or '저장된 summary 없음'}

    [Recent Messages]
    {format_recent_messages(context['recent_messages'])}

    [Current User Message]
    {user_message}
    """

def create_model():
    from dotenv import load_dotenv
    from langchain_openai import ChatOpenAI

    load_dotenv()
    if not os.getenv("OPENAI_API_KEY"):
        raise ValueError("OPENAI_API_KEY가 없습니다. .env파일을 확인하세요")

    return ChatOpenAI(model="gpt-4o-mini", temperature=0, max_tokens=300)

def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--run", action="store_true", help="OpenAI API를 호출합니다.")
    args = parser.parse_args()

    memory = ChatMemory(max_recent_messages=6)
    model = create_model() if args.run else None
    final_prompt = ""

    for user_message in DEMO_MESSAGES:
        # 현재 메시지의 profile 정보를 먼저 추출해야 현재 답변에도 반영된다.
        memory.remember_profile(user_message)
        final_prompt = build_prompt(memory, user_message)
        memory.add_message("user", user_message)

        if model:
            reply = model.invoke(final_prompt).content # 실제 OpenAI API를 호출
        else:
            reply = "[DRY RUN응답 : 실제 모델을 호출하지 않는다.]"

        memory.add_message("assistant", reply)
        print("User:", user_message)
        print("Bot:", reply)
        print()

    print("\n" + "🍋‍🟩" * 80)
    print("[마지막 답변에 전달되는 프롬프트 ]")
    print(final_prompt)
    print("\n" + "🍉" * 80)

    if not args.run:
        print("\n실제 실행: python ./07_openai_memory_chatbot.py --run")

if __name__ == "__main__":
    main()