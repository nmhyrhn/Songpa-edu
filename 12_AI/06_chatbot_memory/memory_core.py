from datetime import datetime
import re


# profile에 저장할 표현과 내부 key를 한 곳에서 관리합니다.
PROFILE_MARKERS = {
    "내 이름은": "name",
    "관심사는": "interest",
    "목표는": "goal",
    "내 수준은": "level",
    "설명 방식은": "preferred_style",
}

PROFILE_LABELS = {
    "name": "이름",
    "interest": "관심사",
    "goal": "목표",
    "level": "학습 수준",
    "preferred_style": "선호 설명 방식",
}

SENSITIVE_KEYWORDS = (
    "비밀번호",
    "패스워드",
    "주민등록번호",
    "계좌번호",
    "api key",
    "api키",
    "api 키",
)


def clean_korean_suffix(text: str) -> str:
    """추출한 값 끝의 간단한 한국어 종결 표현을 제거합니다."""
    value = text.strip()
    for suffix in ("입니다", "이에요", "예요", "이야", "야"):
        if value.endswith(suffix):
            return value[: -len(suffix)].strip()
    return value


def contains_sensitive_info(text: str) -> bool:
    """수업에서 다루는 대표적인 민감정보 표현을 탐지합니다."""
    lowered = text.lower()
    if any(keyword in lowered for keyword in SENSITIVE_KEYWORDS):
        return True

    # OpenAI 형태의 키와 주민등록번호 형태를 추가로 검사합니다.
    if re.search(r"\bsk-[A-Za-z0-9_-]{8,}\b", text):
        return True
    if re.search(r"\b\d{6}-[1-4]\d{6}\b", text):
        return True

    return False


class ChatMemory:
    """최근 대화, 사용자 profile, 요약을 함께 관리합니다."""

    def __init__(self, max_recent_messages: int = 6):
        if max_recent_messages <= 0:
            raise ValueError("max_recent_messages는 1 이상이어야 합니다.")

        self.messages: list[dict] = []
        self.profile: dict[str, str] = {}
        self.summary = ""
        self.max_recent_messages = max_recent_messages

    def add_message(self, role: str, content: str) -> None:
        """대화를 저장합니다. 민감정보가 있으면 원문을 남기지 않습니다."""
        if role not in {"user", "assistant", "system"}:
            raise ValueError("role은 user, assistant, system 중 하나여야 합니다.")

        safe_content = (
            "[민감정보가 제거된 메시지]"
            if contains_sensitive_info(content)
            else content.strip()
        )
        self.messages.append({
            "role": role,
            "content": safe_content,
            "time": datetime.now().strftime("%H:%M:%S"),
        })

    def recent_messages(self) -> list[dict]:
        """프롬프트에 전달할 최근 N개 메시지만 반환합니다."""
        return self.messages[-self.max_recent_messages :]

    def remember_profile(self, user_message: str) -> str:
        """허용된 사용자 정보만 profile에 저장하고 처리 결과를 반환합니다."""
        if contains_sensitive_info(user_message):
            return "민감정보는 profile에 저장하지 않았습니다."

        for marker, key in PROFILE_MARKERS.items():
            if marker not in user_message:
                continue

            value = clean_korean_suffix(user_message.split(marker, 1)[1])
            if value:
                self.profile[key] = value
                return f"{PROFILE_LABELS[key]} 정보를 기억했습니다."

        return "저장할 profile 정보를 찾지 못했습니다."

    def forget_profile(self, key: str | None = None) -> bool:
        """특정 profile 또는 전체 profile을 삭제합니다."""
        if key is None:
            had_profile = bool(self.profile)
            self.profile.clear()
            return had_profile

        return self.profile.pop(key, None) is not None

    def update_summary(self) -> str:
        """profile과 누적 대화 수를 짧은 문자열로 압축합니다."""
        parts = [
            f"{PROFILE_LABELS[key]}: {value}"
            for key, value in self.profile.items()
            if key in PROFILE_LABELS
        ]
        user_count = sum(message["role"] == "user" for message in self.messages)
        if user_count:
            parts.append(f"누적 사용자 메시지: {user_count}개")

        self.summary = ". ".join(parts) + ("." if parts else "")
        return self.summary

    def context(self) -> dict:
        """LLM 프롬프트에 전달할 memory context를 만듭니다."""
        return {
            "profile": dict(self.profile),
            "summary": self.update_summary(),
            "recent_messages": self.recent_messages(),
        }

    def to_dict(self) -> dict:
        """JSON 파일에 저장할 수 있는 dictionary로 변환합니다."""
        return {
            "messages": self.messages,
            "profile": self.profile,
            "summary": self.summary,
            "max_recent_messages": self.max_recent_messages,
        }

    @classmethod
    def from_dict(cls, data: dict) -> "ChatMemory":
        """JSON에서 읽은 dictionary로 Memory를 복원합니다."""
        memory = cls(max_recent_messages=data.get("max_recent_messages", 6))
        memory.messages = list(data.get("messages", []))
        memory.profile = dict(data.get("profile", {}))
        memory.summary = data.get("summary", "")
        return memory


def format_recent_messages(messages: list[dict]) -> str:
    """메시지 목록을 프롬프트에서 읽기 쉬운 문자열로 바꿉니다."""
    if not messages:
        return "최근 대화 없음"
    return "\n".join(
        f"{message['role']}: {message['content']}" for message in messages
    )
