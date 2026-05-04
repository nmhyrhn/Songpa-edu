package com.ohgiraffers.section05.overloading;

public class BadCalculator {

    //정수 더하기
    public int addTowInts(int num1, int num2) {
        return num1 + num2;
    }

    //실수 더하기
    public double addTowDoubles(double num1, double num2) {
        return num1 + num2;
    }

    //정수 3개 더하기
    public int addThreeInts(int num1, int num2, int num3) {
        return num1 + num2 + num3;

    }
}
