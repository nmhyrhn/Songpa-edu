# function calling

LESSONS = {
    "shape": "shape는 배열의 모양입니다. 2차원 배열에서는 (행, 열)로 읽습니다.",
    "dtype": "dtype은 배열 안 값의 자료형입니다.",
    "attention": "attention은 문장 안 token사이의 관련도를 계산하는 방법입니다.",
    "fine-tuning": "fine-tuning은 예시 데이터로 모델의 답변 패턴을 추가 학습시키는 방법입니다."
}

def search_lesson(keyword):
    """수업 자료에서 키워드를 검색했다고 가정 """

    keyword = keyword.lower()

    for title, content in LESSONS.items():
        if keyword in title.lower() or keyword in content.lower():
            return {
                "found": True,
                "title": title,
                "content": content
            }
        
    return {
        "found": False,
        "title": None,
        "content": "관련 수업 내용을 찾지 못했습니다."
    }

def make_quiz(topic):
    """ 주제에 맞는 간단한 퀴즈를 만드는 tool """
    return {
        "topic": topic,
        "question": f"{topic}을 초보자에게 한 문자응로 설명해보세요.",
        "answer_hint": "정의 + 쉬운 예시를 포함하면 좋습니다."
    }

def fake_model_decide_tool(user_message):

    if "퀴즈" in user_message:
        topic = user_message.replace("퀴즈", "").strip() or "Numpy"
        return {
            "tool_name": "make_quiz",
            "arguments": {"topic": topic}
        }
    
    if "찾아" in user_message or "설명" in user_message:
        keyword = user_message.replace("찾아", "").replace("설명", "").strip()
        return {
            "tool_name": "search_lesson",
            "arguments": {"keyword": keyword}
        }
    
    return {
        "tool_name": None,
        "arguments": {}
    }


def run_tool(tool_call):
    tool_name = tool_call["tool_name"]
    arguments = tool_call["arguments"]

    if tool_name == "search_lesson":
        return search_lesson(**arguments)
    
    if tool_name == "make_quiz":
        return make_quiz(**arguments)
    
    return {"error": " 사용할 tool이 없습니다."}

def answer_with_tool_result(user_message, tool_result):
    if "error" in tool_result:
        return "질문을 처리할 도구를 찾지 못했습니다."
    
    if "question" in tool_result:
        return f"쿼즈: {tool_result['question']}\n힌트 : {tool_result['answer_hint']}"
    
    if tool_result.get("found"):
        return f"{tool_result['title']} 관련 내용입니다.\n{tool_result['content']}"
    
    return tool_result['content']

def demo(user_message):
    print('사용자:', user_message)

    tool_call = fake_model_decide_tool(user_message)
    print("모델이 선택한 tool:", tool_call)

    tool_result = run_tool(tool_call)
    print("tool 실행결과:", tool_result)

    final_answer = answer_with_tool_result(user_message, tool_result)
    print("최종 답변:")
    print(final_answer)


if __name__ == "__main__":
    demo("shape 설명")
    demo("attention 퀴즈")
    demo("없는내용 찾아")