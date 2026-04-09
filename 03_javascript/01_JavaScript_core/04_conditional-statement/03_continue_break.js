/**
 * continue와 break문
 * - continue: 현재 반복문의 나머지 코드를 건너뛰고, 다음 반복으로 넘어간다.
 * - break: 현재 반복문을 완전히 종료한다.
 */

//continue

for (let i=1; i<=10; i++) {
  if (i % 2 ===0) {
    continue;
  }
  console.log(`홀수: ${i}`);
}

//break
for(let i=1; i<=10; i++) {
  if(i > 5){
    break;
  }
  console.log(`현재 값: ${i}`);
}

//continue를 사용한 중첩 반복문
for(let i=1; i<+3; i++) {
  for(let j=1; j<=3; j++) {
    if(j === 2) {
      continue;
    }
    console.log(`i: ${i}, j: ${j}`);
  }
}

//for...of 반복문(ES6에서 도입)
// 배열이나 이터러블 객체의 각 요소를 순회하는 데 사용

const numbers = [1,2,3,4,5,6,7,8,9];

for (const number of numbers) {
  if(number % 2 === 0) {
    continue;
  }
  console.log(`홀수: ${number}`);
}