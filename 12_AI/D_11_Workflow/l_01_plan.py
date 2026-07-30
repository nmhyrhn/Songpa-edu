import argparse
import os

from dotenv import load_dotenv
from langchain_openai import ChatOpenAI
from pydantic import ValidationError

from travel_domain import Plan, TASK_DESCRIPTIONS

DEMO_GOALS = [
    "송파구 관광지 한 곳의 3명 예상 비용만 계산해줘.",
    "아이와 비 오는 날 20만원 이내의 송파 하루 일정을 만들어줘."
]

def show_invalid_plan() -> None:
    """등록되지 않은 Task와 중복 Task가 Plan계약에서 차단됨을 보여줌"""
    examples = [
        {
            "goal": "송파 여행 계획",
            "tasks": ["search_everything"],
            "success_criteria": ["검색 완료"]
        },
        {
            "goal": "송파 여행 계획",
            "tasks": ["destination", "destination"],
            "success_criteria": ["장소 선택"]
        }
    ]
    print("\n[잘못된 Plan 차단]")
    for value in examples:
        try:
            Plan.model_validate(value)
        except ValidationError as error:
            print("-", error.errors()[0]["msg"])

def generate_plan(goal: str):
    load_dotenv()
    if not os.getenv("OPENAI_API_KEY"):
        raise SystemExit(".env에 OPENAI_API_KEY를 설정하세요.")

    planner = ChatOpenAI(
        model="gpt-4o-mini",
        temperature=0,
    ).with_structured_output(Plan)
    task_guide = "\n".join(
        f"- {name}: {description}" for name, description in TASK_DESCRIPTIONS.items()
    )
    plan = planner.invoke(
        "목표를 실행 Task로 분해하세요. 아래 등록 Task중 꼭 필요한 것만 "
        f"실행 순서대로 선택하세요.\n{task_guide}\n[목표]\n{goal}"
    )
    return Plan.model_validate(plan)

def print_plan(plan: Plan) -> None:
    """검증된 Plan의 Task순서와 각 Task의 실제 책임을 출력"""
    print("목표: ", plan.goal)
    for index, task in enumerate(plan.tasks, start=1):
        print(f"{index}. {task}: {TASK_DESCRIPTIONS[task]}")
    print("완료 기준:", plan.success_criteria)

def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--run", action="store_true")
    args = parser.parse_args()

    """
    - Planner가 필요없는 경우 > 모든 요청이 destination -> budget 순서라면 Workflow로도 충분
    - Planner가 필요한 경우 > weather, destination, budget, compose 필요여부에 따라 달라진다.
    """
    show_invalid_plan()

    if not args.run:
        print("\n --run을 붙여서 실행해주세요.")
        return

    plans = []
    for goal in DEMO_GOALS:
        plan = generate_plan(goal)
        plans.append(plan)
        print("\n[LLM Plan]")
        print_plan(plan)

if __name__ == "__main__":
    main()