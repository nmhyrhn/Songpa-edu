/*
암묵적 타입 변환
자바스크립트 엔진에 의해 함묵적으로 타입이 자동 변환 되는 것
타입 변환 된 결과를 예측할 수 있어야 오류를 방지할 수 있다.
 */

//문자열 타입으로 변환

//문자열 연결 연산자로 동작
//문자열 타입이 아닌 피연산자로 문자열 타입으로 암묵적 변환
console.log(10+'20'); // 1020

console.log(1+'');
console.log(NaN+''); // 'NaN'
console.log(undefined+''); // 'undefined'

//숫자 타입으로 변환
//단, 더하기는 문자열 연결 연산자로 동작하기 때문에 문자열이 포함된 경우 문자열로 변환
console.log(10 - '5'); // 5
console.log(10 * '5'); // 50
console.log(10 / '5'); // 2
console.log(10 % '3'); // 1
console.log(10 % 'JavaScript'); // NaN : not a number


console.log(10 > '5'); // true

// + 단항 연산자는 피연산자가 숫자 타입의 값이 아니면 숫자 타입으로 암묵적 타입 변환 수행
console.log(+''); // 0
console.log(+true); // 1
console.log(+false); // 0
console.log(+null); // 0
console.log(+undefined); // NaN

//불리언 타입으로 변환

//자바스크립트 엔진은 불리언 타입이 아닌 값을 Truthy 값(참으로 평가 되는 값)
//또는 Falsy 값(거짓으로 평가 되는 값)으로 구분한다.

/**
 * Falsy 값 : false, 0, -0, 0n, ''(빈 문자열), null, undefined, NaN
 * Truthy 값 : Falsy 값을 제외한 모든 값
 */

if(10 > 5) console.log("참 입니다.");
if('') console.log("if('')"); // 실행되지 않음
if(0) console.log("if(0)"); // 실행되지 않음