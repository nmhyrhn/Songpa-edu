import  numpy as np

def n_reshape():
  print("== reshape ==")
  numbers = np.arange(12)
  print("원본: ", numbers)

  print("3행 4열: \n",numbers.reshape(3, 4)) # 3 * 4 :12
  print("2행 6열: \n",numbers.reshape(2, 6)) # 2 * 6 :12
  # 변경을 하려면 항상 요소의 개수가 맞아야 한다.
  # print("3행 5열: \n",numbers.reshape(3, 5))
  print()

def n_auto():
  print("== reshape(-1) 자동 계산 ==")
  numbers = np.arange(12)
  # -1은 "나머지는 Numpy가 알아서 계산해줘" 라는 의미
  print("reshape(3, -1):\n", numbers.reshape(3, -1))
  print("reshape(-1, 2):\n", numbers.reshape(-1, 2))

def n_flatten():
  print("== flatten ==")
  # 다차원 -> 1차원 (새 배열 생성)
  matrix = np.array([[1, 2, 3], [4, 5, 6]])
  print("flatten:", matrix.flatten())
  r = matrix.flatten()
  r[0] = 100
  print(matrix) # 원본 유지

def n_ravel():
  print("== ravel() ==")
  matrix = np.array([[1, 2, 3], [4, 5, 6]])
  print("ravel :", matrix.ravel()) 
  r = matrix.ravel()
  r[0] = 100
  print(matrix) # 가능하면 원본메모리를 유지

def n_transpose():
  print("== transpose() ==")
  matrix = np.array([[1, 2, 3], [4, 5, 6]])
  print("transpose():\n", matrix.transpose())
  print("T (축약)\n", matrix.T)




if __name__ == "__main__":
  n_reshape()
  n_auto()
  n_flatten()
  n_ravel()
  n_transpose()