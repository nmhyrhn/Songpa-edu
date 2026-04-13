/**
 * 클래스
 * ES6에서 도입된 객체를 생성하기 위한 새로운 설계도 양식읻.
 * 기존의 생성자 함수보다 훨씬 더 명확하고 체계적으로 객체를 설계할 수 있다.
 */

//클래스 선언
class Student {
  //new 키워드로 객체를 만들 때, 딱 한 번 실행되는 함수
  constructor(name, group) {
    this.name = name;
    this.group = group;
  }

  //메서드:이 클래스로 만들어진 모든 객체가 공유하는 기능
  //이 함수는 객치마다 복사되지 안혹, Student.prototype에 저장된다.
  introduce() {
    console.log(`안녕하세요! ${this.group}반 ${this.name}입니다.`);
  }
}

const studentA = new Student('홍길동', 'A');
// const student = Student('홍길동', 'A'); //new 키워드 없으면 에러 발생, TypeError: Class constructor Student cannot be invoked without 'new'
const studentB = new Student('홍길순', 'B');


//모든 인스턴스가 같은 함수를 쓴다 -> 메모리 절약, 성능 효율
console.log(studentA.introduce === studentB.introduce);
