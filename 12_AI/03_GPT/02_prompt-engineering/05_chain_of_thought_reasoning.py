import os
from dotenv import load_dotenv

load_dotenv()

PROBLEM = """
내 차를 세차하고 싶다.
세차장은 50미터 거리에 있다.
나는 세차장까지 걸어가야 하는가, 아니면 운전해서 가야 하는가?
""".strip()

def has_api_key() -> bool:
    return bool(os.getenv("OPENAI_API_KEY"))

def create_openai_client():
    try:
        from openai import OpenAI
    except ModuleNotFoundError:
        print("[오류] openai패키지가 설치되어 있지 않습니다")
        return None
    
    return OpenAI()


def get_gpt_response(client, prompt: str) -> str:
    response = client.chat.completions.create(
        model="gpt-4o-mini",
        messages=[{"role": "user", "content": prompt}],
        temperature=0.0,
        max_tokens=500
    )
    return response.choices[0].message.content.strip()


def build_direct_prompt() -> str:
    """조건 정리 없이 바로 답을 요구하는 프롬프트"""
    return f""" 아래 질문에 짧게 답하세요. 
    질문:
    {PROBLEM}
    """.strip()

def build_reasoning_prompt() -> str:
    """답변 전에 핵심 조건을 짧게 확인하도록 만든 프롬프트"""
    return f""" 
    아래 질문에 답하기 전에 필요한 조건을 짧게 확인한 뒤 최종 답변을 작성하세요.
    

    질문:
    {PROBLEM}

    출력 형식:
    [핵심 조건]
    1. 세차의 대상;
    2. 그 대상이 이동해야 하는 장소:
    3. 선택해야 하는 이동 방식:

    [최종 답변]
    단계적으로 논리적인 생각을 통해서 답변을 해줘
    """.strip()

def run_experiment() -> None:
    if not has_api_key():
        print("[오류] OPENAI_API_KEY가 없습니다. .env 파일이나 환경변수를 확인해주세요.")
        return
    
    client = create_openai_client()
    if client is None:
        return
    
    print("=" * 80)
    print("실험1. 바로 답안 요구하는 프롬프트")
    print("=" * 80)
    print(build_direct_prompt())
    print("\n[AI 답변]")
    print(get_gpt_response(client, build_direct_prompt()))

    print("=" * 80)
    print("실험2. 핵심 조건을 먼저 확인하는 프롬프트")
    print("=" * 80)
    print(build_reasoning_prompt())
    print("\n[AI 답변]")
    print(get_gpt_response(client, build_reasoning_prompt()))

if __name__ == "__main__":
    run_experiment()