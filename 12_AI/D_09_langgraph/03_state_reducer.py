import operator
from typing import Annotated, TypedDict
from langgraph.graph import END, START, StateGraph

class ResearchState(TypedDict):
    topic: str
    # 여기가 핵심> notes의 State Update는 덮어쓰지 않고 list를 더한다.
    notes: Annotated[list[str], operator.add]

def find_destination(state: ResearchState) -> dict:
    return {"notes": [f"관광지 조사: {state['topic']}의 실내 장소를 찾았습니다."]}

def find_budget(state: ResearchState) -> dict:
    return {"notes": [f"예산 조사: {state['topic']}의 입장료 정보를 찾았습니다."]}

def find_meal(state: ResearchState) -> dict:
    return {"notes": [f"식사 조사: {state['topic']} 주변 식당 정보를 찾았습니다."]}

def find_transport(state: ResearchState) -> dict:
    return {"notes": [f"교통 조사: {state['topic']}의 이동 방법을 찾았습니다."]}

def summarize(state: ResearchState) -> dict:
    return {"notes": [f"수집된 근거: {state['topic']}를 요약합니다."]}


def build_graph():
    builder = StateGraph(ResearchState)
    builder.add_node("destination", find_destination)
    builder.add_node("budget", find_budget)
    builder.add_node("meal", find_meal)
    builder.add_node("transport", find_transport)
    builder.add_node("summarize", summarize)
    builder.add_edge(START, "destination")
    builder.add_edge("destination", "budget")
    builder.add_edge("budget", "meal")
    builder.add_edge("meal", "transport")
    builder.add_edge("transport", "summarize")
    builder.add_edge("summarize", END)
    return builder.compile()



def main() -> None:
    print("장소 조사와 예산 조사가 모두 notes를 반환하면 이전 결과는 어떻게 될까?")
    print("Reducer: 기본 State 값과 새 State Update를 합치는 규칙")
    print("operator.add=notes list를 이어 붙이는 실제 합산 함수")


    result = build_graph().invoke({"topic": "송파 여행", "notes": []})
    for index, note in enumerate(result["notes"], start=1):
        print(f"{index}. {note}")


if __name__ == "__main__":
    main()