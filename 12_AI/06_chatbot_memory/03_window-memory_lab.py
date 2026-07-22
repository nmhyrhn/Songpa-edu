from memory_core import ChatMemory

CONVERSATION = [
  ("user", "내 이름은 민수야."),
  ("assistant", "민수님, 반갑습니다."),
  ("user", "관심사는 RAG와 챗봇이야"),
  ("assistant", "RAG부터 살펴보겠습니다."),
  ("user", "Loader가 뭐야?"),
  ("assistant", "문서를 읽는 구성요소입니다."),
  ("user", "Text Splitter도 설명해줘"),
  ("assistant", "긴 문서를 작은 chunk로 나눕니다."),
  ("user", "그 둘의 차이는 뭐야?"),
  ("assistant", "하나는 읽고, 다른 하나는 나눕니다.")
]

def run_window_experiment(Window_size: int) -> None:
  """같은 대화를 다른 Window 크기로 저장하고 최근 대화를 출력"""
  memory = ChatMemory(max_recent_messages=Window_size)

  for role, content in CONVERSATION:
    memory.add_message(role, content)
    if role == "user":
      memory.remember_profile(content)

  print("\n" + "=" * 72)
  print(f"Memory Window: 최근 {Window_size}개 / 전체 {len(memory.messages)}개")
  for message in memory.recent_messages():
    print(f"- {message['role']}: {message['content']}")

  print("Profile:", memory.profile) # 최근 대화에서 이름이 사라져도 profile에서는 남아있다

def main() -> None:
  for window_size in (2, 4, 6):
    run_window_experiment(window_size)

if __name__ == "__main__":
  main()