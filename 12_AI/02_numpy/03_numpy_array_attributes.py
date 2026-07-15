import numpy as np

def n_shape():
  print("== shape ==")
  # shape는 배열의 모양을 튜플로 알려준다.
  # 1차원 (개수,) 2차원(행, 열)
  print("1차원 (3,)   :", np.array([80, 90, 100]).shape)
  print("2차원 (2, 3)   :", np.array([[80, 90, 100], [70,88,99]]).shape)
  print("3차원 (2, 3, 4))", np.zeros((2, 3, 4)).shape) # 깊이, 행, 열

def n_ndim(): # 배열의 차원수
  print("== ndim ==")
  print("1차원 (3,)   :", np.array([80, 90, 100]).ndim)
  print("2차원 (2, 3)   :", np.array([[80, 90, 100], [70,88,99]]).ndim)
  print("3차원 (2, 3, 4))", np.zeros((2, 3, 4)).ndim) # 깊이, 행, 열

def n_size(): # 전체 요소 개수(shape의 곱)
  print("== size ==")
  print("(2, 3) -> size:", np.array([[80, 90, 100], [99, 24, 67]]).size)
  print("(2, 3, 4) -> size :", np.zeros((2, 3, 4)).size)

def n_dtype():
  print("== dtype ==")
  print("정수: ", np.array([10, 20, 30]).dtype)
  print("실수: ", np.array([10., 20., 30.]).dtype)

  data = np.array([100, 200, "Python"]) # Numpy는 하나의 자료형만 저장 -> 섞이면 자동 변환
  print(data, "/", data.dtype)
  print(data)

if __name__ == "__main__":
  n_shape()
  n_ndim()
  n_size()
  n_dtype()