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
        
    }
}
