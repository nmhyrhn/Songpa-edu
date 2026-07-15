# 사용자 모듈 작성
x: int = 100  # :int는 힌트

pi: float = 3.14

# private 변수: 모듈.__z로 직접 접근은 가능하지만, 외부사용 안함을 권장(*로 임포트가 안됨)
__z: int = 99

def get_circle_area(r: int):
  return r ** 2 * pi

print('my_math 실행중...')

if __name__ == '__main__':
  """현재 모듈을 직접 실행할때만 if블럭 실행. import시에는 실행되지 않는다."""
  print('my_math를 실행해주셔서 감사합니다.')