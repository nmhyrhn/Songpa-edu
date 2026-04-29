package com.ohgiraffers.section01.operator;

public class Application2 {
    public static void main(String[] args) {
        /*
        * 논리 연산자
        *
        * 1. 논리 연결 연산자
        * - &&(AND) : 두 개의 논리식 모두 참이면 참, 한개라도 거짓인 경우 거짓 반환
        * - ||(OR) : 두 개의 논리식 중 하나라도 참이면 참, 둘 다 거짓이면 거짓 반환
        *
        * 2. 논리 부정 연션자
        * - !(NOT) : 논리식의 결과가 참이면 거짓을, 거짓이면 참을 반환
        * */

        int a = 10;
        int b = 20;
        int c = 30;
        int d = 40;

        System.out.println(a < b && c > d);
        System.out.println(a < b || c> d);

        /*1 부터 100 사이의 값인지 확인*/
        // 1 <= 변수 <= 100  -->  변수 >= 1 && 변수 <=100
        int num = 29;
        System.out.println(num >= 1 && num <= 100);

        /*
        * 논리식 && 논리식 : 앞의 결과가 false이면 뒤를 실행 안함
        * 논리식 || 논리식 : 앞의 결과가 true이면 뒤를 실행 안함
        * */
        int num3 = 10;
        int result2 = (false && ++num3 > 0) ? num3 : num3;
        System.out.println(result2);

        /*삼항연산자
        * (조건식)? true일 때사용할 값 : false일 때 사용할 값
        * */

        int num1 = 10;
        int num2 = -20;

        String result = (num1 > 0) ? "양수다." : "양수가 아니다.";
        System.out.println(result);
    }
}
