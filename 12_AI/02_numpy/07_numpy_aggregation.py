import numpy as np

def n_sum():
  print("== sum ==")
  print("1차원 :", np.array([80, 90, 100]).sum())
  print("2차원 :", np.array([[80, 90, 100], [88, 94, 87]]).sum())

def n_mean():
  print("== mean ==")
  print("1차원 :", np.array([80, 90, 100]).mean())
  print("2차원 :", np.array([[80, 90, 100], [88, 94, 87]]).mean())

def n_max_min():
  print("== max / min ==")
  scores = np.array([80, 90, 100, 70])

  print("max: ", scores.max())
  print("min: ", scores.min())

  # argmax / argmin 값이 아니라 위치(index)를 돌려준다.
  print("argmax: ", scores.argmax())
  print("argmin: ", scores.argmin())

def n_axis():
  print("== axis ==")
  scores = np.array([
    [80, 90, 70], 
    [85, 95, 75], 
    [90, 88, 92]
  ])
  print("전체 형균: ", scores.mean())
  print("열별 평균 (과목별)평균", scores.mean(axis=0)) # 세로
  print("행별 평균 (학생별)평균", scores.mean(axis=1)) #가로

def n_std():
  print("== std ==")
  scores = np.array([80, 90, 100])
  print("std:", scores.std()) # 표준편차가 클수록 값들이 평균에서 멀리 흩어져있다.

if __name__ == "__main__":
  n_sum()
  n_mean()
  n_max_min()
  n_axis()
  n_std()