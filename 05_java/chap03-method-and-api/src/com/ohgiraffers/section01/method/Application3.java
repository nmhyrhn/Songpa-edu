package com.ohgiraffers.section01.method;

public class Application3 {
    public static void main(String[] args) {
        /*
        * static 메소드 호출 방법
        * 호출 방법
        * 클래스명.메소드명();
        * */

        System.out.println(Application3.sumTwoNumbers(10,29));

        //동일한 클래스 내에 작성된 static 메소드는 클래스명 생략 가능
        System.out.println(sumTwoNumbers(10,29));
    }

    public static int sumTwoNumbers(int a, int b) {
        return a + b;
    }

}
