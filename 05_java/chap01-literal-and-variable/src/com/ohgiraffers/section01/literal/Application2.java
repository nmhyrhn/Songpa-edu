package com.ohgiraffers.section01.literal;

public class Application2 {
    public static void main(String[] args) {
        /* 숫자와 숫자의 연산 */

        // 정수와 정수의 연산
        System.out.println(123 + 456);
        System.out.println(123 - 456);
        System.out.println(123 * 456);
        System.out.println(123 / 456);
        System.out.println(123 % 456);

        // 실수와 실수의 연산
        System.out.println(1.12 + 1.23);

        // 정수와 실수의 연산 (항상 실수가 나온다)
        System.out.println(123 + 0.23);

        /* 문자의 연산 */
        System.out.println('a' + 'b'); // 내부적으로 숫자 취급을 한다(아스키코드와 유니코드)

        /* 문자열의 연산 */
        // 문자열과 문자열의 '+' 연산 결과는 문자열 합치기가 된다.
        System.out.println("hello" + "world");
//        System.out.println("hello" - "world"); // + 연산 외에 다른 연산은 사용하지 못함

        System.out.println("hello" + 123); // 다른 형태의 값과 + 연산도 문자열 합치기 결과가 나온다
        System.out.println("hello" + 'a');

        /* 논리값의 연산 */

        // 모든 연산자 사용 불가
//        System.out.println(true + false);
//        System.out.println(true + 1);
//        System.out.println(true + 1.0);
//        System.out.println(true + 'a');

        System.out.println(true + "a"); // 유일하게 문자열은 합치기가 가능하다

    }
}
