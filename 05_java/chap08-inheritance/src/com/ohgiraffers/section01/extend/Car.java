package com.ohgiraffers.section01.extend;

public class Car {

    private boolean runningStatus;

    public Car() {
        System.out.println("Car 클래스의 기본 생성자 호출");
    }

    public void run() {
        runningStatus = true;
        System.out.println("자동차가 달립니다.");
    }

    public void soundHorn() {
        if(isRunning()) {
            System.out.println("빵빵");
        } else {
            System.out.println("주행중이 아닙니다.");
        }
    }

    /*protected: 같은 패키지 + 상속관계의 자식 클래스에서 접근이 가능*/
    protected boolean isRunning() {
        return runningStatus;
    }

    public void stop() {
        runningStatus = false;
    }



}
