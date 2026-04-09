const dog = {
  name: '뽀삐'
}

//이미 존재하는 프로퍼티에 값을 할당하면 값 갱신
dog.name = '두부';
console.log(dog.name);

//프로퍼티 추가 : 존재하지 않는 프로퍼티에 값 할당 -> 동적으로 생성되어 추가
dog.age = 3;
console.log(dog);

//프로퍼티 삭제 : delete
delete dog.age;
console.log(dog);

//완전한 재할당은 금지(const)
// dog = {name: '초코'};

//프로퍼티 존재 확인(in 연산자)
console.log('name' in dog);
console.log('age' in dog);

//객체 순회 (for...in)
for (const key in dog) {
  console.log(`키: ${key}`);
  console.log(`값: ${dog[key]}`);
}