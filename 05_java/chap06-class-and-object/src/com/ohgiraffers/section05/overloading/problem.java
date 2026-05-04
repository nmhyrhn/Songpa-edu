package com.ohgiraffers.section05.overloading;

public class problem {
    public static void main(String[] args) {
        BadCalculator bc = new BadCalculator();

        System.out.println(bc.addTowInts(10, 20));
        bc.addTowDoubles(10.5, 20.4);
        bc.addThreeInts(20, 30, 40);
    }
}
