# 여행 예산에 대한 계산을 이용해서 State, Node, Edge의 각각의 역할을 확인

from typing import TypedDict
from langgraph.graph import END, START, StateGraph

DEMO_CASES = [
    {"label": "기본", "place": "아쿠아리움", "travelers": 1, "ticket_price": 35000},
    {"label": "비교", "place": "서울스카이", "travelers": 2, "ticket_price": 31000},
    {"label": "경계", "place": "석촌호수", "travelers": 4, "ticket_price": 0},
]

class TripState(TypedDict):
    """모든 Node가 공유하지만 각 Node는 필요한 필드만 읽고 수정"""
    place: str
    travelers: int
    ticket_price: int
    total_price: int
    message: str
    path: list[str]

def calculate_total(state: TripState) -> dict:
    # Node1 : 입력 State를 읽고 total_price와 path만 업데이트
    total = state["travelers"] * state["ticket_price"]
    return {"total_price": total, "path": ["calculate_total"]}

def make_message(state: TripState)-> dict:
    # Node2 : 앞 Node가 만든 total_price를 읽고 문장을 만든다.
    message = (
        f"{state['place']} 여행자 {state['travelers']}명의 예상 입장료는 "
        f"{state['total_price']:,}원입니다."
    )
    return {"message": message, "path": state["path"] + ["make_message"]}

def build_graph():
    builder = StateGraph(TripState)

    builder.add_node("calculate_total", calculate_total)
    builder.add_node("make_message", make_message)

    builder.add_edge(START, "calculate_total")
    builder.add_edge("calculate_total", "make_message")
    builder.add_edge("make_message", END)
    return builder.compile()

def main() -> None:
    print("[State] : 여행 데이터를 공유하는 작업지")
    print("[Node]: calculate_total, make_message")
    print("[Edge] = START -> calculate-total -> make_message -> END")

    graph = build_graph()
    for case in DEMO_CASES:
        initial: TripState = {
            "place": case["place"],
            "travelers": case["travelers"],
            "ticket_price": case["ticket_price"],
            "total_price": 0,
            "message": "",
            "path": []
        }
        result = graph.invoke(initial)
        print(f"\n[샘플: {case['label']}]")
        print("입력 State:", initial)
        print("변경된 total_price:", result["total_price"])
        print("실행 경로:", "-> ".join(result["path"]))
        print("최종 message:", result["message"])
        # Node는 State 전체가 아니라 변경할 필드만 반환해도 된다.
        
if __name__ == "__main__":
    main()