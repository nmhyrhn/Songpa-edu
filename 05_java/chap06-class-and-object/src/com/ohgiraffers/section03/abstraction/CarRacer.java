package com.ohgiraffers.section03.abstraction;

public class CarRacer {

    //자신의 자동차를 가지고 있다.(소유 관계 / has-a 관계)
    private final Car myCar = new Car(); //필드로 선언해준거임


    //시동을 걸도록 명령
    public void startUp() {
        // 카레이서는 시동 거는 방법을 직접 모른다.
        // 단지 자신이 차에게 '시동 걸어!'라고 요청(메세지 전송) 할 뿐이다.
        myCar.startUp();
    }

    // 엑셀 밟도록 명령
    public void stepAccelator() {
        myCar.go();
    }

    public void stepBreak() {
        myCar.stop();
    }

    public void turnOff() {
        myCar.turnOff();
    }

}
