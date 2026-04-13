
/*
1. Rest 파라미터와 Spread 문법 활용
- 두 개의 함수 sumAll과 mergeArrays를 작성하세요.
  1. sumAll 함수는 가변 인수를 받아 모든 숫자의 합을 반환합니다.
  2. mergeArrays 함수는 두 개의 배열을 인수로 받아 두 배열을 합친 새 배열을 반환합니다.
- 두 함수를 각각 호출하여 결과를 출력하세요.

예시 출력:
합계: 15
병합된 배열: [1, 2, 3, 4, 5, 6]
*/

//가변인수를 받아 모두 더하는 함수
function sumAll(...numbers) {
  let sum = 0;
  for (let num of numbers) {
    sum += num;
  }
  return sum;
}

//두 배열을 합치는 함수
function mergeArrays(arr1, arr2) {
  return [...arr1, ...arr2];
}

//함수 호출
const total = sumAll(1, 2, 3, 4, 5);
const merged = mergeArrays([1, 2, 3], [4, 5, 6]);

console.log("합계:", total);
console.log("병합된 배열:", merged);

/*
2. 구조분해 할당 활용
- user 객체를 생성하고 name, age, location 속성을 초기화합니다.
- 구조분해 할당을 사용하여 name과 age를 추출하고 이를 이용해 "name은 age살입니다." 형태의 문장을 출력하세요.

임의의 배열을 리터럴로 생성하고,
- 구조분해 할당을 사용하여 배열에서 첫 번째 요소와 나머지 요소를 분리하여 출력하세요.

예시 출력:
홍길동은 30살입니다.
첫 번째 요소: 1
나머지 요소: [2, 3, 4, 5]
*/
// 객체 생성
const user = {
  name: "홍길동",
  age: 30,
  location: "서울"
};

// 구조분해 할당으로 name, age 추출
const { name, age } = user;
console.log(`${name}은 ${age}살입니다.`);

// 배열 생성
const numbers = [1, 2, 3, 4, 5];

// 구조분해 할당으로 첫 번째 요소와 나머지 요소 분리
const [first, ...rest] = numbers;

console.log("첫 번째 요소:", first);
console.log("나머지 요소:", rest);


/*
3. 클래스와 구조분해 할당을 활용한 학생 관리 시스템
- Student 클래스를 정의하고 name, age, score 속성을 추가하세요.
- 3명의 학생 데이터를 가진 배열 students를 생성하세요.
- 구조분해 할당을 사용하여 학생들의 이름과 점수만 배열로 추출하여 출력하세요.

예시 출력:
학생 이름: [유관순, 홍길동, 장보고]
학생 점수: [90, 80, 70]
*/
// Student 클래스 정의
class Student {
  constructor(name, age, score) {
    this.name = name;
    this.age = age;
    this.score = score;
  }
}

// 학생 배열 생성
const students = [
  new Student("유관순", 20, 90),
  new Student("홍길동", 21, 80),
  new Student("장보고", 22, 70)
];

// 구조분해 할당을 사용해 이름과 점수만 추출
const names = students.map(({ name }) => name);
const scores = students.map(({ score }) => score);

console.log("학생 이름:", names);
console.log("학생 점수:", scores);