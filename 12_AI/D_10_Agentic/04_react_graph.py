"""
03_create_agent.py 여기에서는 Tool Calling이 정상적인것을 확인
이번에는 Tool결과가 부족할 때 다음 Action을 바꾸는걸 반복

Action 1: strict검색
Observation 1: NOT_FOUND
Action 2: broad검색으로 조건 수정
Observation 2: FOUND
Final Answer: 성공한 결과를 근거로 답변
"""

import argparse
import os
from typing import Literal

from dotenv import load_dotenv
from langchain_core.messages import AIMessage, SystemMessage, ToolMessage
from langchain_core.tools import tool
from langchain_openai import ChatOpenAI
from langgraph.graph import START, MessagesState, StateGraph
from langgraph.prebuilt import ToolNode, tools_condition
from pydantic import BaseModel, Field

RECOVERY_QUESTION = "조용하고 자연을 느낄 수 있는 실내 장소를 찾아줘."
CLARIFICATION_QUESTION = "3명 여행 예산을 계산해줘"

class DestinationSearchInput(BaseModel):
    condition: str = Field(description="사용자가 원하는 여행 장소 조건")
    search_mode: Literal["strict", "broad"] = Field(
        description=(
            "첫 검색은 strict, strict 결과가 NOT_FOUND이면 핵심 조건으로 broad재검색"
        )
    )

@tool(args_schema=DestinationSearchInput)
def destination_lookup(
    condition: str,
    search_mode: Literal["strict", "broad"]
) -> str:
    """송파 장소를 검색합니다. 실패 시 결과의 retry_hint를 참고해서 재검색하세요."""
    exact_data = {
        "실내": "롯데월드 아쿠아리움, 서울스카이",
        "무료 산책": "석촌호수, 올림픽공원",
        "전망": "서울스카이"
    }

    if search_mode == "strict":
        exact_match = exact_data.get(condition.strip())
        if exact_match:
            return f"FOUND | mode=strict | places={exact_match}"
        return (
            "NOT_FOUND | mode=strict | reason=모든 조건의 정확한 일치 결과 없음 | "
            "retry_hint=핵심 조건 하나로 broad 재검색"
        )
    broad_matches = [
        places for keyword, places in exact_data.items()
        if any(word in condition for word in keyword.split())
    ]
    if broad_matches:
        unique_places = list(dict.fromkeys(broad_matches))
        return f"FOUND | mode=broad | places={", ".join(unique_places)}"
    return (
        "NOT_FOUND | mode=broad | reason=핵심 조건으로도 결과 없음 | "
        "retry_hint=사용자에게 조건 변경 요청"
    )
class BudgetInput(BaseModel):
    """estimate day budget tool이 받을 입력 """
    travelers: int = Field(description="여행 인원수")
    ticket_price: int = Field(description="1인 기준 입장료(원)")
    meal_budget: int = Field(description="1인 기준 식비(원)")


@tool(args_schema=BudgetInput)
def estimate_day_budget(
    travelers: int, 
    ticket_price: int,  
    meal_budget: int   
) -> int:
    """인원수와 1인 비용을 사용해 여행자의 하루 전체 예산을 계산"""
    return travelers * (ticket_price + meal_budget)

    
def build_agent_graph():
    """model과 tools 사이를 반복하는 ReAct Graph 구성"""
    tools = [destination_lookup, estimate_day_budget]
    model_with_tools = ChatOpenAI(model="gpt-4o-mini", temperature=0).bind_tools(tools)

    def call_model(state: MessagesState) -> dict:
        response = model_with_tools.invoke(
            [
                SystemMessage(
                    content=(
                        "너는 여행 도우미다. 다음 규칙을 지켜라.\n"
                        "1. 장소 검색의 첫 호출은 사용자의 조건 전체를 condition에 넣고 "
                        "search_mode='strict'를 사용한다. \n"
                        "2. strict Observation이 NOT_FOUND이면 retry_hint를 읽고 핵심 "
                        "조건 하나로 search_mode='broad'를 한 번 호출한다.\n"
                        "3. broad도 NOT_FOUND이면 더 호출하지 말고 사용자에게 조건 "
                        "변경을 요청한다.\n"
                        "4. 예산 계산에 인원, 입장료, 식비 중 하나라도 없으면 값을 "
                        "추측하거나 Tool을 호출하지 말고 사용자에게 질문한다.\n"
                        "5. FOUND Observation을 받은 뒤에만 장소를 추천한다."
                    )
                ),
                *state["messages"]
            ]
        )
        return {"messages": [response]}

    builder = StateGraph(MessagesState)
    builder.add_node("agent", call_model)
    builder.add_node("tools", ToolNode(tools))
    builder.add_edge(START, "agent")
    # tools_condition은 성공 여부를 판단하지 않는다. 마지막 AIMessage에 tool_calls가 있으면
    # tools로 없으면 END로 보내는역할
    builder.add_conditional_edges("agent", tools_condition)
    # ToolMessage를 포함한 전체 State를 모델이 다시 읽어서, 인자를 수정하거나
    # 최종 답변 또는 추가 질문을 선택한다.
    builder.add_edge("tools", "agent")
    return builder.compile()


def print_react_trace(messages: list) -> None:
    """외부에서 관찰 가능한 Action과 Observation만 순서대로 출력합니다."""
    print("\n[ReAct 실행 추적]")
    action_number = 0
    observation_number = 0

    for message in messages:
        if isinstance(message, AIMessage) and message.tool_calls:
            for tool_call in message.tool_calls:
                action_number += 1
                print(f"Action {action_number}")
                print("  Tool:", tool_call["name"])
                print("  인자:", tool_call["args"])
                print("  tool_call_id:", tool_call["id"])
            continue

        if isinstance(message, ToolMessage):
            observation_number += 1
            print(f"Observation {observation_number}")
            print("  Tool:", message.name)
            print("  결과:", message.content)
            print("  tool_call_id:", message.tool_call_id)
            continue

        if isinstance(message, AIMessage):
            print("Final Answer")
            print("  내용:", message.content)


def analyze_recovery(messages: list) -> None:
    """실패 후 strict에서 broad로 행동이 바뀌었는지 검증합니다."""
    search_modes: list[str] = []
    observations: list[str] = []

    for message in messages:
        if isinstance(message, AIMessage):
            for tool_call in message.tool_calls:
                if tool_call["name"] == "destination_lookup":
                    search_modes.append(tool_call["args"].get("search_mode", "없음"))
        if isinstance(message, ToolMessage):
            observations.append(str(message.content))

    passed = (
        search_modes[:2] == ["strict", "broad"]
        and any(result.startswith("NOT_FOUND") for result in observations)
        and any(result.startswith("FOUND") for result in observations)
    )
    print("\n[회복 검증]")
    print("검색 mode 변화:", search_modes)
    print("NOT_FOUND 관찰:", any(item.startswith("NOT_FOUND") for item in observations))
    print("FOUND 관찰:", any(item.startswith("FOUND") for item in observations))
    print("결과:", "PASS" if passed else "CHECK")


def analyze_clarification(messages: list) -> None:
    """정보가 부족한 질문에서 Tool을 호출하지 않았는지 검증합니다."""
    tool_calls = [
        tool_call
        for message in messages
        if isinstance(message, AIMessage)
        for tool_call in message.tool_calls
    ]
    print("\n[추가 질문 검증]")
    print("Tool 호출 수:", len(tool_calls))
    print("결과:", "PASS" if not tool_calls else "CHECK")
    print("확인:", "최종 답변이 입장료와 식비를 다시 질문하는지 읽어보세요.")

def run_case(graph, label: str, question: str) -> list:
    print(f"\n{'=' * 80}")
    print(f"[사례: {label}]")
    print("질문: ", question)
    result = graph.invoke(
        {"messages": [{"role": "user", "content": question}]},
        config={"recursion_limit": 10}
    )
    messages = result["messages"]
    print_react_trace(messages)
    return messages


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--run",
        action="store_true",
        help="실제 OpenAI 모델로 실패 후 회복 과정을 실행합니다."
    )
    args = parser.parse_args()

    if not args.run:
        print("\n--run을 붙이면 실제 Action과 Observation변화를 확인할수있다.")
        return

    load_dotenv()
    if not os.getenv("OPENAI_API_KEY"):
        raise SystemExit(".env에 OPENAI_API_KEY를 설정한 뒤 --run을 사용하세요.")

    graph = build_agent_graph()

    recovery_messages = run_case(graph, "검색 실패 후 회복", RECOVERY_QUESTION)
    analyze_recovery(recovery_messages)

    clarification_messages = run_case(
        graph,
        "계산 정보 부족",
        CLARIFICATION_QUESTION
    )
    analyze_clarification(clarification_messages)

if __name__ == "__main__":
    main()