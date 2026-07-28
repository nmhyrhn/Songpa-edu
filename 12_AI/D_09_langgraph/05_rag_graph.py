import argparse
import os
from typing import TypedDict

from dotenv import load_dotenv
from langchain_core.vectorstores import InMemoryVectorStore
from langchain_openai import ChatOpenAI, OpenAIEmbeddings
from langgraph.graph import END, START, StateGraph

# RAG에서 사용할 데이터
DOCUMENTS = [
  "롯데월드 아쿠아리움은 송파구의 실내 장소이며 수업용 성인 입장료는 35,000원이다.",
  "서울스카이는 송파구의 실내 전망대이며 수업용 성인 입장료는 31,000원이다.",
  "석촌호수는 송파구의 야외 산책 장소이며 입장료는 무료이다.",
  "올림픽공원은 야외 활동애 적합하며 입장료는 무료이다."
]

DEMO_QUESTIONS = [
  ("기본", "비 오는 날 송파에서 갈 만한 장소와 입장료는?"),
  ("비교", "무료로 산책할 수 있는 장소는?"),
  ("경계", "부산에서 아이와 갈 실내 장소는?")
]

class RagState(TypedDict):
  question: str
  context: list[str]
  answer: str
  path: list[str]

def require_api_key() -> None:
  load_dotenv()
  if not os.getenv("OPENAI_API_KEY"):
    raise SystemExit(".env에 OPENAI_API_KEY를 설정한 뒤 --run을 사용하세요")

def build_graph():
  # RAG 준비 : 문서를 Embedding하고 VectorStroe에 넣는다.
  embeddings = OpenAIEmbeddings(model="text-embedding-3-small")
  store = InMemoryVectorStore.from_texts(DOCUMENTS, embedding=embeddings)
  model = ChatOpenAI(model="gpt-4o-mini", temperature=0)

  def retrieve(state: RagState) -> dict:
    """Retrieval Node: 질문으로 VectorStore를 검색해서 context를 만든다."""
    docutments = store.similarity_search(state["question"], k=2)
    return {
      "context": [document.page_content for document in docutments],
      "path": ["retrieve"]
    }

  def generate(state: RagState) -> dict:
    """Generation Node: context만 근거로 답변을 생성"""
    context = "/n".join(f"- {text}" for text in state["context"])
    prompt = (
      "아래 수업용 여행 문서만 근거로 답하세요. 근거가 없으면 모른다고 답하세요.\n"
      f"[문서]\n {context}\n[질문]\n{state['question']}"
    )
    answer = model.invoke(prompt).content
    return {"answer": str(answer), "path": state["path"] + ["generate"]}

  builder = StateGraph(RagState)
  builder.add_node("retrieve", retrieve)
  builder.add_node("generate", generate)
  builder.add_edge(START, "retrieve")
  builder.add_edge("retrieve", "generate")
  builder.add_edge("generate", END)
  return builder.compile()

def main() -> None:
  parser = argparse.ArgumentParser()
  parser.add_argument("--run", action="store_true", help="실제 Embedding과 LLM을 호출")
  args = parser.parse_args()

  print("RAG가 틀렸을때 검색과 답변 생성 중 어느 단계가 문제인지 어떻게 찾을까?")
  print("**Retrieval > VectorStore에서 관련 여행 문서를 가져오는 단계")
  print("**Context** > 검색되어 다음 Node로 전달되는 문서 근거")
  print("\n[RAG Graph 구조]")
  print("START -> retrieve -> generate -> END")

  require_api_key()
  graph = build_graph()

  for label, question in DEMO_QUESTIONS:
    result = graph.invoke(
      {"question": question, "context": [], "answer": "", "path": []}
    )

    print(f"\n[샘플: {label}]")
    print("질문:", question)
    print("검색된 Context:")
    for context in result["context"]:
      print("-", context)
    print("경로:", "->".join(result["path"]))
    print("답변:", result["answer"])


if __name__ == "__main__":
  main()
