package com.ohgiraffers.section01.literal;

public class Application1 {
    public static void main(String[] args) {

        // 숫자 형태의 값
        /* 정수 형태의 값 출력 */
        System.out.println(123);

        /* 실수 형태의 값 출력 */
        System.out.println(1.23);

        /* 문자 형태의 값 */
        System.out.println('a'); // 문자 형태의 값은 홀따움표로 감싸주어야 한다.
//        System.out.println('ab'); // 두 개 이상은 문자로 취급하지 않는다. 에러 발생
//        System.out.println(''); //아무 문자도 기록되지 않은 경우도 에러 발생

        /* 문자열 형태의 값 */
        System.out.println("안녕하세요"); //문자 여럭 개가 나열된 형태를 말하며, 쌍따움표로 감싸주어야 한다.
        System.out.println("");
        System.out.println("a"); //한 개의 문자도 쌍따움표로 감싸면 문자열이다. (문자 'a'와는 다름)

        /* 논리 형태의 값 출력*/
        System.out.println(true);
        System.out.println(false);
    }
}
