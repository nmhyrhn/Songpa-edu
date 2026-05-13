package com.ohgiraffers.section01.intro;

@FunctionalInterface //<- 이 인터페이스는 메소드가 딱 하나만 있어야 해 를 의미
//함수형 인터페이스 : 메소드가 단 하나만 정의도니 인터페이스(람다식 사용의 필수 조건)
public interface Calculator {

    int sumTwoNumber(int a, int b);
}
