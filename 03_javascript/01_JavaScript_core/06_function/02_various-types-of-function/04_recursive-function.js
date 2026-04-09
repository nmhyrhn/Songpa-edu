/**
 * 재귀 함수
 * 함수가 자기 자신을 다시 호출하는 방식
 * 반복되는 작업을 위해 사용하며, 반드시 '종료 조건'이 있어야 한다.
 */

//factorial (5! = 5 * 4 * 3 * 2 * 1)
function factorialWithLoop(n) {
  let result = 1;
  for(let i=n; i>1; i--) {
    result *= i;
  }
  return result;
}

console.log(factorialWithLoop(5));

function factorial(n) {
  if(n <= 1) {
    return 1;
  }
  return n * factorial(n-1);
}
