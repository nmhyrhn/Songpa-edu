/**
 * 화살표 함수
 * ES6에서 도입된, 함수를 간결하게 표현하는 문법이다.
 * 단순히 문법만 짧아진 것이 아니라, 'this'를 다루는 방식도 바뀌었다.
 */

const { title } = require("process");

//기본 변환 function 키워드를 '=>' 로 바꿈
const message = function() {return 'hello world!';};
const arrowMsg1 = () => {return 'hello world!';};
console.log(message());
console.log(arrowMsg1());

//본문이 return 한줄이면, 중괄호{}와 return 동시 생략 가능
const arrowMsg2 = () => 'hello simple arrow!';
console.log(arrowMsg2());

//매개변수가 하나일 때, 소괄호 () 생략 가능
const power = function(x) {return x * x;};
const arrowPower = x => x * x;

//매개변수가 없거나, 두 개 이상이면 소괄호() 필수
const greet = () => 'hello!';
const sum = (a, b) => a + b;

//(주의!) 객체 리터럴을 번환할 때는 소괄호()로 감싸야 한다.
//감싸지 않으면 함수 몸체의 중괄호{}로 오해한다.
const createUser = (id, name) => ({id, name});

//고차함수와 쓸 때 위력이 극대화 된다.
const numbers = [1, 2, 3, 4, 5];
// const mappedNumbers = numbers.map(function(val) {return val * 10;}); //일반함수 케이스
const mappedNumbers = numbers.map(val => val * 10);
console.log(mappedNumbers);

/**
 * 일반 함수의 this는 호출할 때 결정된다.
 * 즉, 누가 부르냐에 따라 바뀜
 * 하지만 화살표 함수는 자신만의 'this'를 가지지 않고, 바깥 스코프의 'this'를 그대로 쓴다.
 */

const theater = {
  store: '가락시장점', 
  titles: ['살목지', '패왕별희', '왕과 사는 남자'],

  showMovieList() {
    console.log(`--- ${this.store}상영 영화 목록 ---`);

    //일반 함수를 사용한 경우
    // this.titles.forEach(function(title) {
    //   console.log(this.store + ': ' + title);
    // })

    //화살표 함수를 사용한 경우
    //자신만의 this가 없기 때문에 바깥 함수인 showMovieList의 this를 물려받음
    this.titles.forEach(title => console.log(this.store + ': ' + title));
  }
}

theater.showMovieList();


//객체 메소드 = 무조건 일반 함수 / 화살표 함수로 쓰면 this가 깨진다.
const obj = {
  name: 'kwon', 
  //객체 내무 메서드를 화살표 함수로 작성할 경우 this는 전역객체(window)를 가리킨다.
  sayHi: () => {
    console.log(this.name);
  }

  //일반 함수 작성시
  // syaHi: function() {
  //   console.log(this.name);
  // }
}

obj.sayHi(); // undefined