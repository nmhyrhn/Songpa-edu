package com.ohgiraffers.section02.package_and_import;

import java.util.Random;

public class Application2 {
    public static void main(String[] args) {

        /*
        * java.lang.Math
        * 수학에서 자주 사용하는 상수들과 함수들을 미리 구현해놓은 클래스로, 모든 메소드는 static 메소드이다.
        * java.lang 패키지에 속해 있어 별도의 import가 필요 없다.
        * */

        System.out.println("최소값: " + Math.min(34, 46));

        System.out.println("원주율: " + Math.PI);

        System.out.println(Math.random());// 0.0부터 1.0미만의 실수

        //(int) (Math.random() * 구하려는 난수 갯수) + 구하려는 난수의 최소값
        //1~10 난수 발생
        int random = (int) (Math.random() * 10) + 1;
        System.out.println(random);

        //Random 객체 생성
        Random random1 = new Random();
        //nextInt(int bound): 0부터 bound-1 까지의 정수 난수를 반환
        int randomNum = random1.nextInt(10);
        System.out.println(randomNum);

        //20 ~ 45
        int randomNum2 = random1.nextInt(26) + 20;
        System.out.println(randomNum2);
    }
}
