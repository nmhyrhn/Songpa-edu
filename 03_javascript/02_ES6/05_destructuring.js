/**
 * 구조 분해 할당
 * 배열이나 객체의 속성을 분해하여 그 값을 개별 변수에 쉽게 담을 수 있게 하는 문법
 */

const nameArr = ['Panda', 'Kwon'];

//기존 방식
// const firstName = nameArr[0];
// const lastName = nameArr[1];

//구조 분해 할당 방식 - 배열은 '순서'가 기준
const [firstName, lastName] = nameArr;
console.log(`성: ${lastName}, 이름: ${firstName}`);

//쉼표를 사용해 특정 요소를 건너뛸 수 있다.
const [, name] = ['kwon', 'panda'];
console.log(name);

//rest 파라미터로 나머지 요소들을 새로운 배열로 모을 수 있다.
const [leader, ...members] = ['팀장', '팀원1', '팀원2', '팀원3'];
console.log(leader);
console.log(members);

//기본값 설정: 할당할 값이 없을 때 사용될 기본값 지정
const [user1, user2 = '기본값'] = ['사용자1'];
console.log(user1);
console.log(user2);

//객체 구조 분해 할당
//객체는 '키(key)이름'이 중요하다. 변수 이름과 같은 키를 찾아 값이 할당된다.
//순서는 중요하지 않아.

const student = {
  name1: '판다', 
  age: 5, 
  major: '영화감상'
}

//기존 방식 
// const name1 = student.name1;
// const age1 = student.age1;

//콜론(:)을 사용하면, 'name1'키의 값을 'name1'이 아닌 'studentName'이라는 새 변수명으로 할당 가능
const{studentName1, age} = student;
// console.log(`이름: ${name1}, 나이: ${age}`);
console.log(`이름: ${studentName1}, 나이: ${age}`);

//기본값(=) 설정
const {name1: sName, job} = student;
console.log(job);

//rest 파라미터로 나머지 프로퍼티들을 새로운 객체로 모을 수 있다.
const {age: studentAge, ...restInfo} = student;
console.log(restInfo);

/**
 * 함수 매개변수 구조 분해
 * 함수에 객체를 인자로 전달할 때, 구조 분해 할당을 사용하면
 * 매개변수의 순서를 신경 쓸 필요 없고, 코드의 가독성이 매우 높아진다.
 */

const product = {
  id: 'p-001', 
  name: '노트북',
  price: 1500000,
  spec: {
    cpu: 'i7',
    ram: '16GB'
  }
}

// function printProductInfo(product) {
//   console.log(`상품아이디:${product.id}`);
//   console.log(`가격: ${product.price}`);
// }

function printProductInfo({id, price, spec:{ram}, producer = '삼성' }) {
  console.log(`상품아이디 : ${id}`);
  console.log(`상품가격 : ${price}`);
  console.log(`RAM : ${ram}`);
  console.log(`제조사 : ${producer}`);
}

printProductInfo(product);