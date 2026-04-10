/**
 * 생성자 함수
 */

const student1 = {
  name : '판다',
  age : 5,
  getInfo: function() {
    return `${this.name}은 ${this.age}세 입니다.`;
  }
};

const student2 = {
  name : '코알라',
  age : 3,
  getInfo: function() {
    return `${this.name}은 ${this.age}세 입니다.`;
  }
};

/**
 * 생성자 함수
 * 0. 일반 힘수랑 비슷하게 생겼지만, 규칙이 있다.
 * 1. 이름의 첫 글자를 대문자로 짓는다.
 * 2. this 키워드는 '앞으로 생성될 객체'를 의미한다.
 */

function Student(name, age) {
  this.name = name;
  this.age = age;
  this.getInfo = function() {
    return `${this.name}은 ${this.age}세 입니다.`;
  }
}

//'new' 키워드를 이용하여 객체를 생성한다.

const student3 = new Student('원숭이', 10);
const student4 = new Student('호랑이', 12);

console.log(student3);
console.log(student4.getInfo());