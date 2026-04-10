//soer : 배열의 요소를 정렬하는 메소드. 원본 배열을 직접 변경한다.

const numbers = [3, 1, 10, 4, 15, 5];

//그냥 호출하면 요소를 문자열로 취급하여 정렬
numbers.sort();
console.log(numbers); 

//다른 정렬 기준을 사용하려면 매개변수로 compare 콜백 함수 전달

numbers.sort((a,b) => a-b); //오름차순 정렬
console.log(numbers); 

numbers.sort((a,b) => b-a); //내림차순 정렬
console.log(numbers); 