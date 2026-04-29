package com.ohgiraffers.section02.package_and_import;
import com.ohgiraffers.section01.method.Calculator;

public class Application {
    public static void main(String[] args) {

        //원래는 이렇게 호출해줘야 함
        com.ohgiraffers.section01.method.Calculator calc = new com.ohgiraffers.section01.method.Calculator();
        int min = calc.minNumberOf(3,5);
        System.out.println(min);

        int max = com.ohgiraffers.section01.method.Calculator.maxNumberOf(2,5);
        System.out.println(max);

        //import를 가져오면 생략이 가능함 (non-static)
        Calculator calc2 = new Calculator();
        int min2 = calc2.minNumberOf(3,1);
        System.out.println(min2);

        //static
        int max2 = Calculator.maxNumberOf(4,7);
        System.out.println(max2);


    }
}
