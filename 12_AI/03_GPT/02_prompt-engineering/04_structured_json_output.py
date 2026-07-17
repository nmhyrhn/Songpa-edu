# 답변의 결과를 사용자가 편한형태말고
# 프로그램이 바로 처리할수있도록 JSON데이터로 변경

import os
import json
from dotenv import load_dotenv

load_dotenv()

FEEDBACKS = [
    {
        "id": 1,
        "text": "진도가 조금 빠르지만 실습코드가 많아서 따라가다보니 이해가 됩니다."
    },
    {
        "id": 2,
        "text": "API 키 설정에서 계속 막혀서 오늘 수업은 거의 못 따라갔습니다."
    },
    {
        "id": 3,
        "text": "내용은 어려운데 예제를 좀더 연습해보고 싶습니다."
    }
]

def has_api_key() -> bool:
    return bool(os.getenv("OPENAI_API_KEY"))

def create_openai_client():
    try:
        from openai import OpenAI
    except ModuleNotFoundError:
        print("[오류] openai패키지가 설치되어 있지 않습니다")
        return None
    
    return OpenAI()

def get_gpt_response(client, prompt: str, json_mode: bool = False) -> str:

    request = {
        "model" : "gpt-4o-mini",
        "messages" : [{"role":"user", "content": prompt}],
        "temperature": 0.0,
        "max_tokens": 700
    }

    if json_mode:
        request["response_format"] = {"type": "json_object"}
    
    response = client.chat.completions.create(**request)
    return response.choices[0].message.content.strip()

def build_free_text_prompt() -> str:
    """구조를 강제화 하지 않은 일반 프롬프트"""
    feedback_text = "\n".join(f" - {item['text']}" for item in FEEDBACKS)
    return f""" 아래 수강평들을 읽고 전반적인 만족도를 분석해줘. {feedback_text}""".strip()

def build_json_prompt() -> str:
    feedback_json = json.dumps(FEEDBACKS, ensure_ascii=False, indent=2)
    return f"""
    너는 교육센터 수강평을 분석하는 데이터 분류기다.
    아래 <feedbacks>안의 JSON배열을 분석하라.

    규칙:
    - 반드시 순수 JSON객체만 출력한다.
    - 최상위 키는 "items"하나만 사용한다.
    - 각 item은 id, sentiment, score, key_reason필드만 가진다.
    - sentiment는 "positive", "negative", "mixed"중 하나만 사용한다.
    - score는 0부터 100 사이의 정수로 작성한다.
    - key_reason은 한글 1문장으로 작성한다.

    출력예시:
    {{
        "items": [
            {{
                "id": 1,
                "sentiment": "mixed",
                "score": 75,
                "key_reason": "어려움은 있지만 실습이 이해에 도움이 된다는 평가입니다.
            }}
        ]
    }}
    <feedbacks>
    {feedback_json}
    </feedbacks>
    """.strip()

def run_experiment() -> None:
    if not has_api_key():
        print("[오류] OPENAPI_API_KEY가 없습니다. .env 파일을 확인해주세요.")
        return
    
    client = create_openai_client()
    if client is None:
        return
    

    print("*" * 80)
    print("실험1")
    print("*" * 80)
    json_response = get_gpt_response(client, build_free_text_prompt())
    print(json_response)

    print("*" * 80)
    print("실험2")
    print("*" * 80)
    json_response = get_gpt_response(client, build_json_prompt(), json_mode=True)
    print(json_response)

if __name__ == "__main__":
    run_experiment()