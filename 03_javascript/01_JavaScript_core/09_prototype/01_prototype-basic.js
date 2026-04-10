/**
 * 
 * 자바스크립트는 '클래스 기반'이 아니라 '프로토타입 기반'상속 언어이다.
 * 
 * 자바스크립트의 모든 객체는 [[Prototype]]이라는 '연결고리'를 가지고 있다.
 * 이 연결고리는 다른 객체를 가리킬 수 있는데, 이 대상을 '프로토타입'이라고 부른다.
 */

const user = {
  id: 'user',
  activate: true,
  login: function(){
    //이 메소드를 누가 호출하든, this는 '호출한 주체'를 가리킨다
    console.log(`로그인이 되었습니다.`);
  }
};

const student = {
  passion: true
};

//의미 : student 객체가 user 객체를 상속 받는다.
Object.setPrototypeOf(student, user);

console.log(student.activate);
student.login();
console.log(student.passion);

//'쓰기'는 프로토타입에 영향을 주지 않고, 자기 자신에게 프로퍼티를 만든다.
student.id = 'student'

console.log(student.id);

//user의 login 메소드를 빌렸지만, this는 user가 아닌 '호출한 주체'가 된다.
student.login();