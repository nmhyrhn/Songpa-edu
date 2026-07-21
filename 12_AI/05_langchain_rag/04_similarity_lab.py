from rag_core import consine_similarity, vectorize

def compare(question: str, sentence: str)-> None:
    question_vector = vectorize(question)
    sentence_vector = vectorize(sentence)
    score = consine_similarity(question_vector, sentence_vector)

    print("\n" + "=" * 80)
    print("질문: ", question)
    print("문장: ", sentence)
    print("질문 벡터:", dict(question_vector))
    print("문장 벡터:", dict(sentence_vector))
    print(f"cosine similarity: {score:.3f}")

def main() -> None:
    question = "수강 취소 환불 규정을 알려주세요"

    examples = [
        "수강 취소는 수업 시작 24시간 전까지 신청해야 전액 환불된다.",
        "토요일 실습실 운영 시간은 오전 10시부터 오후 5시까지다.",
        "강의를 듣지 않기로 했다면 낸 비용을 돌려받을 수 있다."
    ]

    for sentence in examples:
        compare(question, sentence)

if __name__ == "__main__":
    main()