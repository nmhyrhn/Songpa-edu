import json
from openai import OpenAI
from dotenv import load_dotenv

load_dotenv()
client = OpenAI()

LESSONS = {
    "shape": "shape는 배열의 모양입니다.",
    "dtype": "dtype은 배열 안 값의 자료형입니다.",
    "attention": "attention은 토큰 간의 관련도를 계산하는 방법입니다."
}

def search_lesson(keyword):
    """수업 자료에서 키워드를 검색했다고 가정 """

    keyword = keyword.lower()

    for title, content in LESSONS.items():
        if keyword in title.lower():
            return {
                "found": True,
                "title": title,
                "content": content
            }
        
    return {
        "found": False,
        "content": "관련 수업 내용을 찾지 못했습니다."
    }

TOOLS = [
    {
        "type": "function",
        "function": {
            "name": "search_lesson",
            "description": "수업 자료를 검색한다.",
            "parameters": {
                "type":"object",
                "properties": {
                    "keyword": {
                        "type": "string"
                    }
                },
                "required": ["keyword"]
            }
        }
    }
]

messages = [
    {
        "role": "system",
        "content": (
            "사용자가 수업 내용을 물어보면 반드시 search_lesson tool을 사용하세요."
        )
    },
    {
        "role":"user",
        "content": "shape 설명해줘"
    }
]

print("=== 1. GPT에게 질문 + Tool 목록 전달 ===")

response = client.chat.completions.create(
    model = "gpt-4.1-mini",
    messages = messages,
    tools = TOOLS
)

assistant = response.choices[0].message
tool_call = assistant.tool_calls[0]

print("\n===2. GPT가 선택한 Tool ===")
print(tool_call.function.name)
print(tool_call.function.arguments)

args = json.loads(tool_call.function.arguments)

print("\n=== 3. Python이 실제 함수 실행 ===")
result = search_lesson(**args)

print(result)

messages.append(assistant)

messages.append(
    {
        "role": "tool",
        "tool_call_id": tool_call.id,
        "content": json.dumps(result, ensure_ascii=False)
    }
)

print("\n ===4. Tool 결과를 GPT에게 전달 ===")
response = client.chat.completions.create(
    model="gpt-4.1-mini",
    messages=messages
)

print("\n=== 최종 답변 ===")
print(response.choices[0].message.content)