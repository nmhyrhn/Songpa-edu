/**
 * Paramete와 Argument
 * 함수를 정의할 때 설정하는 통로를 매개변수(parameter),
 * 실제 호출할 때 넘기는 갓을 인수(Argument)라고 한다.
 */

//매개변수의 유효범위
function greet(name) {
  console.log(name); //내부에서는 자유로움
  console.log(arguments);
  return `${name}님 안녕하세요!`
}

// console.log(name); //ReferenceError : name은 함수 내부(지역 스코프)에서만 살아있다.

//인수 개수 불일치
console.log(greet('판다'));
console.log(greet()); //undefined로 할당됨
console.log(greet('판다', '코알라')); //첫번째만 사용된다.

//매개변수 기본값 활용
function hi(name='아무개') {
  //인수가 전달되지 않거나 undefined가 들어오면 '아무개'가 기본으로 쓰임
  return `${name} 안녕?`;
}

console.log(hi());
console.log(hi('몽키'));

/**
 * return (반환문)
 */

function add(a,b) {
  return a+b;
}

const result = add(10,20);
console.log(result);

//함수의 종료
function sayHello(name) {
  return `${name}님 안녕하세요~`

  console.log('여기는 출력이 안된다.') //return문을 만나 이미 종료됨
}

//반환값의 생략
function noReturn() {
  console.log('함수 호출됨');
  return;
}

noReturn();
console.log(noReturn()); //return 값을 명시하지 않거나 생략하면 undefined 반환

//조기 종료 (Early Return)
function reristerUser(nickname) {
  if(nickname.length < 2) {
    console.log('닉네임이 너무 짧습니다.');
    return; //여기서 함수를 끝냄
  }

  if(nickname === 'admin') {
    console.log('사용 불가 닉네임');
    return;
  }

  console.log(`${nickname}님 환영합니다.`);
  //...실제 회원가입 처리 로직
}