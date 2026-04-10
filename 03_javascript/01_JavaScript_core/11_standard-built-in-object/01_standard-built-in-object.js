/**
 * 표준 내장 객체
 * 자바스크립트가 기본적으로 제공하는 유용한 객체들이다.
 */

/**
 * String : 텍스트를 다루는 데 필수적인 기능들을 제공한다.
 */

const MyIntro = '안녕하세요! 제 이름은 판다입니다.';

//검색 : indexOf(), includes()
//'이름'이라는 단어가 몇 번째 인덱스에 처음 등장하나요
console.log(MyIntro.indexOf('이름'));
//'나이'라는 단어가 포함되어 있나요
console.log(MyIntro.includes('나이'));
console.log(MyIntro.includes('판다'));

//잘라내기 : slice(), subString()
const fileName = 'my-profile.jpg';
console.log(fileName.slice(11));
//0번부터 10번 전까지 잘라내기 (파일명 추출)
console.log(fileName.slice(0,10));

// .split(): 쪼개서 배열 만들기
const tags = '#자바스크립트#개발자#꿀팁#프론트엔드';
//'#'을 기준으로 문자열을 쪼개서 배열
const tagArray = tags.split('#');
console.log(tagArray);

//trim() : 앞 뒤 공백 제거
const userId = "    USeR123   ";
console.log(userId);
console.log(userId.trim());

//모두 대문자/소문자 바꾸기
console.log(userId.toLowerCase());
console.log(userId.toUpperCase());

/**
 * Math: 랜덤 숫자 생성, 반올림 등 간단하지만 활용도 높은 숫자 관련 기능을 제공
 */

//Math.random():랜덤 숫자 만들기
//0 이상 1 미만의 랜덤한 소수 반환
console.log(Math.random());

const randomNumber = Math.floor(Math.random()*10)+1;
console.log(randomNumber);

//숫자 반올림/올림 사용
console.log(Math.round(3.14)); //반올림
console.log(Math.floor(3.124)); //소수점 버림
console.log(Math.ceil(3.34)); //소수점 올림

/**
 * Date
 * 날짜와 시간을 다루는 기본적인 기능 제공
 */

const now = new Date();
console.log(now);

console.log(now.toLocaleString("ko-KR"));

console.log(now.getFullYear());
console.log(now.getMonth()+1);
console.log(now.getDate());

