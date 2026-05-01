package com.ohgiraffers.section02.encapsulation;

public class Application {
    public static void main(String[] args) {

        Children child1 = new Children();
//        child1.age = 5;

        child1.setAge(7);
        System.out.println(child1.getAge());
    }
}
