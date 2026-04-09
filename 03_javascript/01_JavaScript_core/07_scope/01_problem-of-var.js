//ES5까지 사용되던 var 키워드는 세 가지 주요 문제점을 가지고 있다.

//1. 변수 중복 선언 허용
var msg = '안녕하세요';
console.log(msg);

var msg = '안녕히가세요';
console.log(msg); //마지막 값이 덮어써짐 -> 실수로 다른 코드 변수 덮어쓰면 버그 발생

//2. 함수 레벨 스코프
var i = 100; //중요한 전역 변수

for (var i=0; i<=5; i++) {
  console.log(`for문 내부의 i: ${i}`);
}

//for문의 i가 전역 변수 i를 덮어씀(오염)
  console.log(`for문 바깥의 i: ${i}`);

  //3. 변수 호이스팅 -> 가독성 및 예측 가능성 저하
console.log(test);

test = '반갑습니다'
console.log(test);

var test; // 선언이 가장 위로 끌어올려진 것처럼 동작함