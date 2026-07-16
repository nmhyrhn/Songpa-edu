from __future__ import annotations
import os
from dotenv import load_dotenv
import openai


load_dotenv()
API_KEY = os.getenv("OPENAI_API_KEY")
if not API_KEY:
    print("환경변수 OPENAI_API_KEY를 설정하세요. 예: .env 파일에 값을 넣거나 환경변수로 설정합니다.")
    raise SystemExit(1)

openai.api_key = API_KEY


def chat_loop():
    print("간단한 챗봇입니다. 'exit' 또는 'quit' 입력 시 종료됩니다.")
    system_prompt = "당신은 친절한 한국어 도우미입니다."
    history: list[dict[str, str]] = []
    while True:
        try:
            user_input = input("You: ").strip()
        except (EOFError, KeyboardInterrupt):
            print("\n종료합니다.")
            break
        if not user_input:
            continue
        if user_input.lower() in ("exit", "quit"):
            print("종료합니다.")
            break

        history.append({"role": "user", "content": user_input})
        # keep a short window of conversation to save tokens
        messages = [{"role": "system", "content": system_prompt}] + history[-10:]

        try:
            resp = openai.ChatCompletion.create(
                model="gpt-3.5-turbo",
                messages=messages,
                max_tokens=500,
                temperature=0.7,
            )
            reply = resp["choices"][0]["message"]["content"].strip()
            print("Bot:", reply)
            history.append({"role": "assistant", "content": reply})
        except Exception as e:
            print("에러 발생:", e)


if __name__ == "__main__":
    chat_loop()
