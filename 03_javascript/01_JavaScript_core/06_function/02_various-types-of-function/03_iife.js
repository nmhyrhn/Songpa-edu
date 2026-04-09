/**
 * 즉시 실행 함수 (IIFE)
 * 함수를 정의하자마자 딱 한 번만 실행되는 '일회용 함수'이다.
 * 목적: 전역 오염 방지 + 변수 보호
 */

(function() {
  let secretMessage = '이 변수는 이 안에서만 존재한다.'; //함수 안에서만 존재
  console.log('즉시 실행 함수가 실행되었습니다.');
  console.log(secretMessage);
})();

//(function(){}) ();

// console.log(secretMessage); // not defined

const result = (function(name) {
  let a = 10;
  let b = 20;
  console.log(name);
  return a+b;
}) ('판다');

console.log(result);