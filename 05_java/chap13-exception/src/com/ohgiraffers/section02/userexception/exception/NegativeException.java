package com.ohgiraffers.section02.userexception.exception;

public class NegativeException extends Exception {

    //생성자에서 받은 메세지는 super(message)로 부모인 Exception에 전달
    //그래야 e.getMessage()로 메세지를 꺼낼 수 있음
    public NegativeException(String message) {
        super(message);


    }

}
