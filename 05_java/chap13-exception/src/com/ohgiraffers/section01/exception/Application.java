package com.ohgiraffers.section01.exception;


//main에서 throws Exception을 실행할 수 있지만 권장하지 않음, 좋은 방법이 아, 컴파일 에러는 사라지지만 JVM 까지 가서 비정상 종료됨
public class Application {
    public static void main(String[] args) throws Exception {

        ExceptionTest et = new ExceptionTest(); //객체 생성

        System.out.println("정상 케이스");
        et.checkEnoughMoney(10000, 50000);

        System.out.println("예외 발생 케이스");
        et.checkEnoughMoney(50000, 10000); //Exception 발생


        System.out.println("프로그램을 종료합니다"); //예외가 발생하면 이 줄은 실행 안됨

    }
}
