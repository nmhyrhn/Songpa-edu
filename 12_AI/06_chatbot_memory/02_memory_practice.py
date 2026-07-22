from datetime import datetime


def clean_korean_suffix(text):
    text = text.strip()
    for suffix in ("이야", "야"):
        if text.endswith(suffix):
            return text[: -len(suffix)].strip()
    return text


class ChatMemory:
    def __init__(self, max_recent_messages=6):
        self.messages = []
        self.profile = {}
        self.summary = ""
        self.max_recent_messages = max_recent_messages

    def add_message(self, role, content):
        self.messages.append({
            "role": role,
            "content": content,
            "time": datetime.now().strftime("%H:%M:%S"),
        })

    def recent_messages(self):
        return self.messages[-self.max_recent_messages:]

    def remember_profile(self, user_message):
        if "내 이름은" in user_message:
            name = clean_korean_suffix(user_message.split("내 이름은", 1)[1])
            if name:
                self.profile["name"] = name

        if "관심사는" in user_message:
            interest = clean_korean_suffix(user_message.split("관심사는", 1)[1])
            if interest:
                self.profile["interest"] = interest

        if "목표는" in user_message:
            goal = clean_korean_suffix(user_message.split("목표는", 1)[1])
            if goal:
                self.profile["goal"] = goal

    def update_summary(self):
        parts = []

        if "name" in self.profile:
            parts.append(f"사용자 이름은 {self.profile['name']}입니다")

        if "interest" in self.profile:
            parts.append(f"관심사는 {self.profile['interest']}입니다")

        if "goal" in self.profile:
            parts.append(f"목표는 {self.profile['goal']}입니다")

        self.summary = ". ".join(parts) + ("." if parts else "")

    def context(self):
        self.update_summary()
        return {
            "profile": self.profile,
            "summary": self.summary,
            "recent_messages": self.recent_messages(),
        }


def generate_reply(user_message, memory):
    memory.remember_profile(user_message)
    context = memory.context()
    profile = context["profile"]

    if "내 이름" in user_message and "뭐" in user_message:
        return f"이름은 {profile['name']}입니다." if "name" in profile else "아직 이름을 모릅니다."

    if "내 관심사" in user_message and "뭐" in user_message:
        return f"관심사는 {profile['interest']}입니다." if "interest" in profile else "아직 관심사를 모릅니다."

    if "내 목표" in user_message and "뭐" in user_message:
        return f"목표는 {profile['goal']}입니다." if "goal" in profile else "아직 목표를 모릅니다."

    if "요약" in user_message:
        return context["summary"] or "요약할 정보가 없습니다."

    return "기억해둘게요."


def chat_once(user_message, memory):
    memory.add_message("user", user_message)
    reply = generate_reply(user_message, memory)
    memory.add_message("assistant", reply)
    return reply


def main():
    memory = ChatMemory()

    messages = [
        "내 이름은 민수야",
        "관심사는 RAG와 챗봇이야",
        "목표는 수업 도우미 만들기야",
        "내 이름이 뭐였지?",
        "내 관심사가 뭐였지?",
        "지금까지 요약해줘",
    ]

    for message in messages:
        print("User:", message)
        print("Bot :", chat_once(message, memory))
        print()

    print("Context:")
    print(memory.context())


if __name__ == "__main__":
    main()
