package com.ohgiraffers.section03.interfaceimplements;

public class Computer {

    //핵심 파라미터 타입이 Mouse도, Keyboard도 아닌 인터페이스 이다.
    //규격만 맞으면 뭐든 받겠다는 선언
    public void connectDevice(IConnectable device){
        System.out.println("컴퓨터의 포트에 장치를 연결합니다.");
        device.connect();
    }
}
