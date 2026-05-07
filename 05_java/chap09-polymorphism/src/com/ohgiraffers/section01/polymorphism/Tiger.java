package com.ohgiraffers.section01.polymorphism;

public class Tiger extends Animal{

    @Override
    public void cry() {
        System.out.println("호랑이가 울부짖는다.");
    }

    public void bite() {
        System.out.println("호랑이가 물어뜯습니다.");
    }
}
