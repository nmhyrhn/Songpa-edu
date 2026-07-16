import numpy as np

def n_elementwise():
  print("== 요소별 연산 ==")
  a = np.array([1, 2, 3])
  b = np.array([4, 5, 6])

  print("a + b :", a + b)
  print("a - b :", a - b)
  print("a * b :", a * b)
  print("a / b :", a / b)
  print("*" * 40)

def n_scalar():
  print("== 스칼라 연산 ==")
  scores = np.array([80, 90, 70])
  print("scores + 10 :", scores + 10)
  print("scores * 2 :", scores * 2)
  print("scores / 10 :", scores / 10)

def n_math():
  print("== 수학 함수 ==")
  print("abs      :", np.abs(np.array([-2, -1, -4, -5])))
  # round 반올림, ceil 올림, floor 내림
  nums = np.array([1.3, 2.8, 5.4])
  print("round :", np.round(nums))
  print("ceil :", np.ceil(nums))
  print("floor :", np.floor(nums))

def n_broadcasting():
  print("== Broadcasting ==")
  scores = np.array([80, 90, 70])
  print("배열 + 숫자 :", scores + 5)
  grid = np.array([
    [80, 90, 70],
    [85, 95, 75]
  ])
  bonus = np.array([5, 10, 0])
  print("(2,3) + (3,)\n", grid + bonus)
  weight = np.array([[1], [2]]) # (2,1)
  print("(2, 3) * (2, 1): \n", grid * weight) # (2, 1)은 행마다 다른 가중치를 줄때 사용

def broadcasting_rules():
  print("== Broadcasting 규칙 ==")
  # 뒤쪽 차원부터 비교한다. 같거나, 둘 중 하나가 1이면 계산 가능하다.
  print("(2, 3) + (3,) -> 가능") # 끝차원일치
  print("(2, 3) + (2, 1) -> 가능") # 한쪽 차원이 1
  print("(2, 3) + (2, ) -> 불가능") # 끝 차원(3 vs 2) 불일치
  np.array([[80, 90, 70], [85, 95, 75]]) + np.array([[1], [2]])

if __name__ == "__main__":
  n_elementwise()
  n_scalar()
  n_math()
  n_broadcasting()
  broadcasting_rules()