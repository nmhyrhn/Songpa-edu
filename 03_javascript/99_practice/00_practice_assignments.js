/* 
  [실습 1] 다크 모드 토글 버튼
  - HTML 파일에 있는 #theme-btn 버튼을 누르면, 배경색이 까매졌다 하얘졌다 변하게 만듭니다.
*/
const $themeBtn = document.querySelector('#theme-btn');
let isDark = false; // 현재 다크모드인지 확인하는 스위치 역할

$themeBtn.addEventListener('click', function () {
  // TODO: isDark 변수를 활용해 조건문을 짭니다.
  // 힌트: document.body.classList.toggle('dark-mode') 를 쓰면 if문 없이도 아주 쉽게 가능합니다! (자율 선택)
  // 힌트: 내부 글자도 '☀️ 라이트 모드 켜기' 로 바꿔보세요!

});


/* 
  [실습 2] '좋아요' 카운터 만들기
  - 빨간색 빈 하트(#heart-btn)를 클릭할 때마다 채워진 하트(♥)로 바꾸고, 클릭한 숫자를 세어보세요.
*/
const $heartBtn = document.querySelector('#heart-btn');
const $likeCount = document.querySelector('#like-count');
let count = 0; // 좋아요 숫자

$heartBtn.addEventListener('click', function () {
  // TODO: 1) count 변수를 1 증가시킵니다.
  // TODO: 2) $likeCount 안의 숫자를(innerText) 바뀐 count로 바꿔줍니다.
  // TODO: 3) $heartBtn 안의 모양을 '♥' (채워진 하트)로 바꿔줍니다.

});


/* 
  [실습 3] 넷플릭스 영화 평점 필터기
  - 배열의 .filter() 고차 함수와 '화살표 함수'를 써서 9점 이상의 띵작만 추출합니다.
*/
const movies = [
  { title: "어벤져스", rating: 9.5 },
  { title: "슈퍼맨", rating: 8.2 },
  { title: "스파이더맨", rating: 9.1 },
  { title: "배트맨", rating: 7.9 }
];

// TODO: filter() 와 화살표 함수 () => {} 를 이용해 9점 이상 영화만 'goodMovies' 배열에 담고 콘솔에 출력하세요!
// const goodMovies = movies.filter( ... );


/* 
  [실습 4] 3초 펑! 토스트 알림창 띄우기
  - 💾 저장하기 버튼을 누르면 숨겨진 알림창(#toast)이 나타납니다.
  - 정확히 3초 뒤에, 자바스크립트의 함수(setTimeout)를 써서 다시 사라지게 만드세요.
*/
const $saveBtn = document.querySelector('#save-btn');
const $toast = document.querySelector('#toast');

$saveBtn.addEventListener('click', function () {
  // 알림창이 눈에 보이게 만듭니다. (CSS display: block)
  $toast.style.display = 'block';

  // TODO: setTimeout 비동기 함수를 사용해서 3초(3000ms) 뒤에 다시 'none'으로 돌아가게 짜보세요.

});


/* 
  [실습 5] 스프레드 문법
  - 기존 회원의 나이만 21살로 바꾸고 싶습니다. 
  - 리액트 불변성 법칙에 따라 원본 객체는 절대 건드리지 말고, 복사본을 만들어보세요!
*/
const user = { name: '홍길동', age: 20, gender: '남성' };

// TODO: 스프레드 문법(...)을 사용하여 user를 복사하면서, 나이(age) 속성만 21로 덮어씌운 newUser를 만들어보세요.
// const newUser = { ... };

// 테스트용 (주석 풀고 확인)
// console.log("원본:", user);
// console.log("복사본:", newUser); 
// console.log("두 객체가 다른가?:", user !== newUser); // true가 나와야 성공!ㄴ