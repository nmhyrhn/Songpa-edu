import argparse

from langchain_core.documents import Document
from langchain_core.prompts import ChatPromptTemplate
from langchain_openai import ChatOpenAI

from vectorstore_common import load_environment, open_chroma_store

PROMPT = ChatPromptTemplate.from_messages(
    [
        (
            "system",
            "너는 AI 수업 도우미다. 아래 Context만 근거로 답하라."
            "근거가 없으면 문서에서 찾을 수 없다고 답하라"
            "답변 마지막에 사용한 출처를 표시하라.\n\n[Context]\n{context}",
        ),
        ("human", "{question}")
    ]
)


def parse_agrs() -> argparse.Namespace:
    parser = argparse.ArgumentParser()
    parser.add_argument("--question", default="VectorStore는 왜 사용하나요?")
    parser.add_argument("--top-k", type=int, default=2)
    parser.add_argument(
        "--rebuild-index",
        action="store_true",
        help="기존 Chroma collection을 초기화하고 문서를 다시 Embedding합니다."
    )
    args = parser.parse_args()
    if args.top_k < 1:
        parser.error("--top-k는 1 이상이어야 합니다.")
    return args

def format_context(documents: list[Document]) -> str:
    if not documents:
        return "검색된 문서 근거 없음"
    return "\n\n".join(
        f"[출처: {doc.metadata.get('source')}:{doc.metadata.get('line')}]\n"
        f"{doc.page_content}" 
        for doc in documents
    )

def main() -> None:
    args = parse_agrs()
    load_environment()
    print("VectorStore가 찾은 Document는 어떻게 출처가 있는 답변으로 변경될까?")

    store, indexed_now, document_count = open_chroma_store(args.rebuild_index)
    print("[Chroma Index]")
    print("상태: ", "새로 색인함" if indexed_now else "기존 색인 재사용")
    print("저장 Document 수: ", document_count)
    print()

    retriever = store.as_retriever(search_kwargs={"k": args.top_k})

    retrieved_docs = retriever.invoke(args.question)
    context = format_context(retrieved_docs)

    print("[1. Retriever가 찾은 Document]")
    for rank, document in enumerate(retrieved_docs, start=1):
        print(
            f"{rank}. {document.id} / source={document.metadata.get('source')}:"
            f"{document.metadata.get('line')}\n     {document.page_content}" 
        )    

    promt_value = PROMPT.invoke({"context": context, "question": args.question})
    # print("\n",promt_value)
    model = ChatOpenAI(model="gpt-4o-mini", temperature=0, max_tokens=300)
    answer = model.invoke(promt_value).content

    print("\n[실제 LLM 답변]")
    print(answer)


if __name__ == "__main__":
    main()