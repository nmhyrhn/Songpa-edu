/**
 * array(배열)
 * 배열은 여러 개의 값을 순차적으로 나열한 자료구조이다.
 */

//배열 리터럴을 통해 배열 생성
const fruits = ['사과', '바나나', '복숭아'];

//요소는 자신의 위치를 나타내는 인덱스를 가지며, 배열의 요소에 접근할 때 사용
//요소 접근 시에는 대괄호 표기법을 사용한다.
console.log(fruits[0]);
console.log(fruits[1]);
console.log(fruits[2]);

console.log(fruits.length);

for(let i=0; i<fruits.length; i++) {
  console.log(fruits[i]);
}

console.log(typeof fruits);