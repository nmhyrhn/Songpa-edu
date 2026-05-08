package com.ohgiraffers.section02.extend;

//T는 Rabbit 또는 Rabbit의 자손이어야만 한다.
public class RabbitFarm<T extends Rabbit> {

    private T animal;

    public RabbitFarm() {
    }

    public RabbitFarm(T animal) {
        this.animal = animal;
    }

    public T getAnimal() {
        return animal;
    }

    public void setAnimal(T animal) {
        this.animal = animal;
    }
}
