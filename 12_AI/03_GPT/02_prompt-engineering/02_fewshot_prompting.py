# 예시문 없이 규칙만 알려주는 zero-shot
# 올바른 입출력 예시 쌍으로 프롬프트에 주입하는 few-shot

import os
from dotenv import load_dotenv
import openai 

load_dotenv()

def get_gpt_response(client, prompt: str) -> str:
    """ OPENAI API를 호출하여 응답을 받아옵니다. """
    try:
        response = client.chat.completions.create(
            model="gpt-4o-mini",
            messages=[
                {"role": "user", "content": prompt}
            ],
            temperature=0.0
        )
        return response.choices[0].message.content.strip()
    except Exception as e:
        return f"API 호출 에러: {e}"


def run_fewshot_experiment():
    if not os.getenv("OPENAI_API_KEY"):
        print("[오류] OPENAI_API_KEY가 존재하지 않습니다. .env 파일을 설정해 주세요.")
        return
    
    client = openai.OpenAI(api_key=os.getenv("OPENAI_API_KEY"))
    test_cases = [
        "진도가 너무 많은데 뇌정지가 오고있어",   # ACADEMIC (어려운 진도 호소)
        "출석 큐알코드 안 찍히는데 수동체크 요망", #  ADMIn (행정 및 출석 시스템 오류)
        "강사님 오늘은 커피가 필요한데 간식은 없을까요 ㅋㅋㅋ", # CHAT(단순 대화)
        "강의실 뒤쪽 에어콘 온도 좀 올려주세요. 너무 추워요" # ADMIN(시설 및 인프라)
    ]

    # 1. Zero-shot 정의(예시 없이 규칙만 전달)
    zero_shot_template = """
    너는 교육 센터의 질문 분류기다. 수강생의 피드백 문장을 아래 3가지 카테고리 중 하나로 
    정확하게 분류해라.
    - 'ACADEMIC': 진도, 소스코드 오류, 에러 질문
    - 'ADMIN': 행정 문의, 출석 QR 오류, 강의실 냉난방 등 시설문의
    - 'CHAT': 일상대화, 친근한 농담, 인사

    결과는 오직 카테고리 단어('ACADEMIC', 'ADMIN', 'CHAT')만 출력

    문장: {sentence}
    """
    # 2. Few-shot 프롬프트 정의
    few_shot_template = """
    너는 교육 센터의 질문 분류기다. 수강생의 피드백 문장을 아래 3가지 카테고리 중 하나로 정확하게
    분류해라.
    - 'ACADEMIC': 진도, 소스코드 오류, 에러 질문
    - 'ADMIN': 행정 문의, 출석 QR 오류, 강의실 냉난방 등 시설문의
    - 'CHAT': 일상대화, 친근한 농담, 인사
    결과는 오직 카테고리 단어('ACADEMIC', 'ADMIN', 'CHAT')만 출력

    [예시 쌍]
    입력: "코드 돌렸는데 index out of range 에러 나요"
    출력: ACADEMIC

    입력: "행정실에서 취업 지원서 제출 마감이 언제인지 가르쳐주세요"
    출력: ADMIN

    입력: "오늘 날씨 좋네요 화이팅!"
    출력: CHAT

    입력: "진도가 너무 빨라서 멘붕이 옵니다"
    출력: ACADEMIC

    입력: "ㅋㅋㅋ 대박이네요 강사님"
    출력: CHAT

    [실제 판정할 문장]
    입력: "{sentence}"
    출력: """


    print("=" * 80)
    print("Zero-shot vs Few-shot 성능 비교")
    print("=" * 80)


    for i, test_sentence in enumerate(test_cases, start=1):
        print(f"\n[테스트 케이스 {i}] 대상 문장 : '{test_sentence}'")

        # Zero-shot 예측
        zero_prompt = zero_shot_template.format(sentence=test_sentence)
        zero_res = get_gpt_response(client, zero_prompt)

        # Few-shot 예측
        few_prompt = few_shot_template.format(sentence=test_sentence)
        few_res = get_gpt_response(client, few_prompt)

        print(f"    - Zero-shot 판정 결과: {zero_res}")
        print(f"    - Few-shot 판정 결과 : {few_res}")

        if zero_res != few_res:
            print("    [차이 감지] 예시문이 들어가면서 애매한 신조어/구어체를 더 정확하게 판정")


if __name__ == "__main__":
    run_fewshot_experiment()