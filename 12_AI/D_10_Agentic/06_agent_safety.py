# 실패 종류에 따라 재시도, Fallback, Handoff를 결정

"""
안전 규칙
FOUND                  -> 성공 종료
NOT_FOUND  / TIMEOUT   -> 최대 횟수 안에서 재시도
최대 횟수 도달         -> Fallback 후 종료
AUTH_ERROR             -> 재시도 없이 Handoff
종료 Edge가 없는 Graph -> recursion_limit가 최종 차단
"""
import argparse
import os
from typing import Literal, TypedDict

from dotenv import load_dotenv
from langchain_core.messages import AIMessage, SystemMessage, ToolMessage
from langchain_core.tools import tool
from langchain_openai import ChatOpenAI
from langgraph.errors import GraphRecursionError
from langgraph.prebuilt import ToolNode
from langgraph.graph import END, START, MessagesState, StateGraph
from pydantic import BaseModel, Field

ResultCode = Literal[
    "NOT_RUN",
    "FOUND",
    "NOT_FOUND",
    "TIMEOUT",
    "AUTH_ERROR"
]
FinalStatus = Literal["running", "success", "fallback", "handoff"]

RETRYABLE_RESULTS = {"NOT_FOUND", "TIMEOUT"}
NON_RETRYABLE_RESULTS = {"AUTH_ERROR"}

class SafetyState(TypedDict):
    attempts: int
    max_attempts: int
    simulated_results: list[ResultCode]
    latest_result: ResultCode
    observations: list[str]
    status: FinalStatus
    termination_reason: str
    final_response: str

def route_after_search(
    state: SafetyState,
) -> Literal[
    "search_destination",
    "finalize_success",
    "finalize_fallback",
    "finalize_handoff",
]:
    """검색 결과의 재시도 가능 여부와 최대 횟수로 다음 경로를 결정합니다."""
    result = state["latest_result"]

    if result == "FOUND":
        return "finalize_success"
    if result in NON_RETRYABLE_RESULTS:
        return "finalize_handoff"
    if result in RETRYABLE_RESULTS and state["attempts"] < state["max_attempts"]:
        return "search_destination"

    # 알 수 없는 오류나 재시도 한도 도달은 더 반복하지 않습니다.
    return "finalize_fallback"

def simulate_search_node(state: SafetyState) -> dict:
    """외부 검색 Tool/API의 결과를 ResultCode로 재현합니다."""
    attempt = state["attempts"] + 1
    result_index = min(attempt - 1, len(state["simulated_results"]) - 1)
    result = state["simulated_results"][result_index]
    observation = f"{attempt}번째 검색 결과: {result}"
    return {
        "attempts": attempt,
        "latest_result": result,
        "observations": state["observations"] + [observation],
    }

def finalize_success(state: SafetyState) -> dict:
    """검색 성공 상태와 사용자 응답을 State에 기록합니다."""
    return {
        "status": "success",
        "termination_reason": "FOUND",
        "final_response": (
            f"{state['attempts']}번의 시도 후 조건에 맞는 장소를 찾았습니다."
        ),
    }


def finalize_fallback(state: SafetyState) -> dict:
    """재시도 한도 도달 후 조건 변경 요청을 State에 기록합니다."""
    return {
        "status": "fallback",
        "termination_reason": "MAX_ATTEMPTS",
        "final_response": (
            "조건에 맞는 장소를 찾지 못했습니다. "
            "실내, 무료, 산책 중 하나로 조건을 변경해 주세요."
        ),
    }

def finalize_handoff(state: SafetyState) -> dict:
    """재시도할 수 없는 오류를 담당자 전달 상태로 기록합니다."""
    return {
        "status": "handoff",
        "termination_reason": f"NON_RETRYABLE:{state['latest_result']}",
        "final_response": (
            "인증 문제로 자동 검색을 계속할 수 없습니다. 담당자 확인이 필요합니다."
        ),
    }


def build_safe_graph():
    builder = StateGraph(SafetyState)
    builder.add_node("search_destination", simulate_search_node)
    builder.add_node("finalize_success", finalize_success)
    builder.add_node("finalize_fallback", finalize_fallback)
    builder.add_node("finalize_handoff", finalize_handoff)

    builder.add_edge(START, "search_destination")
    builder.add_conditional_edges("search_destination", route_after_search)
    for final_node in (
        "finalize_success",
        "finalize_fallback",
        "finalize_handoff"
    ):
        builder.add_edge(final_node, END)
    return builder.compile()

def initial_state(
    simulated_results: list[ResultCode],
    max_attempts: int,
) -> SafetyState:
    """사례별 초기 State를 만듭니다."""
    if max_attempts < 1:
        raise ValueError("max_attempts는 1 이상이어야 합니다.")
    if not simulated_results:
        raise ValueError("simulated_results는 한 개 이상이어야 합니다.")
    return {
        "attempts": 0,
        "max_attempts": max_attempts,
        "simulated_results": simulated_results,
        "latest_result": "NOT_RUN",
        "observations": [],
        "status": "running",
        "termination_reason": "",
        "final_response": "",
    }

def run_safe_case(
    graph,
    label: str,
    simulated_results: list[ResultCode],
    max_attempts: int,
    expected_status: FinalStatus,
    expected_attempts: int,
) -> None:
    """안전 Graph의 결과를 예상 상태와 비교합니다."""
    result = graph.invoke(
        initial_state(simulated_results, max_attempts),
        # 정상 Graph에서는 업무 조건으로 먼저 END에 도달해야 합니다.
        config={"recursion_limit": 20},
    )
    passed = (
        result["status"] == expected_status
        and result["attempts"] == expected_attempts
    )

    print(f"\n[사례: {label}]")
    print("예정된 Tool 결과:", simulated_results)
    print("관찰 기록:", result["observations"])
    print("시도 횟수:", result["attempts"])
    print("최종 status:", result["status"])
    print("종료 이유:", result["termination_reason"])
    print("사용자 응답:", result["final_response"])
    print("검증:", "PASS" if passed else "CHECK")

def build_broken_graph():
    """업무 종료 조건을 실수로 누락한 잘못된 Graph입니다."""
    builder = StateGraph(SafetyState)
    builder.add_node("search_destination", simulate_search_node)
    builder.add_edge(START, "search_destination")
    builder.add_edge("search_destination", "search_destination")
    return builder.compile()

def run_broken_graph_case() -> None:
    """종료 Edge가 없는 Graph를 실행 엔진의 최종 한도로 차단합니다."""
    print("\n[사례: 종료 조건이 없는 잘못된 Graph]")
    try:
        build_broken_graph().invoke(
            initial_state(["NOT_FOUND"], max_attempts=3),
            config={"recursion_limit": 5},
        )
    except GraphRecursionError:
        print("GraphRecursionError: recursion_limit가 끝나지 않는 Graph를 차단했습니다.")
        print("검증: PASS")
        return
    print("검증: CHECK")


class DestinationInput(BaseModel):
    condition: str = Field(description="사용자가 원하는 여행 장소 조건")
    search_mode: Literal["strict", "broad"] = Field(
        description="첫 검색은 strict, 실패 후 핵심 조건 재검색은 broad"
    )


@tool(args_schema=DestinationInput)
def destination_lookup(
    condition: str,
    search_mode: Literal["strict", "broad"],
) -> str:
    """장소를 검색하고 FOUND, NOT_FOUND, AUTH_ERROR 상태를 반환합니다."""
    if "회원 전용" in condition:
        return "AUTH_ERROR | 회원 전용 데이터 접근 권한 없음"

    if search_mode == "strict":
        return (
            "NOT_FOUND | mode=strict | 정확히 일치하는 장소 없음 | "
            "retry_hint=핵심 조건 하나로 broad 재검색"
        )

    broad_data = {
        "실내": "롯데월드 아쿠아리움, 서울스카이",
        "무료": "석촌호수, 올림픽공원",
        "산책": "석촌호수, 올림픽공원",
    }
    matches = [
        places
        for keyword, places in broad_data.items()
        if keyword in condition
    ]
    if matches:
        return f"FOUND | mode=broad | places={', '.join(dict.fromkeys(matches))}"
    return "NOT_FOUND | mode=broad | 핵심 조건으로도 결과 없음"


class AgentSafetyState(MessagesState):
    """실제 Agent 메시지에 안전 제어 필드를 추가한 Graph State입니다."""

    attempts: int
    max_attempts: int
    latest_result: ResultCode
    observations: list[str]
    status: FinalStatus
    termination_reason: str
    final_response: str

def integrated_initial_state(question: str, max_attempts: int = 2) -> AgentSafetyState:
    """실제 Agent 안전 Graph의 초기 State입니다."""
    return {
        "messages": [{"role": "user", "content": question}],
        "attempts": 0,
        "max_attempts": max_attempts,
        "latest_result": "NOT_RUN",
        "observations": [],
        "status": "running",
        "termination_reason": "",
        "final_response": "",
    }


def print_integrated_trace(state: AgentSafetyState) -> None:
    """실제 Agent, Tool, guard가 남긴 실행 증거를 출력합니다."""
    action_number = 0
    observation_number = 0
    for message in state["messages"]:
        if isinstance(message, AIMessage) and message.tool_calls:
            for tool_call in message.tool_calls:
                action_number += 1
                print(
                    f"Action {action_number}: {tool_call['name']} "
                    f"{tool_call['args']}"
                )
        if isinstance(message, ToolMessage):
            observation_number += 1
            print(f"Observation {observation_number}: {message.content}")
    print("guard observations:", state["observations"])
    print("최종 status:", state["status"])
    print("종료 이유:", state["termination_reason"])
    print("최종 응답:", state["final_response"])

def run_integrated_case(
    graph,
    label: str,
    question: str,
    expected_status: FinalStatus,
    expected_attempts: int,
) -> None:
    """실제 Agent에 guard가 적용된 결과를 예상값과 비교합니다."""
    result = graph.invoke(
        integrated_initial_state(question),
        config={"recursion_limit": 20},
    )
    passed = (
        result["status"] == expected_status
        and result["attempts"] == expected_attempts
    )
    print(f"\n[실제 Agent 사례: {label}]")
    print("질문:", question)
    print_integrated_trace(result)
    print("검증:", "PASS" if passed else "CHECK")

def result_code_from_tool_message(message: ToolMessage) -> ResultCode:
    """ToolMessage의 표준 상태 문자열을 ResultCode로 변환합니다."""
    content = str(message.content)
    for result in ("AUTH_ERROR", "NOT_FOUND", "TIMEOUT", "FOUND"):
        if content.startswith(result):
            return result  # type: ignore[return-value]
    return "NOT_RUN"

def guard_tool_result(state: AgentSafetyState) -> dict:
    """ToolMessage를 읽고 시도 횟수와 최신 결과를 State에 기록합니다."""
    latest_tool_message = next(
        (
            message
            for message in reversed(state["messages"])
            if isinstance(message, ToolMessage)
        ),
        None,
    )
    if latest_tool_message is None:
        return {
            "latest_result": "NOT_RUN",
            "observations": state["observations"] + ["ToolMessage 없음"],
        }

    attempt = state["attempts"] + 1
    result = result_code_from_tool_message(latest_tool_message)
    return {
        "attempts": attempt,
        "latest_result": result,
        "observations": state["observations"]
        + [f"{attempt}번째 Tool 결과: {latest_tool_message.content}"],
    }

def finalize_agent_success(state: AgentSafetyState) -> dict:
    """성공한 Tool 결과를 사용한 모델 답변을 최종 State에 기록합니다."""
    return {
        "status": "success",
        "termination_reason": "FOUND",
        "final_response": str(state["messages"][-1].content),
    }


def finalize_agent_clarification(state: AgentSafetyState) -> dict:
    """Tool 호출 전 정보가 부족한 경우 모델의 추가 질문을 기록합니다."""
    return {
        "status": "fallback",
        "termination_reason": "MISSING_INPUT",
        "final_response": str(state["messages"][-1].content),
    }
def route_after_agent(
    state: AgentSafetyState,
) -> Literal[
    "tools",
    "finalize_success",
    "finalize_fallback",
    "finalize_clarification",
]:
    """모델의 Tool Call 유무와 직전 결과로 다음 Node를 선택합니다."""
    last_message = state["messages"][-1]
    if isinstance(last_message, AIMessage) and last_message.tool_calls:
        return "tools"
    if state["latest_result"] == "FOUND":
        return "finalize_success"
    if state["attempts"] == 0:
        return "finalize_clarification"
    return "finalize_fallback"

def route_after_guard(
    state: AgentSafetyState,
) -> Literal["agent", "finalize_fallback", "finalize_handoff"]:
    """업무 안전 규칙으로 Agent 재실행, Fallback, Handoff를 결정합니다."""
    result = state["latest_result"]
    if result == "AUTH_ERROR":
        return "finalize_handoff"
    if result == "FOUND":
        return "agent"
    if result in RETRYABLE_RESULTS and state["attempts"] < state["max_attempts"]:
        return "agent"
    return "finalize_fallback"

def build_integrated_agent_graph():
    """실제 Agent/Tool 반복을 결정적 guard가 감싸는 Graph를 만듭니다."""
    tools = [destination_lookup]
    model_with_tools = ChatOpenAI(model="gpt-4o-mini", temperature=0).bind_tools(tools)

    def call_agent(state: AgentSafetyState) -> dict:
        response = model_with_tools.invoke(
            [
                SystemMessage(
                    content=(
                        "너는 여행 도우미다. 장소 검색 첫 호출은 사용자 조건 전체와 "
                        "search_mode='strict'를 사용한다. NOT_FOUND를 받으면 핵심 "
                        "조건 하나로 search_mode='broad'를 호출한다. FOUND를 받으면 "
                        "그 결과로 답하고, AUTH_ERROR를 받으면 추가 Tool을 호출하지 "
                        "않는다. 값을 임의로 만들지 않는다."
                    )
                ),
                *state["messages"],
            ]
        )
        return {"messages": [response]}

    builder = StateGraph(AgentSafetyState)
    builder.add_node("agent", call_agent)
    builder.add_node("tools", ToolNode(tools))
    builder.add_node("guard", guard_tool_result)
    builder.add_node("finalize_success", finalize_agent_success)
    builder.add_node("finalize_fallback", finalize_fallback)
    builder.add_node("finalize_handoff", finalize_handoff)
    builder.add_node("finalize_clarification", finalize_agent_clarification)

    builder.add_edge(START, "agent")
    builder.add_conditional_edges("agent", route_after_agent)
    builder.add_edge("tools", "guard")
    builder.add_conditional_edges("guard", route_after_guard)
    for final_node in (
        "finalize_success",
        "finalize_fallback",
        "finalize_handoff",
        "finalize_clarification",
    ):
        builder.add_edge(final_node, END)
    return builder.compile()

def run_integrated_agent_demo() -> None:
    """실제 Agent Graph에서 success, fallback, handoff를 확인합니다."""
    load_dotenv()
    if not os.getenv("OPENAI_API_KEY"):
        raise SystemExit(".env에 OPENAI_API_KEY를 설정한 뒤 --run을 사용하세요.")

    graph = build_integrated_agent_graph()
    run_integrated_case(
        graph,
        label="실패 후 broad 검색 성공",
        question="조용하고 자연을 느낄 수 있는 실내 장소를 찾아줘",
        expected_status="success",
        expected_attempts=2,
    )
    run_integrated_case(
        graph,
        label="두 번 실패 후 Fallback",
        question="반려동물 동반 숙박 장소를 찾아줘",
        expected_status="fallback",
        expected_attempts=2,
    )
    run_integrated_case(
        graph,
        label="인증 오류 즉시 Handoff",
        question="회원 전용 여행 장소를 찾아줘",
        expected_status="handoff",
        expected_attempts=1,
    )



def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument(
        "--run",
        action="store_true",
        help="실제 ChatOpenAI Agent에 guard안전 규칙을 결합합니다."
    )
    args = parser.parse_args()
    print("[안전 제어 원칙] : 재시도 여부와 최대횟수는 모델이 아니라 Graph State와 Edge가 강제한다.")

    graph = build_safe_graph()
    run_safe_case(
        graph,
        label="두 번째 시도에서 성공",
        simulated_results=["NOT_FOUND", "FOUND"],
        max_attempts=3,
        expected_status="success",
        expected_attempts=2
    )

    run_safe_case(
        graph,
        label="재시도 한도 도달 후 Fallback",
        simulated_results=["NOT_FOUND", "TIME_OUT", "NOT_FOUND"],
        max_attempts=3,
        expected_status="fallback",
        expected_attempts=3
    )

    run_safe_case(
        graph,
        label="재시도 불가능 오류 Handoff",
        simulated_results=["AUTH_ERROR"],
        max_attempts=3,
        expected_status="handoff",
        expected_attempts=1
    )
    print("\n" + "=" * 80)
    print("OpenAI와 guard 통합")
    run_integrated_agent_demo()

    
if __name__ == "__main__":
    main()