package com.ohgiraffers.section03.abstraction;

public class Car {
    private boolean isOn; // 시동 상태
    private int speed; // 현재 시속

    /*자동차가 직접 할 수 있는 행동(메서드) 정의*/
    //시동을 거는 기능
    public void startUp() {
        if (isOn) {
            System.out.println("이미 시동이 걸려 있습니다.");
        } else {
            this.isOn = true;
            System.out.println("시동을 걸었습니다. 출발 준비 완료");
        }
    }

    public void go() {
        if (isOn) {
            System.out.println("차가 앞으로 움직입니다.");
            this.speed += 10;
            System.out.println("현재 시속은 " + this.speed + "입니다.");
        } else {
            System.out.println("시동을 먼저 걸어주세요.");
        }
    }

    public void stop() {
        if (isOn) {
            if(this.speed > 0) {
                this.speed = 0;
                System.out.println("차가 서서히 멈춥니다.");
            } else {
                System.out.println("차가 이미 멈춰있는 상태입니다.");
            }
        } else {
            System.out.println("시동이 걸려있지 않습니다.");
        }
    }

    public void turnOff() {
        if (isOn) {
            if(speed > 0) {
                System.out.println("차를 먼저 멈춰주세요.");
            } else {
                this.isOn = false;
                System.out.println("시동을 끕니다.");
            }
        } else {
            System.out.println("이미 시동이 꺼져있는 상태입니다.");
        }
    }

}
