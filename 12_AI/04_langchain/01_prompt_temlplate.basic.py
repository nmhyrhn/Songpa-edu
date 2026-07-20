from langchain_core.prompts import ChatPromptTemplate, PromptTemplate

def show_prompt_template():
  print("== 01. PRomptTemplate ==")

  template = PromptTemplate.from_template(
    """
    너는 AI 기초를 가르치는 강사다.
    {audience}에게 {topic}을 쉽게 설명해라.

    조건: 
    - 전문 용어는 처름 나올 때 쉬운 말로 정의한다.
    - Numpy에서 배운 vector 개념과 연결한다.
    - 마지막에 한 줄 요약을 붙인다.
    """
  )

  prompt_value = template.invoke({
    "audience": "Python과 Numpy를 막 배운 학생",
    "topic": "Embedding"
  })
  print(prompt_value)
  print()

def show_chat_prompt_template():
  print("== 02. ChatPromptTemplate ==")

  chat_prompt = ChatPromptTemplate.from_messages([
    (
      "system","너는 초보자를 가르치는 AI 강사다. 어려윤 용어는 쉽게 풀어쓴다."
    ),
    (
      "user",
      "질문:{question}\n답변 형식: {answer_format}"
    )
  ])

  prompt_value = chat_prompt.invoke({
    "question": "Attention이 왜 필요한가요?",
    "answer_format": "1. 한줄 정의\n3. 쉬운 예시\n3. Numpy와 연결 \n4. 요약 "
  })

  for message in prompt_value.to_messages():
    print(f"[{message.type}] {message.content}")

if __name__ == "__main__":
  show_prompt_template()
  show_chat_prompt_template()