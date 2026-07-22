from datetime import datetime
import sys

def clean_korean_suffix(text):
    text = text.strip()
    for suffix in ("이야", "야"):
        if text.endswith(suffix):
            return text[:-len(suffix)].strip()
    return text

class ChatMemory:
    """대화 기록, 사용자 정보, 요약을 관리"""

    def __init__(self, max_recent_messages=6):
        self.messages = []
        self.profile = {}
        self.summary = ""
        self.max_recent_messages = max_recent_messages

    def add_message(self, role, content):
        self.messages.append({
            "role": role,
            "content": content,
            "time": datetime.now().strftime("%H:%M:%S")
        })

    def recent_messages(self):
        return self.messages[-self.max_recent_messages:]

    def remember_profile(self, user_message):
        """간단한 규칙으로 사용자 정보를 추출"""
        if "내 이름은" in user_message:
            name = clean_korean_suffix(user_message.split("내 이름은", 1)[1])
            if name:
                self.profile["name"] = name
        if "관심사" in user_message:
            interest = clean_korean_suffix(user_message.split("관심사", 1)[1])
            if interest:
                self.profile["interest"] = interest        
        if "목표는" in user_message:
            goal = clean_korean_suffix(user_message.split("목표는", 1)[1])
            if goal:
                self.profile["goal"] = goal

    def update_summary(self):
        """
           요약을 할껀데 실제 서비스에서는 LLM이 요약을 진행한다. 
           여기서는 규칙 기반으로 요약
        """
        parts = []

        if "name" in self.profile:
            parts.append(f"사용자 이름은 {self.profile['name']}입니다")

        if "interest" in self.profile:
            parts.append(f"관심사는 {self.profile['interest']}입니다")        

        if "goal" in self.profile:
            parts.append(f"목표는 {self.profile['goal']}입니다")

        user_messages = [m["content"] for m in self.messages if m["role"] == "user"]
        if user_messages:
            parts.append(f"최근 질문 수는 {len(user_messages)}개 입니다.")

        self.summary = ". ".join(parts) + ("." if parts else "")

    def context(self):
        """답변 생성에 사용할 memory context를 만든다."""
        self.update_summary()
        return {
            "profile": self.profile,
            "summary": self.summary,
            "recent_messages": self.recent_messages()
        }

def generate_reply(user_message, memory):
    """memory를 참고해서 간단한 답변으 생성"""
    memory.remember_profile(user_message)
    context = memory.context()
    profile = context["profile"]

    if "내 이름" in user_message and "뭐" in user_message:
        name = profile.get("name")
        return f"이름은 {name}라고 하셨어요." if name else "아직 이름을 듣지 못했어요."
    
    if "관심사" in user_message and "뭐" in user_message:
        interest = profile.get("interest")
        return f"관심사는 {interest}입니다." if interest else "아직 관심사를 듣지 못했어요."
    
    if "목표" in user_message and "뭐" in user_message:
        goal = profile.get("goal")
        return f"목표는 {goal}입니다." if goal else "아직 목표를 듣지 못했어요."

    if "요약" in user_message:
        return context["summary"] or "요약할 대화가 아직 없습니다."

    if "memory" in user_message.lower() or "기억" in user_message:
        return "Memory는 이전 대화나 사용자 정보를 저장해서 다음 답변에 반영하는 기능입니다."

    return "좋아요. 그 내용을 기억해둘게요. 더 구체적으로 질문해도 됩니다."


def chat_once(user_message, memory):
    memory.add_message("user", user_message)
    reply = generate_reply(user_message, memory)
    memory.add_message("assistant", reply)
    return reply

def print_memory(memory):
    context = memory.context()

    print("Profile:", context["profile"])
    print("Summary:", context["summary"])
    print("Recent messages: ")
    for message in context["recent_messages"]:
        print(f"- {message['time']} {message['role']}: {message['content']}")


def run_demo():
    """시작 시 먼저 실행하는 고정 시나리오"""
    memory = ChatMemory()
    messages = [
        "내 이름은 민수야",
        "관심사는 RAG와 챗봇이야",
        "목표는 수업 도우미 챗봇 만들기",
        "내 이름이 뭐였지?",
        "내 관심사가 뭐였지?",
        "지금까지 요약해줘"
    ]

    for message in messages:
        print("User: ", message)
        print("Bot :", chat_once(message, memory))
        print()
    print("=" * 60)
    print_memory(memory)

def print_interactive_help():
    print("""
    사용 가능한 입력 예시
        정보 저장 : 내이름은 민수야
                    관심사는 RAG와 챗봇이야
                    목표는 수업 도우미 챗봇 만들기야
        기억 질문: 내 이름은 뭐였지?
                  내 관심사가 뭐였지?
                  내 목표가 뭐였지?
        요약 요청: 지금까지 요약해줘
        상태 확인: /memory
        도움말   : /help
        종료     : exit
    """)

def run_interactive():
    memory = ChatMemory()

    print("Chatbot Memory Demo")
    print("이 프로그램은 자유 대화형 LLM이 아니라 규칙 기반 Memory 실습")

    while True:
        user_message = input("User: ").strip()

        if user_message.lower() == "exit":
            break

        if user_message == "/memory":
            print_memory(memory)
            print()
            continue

        if user_message == "/help":
            print_interactive_help()
            continue

        reply = chat_once(user_message, memory)
        print("Bot :", reply)


if __name__ == "__main__":
    if "--interactive" in sys.argv:
        run_interactive()
    else: 
        run_demo()