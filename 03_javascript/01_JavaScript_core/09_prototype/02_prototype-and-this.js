//생성자 함수 프로토타입
//new 연산자를 사용해 만든 객체는 생성자 함수의 프로토타입 정보를 사용해
//[[Prototype]]을 설정한다.

const user = {
  activate: true,
  login : function() {
    console.log('로그인 되었습니다.')
  }
};

function Student(name) {
  this.name = name;
}

//new로 객체 만들 때 연결해줄 대상 설정
//새로 만들어질 객체들이 바라볼 프로토타입 객체를 지정하는 설정값이다
Student.prototype = user;
//Student.prototype.login = function() {}
//Student.prototype = {
// constructor: Student,
// login(){}
//};

//student.__proto__ == user
//Student.prototype에 지정된 user 객체를 자동으로 가리키게된다.
let student = new Student('홍길동');

console.log(student.activate);