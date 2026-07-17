"""
1. 나쁜 프롬프트 전송
2. 구조적 프롬프트 전송
3. JSON 강제 프롬프트 전송
"""
import os
from dotenv import load_dotenv
import openai 

# .env 파일 로드
load_dotenv()

def build_prompt(role, goal, audience, constraints, output_format, example=None):
    lines=[
        f"[역할/페르소나]\n{role}\n",
        f"[목표]\n[goal]\n",
        f"[독자 대상]\n[audience]\n",
        "[지시 및 제약 조건]"
    ]

    for constraint in constraints:
        lines.append(f"- {constraints}")

    lines.extend([
        "",
        "[출력 형식]",
        output_format,
    ])

    if example:
        lines.extend(["", "[예시]"])
        for index, example in enumerate(example, start=1):
            lines.append(f"예시 {index}.{example}")
    return "\n".join(lines)

        

def get_gpt_response(client, prompt: str) -> str:
    """
    OPENAI API를 호출하고 응답을 받아온다.
    """
    try:
        response = client.chat.completions.create(
            model="gpt-4o-mini",
            messages=[
                {"role": "user", "content": prompt} # role: user, assistant, system
            ],
            temperature=0.7
        )
        return response.choices[0].message.content
    except Exception as e:
        return f"API 호출 에러: {e}"


def run_experiment():
    # API 키 체크
    if not os.getenv("OPENAI_API_KEY"):
        print("[오류] OPENAI_API_KEY가 존재하지 않습니다. .env 파일을 설정해 주세요.")
        return
    
    client = openai.OpenAI(api_key=os.getenv("OPENAI_API_KEY"))
    # 1. 나쁜 프롬프트 전송
    print("=" * 80)
    print("실험1. 나쁜 프롬프트")
    print("=" * 80)
    bad_prompt = "Attention에 대해서 설명해줘"
    print(f"-> 전송할 프롬프트 \n'{bad_prompt}'")
    print("\n[AI 답변 대기 중...]")
    bad_response = get_gpt_response(client, bad_prompt)
    print(f"\n[AI 답변 결과]:\n{bad_response}\n")

    # 2. 구조화된 좋은 프롬포트 전송
    print("=" * 80)
    print("실험2. 좋은 구조적 프롬프트")
    print("=" * 80)    

    good_prompt = build_prompt(
        role="너는 대학교에서 인공지는 기초를 가르치는 강사이다.",
        goal="Transformer 모델의 핵심인 Attention 메커니즘을 설명한다.",
        audience="파이썬 기초 문법과 Numpy 내적(dot product) 연산을 막 끝낸 입문자 수강생",
        constraints=[
            "전문 수학 공식 대신 일상생활의 비유를 사용하여 감을 잡게 할 것",
            "수싯보다는 단어 간의 관련성이라는 직관적 원리를 기술할 것",
            "Numpy의 벡터 연산 및 내적(keys @ query) 개념을 적용하여 설명할 것",
            "답변은 전체 6문장 이내로 압축할 것."
        ],
        output_format=(
            "1. Attention의 한줄 요약 정리\n"
            "2. 일상적 비유 설명\n"
            "3. Numpy 벡터 내적과의 연결점\n"
            "4. 학습자용 핵심 결론 한 마디"
        ),
        example=[
            "두 벡터의 내적값이 크다는 것은 두 단어가 문맥적으로 밀접하게 연관되어 있다는 방증이다."
        ]
    )

    print(f"-> 전송할 프롬프트;\n", {good_prompt})
    print("\n[AI 답변 대기 중...]")
    good_response = get_gpt_response(client, good_prompt)
    print(f"\n[AI 답변 결과]: \n{good_response}\n")



if __name__ == "__main__":
    run_experiment()