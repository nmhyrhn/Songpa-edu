/* 문제 1: 나이에 따른 영화 관람 가능 여부(삼항 연산자 사용)
 - 변수 age를 선언하고, 삼항 연산자를 사용해 18세 이상이면 '관람 가능', 아니면 '관람 불가'를 출력하세요. */
let age = 20;

let result = (age >= 18) ? '관람 가능' : '관람 불가';
console.log(result);


/* 문제 2: 점수에 따른 보너스 지급 (if문 사용)
- 변수 score를 선언하고 80 이상이면 10점 보너스를 추가하세요. 
- 50 이상 80 미만이면 5점 보너스를 추가하세요. 
- 50 미만이면보너스가 없습니다.

 **hint: else문은 작성하지 않아도 된다. */
let score = 75;

if (score >= 80) {
  score += 10;
}

if (score >= 50 && score < 80) {
  score += 5;
}

console.log(score);


/* 문제 3: 월에 따른 계절 출력(switch문 사용)
- 변수 month를 선언하고, 해당 월에 따라 계절을 출력하세요.
- season 변수를 선언하고, season 에 계절이 할당되도록 case문을 작성하세요.
 - 12, 1, 2: 겨울 / 3, 4, 5: 봄 / 6, 7, 8: 여름 / 9, 10, 11: 가을 */
let month = 4;
let season = '';

switch (month) {
  case 12:
  case 1:
  case 2:
    season = '겨울';
    break;

  case 3:
  case 4:
  case 5:
    season = '봄';
    break;

  case 6:
  case 7:
  case 8:
    season = '여름';
    break;

  case 9:
  case 10:
  case 11:
    season = '가을';
    break;
}

console.log(season);


/* 문제 5: 1부터 n까지의 홀수 출력(while문 사용)
- 변수 n을 선언하고, 1부터 n까지의 숫자 중 홀수를 출력하세요. 
 **hint: 1부터 시작할 변수가 필요하겠죠? */
let n = 10;
let i = 1;

while (i <= n) {
  if (i % 2 === 1) {
    console.log(i);
  }
  i++;
}


/* 문제 6: 특정 숫자 건너뛰기
 - 1부터 20까지의 숫자 중 5의 배수를 제외하고 출력하세요. */
for (let i = 1; i <= 20; i++) {
  if (i % 5 === 0) continue;
  console.log(i);
}


/* 문제 7: 반복 중단
 - 1부터 50까지 숫자를 출력하다가, 30 이상일 때 반복을 중단하세요. */
  for (let i = 1; i <= 50; i++) {
  if (i >= 30) break;
  console.log(i);
}