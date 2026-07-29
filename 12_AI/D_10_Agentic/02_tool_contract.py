"""
일반 함수는 실행할 수 있지만 모델이 사용방법을 알 수 없다.
LangChain Tool은 함수에 이름, 설명, 입력 Schema를 붙여서 모델에 제공

Schema 검증: 필수 필드와 타입처럼 함수 실행 전에 확인할 내용
업무 규칙 검증: 인원수 범위처럼 함수 내부에서 판단할 내용
검색 결과 없음: 입력 오류가 아니라 정상적으로 실행된 결과
"""

import json

from langchain_core.tools import tool
from pydantic import BaseModel, Field, ValidationError

def calculate_day_budget(
    travelers: int, 
    ticket_price: int,
    meal_budget: int    
) -> int:
  if travelers < 1:
    raise ValueError("여행 인원은 1명 이상이어야 합니다.")
  if ticket_price < 0 or meal_budget < 0:
    raise ValueError("입장료와 식비는 0원 이상이어야 합니다.")
  return travelers * (ticket_price + meal_budget)

class BudgetInput(BaseModel):
  """estimate day budget tool이 받을 입력"""
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
  return calculate_day_budget(travelers, ticket_price, meal_budget)


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

def describe_tool(selected_tool) -> None:
  """모델에게 전달되는 Tool계약의 세 요소를 출력"""
  print(f"\nTool 이름: {selected_tool.name}")
  print(f"Tool 설명: ", selected_tool.description)
  print("입력 JSON Schema: ")
  print(
    json.dumps(
      selected_tool.args_schema.model_json_schema(),
      ensure_ascii=False,
      indent=2
    )
  )

def print_schema_error(label: str, arguments: dict) -> None:
  """잘못된 Tool 인자를 실행하고 Schema 오류의 핵심만 출력"""
  try:
    estimate_day_budget.invoke(arguments)
  except ValueError as error:
    first_error = error.errors()[0]
    print(f"{label}: ValidationError")
    print("     오류 위치: ", "->".join(str(item) for item in first_error["loc"]))
    print("     오류 종류: ", first_error["type"])
    print("     오류 설명: ", first_error["msg"])

def main() -> None:
  print("[1. 일반 함수와 Tool객체 비교]")
  print("일반 함수 타입: ", type(calculate_day_budget).__name__)
  print("일반 함수에 args_schema가 있는가?", hasattr(calculate_day_budget, "args_schema"))
  print("Tool 객체 타입: ", type(estimate_day_budget).__name__)
  print("Tool객체에 args_schema가 있는가?", hasattr(estimate_day_budget, "args_schema"))

  print("\n[2. 모델에게 제공되는 Tool 계약]")
  for selected_tool in (estimate_day_budget, search_destination):
    describe_tool(selected_tool)

  print("\n[3. 정상 실행]")
  budget = estimate_day_budget.invoke(
    {"travelers": 3, "ticket_price": 35000, "meal_budget": 20000}
  )
  print("예산 계산: ", f"{budget:,}원")
  print("장소 검색: ", search_destination.invoke({"condition":"실내"}))

  print("\n[4. Schema 검증 실패: 함수 실행 전]")
  print_schema_error(
    "필수 인자 누락",
    {"traveler": 3, "ticket_price": 35000}
  )

  print("\n[5. 함수내부]")
  try:
    estimate_day_budget.invoke(
    {"traveler": 0, "ticket_price": 35000, "meal_budget": 20000}
    )
  except ValueError as error:
    print(type(error).__name__+ ":", error)

if __name__ == "__main__":
  main()