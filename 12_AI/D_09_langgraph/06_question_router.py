import argparse
import os
from typing import Literal, TypedDict

from dotenv import load_dotenv
from langchain_core.vectorstores import InMemoryVectorStore
from langchain_openai import ChatOpenAI, OpenAIEmbeddings
from langgraph.graph import END, START, StateGraph

DOCUMENTS = [
    "롯데월드 아쿠아리움은 실내 장소이며 수업용 입장료는 35,000원이다.",
    "서울스카이는 실내 전망대이며 수업용 입장료는 31,000원이다.",
    "석촌호수는 무료 야외 산책 장소이다."
]

class RouterState(TypedDict):
    question: str
    route: str
    context: list[str]
    answer: str
    path: list[str]

def classify(state: RouterState) -> dict:
    """Query Router: 여행지 문서가 필요한 질문인지 판단"""
    destination_words = (
        "아쿠아리움", "서울스카이", "석촌호수", "입장료", "장소", "실내", "무료"
    )
    route = "rag" if any(word in state["question"] for word in destination_words) else "general"
    return {"route": route, "path": ["classify"]}

def route_after_classify(state: RouterState) -> Literal["retrieve", "general"]:
    """ route State를 실제 다음 Node이름으로 변환 """
    return "retrieve" if state["route"] == "rag" else "general"


def build_graph():
    embeddings = OpenAIEmbeddings(model="text-embedding-3-small")
    store = InMemoryVectorStore.from_texts(DOCUMENTS, embedding=embeddings)
    model = ChatOpenAI(model="gpt-4o-mini", temperature=0)

    def retrieve(state: RouterState)-> dict:
        docs = store.similarity_search(state["question"], k=2)
        return {
            "context": [doc.page_content for doc in docs],
            "path": state["path"] + ["retrieve"]
        }

    def rag_answer(state: RouterState) -> dict:
        prompt = f"문서만 근거로 답하세요.\n문서: {state['context']}\n질문: {state['question']}"
        return {
            "answer": str(model.invoke(prompt).content),
            "path": state["path"] + ["rag_answer"]
        }

    def general(state: RouterState) -> dict:
        return {
            "answer": str(model.invoke(state["question"]).content),
            "path": state["path"] + ["general"]
        }

    builder = StateGraph(RouterState)
    builder.add_node("classify", classify)
    builder.add_node("retrieve", retrieve)
    builder.add_node("rag_answer", retrieve)
    builder.add_node("general", general)
    builder.add_edge(START, "classify")
    builder.add_conditional_edges("classify", route_after_classify)
    builder.add_edge("retrieve", "rag_answer")
    builder.add_edge("rag_answer", END)
    builder.add_edge("general", END)
    return builder.compile()

def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--run", action="store_true")
    args = parser.parse_args()

    """
    여행지 문서가 필요없는 질문에도 Embedding 검색비용을 써야할까

    Query Routing> 질문에 맞는 처리 전략을 선택
    RAG Branch = classify -> retrieve -> rag_answer
    General Branch = classify -> general
    """
    load_dotenv()
    if not os.getenv("OPENAI_API_KEY"):
        raise SystemExit(".env에 OPENAI_API_KEY를 설정하세요")

    graph = build_graph()
    demo_questions = (
        ("기본-RAG", "아쿠아리움 입장료는 얼마야?"),
        ("기본-General", "여행 가방을 가볍게 싸는 방법은?"),
        ("비교-RAG", "무료 산책 장소는 어디야?"),
        ("비교-General", "아이 여행 준비물은 무엇이야?")
    )

    for label, question in demo_questions:
        initial: RouterState = {
            "question": question, "route": "", "context": [], "answer":"", "path":[]
        }
        result = graph.invoke(initial, config={"recursion_limit" : 10})
        print(f"\n[샘플: {label}]")
        print("질문: ", question)
        print("분류:", result["route"])
        print("경로:", " -> ".join(result["path"]))
        print("Context:", result["context"] or "사용하지 않음")
        print("답변: ", result["answer"])

if __name__ == "__main__":
    main()