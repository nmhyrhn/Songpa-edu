package com.ohgiraffers.section03.interfaceimplements;


//implements: 인터페이스를 구현한다. 쉼표로 여러 개 가능
public class Keyboard implements IConnectable{
    @Override
    public void connect() {
        System.out.println("키보드가 포트에 연결 되었습니다.");
    }

    @Override
    public void disconnect() {
        System.out.println("키보드 연결을 해제합니다.");
    }

    @Override
    public void showStatus() {
        IConnectable.super.showStatus();
    }
}
