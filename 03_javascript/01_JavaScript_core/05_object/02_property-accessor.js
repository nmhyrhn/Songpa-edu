/**
 * 프로퍼티 접근
 */

const dog = {
  name : '뽀삐',
  eat : function(food) {
    console.log(`${this.name}는 ${food}를 맛있게 먹어요.`)
  }
}

//마침표(.) 표기법
console.log(dog.name);
dog.eat('고구마');


//대괄호 표기법 - 프로퍼티 키는 반드시 따옴표로 감싼 문자열을 사용
console.log(dog['name']);
dog['eat']('감자');

const obj = {
  'dash-key' : 'dash-value'
}

//프로퍼티 키가 식별자 네이밍 규칠을 준수하지 않는 이름일 경우 반드시 대괄호 표기법을 사용한다.
//console.log(obj.dash-key); //컴퓨터가 obj.dash 빼기 key로 오해함
console.log(obj["dash-key"]);

//key 변수에 담겨 있을 때
const prop = 'name';
//dog.prop 이라고 쓰면 'prop'이라는 키를 찾으려고 함
// console.log(dog.prop);
//우리가 원하는 'name'키의 값을 가져오려면 반드시 대괄호를 써야 함
console.log(dog[prop]); //dog['name']