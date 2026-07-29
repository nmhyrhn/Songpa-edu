"""
1. CheckPointer가 같은 thread_id의 대화 메시지를 이어준다.
2. Agent가 이전 메시지의 이름, 동행인, 예산으로 Tool인자를 생성
3. 예산 수정 후 다음 Tool Call에는 최신 값이 들어간다.
4. 다른 thread_id에는 이전 정보가 없으므로 Tool 대신 추가 질문을 한다.

https://docs.langchain.com/oss/python/langgraph/add-memory
"""
import argparse
import os

from dotenv import load_dotenv
from langchain.agents import create_agent
from langchain_core.messages import AIMessage, HumanMessage
from langchain_core.tools import tool
from langchain_openai import ChatOpenAI
from langgraph.checkpoint.memory import InMemorySaver
from pydantic import BaseModel, Field

PROFILE_MESSAGE="내 이름은 수진이고 아이와 여행해. 전체 예산은 20만원이야."
RECOMMENDATION_QUESTION = "내 조건에 맞는 장소를 추천해줘."
BUDGET_UPDATE = "전체 예산을 15만원으로 변경해줘"

class RecommendationInput(BaseModel):
    traveler_name: str = Field(description="이전 대화에서 확인한 여행자 이름")
    companion: str = Field(description="이전 대화에서 확인한 동행인")
    max_budget: int = Field(description="이전 대화에서 확인한 전체 예산 상환(원)")

@tool(args_schema=RecommendationInput)
def recommend_destination(
    traveler_name: str,
    companion: str,
    max_budget: int
) -> str:
    """여행자 이름, 동행인, 전체예산이 모두 있을 때 송파 장소를 추천합니다."""
    # 실제 서비스라면 VectorStore나 외부 장소 API를 호출할 자리
    options = [
        ("롯데월드 아쿠아리움", 180000, "아이와 함께 방문하기 좋은 실내 장소"),
        ("송파책박물관", 120000, "아이와 함께 둘러볼 수 있는 실내 장소"),
        ("석촌호수", 50000, "가볍게 산책할 수 있는 야외 장소")
    ]
    affordable = [
        f"{name}: 수업용 예상 비용 {cost:,}원, {reason}"
        for name, cost, reason in options
        if cost <= max_budget
    ]
    if not affordable:
        return f"{traveler_name}님의 예산 {max_budget:,}원 이하 추천 결과가 없습니다."
    return (
        f"{traveler_name}님과 {companion}조건의 추천 결과\n"
        + "\n".join(affordable)
    )

def build_agent():
    """Thread별 Message State를 저장하는 실제 Agent"""
    load_dotenv()
    if not os.getenv("OPENAI_API_KEY"):
        raise SystemExit(".env에 OPENAI_API_KEY를 설정한 뒤 --run을 사용하세요.")

    return create_agent(
        model=ChatOpenAI(model="gpt-4o-mini", temperature=0),
        tools=[recommend_destination],
        checkpointer=InMemorySaver(),
        system_prompt=(
            "너는 여행 도우미다. 다음 규칙을 지켜라.\n"
            "1. 사용자가 장소 추천을 요청할 때만 recommed_destination을 호출한다.\n"
            "2. 현재 Thread의 대화에서 가장 최근 이름, 동행인, 전체 예산을 찾는다.\n"
            "3. 세 정보가 모두 있을 때만 Tool을 호출한다.\n"
            "4. 정보가 하나라도 없으면 값을 추측하지 말고 사용자에게 질문한다.\n"
            "5. 사용자가 정보를 수정하면 가장 최근 값을 사용한다."
        )
    )


def thread_config(thread_id: str) -> dict:
    """Checkpointer가 사용할 Thread 식별 설정을 만듭니다."""
    return {"configurable": {"thread_id": thread_id}}


def checkpoint_messages(agent, config: dict) -> list:
    """현재 Thread의 Checkpoint에서 저장된 messages를 읽습니다."""
    state = agent.get_state(config)
    return list(state.values.get("messages", []))


def invoke_turn(agent, config: dict, user_message: str) -> list:
    """한 Turn을 실행하고 이번 Turn에 새로 추가된 메시지만 반환합니다."""
    before_count = len(checkpoint_messages(agent, config))
    result = agent.invoke(
        {"messages": [{"role": "user", "content": user_message}]},
        config=config,
    )
    return list(result["messages"][before_count:])


def extract_tool_calls(messages: list) -> list[dict]:
    """이번 Turn에서 모델이 만든 Tool Call을 모두 추출합니다."""
    return [
        tool_call
        for message in messages
        if isinstance(message, AIMessage)
        for tool_call in message.tool_calls
    ]


def final_answer(messages: list) -> str:
    """이번 Turn의 마지막 AIMessage 내용을 반환합니다."""
    for message in reversed(messages):
        if isinstance(message, AIMessage) and not message.tool_calls:
            return str(message.content)
    return "최종 AIMessage 없음"


def verify_no_tool(label: str, messages: list) -> None:
    """정보 저장·수정·부족 Turn에서 Tool 미호출을 확인합니다."""
    calls = extract_tool_calls(messages)
    print(f"\n[{label}]")
    print("Tool 호출 수:", len(calls))
    print("결과:", "PASS" if not calls else "CHECK")
    print("Agent 답변:", final_answer(messages))


def verify_recommendation_call(
    label: str,
    messages: list,
    expected_budget: int,
) -> None:
    """Memory에서 만든 Tool 인자를 예상 이름·동행인·예산과 비교합니다."""
    calls = extract_tool_calls(messages)
    recommendation_calls = [
        call for call in calls if call["name"] == "recommend_destination"
    ]

    print(f"\n[{label}]")
    if not recommendation_calls:
        print("Tool Call: 없음")
        print("결과: CHECK")
        print("Agent 답변:", final_answer(messages))
        return

    arguments = recommendation_calls[-1]["args"]
    name_ok = arguments.get("traveler_name") == "수진"
    companion_ok = "아이" in str(arguments.get("companion", ""))
    budget_ok = arguments.get("max_budget") == expected_budget

    print("Tool:", recommendation_calls[-1]["name"])
    print("Tool 인자:", arguments)
    print("이름 기억:", "PASS" if name_ok else "CHECK")
    print("동행인 기억:", "PASS" if companion_ok else "CHECK")
    print("예산 기억:", "PASS" if budget_ok else "CHECK")
    print(
        "전체 결과:",
        "PASS" if name_ok and companion_ok and budget_ok else "CHECK",
    )
    print("Agent 답변:", final_answer(messages))


def print_checkpoint_evidence(agent, label: str, config: dict) -> None:
    """Thread별 실제 Checkpoint 메시지 수와 사용자 메시지를 출력합니다."""
    state = agent.get_state(config)
    messages = list(state.values.get("messages", []))
    human_messages = [
        str(message.content)
        for message in messages
        if isinstance(message, HumanMessage)
    ]

    print(f"\n[Checkpoint: {label}]")
    print("State keys:", list(state.values.keys()))
    print("저장 메시지 수:", len(messages))
    print("저장 사용자 메시지:")
    for content in human_messages:
        print("-", content)
    print("구조화된 profile 필드가 있는가?:", "profile" in state.values)

def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--run",
        action="store_true",
        help="실제 OpenAI Agent와 Checkpoint를 실행합니다."
    )
    args = parser.parse_args()
    print("Thread 대화 기억 -> Tool인자 생성 -> 수정값 반영 -> 다른 Thread격리")

    if not args.run:
        print("--run을 붙이세요")
        return

    agent = build_agent()
    same_thread = thread_config("student-1")
    different_thread = thread_config("student-2")

    profile_turn = invoke_turn(agent, same_thread, PROFILE_MESSAGE)
    verify_no_tool("1. 같은 Thread에 여행 조건 저장", profile_turn)

if __name__ == "__main__":
    main()