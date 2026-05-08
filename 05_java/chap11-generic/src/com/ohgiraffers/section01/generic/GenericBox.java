package com.ohgiraffers.section01.generic;

//<T>: 타입 파라미터(Type Parameter). T는 타입을 담는 변수 자리
//이 클래스를 사용할 때 T 자리에 실제 타입을 넣는다.
public class GenericBox<T> {

    private T content;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }



}
