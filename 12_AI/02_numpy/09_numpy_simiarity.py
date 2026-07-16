import numpy as np

# cos(A, B) = (A•B) / (|A| * |B|)
# 분자: 두 벡터의 내적, 분모: 두 벡터의 크기의 곱
# 결과가 1에 가까울수록 방향이 비슷핟.
def cosine_similarity(a, b):
    return np.dot(a, b)/ (np.linalg.norm(a) * np.linalg.norm(b))

def n_dot_limit():
  print("== Dot Product의 한계 ==")
  a = np.array([1, 1])
  print("dot([1, 1], [100, 100]) :", np.dot(a, np.array([100, 100])))

def n_example():
  print("== Cosine similiarity 예제 ==")
  print("[1, 1] vs [2, 2] :", cosine_similarity(np.array([1, 1]), np.array([2, 2])))
  print("[1, 0] vs [0, 1] :", cosine_similarity(np.array([1, 0]), np.array([0, 1])))
  print("[1, 0] vs [-2, 0] :", cosine_similarity(np.array([1, 0]), np.array([-1, 0])))

def n_scale_invariamt():
  print("== 크기에 영향받지 않음 ==")
  a = np.array([1, 1])
  print("[1, 1] vs [10, 10] :", cosine_similarity(a, np.array([10, 10])))
  print("[1, 1] vs [100, 100] :", cosine_similarity(a, np.array([100, 100])))


if __name__ == "__main__":
  n_dot_limit()
  n_example()
  n_scale_invariamt()