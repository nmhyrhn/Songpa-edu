package com.ohgiraffers.section01.exception;

public class Application2 {
    public static void main(String[] args) {

        ExceptionTest et = new ExceptionTest();

        //정상케이스
            try {
                et.checkEnoughMoney(1000, 3000);
                System.out.println("상품 구입 가능");
            } catch (Exception e) {
                System.out.println("상품 구입 불가");
            }

        //예외 발생 케이스
        try {
            et.checkEnoughMoney(3000, 1000);//try 블록에서 예외 발생한 줄에서 즉시 중단
            System.out.println("상품 구입 가능");  //예외가 발생한 줄 아래 try 코드는 실행되지 않음

        } catch (Exception e) {
            System.out.println("상품 구입 불가");
            System.out.println(e.getMessage()); //throw할 때 넣은 메세지 꺼내기
            e.printStackTrace(); //예외 경로 전체 추적 출력
        }

        System.out.println("프로그램을 종료합니다."); //반드시 실행됨

    }
}
