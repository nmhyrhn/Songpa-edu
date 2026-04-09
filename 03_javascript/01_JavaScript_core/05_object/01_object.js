/**
 * 객체
 * 자바스크립트는 객체 기반 프로그래밍 언어로 원시 값을 제외한 나머지(함수, 배열, 정규 표현식 등)은 모두 객체이다.
 * 객체는 0개 이상의 프로퍼티로 구서어 된 집합이며 프로퍼티는 키(key)와 값(value)으로 구성된다.
 * 자바스크립트에서 사용할 수 있는 모든 값은 프로퍼티 값이 될 수 있다.
 * 프로퍼티 값이 함수일 경우 method 라고 부른다.
 */

const { type } = require("os")

//객체 생성 - 객체 리터럴 {}
const student = {
  // 키-값 쌍으로 구성 된 프로퍼티
  // 프로퍼티: 객체의 상태를 나타내는 값(data)
  name: "권판다",
  age: 1,
  //메서드 : 프로퍼티(상태 데이터)를 참조하고 조작할 수 있는 동작
  getInfo: function () {
    return `${this.name}(은)는 ${this.age}세 입니다.`
  }
}

console.log(typeof student);
console.log(student);

//프로퍼티를 정의하지 않을 시 빈 객체 생성
const student2 = {}
console.log(typeof student2);
console.log(student2);
