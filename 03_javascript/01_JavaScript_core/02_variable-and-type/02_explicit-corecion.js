/**
 * 명시적 타입 변환 (Explicit Type Coercion)
 * - 개발자가 명시적으로 타입을 변환하는 경우
 */

//문자열 타입으로 변환

//1. String 생성자 함수를 new 연산자 없이 호출
// 어떤 값이 들어올지 모를 때 가장 안전하게 쓸 수 있는 방법
console.log(String(10)); // '10' 숫자가 문자열 타입으로 변환됨
console.log(String(true)); // 'true' 불리언이 문자열 타입으로 변환됨
console.log(String(null)); // 'null' null이 문자열 타입으로 변환됨

//2. toString() 메서드 사용
console.log((10).toString()); // '10' 숫자가 문자열 타입으로 변환됨
// console.log((null).toString()); // typeError: null은 toString() 메서드를 사용할 수 없음

//숫자 타입으로 변환  
//1. Number 생성자 함수를 new 연산자 없이 호출(완벽히 숫자일때만)
console.log(Number('10.01')); // 10.01 문자열이 숫자 타입으로 변환됨
console.log(Number('10원')); // NaN 숫자로 변환할 수 없는 문자열은 NaN이 됨
console.log(Number(true)); // 1 불리언이 숫자 타입으로 변환됨
console.log(Number(false)); // 0 불리언이 숫자 타입으로 변환됨
console.log(Number(null)); // 0 null이 숫자 타입으로 변환됨
console.log(Number(undefined)); // NaN undefined가 숫자 타입으로 변환됨

//2. parseInt(), parseFloat() 함수 사용 (문자열 -> 숫자만 가능)
console.log(parseInt('10.01')); // 10
console.log(parseFloat('10.01')); // 10.01

//3. 논리 타입으로 변환

//1. Boolean 생성자 함수를 new 연산자 없이 호출
/**
 * Falsy 값 : false, 0, -0, 0n, ''(빈 문자열), null, undefined, NaN 는 false
 * 나머지는 true로 바꿔주는 방법
 */
console.log(Boolean('JS')); // true
console.log(Boolean(0)); // false
console.log(Boolean(NaN)); // false

//2. ! 부정 논리 연산자를 두 번 사용하는 방법
console.log(!true); // false
console.log(!!'JS'); // true
console.log(!!0); // false
console.log(!!NaN); // false