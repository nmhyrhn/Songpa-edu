from typing import TypedDict

from langchain_core.runnables import RunnableLambda
from langgraph.graph import END, START, StateGraph

DEMO_QUESTIONS = [
    ("기본", "비 오는 날 송파에서 갈 만한 실내 장소는?"),
    ("비교", "무료로 산책할 수 있는 장소는?")
]

class LessonState(TypedDict):
    """Chain과 Graph가 공통으로 전달하는 여행 작업 데이터"""
    question: str
    context: str
    answer: str
    path: list[str]

def retrieve(state: LessonState) -> dict:
    """나중에 프로젝트에서는 VectorStore Retriever가 맡는 자리"""
    if "무료" in state["question"] or "산책" in state["question"]:
        context = "수업용 데이터: 석촌호수는 무료 야외 산책 장소입니다."
    else:
        context = (
            "수업용 데이터: 롯데월드 아쿠아리움은 실내 장소이고 "
            "성인 기준 입장료는 35,000원 입니다."
        )
    return {"context": context, "path": state["path"] + ["retrieve"]}

def generate(state: LessonState) -> dict:
    answer = f"여행 질문: {state['question']}\n검색 근거: {state['context']}"
    return {"answer": answer, "path": state["path"] + ["generate"]}

def build_graph():
    """그래프는 실행 단계를 노드와 엣지로 분리해 보여준다."""
    # 1. 어떤 모양의 State를 공유할지 정한다.
    builder = StateGraph(LessonState)
    # 2. 일반 Python 함수를 Graph의 Node로 등록
    builder.add_node("retrieve", retrieve)
    builder.add_node("generate", generate)
    # 3. Edge로 실행 순서를 표현
    builder.add_edge(START, "retrieve")
    builder.add_edge("retrieve", "generate")
    builder.add_edge("generate", END)
    return builder.compile()

def retrieve_chain_step(state: LessonState) -> LessonState:
    # LangChain의 다은 Runnable에 전체 State를 전달
    return {**state, **retrieve(state)}

def generate_chain_step(state: LessonState) -> LessonState:
    # 앞의 Runnable이 만든 context를 사용하고 전체 state를 반환
    return {**state, **generate(state)}

def build_langchain():
    """LCEL의 |연산자로 고정 실행 순서 표현"""
    return RunnableLambda(retrieve_chain_step) | RunnableLambda(generate_chain_step)

def run_fixed_chain(question: str) -> LessonState:
    state: LessonState = {"question": question, "context": "", "answer": "", "path":[]}
    return build_langchain().invoke(state)

def main() -> None:
    print("같은 여행 추천 작업을 Chain과 Graph로 만들면 뭐가 다를까")
    print("[선택 기준]")
    print("고정 순서라면 LangChain만으로 충분하다.")
    print("분기, 반복, 중단, 공유 state가 필요해질 때 LangGraph를 함께 사용")

    graph = build_graph()

    for label, question in DEMO_QUESTIONS:
        chain_result = run_fixed_chain(question)
        graph_result = graph.invoke(
            {"question": question, "context": "", "answer": "", "path": []}
        )

        print(f"\n[샘플: {label}] {question}")
        print("Chain 경로: ", "-> ".join(chain_result["path"]))
        print("Graph 경로: ", "-> ".join(graph_result["path"]))
        print("검색 근거:", graph_result["context"])
        print("결과 동일: ", chain_result["answer"] == graph_result["answer"])

if __name__ == "__main__":
    main()