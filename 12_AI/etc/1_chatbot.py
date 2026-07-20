import os

import streamlit as st
from dotenv import load_dotenv
from openai import OpenAI

load_dotenv()
api_key = os.getenv("OPENAI_API_KEY")

st.title("AI 상담 챗봇")

if not api_key:
  st.error("OPENAI_API_KEY가 없습니다. .env 파일을 확인해 주세요.")
  st.stop()

client = OpenAI(api_key=api_key)


def get_ai_response(messages):
  response = client.chat.completions.create(
    model="gpt-4o-mini",
    temperature=0.9,
    messages=messages
  )
  return response.choices[0].message.content

if "messages" not in st.session_state:
  st.session_state.messages = [
    {"role": "system", "content": "너는 사용자를 도와주는 상담사야."}
  ]

for message in st.session_state.messages:
  if message["role"] != "system":
    with st.chat_message(message["role"]):
      st.markdown(message["content"])

if user_input := st.chat_input("메시지를 입력해 주세요."):
  st.session_state.messages.append({"role": "user", "content": user_input})
  with st.chat_message("user"):
    st.markdown(user_input)

  try:
    with st.spinner("답변을 작성하고 있습니다..."):
      ai_response = get_ai_response(st.session_state.messages)
  except Exception:
    st.error("답변을 가져오는 중 오류가 발생했습니다.")
  else:
    st.session_state.messages.append(
      {"role": "assistant", "content": ai_response}
    )
    with st.chat_message("assistant"):
      st.markdown(ai_response)
