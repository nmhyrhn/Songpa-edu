package com.ohgiraffers.section03.interfaceimplements;

public class Application {
    public static void main(String[] args) {

        Keyboard keyboard = new Keyboard();
        Mouse mouse = new Mouse();
        Computer computer = new Computer();

        computer.connectDevice(mouse);
        computer.connectDevice(keyboard);
    }
}

/*
* 추상클래스
* abstract class
* extends
* 단일 상속만
* 일반 메서드 가능
* 변수-일반필드가능
* 공통 기능+강제
*
* 인터페이스
* interface
* implements
* 여러개 구현 가능
* default 메서드로만
* 상수만 가능
* 순수 규격/계약정의
* */
