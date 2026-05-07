package com.ohgiraffers.section01.extend;

public class Application {
    public static void main(String[] args) {

        Car car = new Car();
        car.run();
        car.soundHorn();
        car.stop();
        car.soundHorn();

        System.out.println("======");

        FireCar fireCar = new FireCar();
        fireCar.run();
        fireCar.soundHorn();
        fireCar.stop();
        fireCar.soundHorn();
        fireCar.sprayWater();

        System.out.println("======");

        RacingCar racingCar = new RacingCar();
        racingCar.run();


    }
}
