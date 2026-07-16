import numpy as np

def tokenize(text):
  return text.split()

def consine_similarity(a,b):
  norm_a = np.linalg.norm(a)
  norm_b = np.linalg.norm(b)

  if norm_a == 0 or norm_b == 0:
    return 0.0
  return np.dot(a,b) / (norm_a * norm_b)

def softmax(scores):
  shifted = scores - np.max(scores)
  exp_scores = np.exp(shifted) # exp() 각 원소를 지수값으로 변환
  return exp_scores / exp_scores.sum()

def gpt_tokenize():
  print("== tokennize ==")

  sentence = "나는 사과를 먹었다."
  tokens = tokenize(sentence)

  print("문장:", sentence)
  print("토큰:", tokens)

def gpt_embedding():
  print("== Embedding ==")
  embeddings = {
    "사과": np.array([0,9, 0.1, 0.2]),
    "바나나": np.array([0,8, 0.1, 0.3]),
    "자동차": np.array([0,1, 0.9, 0.7])
  }

  apple = embeddings["사과"]
  banana = embeddings["바나나"]
  car = embeddings["자동차"]

  print("사과 벡터:", apple)
  print("바나나 벡터:", banana)
  print("자동차 벡터:", car)
  print("시과 vs 바나나 :", consine_similarity(apple, banana))
  print("사과 vs 자동차 :", consine_similarity(apple, car))

def gpt_attention():
  print("== Attention() 예제 ==")

  tokens = ["철수", "영희", "사과"]

# query는 지금 관심있는 정보
  query = np.array([1.0, 0.0]) # 나는 저런 사람을 찾고싶어 정도로 생각

  # 각 token의 key는 "이 token이 어떤 특징을 갖는가"를 표현
  keys = np.array([
    [1.0, 0.0],
    [0.8, 0.2],
    [0.0, 1.0]
  ])

  # value는 attention결과로 실제 가져올 정보를 의비
  values = np.array([
    [10.0, 0.0],
    [8.0, 2.0],
    [0.0, 10.0]
  ])

  scores = keys @ query

  weights = softmax(scores) # 관련도 점수를 가중치로 변환

  context = weights @ values # 각 value를 가중치만큼 섞는다.

  print("관련된 점수:", scores)
  print("Attent 가중치:", weights)
  print("섞인 결과:", context)

def gpt_next_token():
  print("== 다음 token 예측을 흉내내기 ==")

  candidates = ["갔다", "먹었다", "달린다"]
  scores = np.array([1.2, 2.5, 0.3])
  probabilities = softmax(scores)

  for token, probabilities in zip(candidates, probabilities):
    print("f{token}: {probability:.3f}")

  best_index = np.argmax(probabilities)
  print("가장 가능성 높은 다음 token: ", candidates[best_index])

if __name__ == "__main__":
  gpt_tokenize()
  gpt_embedding()
  gpt_attention()