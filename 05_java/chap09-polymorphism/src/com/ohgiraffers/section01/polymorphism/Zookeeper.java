package com.ohgiraffers.section01.polymorphism;

public class Zookeeper {

    //다형성을 사용하지 않았을 때
    public void feed(Cat cat) {
        System.out.println("고양이에게");
        cat.eat();
    }

    public void feed(Tiger tiger) {
        System.out.println("호랑이에게");
        tiger.eat();
    }
    //다형성 적용
    public void feed(Animal animal) {
        animal.eat(); //동적 바인딩: 실제 객체의 eat() 적용
    }
}
