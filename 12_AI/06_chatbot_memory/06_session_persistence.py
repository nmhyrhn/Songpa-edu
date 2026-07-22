import json
from pathlib import Path

from memory_core import ChatMemory

BASE_DIR = Path(__file__).resolve().parent
STORE_PATH = BASE_DIR/"runtime"/"session_memories.json"

class MemoryStore:
    """session_id마다 서로 다른 ChatMemory를 보관"""

    def __init__(self):
        self.sessions: dict[str, ChatMemory] = {}

    def get(self, session_id: str) -> ChatMemory:
        """기존 session을 반환하고, 없으면 새 Memory를 생성"""
        if session_id not in self.sessions:
            self.sessions[session_id] = ChatMemory()
        return self.sessions[session_id]

    def save(self, path: Path) -> None:
        """모든 session을 한 JSON 파일에 저장"""
        path.parent.mkdir(parents=True, exist_ok=True)
        data = {
            session_id: memory.to_dict()
            for session_id, memory in self.sessions.items()
        }
        path.write_text(
            json.dumps(data, ensure_ascii=False, indent=2), encoding="utf-8"
        )

    @classmethod
    def load(cls, path: Path) -> "MemoryStore":
        """JSON파일이 있으면 session을 복원하고, 없으면 빈 저장소를 반환"""
        store = cls()
        if not path.exists():
            return store

        data = json.loads(path.read_text(encoding="utf-8"))
        store.sessions = {
            session_id: ChatMemory.from_dict(memory_data)
            for session_id, memory_data in data.items()
        }

        return store

def remember_user(memory: ChatMemory, message: str) -> None:
    memory.add_message("user", message)
    memory.remember_profile(message)
    memory.add_message("assistant", "기억했습니다.")

def main() -> None:
    store = MemoryStore()
    remember_user(store.get("student-a"), "내 이름은 민수야")
    remember_user(store.get("student-b"), "내 이름은 수진이야")
    store.save(STORE_PATH)

    print("저장 파일: ", STORE_PATH)
    print("저장 전 student-a:", store.get("student-a").profile)
    print("저장 전 student-b:", store.get("student-b").profile)

    # 다시 시작한 상황이라면 JSON 파일에서 읽어들인다.
    restored_store = MemoryStore.load(STORE_PATH)
    print("\n[프로그램 재실행 후 복원]")
    print("복원 student-a:", restored_store.get("student-a").profile)
    print("복원 student-b:", restored_store.get("student-b").profile)
    print("서로 다른 사용자가 정보가 섞이지 않았습니다.")

if __name__ == "__main__":
    main()