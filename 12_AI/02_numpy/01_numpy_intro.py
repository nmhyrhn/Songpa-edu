import numpy as np # Numpy를 np라고 이름으로 사용

def n_version():
  """설치된 Numpy 버전을 출력"""
  print(np.__version__)


def list_vs_numpy():
  """list의 +는 연결 Numpy의 +는 요소별 계산"""
  print("== Python List vs Numpy ==")

  a_list = [1, 2, 3]
  b_list = [4, 5, 6]
  print(a_list + b_list)

  a = np.array([1, 2, 3])
  b = np.array([4, 5, 6])
  print(a+b)
  print()

def excel_like():
  print("== 엑셀처럼 성적 다루기 ==")
  # 행(row) = 학생, 열(colum) = [국어, 영어 , 수학]
  scores = np.array([
    [80, 90, 70],
    [95, 85, 88],
    [70, 78, 84]
  ])
  print("성적표: \n", scores)
  print("전체 평균:", scores.mean())
  print("최고점:", scores.max())
  print("최저점:", scores.min())
  # 과목별(열 방향) 평균
  print("과목별 평균(국/영/수 : " , scores.mean(axis=0))
  # 학생별(행 방향) 평균
  print("학생별 평균          :", scores.mean(axis=1))

if __name__ == "__main__":
  # n_version()
  # list_vs_numpy()
  excel_like()
