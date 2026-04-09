/**
 * let
 */

//1. 변수 중복 선언 금지
let msg = '안녕하세요';
// let msg = '안녕히 가세요.';

//2. 블루 레벨 스코프
let i = 100;

for(let i=0; i<5; i++) {
  console.log(`for문 내부 i; ${i}`);
}

console.log(`for문 외부 i: ${i}`);

//3. 변수 호이스팅이 일어나지 않은 것처럼 동작
// test = '반갑습니다.';

let test;
test = '반갑습니다.';
console.log(test);

/**
 * const
 */

//1. 선언과 동시에 반드시 초기화해야 한다.
// const greeting;

const greeting = '안녕하세요'

//2. 재활당이 금지!!
// greeting = '안녕';

const student = {
  name: '홍길동',
  age: 20
};

//다른 객체로 바꾸는것 불가능
// student = {name: '유관순', age: 16};

//하지만 객체 내부의 '프로퍼티'를 변경하는 것은 가능하다.
student.name = '유관순';
console.log(student);


let globalVar = '전역 변수'; //가장 넓은 스코프

if(true) {
  let blockVar = '블록 스코프 변수'; //if문 안에서만 유효

  function sayHi() {
    let functionVar = '함수 스코프 변수'; //sayHi 함수 안에서만 유효

    //가장 안쪽에서는 모든 상위 스코프 변수에 접근할 수 있다.
    console.log(globalVar);
    console.log(blockVar);
    console.log(functionVar);
  }
  sayHi();
  // console.log(functionVar); // 함수 밖에서 접근 불가
}

// console.log(blockVar); //블록 밖에서는 접근 불가

/**
 * 1. 기본적으로 모든 변수는 const로 선언한다. (가장 안전)
 * 2. 값이 반드시 바뀌어야 하는 변수에만 let을 사용한다.
 * 3. var는 사용하지 않는다.
 */