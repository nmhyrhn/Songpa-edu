# LangChain LCEL
# AI 작업을 하나의 파이프라인으로 만드는 방법

"""
invoke() -> PromptTemplate -> ChatOpenAI -> StrOutputParser
"""

import os
from dotenv import load_dotenv
from langchain_core.output_parsers import StrOutputParser
from langchain_core.prompts import PromptTemplate
from langchain_openai import ChatOpenAI

load_dotenv()

def build_chain():
  # 1. API key 검증
  if not os.getenv("OPENAI_API_KEY"):
    raise ValueError(
      "OPENAI_API_KEY가 설정되어 있지 않습니다. .env파일을 작성해 주세요."
    )
  # 2. 프롬프트 템플릿 정의
  template = PromptTemplate.from_template(
    """
    너는 AI 기초 강사다.
    주제: {topic}
    대상: {audience}

    조건:
    - 초보자도 이해하기 쉬운 비유를 사용해 설명한다.
    - 3문장 이내로 간단하게 답변한다.
    - 마지막에 한 줄 요약을 붙인다.
    """
  )

  # 3. 실제 OpenAI모델
  model = ChatOpenAI(
    model="gpt-4.1-mini",
    temperature=0.7,
    max_tokens=300
  )

  # 4. LCEL(LangChain Expression Language) 구성
  return template | model | StrOutputParser()

  
if __name__ == "__main__":
  
  try:
    chain = build_chain()
    print("=>", chain)

    print("-> LangChain LCEL 실행 중....")
    result = chain.invoke({
      "topic": "Attention",
      "audience": "Python과 Numpy를 막 배운 학생"
    })

    print("\n=== 실행 결과 ===")
    print(result)

  except Exception as e:
    print(f"\n[오류 발생] {e}")