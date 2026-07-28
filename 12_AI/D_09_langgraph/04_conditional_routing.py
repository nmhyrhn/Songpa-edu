# 모든 질문에 같은 작업을 실행하지 않는다.
# Router가 질문 의도를 State에 기록하고 Conditional Edge가 다음 노드를 선택한다.

from typing import Literal, TypedDict
from langgraph.graph import END, START, StateGraph

class RouteState(TypedDict):
    question: str
    route: str
    answer: str
    path: list[str]

def classify(state: RouteState) -> dict:
    """Router Node: 질문을 분류해 route State를 만든다"""
    question = state["question"]
    if any(word in question for word in ("예산", "비용", "얼마", "입장료")):
        route = "budget"
    elif any(word in question for word in ("장소", "관광지", "실내", "갈 만한")):
        route = "destination"
    else:
        route = "general"
    return {"route": route, "path": ["classify"]}

def budget(state: RouteState)-> dict:
    return {"answer": "인원과 항목을 확인해 예산을 계산합니다.", "path": state["path"] + ["budget"]}

def destination(state: RouteState)-> dict:
    return {"answer": "여행지 문서에서 조건에 맞는 장소를 검색합니다.", "path": state["path"] + ["destination"]}

def general(state: RouteState)-> dict:
    return {"answer": "일반 여행 안내로 답합니다.", "path": state["path"] + ["general"]}

def choose_route(state: RouteState)-> Literal["budget", "destination", "general"]:
    return state["route"]

def build_graph():
    builder = StateGraph(RouteState)
    builder.add_node("classify", classify)
    builder.add_node("budget", budget)
    builder.add_node("destination", destination)
    builder.add_node("general", general)
    builder.add_edge(START, "classify")
    builder.add_conditional_edges("classify", choose_route)
    for node in ("budget", "destination", "general"):
        builder.add_edge(node, END)
    return builder.compile()

def main() -> None:
    print("예산, 장소, 일반 질문에 왜 같은 작업을 실행하면 안될까?")

    graph = build_graph()
    labels = ("기본-예산", "기본-장소", "기본-일반", "경계-복합의도", "경계-미등록의도")
    questions = [
        "3명 입장료는 얼마야?",
        "비 오는 날 갈 만한 실내 장소는?",
        "여행할 때 편한 신발이 필요한가요?",
        "입장료가 싼 실내 장소는?",
        "반려동물과 함께 갈 수 있나요?"
    ]

    for label, question in zip(labels, questions):
        result = graph.invoke({"question": question, "route": "", "answer": "", "path": []})
        print(f"\n[샘플: {label}]")
        print("질문:", question)
        print("Route:", result["route"])
        print("실행된 Branch:", "->".join(result["path"]))
        print("답변:", result["answer"])

if __name__ == "__main__":
    main()