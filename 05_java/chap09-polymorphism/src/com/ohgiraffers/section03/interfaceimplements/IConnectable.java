package com.ohgiraffers.section03.interfaceimplements;


//연결 가능한 장치들의 '표준 규격 계약서'이다.
public interface IConnectable {

    //인터페이스의 변수: 자동으로 public static final 상수
    //구현체 모두가 공유하는 규격값
    public static final int MAX_CONNECTION_COUNT = 4;

    //추상 메서드: 자동으로 public abstract
    public abstract void connect();
    void disconnect();

    //default 메서드: 인터페이스에 기본 구현 제공
    //기존 구현체를 수정하지 않고 새 기능을 추가할 때 사용 (하위 호환성)
    public default void showStatus() {
        System.out.println("장치가 대기 상태입니다.");
    }



}
