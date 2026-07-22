from memory_core import ChatMemory

def process_user_message(memory: ChatMemory, message: str) -> str:
  """사용자 명령을 해석해 저장, 삭제, 차단 중 하나를 수행한다."""
  memory.add_message("user", message)

  if "이름을 잊어" in message or "이름을 삭제" in message:
    deleted = memory.forget_profile("name")
    return "이름을 삭제했습니다." if deleted else "삭제할 이름이 없습니다."
  
  if "모든 기억을 삭제" in message:
    deleted = memory.forget_profile()
    return "모든 profile을 삭제했습니다." if deleted else "삭제할 profile이 없습니다."
  
  return memory.remember_profile(message)

def main() -> None:
  memory = ChatMemory()
  messages = [
    "내 이름은 민수야",
    "내 이름은 지훈이야",
    "관심사는 생성형 AI야",
    "내 비밀번호는 class1234야",
    "내 이름을 삭제해줘"
  ]

  for message in messages:
    result = process_user_message(memory, message)
    print("\nUser:", message)
    print("처리 결과:", result)
    print("현재 profile:", memory.profile)
    print("저장된 마지막 메시지:", memory.messages[-1]["content"])

if __name__ == "__main__":
  main()