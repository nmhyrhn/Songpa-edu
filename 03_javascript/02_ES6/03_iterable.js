/**
 * '순회 가능한' 데이터 컬렉션을 위한 통일된 규약(프로토콜) 입니다.
 * 배열, 문자열, nodeList, Map, Set 등은 모두 '이터러블'이다.
 * 
 * 이터러블의 가장 큰 특징은 'for...of' 반복문을 사용할 수 있다는 것이다.
 */

const array = ['사과', '바나나', '딸기'];

for(const item of array) {
  console.log(item);
}
const string = '안녕하세요';
for (const char of string) {
  console.log(char);
}

/**
 * 이터러블 : '순화 가능한' 규약을 따르는 객체 (for...of 가능)
 * 유사 배열: 인덱스와 length 프로퍼티가 있어서 배열처럼 '생긴 객체'
 */

//대표적인 '유사 배열 객체'
const arrayLike = {
  0: '배열인듯', 
  1: '배열아닌',
  2: '유사배열',
  length: 3
};

// for(const item of arrayLike) {
//   console.log(item); //TypeError: arrayLike is not iterable
// }

// Array.from()을 상ㅇ하며 '진짜 배열'로 변환할 수 있다.
const realArray = Array.from(arrayLike);
console.log(realArray);

//변환과 동시에 map처럼 활용하기
//1부터 5까지의 숫자를 가진 배열 만들기
//실무에서 초기 데이터를 만들 때 사용됨
const fiveArray = Array.from({length: 5}, (value, index) => index + 1);
const fiveArray1 = [0, 0, 0, 0, 0].map((_, i) => i+1);

