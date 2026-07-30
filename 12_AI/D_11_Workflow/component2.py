import os
from typing import Literal, NotRequired, TypedDict

from dotenv import load_dotenv
from langchain_openai import ChatOpenAI
from pydantic import BaseModel, model_validator

from component1 import Context
from travel_domain import ItineraryDraft, Plan, TripRequest, ValidationReport


class ReplanDecision(BaseModel):
    """Replanner가 반환해야 하는 구조화된 판단입니다."""

    action: Literal["retry", "stop"]
    reason: str
    plan: Plan | None = None

    @model_validator(mode="after")
    def require_plan_for_retry(self) -> "ReplanDecision":
        """retry를 선택했는데 수정 Plan이 없는 결과를 차단합니다."""
        if self.action == "retry" and self.plan is None:
            raise ValueError("retry에는 수정 Plan이 필요합니다.")
        return self


class WorkflowState(TypedDict):
    """네 Node가 공유하는 전체 Agentic Workflow State입니다."""

    goal: str
    request: TripRequest
    retries: int
    max_retries: int
    path: list[str]
    plan: NotRequired[Plan]
    context: NotRequired[Context]
    draft: NotRequired[ItineraryDraft]
    validation: NotRequired[ValidationReport]
    action: NotRequired[str]
    reason: NotRequired[str]


def require_api_key() -> None:
    """환경 변수를 읽고 실제 LLM 실행에 필요한 API Key를 확인합니다."""
    load_dotenv()
    if not os.getenv("OPENAI_API_KEY"):
        raise SystemExit(".env에 OPENAI_API_KEY를 설정하세요.")


def create_replanner_model():
    """ReplanDecision 형식으로 답하는 실제 OpenAI 모델을 만듭니다."""
    return ChatOpenAI(
        model="gpt-4o-mini",
        temperature=0,
    ).with_structured_output(ReplanDecision)
