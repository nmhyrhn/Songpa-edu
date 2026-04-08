/**
 * 조건문 : if-else
 * if(조건식) {
 * //조건식이 true인 경우 실행구문
 * } else {
 * //조건식이 false인 경우 실행 구문
 * }
 */

let temperature = 5;

if(temperature < 10) {
  console.log("오늘은 춥습니다.");
} else {
  console.log("오늘은 날씨가 괜찮습니다.");
}

/**조건문: if-else if-else 
 * 여러 조건을 처리할 때 쓴다.
*/

let score = 85;

if(score >= 90) {
  console.log("매우 우수")
} else if (score >= 70) {
  console.log("양호")
} else if (score >= 50) {
  console.log("보통")
} else {
  console.log("부족")
}

/**
 * 조건문 : switch문
 * 하나의 변수에 대해 여러 경우를 처리
 */

let fruit = '바나나';

switch(fruit) {
  case '사과' : 
    console.log('선택한 과일은 사과입니다.');
    break;
  case '바나나' : 
    console.log('선택한 과일은 바나나입니다.');
    break;  
  default:
    console.log('알 수 없는 과일 입니다.');
}