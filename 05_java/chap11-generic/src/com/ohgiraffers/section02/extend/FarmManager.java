package com.ohgiraffers.section02.extend;

public class FarmManager {

    //RabbitFarm<?>의 <?>는 와일드 카드 '?'를 이용해 아무 타입이든 괜찮다
    public void manageAnyFarm(RabbitFarm<?> farm) {
        System.out.println("어떤 토끼 농장이든 관리 가능해요");
        farm.getAnimal().cry();
    }

    public void manageBunnyFarm(RabbitFarm<? extends Bunny> farm) {
        System.out.println("바니 혹은 드렁큰 바니만 관리 가능해요");
        farm.getAnimal().cry();
    }

    public void manageRabbitOrBunnyFarm(RabbitFarm<? super Bunny> farm) {
        System.out.println("Rabbit 혹은 바니만 관리 가능해요");
        farm.getAnimal().cry();
    }
}
