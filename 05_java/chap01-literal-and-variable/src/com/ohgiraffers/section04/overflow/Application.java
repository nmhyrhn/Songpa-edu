package com.ohgiraffers.section04.overflow;

public class Application {
    public static void main(String[] args) {
        /*
        * 자료형 별 값의 최대 범위를 벗어나는 경우
        * 발생하는 carry를 버림처리 하고 sign bit를 반전시켜 최소값으로 순환시킴
        * */

        byte num1 = 127; //byte 최대 저장 범위에 해당

        num1++;
        System.out.println("num1: " + num1); //-128

        byte num2 = -128;

        num2--;
        System.out.println("num2: " + num2); //127

        //int는 약 21억까지만 표현 가능
        int firstNum = 1000000; //100만
        int secondNum = 700000; //70만

        int multi = firstNum * secondNum;
        System.out.println("multi: " + multi); //-79669248

        //이미 오버플로우 처리 된 결과를 변수에 담기 때문에 차이가 없다.
        long longNum = firstNum * secondNum;
        System.out.println("long: " + longNum); //이렇게 진행하면 자료형이 변환이 안됨

        // 계산이 처리되기 전에 long 타임으로 자료형을 변경(강제형변환)
        long longNum2 = (long)firstNum * (long)secondNum;
        System.out.println("long2: " + longNum2); //700000000000

    }
}
