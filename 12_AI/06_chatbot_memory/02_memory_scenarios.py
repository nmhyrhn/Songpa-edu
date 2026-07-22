# 여러 대화 흐름에서 memory가 어떻게 변하는지 확인

from importlib import util # 모듈을 동적으로 불러오는 라이브러리
from pathlib import Path

MODULE_PATH = Path(__file__).with_name("02_memory_practice.py")

# 모듈 불러오기
spec = util.spec_from_file_location("memory_answer", MODULE_PATH)
memory_answer = util.module_from_spec(spec)
spec.loader.exec_module(memory_answer)

SCENARIOS = {
    "기본 기억": [
        "내 이름은 민수야",
        "내 이름이 뭐였지?"
    ],
    "정보 변경": [
        "내 이름은 민수야",
        "내 이름은 지훈이야",
        "내 이름이 뭐였지?"
    ],
    "없는 정보 질문":[
        "내 목표가 뭐였지?"
    ],
    "전체 profile": [
        "내 이름은 수진이야",
        "관심사는 VectorStore야",
        "목표는 RAG 챗봇 만들기야",
        "지금까지 요약해줘"
    ]
}

def run_scenario(name, messages):
    print("=" * 80)
    print("시나리오 : ", name)

    memory = memory_answer.ChatMemory()

    for message in messages:
        reply = memory_answer.chat_once(message, memory)
        print("User : ", message)
        print("Bot : ", reply)
        print()

    print("최종 profile:", memory.profile)
    print("최종 summary: ", memory.context()["summary"])
    print()

def main():
    for name, messages in SCENARIOS.items():
        run_scenario(name, messages)

if __name__ == "__main__":
    main()