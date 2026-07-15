"""
배열 생성 함수 6가지:
np.array()       -  리스트를 배열로 반환
np.zeros()       - 0으로 채움
np.ones()        - 1로 채움
np.full()        - 원하는 값으로 채움
np.arange()      - 일정한 간격으로 생성
np.linespace()   - 일정한 개수로 균등 분할
"""

import numpy as np

def n_array():
  print("== np.array() ==")

  # 1차원 배열: 값이 한줄로 나열된 형태
  scores = np.array([90, 80, 100])
  print("1차원: ", scores)

  # 2차원 배열: 행의 길이는 모두 같아야 한다.
  matrix = np.array([
    [1,2,3],
    [4,5,6]
  ])
  print("2차원\n", matrix)

  floats = np.array([1, 2, 3], dtype=float)
  print("dtype=float:", floats)

def n_zeros():
  print("== np.zeros() ==")  
  a = np.zeros(5) # 정수 0처럼 보여도 기본 dtype은 float 이다.
  print("1차원: ", a)

  matrix = np.zeros((3, 4)) # 2차원: (행, 열)
  print("2차원: \n", matrix)
  print('=' * 15)

def n_ones():
  print("== np.ones() ==")  
  a = np.ones(5) # 정수 0처럼 보여도 기본 dtype은 float 이다.
  print("1차원: ", a)

  matrix = np.ones((2, 4)) # 2차원: (행, 열)
  print("2차원: \n", matrix)
  print('=' * 15)

def n_full():
  print("==== np.full() ====")
  data = np.full((3, 4), 100)  
  print(data)
  print('=' * 15)

def n_arrange(): # 일정한 간격으로 생성
  print("== np.arange() ==")

  print("arange(10)     :", np.arange(10)) # 0~9
  # np.arange(start, end, step) : start부터 step만큼 증가
  print("arange(5, 20, 3) :", np.arange(5, 20, 3)) # 5 8 11 14 17

def n_linspace(): # 일정한 개수(num)로 균등 분할
  print("== np.linspace() ==")

  print("linspace(0, 1, 5) :", np.linspace(0, 1, 5)) # 0부터 1까지 같은 간격 5개로 숫자로 나눈다.
  print("linspace(0, 10, 6) :", np.linspace(0, 10, 6)) 

def compare_arange_linspace():
  print("arange는 간격(step) 기준", np.arange(0, 10, 2))  
  print("linspace는 개수(num) 기준", np.linspace(0, 10, 6))

if __name__ == "__main__":
  n_array()
  n_zeros()
  n_ones()
  n_full()
  n_arrange()
  n_linspace()
  compare_arange_linspace()