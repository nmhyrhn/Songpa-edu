// forEach: 배열의 각 요소에 대해 콜백 함수를 실행한다. 반환 값은 없다.

const names = ['판다', '코알라', '원숭이'];

//for 반복문 버전
for(let i=0; i<names.length; i++) {
  console.log(names[i]);
}

/**
 * forEach 방식
 * 1. names 배열의 첫 번째 요소부터 시작
 * 2. 요소를 하나씩 꺼내서
 * 3. 콜백 함수에 전달
 */
names.forEach(name => console.log(name));

//내부적으로 이런 느낌
// for(let i=0; i<names.length; i++) {
//   const name = names[i];
//   console.log(name);
// }

//콜백 함수는 요소의 값, 인덱스, 배열 자체를 인자로 받을 수 있다.
names.forEach((name, index, array)=> console.log(`${index+1}번째 이름: ${name}`) );