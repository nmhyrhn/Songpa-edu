package com.ohgiraffers.section03.overriding;

public class VendingMachine {

    public String vend() {
        return "밀크 커피";
    }

    //private : 나만 아는 기능 -> 자식은 이 기능의 존재조차 모름
    private void checkStock(){
        System.out.println("자판기 재고를 확인합니다.");
    }

    //final 메소드: 재정의(오버라이딩) 금지
    public final void powerOn() {
        System.out.println("자판기 전원을 켭니다.");
    }

    //protected: 나와 내 자식들까지만 사용 가능한 메소드
    protected void clean() {
        System.out.println("자판기 내부를 청소합니다.");
    }
}
