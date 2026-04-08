/*
변수
변수는 데이터를 저장할 때 쓰이는 '이름이 붙은 저장소'이다. 

변수를 생성할 때 우리는 변수를 선언한다고 표현한다.

키워드 변수명;
키워드 : 변수의 접근 범위를 지정
변수명 : 변수를 부를 이름 설정
*/

//var : ES5 이전에 변수를 선언할 때 사용하던 키워드
var number;
var number = 5;

//let : 값을 변경할 수 있는 변수 선언
let greeting = 'Hello, node.js!';
console.log(greeting); // Hello, node.js!

greeting = "Welcome to JavaScript!";
console.log(greeting); // Welcome to JavaScript!

// const : 재할당이 금지
const num = 1;
// const num;

// num = 1;

// num = 2;

/*
변순 선언 규칙과 스타일
- 변수 이름은 알파벳, 숫자, _, $만 사용할 수 있다.
- 변수 이름은 숫자로 시작할 수 없다.
- 카멜케이스(camelCase)를 사용하는 것이 일반적이다.
- 예약어 사용 금지
- 대소문자 구분: 변수 이름에서 대소문자를 구분한다. A, a는 다른 변수이다.
- 의미 있는 이름 사용: 변수 이름으 그 변수의 역할이나 내용을 명확하게 나타내는 것이 좋다.
*/

//let if 3name = "판다";
//let if = true; 예약어 사용 금지

let userName = "Alice"; // 카멜케이스 사용
let userAge = 30; 

console.log(userName); // Alice
console.log(userAge); // 30
console.log('사용자 이름:' + userName + '나이' + userAge); 
console.log(`사용자 이름: ${userName}, 나이: ${userAge}`); // 템플릿 리터럴

