import numpy as np

def n_create(): # Numpy에서 1차원 배열이 벡터
  print("== 벡터 생성 ==")
  # 벡터는 여러 숫자를 한 줄로 묶은 것
  v = np.array([1, 2, 3])
  print("벡터:", v)
  print("shape:", v.shape)
  print("ndim:", v.ndim)

def n_norm():
  print("== 벡터의 크기(Norm) ==")
  v = np.array([3, 4])
  print("norm([3, 4])", np.linalg.norm(v))
  print("norm([5, 3, 2])", np.linalg.norm([5, 3, 2]))

def n_dot():
  print("== 내적 ==")
  a = np.array([1, 2, 3])
  b = np.array([3, 4, 5])
  # 내적: 같은 위치끼리의 곱한 뒤 모두 더한다.
  print("np.dot(a, b)", np.dot(a, b))
  print("a @ b   :", a @ b) # 행렬곱

if __name__ == "__main__":
  n_create()
  n_norm()
  n_dot()