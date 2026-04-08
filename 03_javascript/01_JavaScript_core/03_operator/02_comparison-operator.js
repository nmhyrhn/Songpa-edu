/**
 * 비교 연산자
 * 비교 연산자는 좌항과 우항의 비교 연산자를 비교한 다음 그 결과를 불리언 값으로 변환
 * if문이나 for문 같은 제어문의 조건식에서 주로 사용한다.
 */

let x = 10;
let y = 20;

//1. 동등 연산자(==, !=): 먼저 암묵적 타입 변환을 통해 타입을 일치시킨 후 같은 값인지 비교
console.log(x == y); // false
console.log(x != y); // true
console.log(x == '10'); // true

//2. 일치 연산자(===, !==): 타입 변환 없이 값과 타입이 모두 같은지 비교
console.log(x === y); // false
console.log(x !== y); // true
console.log(x === 10); // true
console.log(x === '10'); // false

//3. 대소 비교 연산자
console.log(x > y); // false
console.log(x <= y); // true

//문자열도 크기 비교 가능, 우니코드 순서로 비교한다.
//컴퓨터 세상에서는 모든 대문자가 소문자보가 앞에온다.
console.log(`'cat' >= 'Zoo': ${'cat' >= 'Zoo'}`); // false : 대소문자 구분해서 비교`);