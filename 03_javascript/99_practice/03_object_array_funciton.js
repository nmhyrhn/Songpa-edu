/*
2-1. min, max function

1. 전달된 매개변수 중 최소값을 반환하는 함수 min(a, b)와
  전달된 매개변수 중 최대값을 반환하는 함수 max(a, b)를 정의하세요.

2. 변수 초기화를 통해 숫자를 설정하고,
  최소값과 최대값을 함수 호출을 통해 리턴 받아 콘솔에 출력하는
  테스트 코드를 작성하세요.
  
   ** 함수 선언문으로 작성

예시 출력:
최소값 : 10
최대값 : 20
*/
// 최소값
function min(a, b) {
  return a < b ? a : b;
}

// 최대값
function max(a, b) {
  return a > b ? a : b;
}

// 테스트 코드
let num1 = 10;
let num2 = 20;

console.log("최소값 :", min(num1, num2));
console.log("최대값 :", max(num1, num2));


/*
2-2. 화살표 함수로 변경하기

1. 2-1에서 작성한 함수 선언문으로 작성된 min, max 함수를
  화살표 함수로 변경하여 작성하세요.

2. 삼항 연산자를 이용하여 함수 본문을 하나의 구문으로 변경하세요.

3. 변수 초기화를 통해 숫자를 설정하고,
  최소값과 최대값을 함수 호출을 통해 리턴 받아 콘솔에 출력하는
  테스트 코드를 작성하세요.

예시 출력:
최소값 : 10
최대값 : 20
*/
const minArrow = (a, b) => (a < b ? a : b);
const maxArrow = (a, b) => (a > b ? a : b);

// 테스트 코드
let n1 = 10;
let n2 = 20;

console.log("최소값 :", minArrow(n1, n2));
console.log("최대값 :", maxArrow(n1, n2));


/*
3-2. 객체 생성자로 계산기 객체 만들기

1. Calculator라는 생성자 함수를 생성하고 다음과 같은 메소드를 정의하세요.
  - sum 메소드: 저장된 두 값의 합을 반환
  - multi 메소드: 저장된 두 값의 곱을 반환

2. Calculator 생성자를 통해 객체를 생성하고, 
  저장할 두 값을 초기화한 후 각 메소드를 실행하여 결과를 출력하는 코드를 작성하세요.

예시 출력:
합 : 30
곱 : 200
*/

function Calculator(a, b) {
  this.a = a;
  this.b = b;

  this.sum = function () {
    return this.a + this.b;
  };

  this.multi = function () {
    return this.a * this.b;
  };
}

// 객체 생성 및 실행
const calc = new Calculator(10, 20);

console.log("합 :", calc.sum());
console.log("곱 :", calc.multi());

/*
4-2. 숫자값 배열과 문자값 배열 정렬하기

1. 주어진 숫자값 배열과 문자값 배열을 각각 다음과 같이 정렬하여 출력하세요.
  - 숫자 배열: 오름차순 정렬, 내림차순 정렬
  - 문자 배열: 오름차순 정렬(알파벳 순), 내림차순 정렬(역 알파벳 순)

2. 힌트: Array.prototype.sort 메소드를 활용하세요.

예시 출력:
[ 9, 20, 37, 54, 88, 100 ]
[ 100, 88, 54, 37, 20, 9 ]
[ 'hello', 'js', 'party', 'wow' ]
[ 'wow', 'party', 'js', 'hello' ]
*/

let numbers = [20, 100, 37, 54, 88, 9];
let strings = ['wow', 'js', 'party', 'hello'];

// 숫자 오름차순
let ascNumbers = [...numbers].sort((a, b) => a - b);

// 숫자 내림차순
let descNumbers = [...numbers].sort((a, b) => b - a);

// 문자열 오름차순
let ascStrings = [...strings].sort();

// 문자열 내림차순
let descStrings = [...strings].sort().reverse();

console.log(ascNumbers);
console.log(descNumbers);
console.log(ascStrings);
console.log(descStrings);


/*
5-1. 학생 객체 정렬 및 이름 합성 함수 작성

1. Student 생성자 함수를 통해 생성된 3명의 학생 객체를 studentList 배열에 담습니다.

2. 다음 함수를 작성하세요:
  - sortFromScore(arr): 전달된 배열(arr)을 score 속성을 기준으로 내림차순으로 정렬하여 반환하는 함수.
  - makeFullName(arr): 전달된 배열(arr)을 순회하며 lastName과 firstName을 합성한 name 속성을 추가하고,
    객체를 새로운 형식으로 반환하는 함수.

3. sort와 map 메소드를 활용하여 작성하세요.

예시 출력:
[
  Student { firstName: '관순', lastName: '유', score: 80 },
  Student { firstName: '보고', lastName: '장', score: 70 },
  Student { firstName: '길동', lastName: '홍', score: 60 }
]
[
  { name: '유관순', score: 80 },
  { name: '장보고', score: 70 },
  { name: '홍길동', score: 60 }
]
*/

function Student(firstName, lastName, score){
  this.firstName = firstName;
  this.lastName = lastName;
  this.score = score;
}

const studentList = [
  new Student('길동', '홍', 60),
  new Student('보고', '장', 70),
  new Student('관순', '유', 80)
];

sortFromScore(studentList);
console.log(studentList);
console.log(makeFullName(studentList));

function sortFromScore(arr) {
  return arr.sort((a, b) => b.score - a.score);
}

function makeFullName(arr){
  return arr.map(student => ({
    name: student.lastName + student.firstName,
    score: student.score
  }));
}

// 실행
sortFromScore(studentList);
console.log(studentList);

console.log(makeFullName(studentList));
