# 전체 대화화 Summary Memory 길이 비교

from memory_core import ChatMemory, format_recent_messages

def main() -> None:
  memory = ChatMemory(max_recent_messages=6)

  pofile_messages = [
    "내 이름은 수진이야",
    "내 수준은 Pyhton 초보자야",
    "관심사는 RAG 챗봇이야",
    "설명 방식은 짧은 예시 중심이야"
  ]

  for message in profile_messages:
    memory.add_message("user", message)
    memory.remember_profile(message)
    memory.add_message("assistant", "기억했습니다")

  for number in range(1, 9):
    memory.add_message("user", f"RAG질문 {number}번 입니다.")
    memory.add_message("assistant", f"RAG 답변 {number}번 입니다.")

  full_history = format_recent_messages(memory.messages)
  recent_history = format_recent_messages(memory.recent_messages())
  summary = memory.update_summary()

  print("전체 메시지 수:", len(memory.messages))
  print("전체 대화 글자 수:", len(full_history))
  print("최근 대화 글자 수:", len(recent_history))
  print("요약 글자 수:", len(summary))

  print("\n[Summary Memory]")
  print(summary)

  print("\n[최근 대화]")
  print(recent_history)

if __name__ == "__main__":
  main()