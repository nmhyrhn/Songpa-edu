import argparse
import os

from dotenv import load_dotenv
from langchain.agents import create_agent
from langchain_core.messages import AIMessage, HumanMessage, ToolMessage
from langchain_core.tools import tool
from langchain_openai import ChatOpenAI
from pydantic import BaseModel, Field

DEMO_CASES = [
    (
        "장소 Tool",
        "비 오는 날 갈 실내 장소를 찾아줘",
        ("search_destination",),
    ),
    (
        "예산 Tool",
        "3명이 입장료 35000원, 식비 20000원씩 쓰면 얼마야?",
        ("estimate_day_budget",),
    ),
    (
        "Tool 없음",
        "즐거운 여행이 되라고 인사해줘",
        (),
    ),
    (
        "복수 Tool",
        "실내 장소를 찾고, 3명이 입장료 35000원과 "
        "식비 20000원씩 쓰는 예산도 계산해줘",
        ("search_destination", "estimate_day_budget"),
    ),
]

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

class DestinationInput(BaseModel):
    condition: str = Field(description="원하는 장소 조건: 실내, 무료 또는 산책")

@tool(args_schema=DestinationInput)
def search_destination(condition: str) -> str:
    """요청 조건에 맞는 여행지를 검색"""
    knowledge = {
        "실내": "롯데월드 아쿠아리움, 서울스카이",
        "무료": "석촌호수, 올림픽공원",
        "산책": "석촌호수, 올림픽공원"
    }
    matches = [
        value for key, value in knowledge.items() if key in condition or condition in key
    ]
    return "\n".join(matches) if matches else "검색 결과 없음"

def build_agent():
    """같은 Tool 계약을 실제 OpenAI 모델에 제공하는 Agent 생성"""
    load_dotenv()
    if not os.getenv("OPENAI_API_KEY"):
        raise SystemExit(".env에 OPENAI_API_KEY를 설정한 뒤 --run을 사용하세요.")

    model = ChatOpenAI(model="gpt-4o-mini", temperature=0)
    return create_agent(
        model=model,
        tools=[estimate_day_budget, search_destination],
        system_prompt=(
            "너는 여행 도우미다. 필요한 경우에만 Tool을 사용한다. "
            "복합 요청에는 필요한 Tool을 모두 사용한다."
            "Tool 실행 결과를 근거로 최종 답변한다."
        )

    )

def print_message_trace(messages: list) -> None:
    """질문/Action/Observation 해석"""
    print("\n[실제 메시지 추적]")
    for index, message in enumerate(messages, start=1):
        if isinstance(message, HumanMessage):
            print(f"{index}. HumanMessage - 사용자 질문")
            print("     content: ", message.content)
            continue

        if isinstance(message, ToolMessage):
            print(f"{index}. ToolMessage - Observation")
            print("     Tool 이름: ", message.name)
            print("     tool_call_id:", message.tool_call_id)
            print("     실행 결과: ", message.content)
            continue

        if isinstance(message, AIMessage) and message.tool_calls:
            print(f"{index}. AIMessage - Action")
            for tool_call in message.tool_calls:
                print("     Tool 이름: ", tool_call["name"])
                print("     인자:", tool_call["args"])
                print("     tool_call_id:", tool_call["id"])
            continue

        if isinstance(message, AIMessage):
            print(f"{index}. AIMessage - Final Answer")
            print("     content: ", message.content)
            continue
        print(f"{index}. {type(message).__name__}: {message.content}")

def extract_tool_names(messages: list) -> list[str]:
    """여러 AIMessage에 나뉘어 생성된 Tool이름을 모은다."""
    names: list[str] = []
    for message in messages:
        if not isinstance(message, AIMessage):
            continue
        names.extend(tool_call["name"] for tool_call in message.tool_calls)
    return names

def compare_expected_tools(expected: tuple[str, ...], actual: list[str]) -> None:
    """Tool 호출 순서보다 필요한 Tool이 모두 사용됐는지 비교"""
    expected_set = set(expected)
    actual_set = set(actual)
    passed = expected_set == actual_set

    print("\n[예측 검증]")
    print("예상 Tool:", list(expected) if expected else "없음")
    print("실제 Tool:", actual if actual else "없음")
    print("결과:", "PASS" if passed else "CHECK")
    if len(actual) != len(actual_set):
        print("관찰:", "같은 Tool이 두번 이상 호출됐습니다.")

def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--run",
        action="store_true",
        help="실제 OpenAI Tool Calling 메시지를 출력"
    )
    args = parser.parse_args()

    agent = build_agent()
    for label, question, expected_tools in DEMO_CASES:
        print(f"\n{'=' * 80}")
        print(f"[샘플: {label}]")
        print("질문: ", question)

        result = agent.invoke({"messages" : [{"role": "user", "content": question}]})
        messages = result["messages"]
        print_message_trace(messages)
        compare_expected_tools(expected_tools, extract_tool_names(messages))

if __name__ == "__main__":
    main()