package com.ohgiraffers.common;


//계좌 기능을 정의한 인터페이스
public interface Account {

    //잔액 조회
    String getBalance();

    //입금
    String deposit(int money);

    //출금
    String withDraw(int money);




}
