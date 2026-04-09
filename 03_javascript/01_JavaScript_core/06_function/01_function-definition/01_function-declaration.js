
//차이점 표현식 이후에 동작 호출 가능
console.log(calcuateArea); //함수 호이스팅
// console.log(hi); // 실행 안됨


//함수의 기본 구조(함수 선언문)
function calcuateArea(width, height) { //매개변수(parameter)
  const area = width * height;
  return area; //반환값: 호출한 곳으로 값 전달
}

//함수 호출
const result = calcuateArea(10, 20);; //인자(argument) 전달
console.log(result);

//함수 표현식
//JS의 함수는 객체의 타입의 값으로 값의 성질을 갖는 객체를 일급 객체라고 한다.
//일급 객체이므로 함수 리터럴로 생성한 함수 객체를 변수에 할당할 수 있다.

const hi = function (name) {
  return `${name}님 안녕하세요!`
}

// 식별자로 함수 호출
console.log(hi('판다'));

const calc = function add(a,b) {
  return a+b;
}

//함수 호출은 식별자로 이루어진다. 함수명으로 호출은 불가능하다.
console.log(calc(10,20));

/**
 * 함수 호이스팅
 * 함수 선언문은 런타임 이전에 자바스크립트 엔진에 의해 먼저 실행된다.
 * 따라서 함수 선언문 이전에 함수를 참조하거나 호출할 수 있다.
 * 함수 선언문이 코드의 선두로 끌어 올려진 것처럼 동작하는 
 * 자바스크립트 교유의 특징을 함수 호이스팅이라고 한다.
 * 
 * 표현식은 런타임에 할당되기 때문에 반드시 함수 표현식 이후에 참조 또는 호출해야 한다.
 */