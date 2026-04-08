/*
원시 데이터 타입 (Primitive Types)
원시 타입은 변경 불가능한 값들이며, 자주 사용되는 원시 타입으로는
숫자(Number), 문자열(String), 불리언(Boolean), null, undefined 등이 있다.
*/

//1. 숫자(Number)
let age = 30;
console.log(age); // 30

console.log(10 === 10.0); // ===은 완전히 일치해야 true가 나옴 값과 타입이 같아야함
console.log(10 / 4);

//2. 문자열(String)
//작은 따옴표(''), 큰 따옴표(""), 백틱(``) 으로 텍스트를 감싼다.
let string = "문자열";
console.log(string); // 문자열

//3. 불리언(Boolean)
//논리적 참, 거짓을 나타내는 true와 false 뿐이다.
let isStudent = true;
console.log(isStudent); // true

//4. null
//명시적으로 값이 없음을 의도적으로 명시할 때 사용
let address = null;
console.log(address); // null

//5. undefined
//변수에 값이 할당되지 않았을 때 자동으로 할당되는 값
let salary; //선언만 하고 할당은 안한 상황
console.log(salary); // undefined

//6. typeof  : 데이터 타입을 확인할 때 사용
console.log(typeof age);
console.log(typeof string);
console.log(typeof isStudent);
console.log(typeof address);
console.log(typeof salary);

/*
템플릿 리터럴
ES6부터 도입 된 멀티라인 문자열, 표현식 삽입 등의 편리한 문자열 처리 기능을 제공하는 문자열 표기법
작은 따옴표, 큰 따옴표 대신 백틱(``)을 용해 표현한다.
*/

let str = '안녕하세요. 반갑습니다.' // 줄바꿈이 허용되지 않음
let str2 = '안녕하세요.\n반갑습니다.' // \n을 사용하여 줄바꿈 표현
console.log(str);
console.log(str2);

//백틱을 사용하면 줄바꿈과 공백이 있는 그대로 적용됨
let multiline = `안녕하세요.
반갑습니다.`;
console.log(multiline);

let lastName = '홍';
let firstName = '길동';
let fullName = lastName + ' ' + firstName;
console.log(fullName); // 홍 길동

//템플릿 리터럴을 사용하여 표현식 삽입
let fullName2 = `${lastName} ${firstName}`;
console.log(fullName2); // 홍 길동