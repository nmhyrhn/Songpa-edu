import argparse
from vectorstore_common import build_inmemory_store

EVAL_CASES = [
    ("외부 자료를 찾아 답변에 사용하는 방법은?", "RAG는")
    ("파일이나 웹 페이지를 읽어오는 구성 요소는?", "Loader는")
    ("긴 문서를 검색 가능한 작은 조각으로 나누는 이유는", "TextSplitter는")
    ("벡터와 원문을 함께 보관하는 검색 저장소는?", "VectorStore는")
    ("긴 대화를 짧게 압축해서 기억하는 방법은?", "Summary memory는")
    ("모델의 출력 형식과 말투를 원하는 대로 맞추는 방법은?", "Fine-tuning은")
]