//mqp : 배열의 모든 요소에 대해 콜백함수를 호출하고, 
//그 결과를 모아 '새로운 배열'을 만든다.

const numbers = [1, 2, 3, 4, 5];

//각 숫자를 제곱한 새로운 배열 만들기
const squareNumbers = numbers.map(num => num * num);
console.log(squareNumbers);
console.log(numbers); //원본은 그대로

//학생 객체 배열에서 아름만 뽑아 새로운 배열 만들기
const students = [
  {name : '판다', score : 90},
  {name : '코알라', score : 80}
]

const studentNames = students.map(student => student.name);
console.log(studentNames);

//filter:배열의 모든 요소에 대해 콜백 함수를 호출하고, 그 결과가 true인 요소만으로 '새로운 배열'을 생성한다.
//-> 배열에서 조건을 만족하는 요소만 골라서 새로운 배열을 만드는 함수
const numbers2 = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];

//짝수만 걸러내기
const even = numbers2.filter(num => num % 2 === 0);
console.log(even);

//점수가 85점 이상인 학생만 뽑기
const highScorers = students.filter(student => student.score >= 85);
console.log(highScorers);