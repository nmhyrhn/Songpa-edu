import numpy as np
# 영화데이터

movie_names = [
    "호프",
    "미니언즈&몬스터",
    "모아나",
    "토이스토리5"
]

# 열 순서: [액션, 스릴러, 코미디, 감동]
# 1 = 해당 특징 있음, 0 = 해당 특징 없음
movies = np.array([
    [1, 1, 0, 0],
    [1, 0, 1, 1],
    [0, 0, 0, 1],
    [0, 0, 1, 1]
])

# 사용자 입력

print("=" * 40)
print("Movie Recommenation System")
print("=" * 40)

action = int(input("액션을 좋아하나요?(1:예, 0:아니오) :"))
sl = int(input("스릴러를 좋아하나요?(1:예, 0:아니오) :"))
comedy = int(input("코미디를 좋아하나요?(1:예, 0:아니오) :"))
emotion = int(input("감동을 좋아하나요? (1:예, 0:아니오) :"))

user = np.array([
    action,
    sl,
    comedy,
    emotion
])

# 전부 0이면 비교할 취향이 없어서 코사인 유사도를 계산할수 없어서
if not user.any():
    print("\n취향을 하나 이상 선택해야 추천할 수 있습니다.")
    raise SystemExit

def cosine_similarity(a, b):
    return np.dot(a, b) / (np.linalg.norm(a) * np.linalg.norm(b))


# 유사도 계산
scores = []

for movie in movies:
    similarity = cosine_similarity(user, movie)
    scores.append(similarity)  


scores = np.array(scores)


print("scores 출력", scores)

index = np.argmax(scores)

print("\n" + "=" * 40)
print("추천 결과")
print("=" * 40)

print(f"추천 영화: {movie_names[index]}")
print(f"유사도: {scores[index]:.2f}")

for name, score in zip(movie_names, scores):
    print(f"{name:<10} : {score:.2f}")