import os
from dotenv import load_dotenv
from pydantic import BaseModel, Field
from langchain_core.output_parsers import JsonOutputParser
from langchain_core.prompts import PromptTemplate
from langchain_openai import ChatOpenAI

load_dotenv()

# 1. 원하는 출력 형식을 Pydantic 스키마로 정의
class SentimentAnalysis(BaseModel):
  label: str = Field(description="감정 분석 결과('긍정','부정','중립' 중 하나)")
  reason: str = Field(description="그렇게 판단한 상세 이유")

if __name__ == "__main__":
  try:
    # 1. API key검증
    if not os.getenv("OPENAI_API_KEY"):
      raise ValueError(
        "OPENAI_API_KEY가 설정되어 있지 않습니다."
      )
    # 2. Parser 인스턴스 생성
    parser = JsonOutputParser(pydantic_object=SentimentAnalysis)

    # 3. 프롬프트 템플릿 작성
    template = PromptTemplate(
      template= """
      아래 문장을 분석하여 지정된 JSON형식으로 출력해라
      {format_instructions}
      문장:
      {sentence}
      """,
      input_variables=["sentence"],
      partial_variables={ "format_instructions": parser.get_format_instructions()}
    )

    # ChatOpenAI 모델 생성
    model = ChatOpenAI(
      model="gpt-4.1-mini",
      temperature=0.0,
      max_tokens=200
    )

    # 체인 결합
    chain = template | model | parser

    sentence_to_test = "수업이 어렵지만 예제가 있어서 따라갈만하다."
    print(f"-> 분석할 문장: '{sentence_to_test}'")
    print("-> 실행중 ...")

    result = chain.invoke({"sentence", sentence_to_test})
    print("====파싱된 결과====")
    # print(result)
    print(f"타입: {type(result)}")
    print(f"내용: {result}")
    print(f"감정: {result['label']}")
    print(f"이유: {result['reason']}")
  except Exception as e:
    print(f"\n[오류 발생] {e}")





