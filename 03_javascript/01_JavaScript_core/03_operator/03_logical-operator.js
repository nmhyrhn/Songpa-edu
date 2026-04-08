/**
 * 논리 연산자는 여러 조건을 결합하여 하나의 논리적 결과를 도출한다.
 * AND, OR, NOT 연산자가 있다.
 */

const a = true;
const b = false;

//논리 AND (&&) : 모두 true일 때만 true 반환
console.log(a && b); // false : a와 b가 모두 true여야 true
console.log(a && a); // true

//논리 OR (||) : 하나라도 true이면 true 반환
console.log(a || b); // true : a 또는 b 중 하나라도 true이면 true
console.log(b || b); // false

//논리 NOT (!) : 피연산자가 true이면 false, false이면 true 반환
console.log(!a); // false : a가 true이므로 !a는 false
console.log(!b); // true : b가 false이므로 !b는 true

/**
 * 표현식 평가하는 도중 평가 결과가 확정 된 경우 나머지 평가 과정을 생략하는 것
 */

console.log('apple' || 'banana'); // 'apple' : 'apple'이 truthy 값이므로 'banana'는 평가하지 않고 'apple' 반환
console.log('' || 'banana'); // 'banana' : ''는 falsy 값이므로 'banana' 반환


//단축평가를 이용한 Null 체크
let obj = null;

// let objValue = obj.value; //TypeError : obj가 null이므로 obj.value를 읽을 수 없음

let objValue = obj && obj.value; 

/** 치명적 단점
 * 0이나 빈 문자열('')처럼 나름 의미가 있는 값들까지 '문제(Falsy)'로 취급
 */

/**
 * Optional Chaining (옵셔널 체이닝)
 */

const obj1 = null;

//obj가 null 또는 undefined 인 경우 undefined 반환
//obj가 다른 Flasy한 값(0,'')인 경우 그대로 0 또는 '' 반환
const objValue1 = obj?.value;

/**
 * null 병합 연산자 (??)
 * 좌항의 피연산자가 null 또는 undefined인 경우 우항의 피연산자 반환,
 * 그렇지 않으면 좌항의 피연산자를 반환한다.
 */

let test = null ?? '기본 값'; 
console.log(test);

let value = '' ?? '기본 값'
console.log(value);