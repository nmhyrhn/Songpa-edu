# PromptTemplate + LCEL + ChatOpenAI + JsonOutputParser
# 이용하여 퀴즈를 생성하는 AI 프로그램

import os
from dotenv import load_dotenv

from pydantic import BaseModel, Field

from langchain_core.prompts import PromptTemplate
from langchain_core.output_parsers import JsonOutputParser
from langchain_openai import ChatOpenAI

load_dotenv()

# 출력형식정의
class Quiz(BaseModel):
    question: str = Field(description="퀴즈 문제")
    choices: list[str] = Field(description="보기 4개")
    answer: str = Field(description="정답")
    explanation: str = Field(description="정답 설명")

parser = JsonOutputParser(pydantic_object=Quiz)

prompt = PromptTemplate(template="""
                                당신은 AI 강사입니다.
                                주제: {topic}
                                객관식 퀴즈를 1개 만들어 주세요.
                                {format_instructions}
                                """,
                                input_variables=["topic"],
                                partial_variables={
                                    "format_instructions": parser.get_format_instructions()
                                }
                        )

model = ChatOpenAI(
    model="gpt-4o-mini",
    temperature=0.7
)

chain = prompt | model | parser

if __name__ == "__main__":

    result = chain.invoke({
        "topic": "Embedding"
    })

    print("=== 생성된 퀴즈 ===")
    print()

    print("문제")
    print(result["question"])
    print()

    print("보기")
    for i, choice in enumerate(result["choices"], start=1):
        print(f"{i}. {choice}")

    print()
    print("정답: ", result["answer"])
    print()
    print("해설")
    print(result["explanation"])