package com.ohgiraffers.section01.method;

public class Calculator {

    //non-static 메서드
    public int minNumberOf(int first, int second) {
        return (first < second) ? first : second;
    }

    //static 메서드
    public static int maxNumberOf(int first, int second) {
        return (first < second) ? second : first;
    }
}
