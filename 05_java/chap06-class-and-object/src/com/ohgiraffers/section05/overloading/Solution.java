package com.ohgiraffers.section05.overloading;

public class Solution {
    public static void main(String[] args) {
        Calculator calc = new Calculator();

        System.out.println(calc.add(10, 20));
        System.out.println(calc.add(10.5, 20.4));
        System.out.println(calc.add(10, 20, 30));
    }
}
