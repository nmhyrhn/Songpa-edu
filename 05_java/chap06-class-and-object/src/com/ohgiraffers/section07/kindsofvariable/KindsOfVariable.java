package com.ohgiraffers.section07.kindsofvariable;

public class KindsOfVariable {

    /*변수는 선언된 위치에 따라 세 종류로 나뉜다.*/

    //인스턴스 변수(non-static field) (변수=필드) - 클래스 영역에 선언,
    //new 키워드로 객체 생성과 함께 heap 영역에 생성
    //같은 클래스 내 어느 메서드에서든 접근 가능
    private int instanceNum;

    //클래스 변수(static field) - 프로그램 시작과 함께 static 영역에 단 하나 생성
    //모든 인스턴스가 공유하며 프로그램 종료 시까지 사라지지 않는다.
    private static int staticNum;

    //지역변수(local variable) - 메서드 블록 안에 선언
    public void method(int num) {

        //지역변수와 매개변수 모두 메소드 호출 시 stack 메모리에 생성, 메소드가 끝나면 사라짐
        int localNum;

        System.out.println(num); //호출 시 값이 넘어오기 때문에 초기화 필요 없다.
//        System.out.println(localNum); //반드시 사용 전에 초기화 해야 한다.

        System.out.println(instanceNum); //객체 전체에서 접근 가능
        System.out.println(staticNum); //프로그램 전체 공유 변수이므로 접근 가능

    }

    public void method2(){
        System.out.println(instanceNum);
        System.out.println(staticNum);
//        System.out.println(localNum); //method2에 존재하지 않음
    }

}
