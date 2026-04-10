/**
 * 'new' 로 객체를 생성했을 때 내부에서 벌어지는 일(인스턴스 생성 과정)
 */

function Student(name, age) {
  // 1단계 : '빈 객체{}'가 암묵적으로 만들어지고, this에 할당
  // 마치 const this = {};코드가 맨 위에 있는 것처럼 동작
  console.log(this);

  //2단계 : this에 할당된 빈 객체에 속성들을 추가하며 초기화
  this.name = name;
  this.age = age;
  this.getInfo = function() {
    return `${this.name}은 ${this.age}세 입니다.`
  }

  //3단계 : 완성된 객체(this)가 암묵적으로 반환된다.
  // 마치 return this; 코드가 맨 마지막에 있는 것처럼 동작
}

//new : 객체 생성 + this 바인딩 +반환까지 자동 처리
const student = new Student('판다', 4);
console.log('완성된 객체 :', student);

//new 없이 호출하면 생성자 함수가 아닌 '일반 함수'로 동작
//이때 함수 안의 this는 '전역 객체(window/global)'를 가리키게 된다.
// -> 의도치 않게 전역 공간에 name, age, getInfo 변수(함수)가 만들어짐
const student2 = new Student('몽키', 2);
console.log(student2);

console.log(global.name); 

/**
 * new.target을 이용한 안전장치 설정
 */
function Dog(name, age) {
  if(!new.target) {
    console.log('new 없이 호출했네요! new를 붙여서 다시 실행합니다.')
    return new Dog(name,age);
  }
  this.name = name;
  this.age = age;
}

// new 없이 호출해도 안전장치 덕분에 new를 붙여 호출한 것과 동일한 효과
const dog = Dog('두부', 3);
console.log(dog);

//new와 함께 호출해도 잘 동작
const dog2 = new Dog('바둑이', 1);
console.log(dog2);