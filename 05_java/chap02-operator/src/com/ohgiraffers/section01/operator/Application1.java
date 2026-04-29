package com.ohgiraffers.section01.operator;

public class Application1 {
    public static void main(String[] args) {
        /*
        * 산술 연산자(+, -, *, /, %)
        * 피연산자들의 연산 방향은 왼쪽에서 오른쪽이다.
        *
        * '%' : 왼쪽의 피연산자를 오른쪽의 피연산자로 나눈 나머지를 반환
        * */

        /*산술 복합 대입 연산자 (+=, -=, *=, /=, %=)
        * 왼쪽의 피연산자에 오른쪽이 피연산자를 산술 한 결과를 왼쪽의 피연산자에 대입*/

        int num = 12;

        num += 3;

        /*증감 연산자( ++, --)
        * '++' : 1 증가
        * '--' : 1 감소
        * */

        num++; // 다른 연산을 먼저 진행하고 마지막에 1증가
        ++num; //피연산자의 값을 먼저 1 증가시킨 후 다른 연산 진행

        int firstNum = 20;
        System.out.println("firstNum = " + firstNum);
        int result = ++firstNum * 3; //63
        System.out.println("result = " + result);
        System.out.println("firstNum = " + firstNum);

        /*비교연산자 (==, !=, >, <, <=, >=)
        * 피연산자 사이에서 상대적 크기를 판단하여 참 or 거짓 반환*/

        int num1 = 10;
        int num2 = 20;

        System.out.println(num1 == num2);
        System.out.println(num1 != num2);

        char ch1 = 'a'; //97
        char ch2 = 'A'; //65
        System.out.println(ch1 >= ch2);

        /*문자열, 논리값 비교*/
        //String pool(문자열 상수 풀)에서 같은 문자열이면 재사용하여 str1과 str2의 주소값은 같다
        String str1 = "java";
        String str2 = "java";

        System.out.println(str1 == str2);

        //new String()은 항상 새로운 주소의 객체를 생성하여 str3과 str4의 주소값은 다르다
        String str3 = new String("java");
        String str4 = new String("java");

        System.out.println(str3 == str4);
        //문자열 값 비교는 무저간 .equals() 사용 (값 비교)
        System.out.println(str1.equals(str4));
        System.out.println(str3.equals(str4));

        boolean bool1 = true;
        boolean bool2 = false;
        System.out.println(bool1 == bool2);

    }
}
