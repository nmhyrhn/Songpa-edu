package com.ohgiraffers.section02.extend.run;

import com.ohgiraffers.section02.extend.*;

public class Application {
    public static void main(String[] args) {

//        RabbitFarm<Snake> rabbitFarm = new RabbitFarm<>();
        //RabbitFarm에 제한을 걸었기에 사용 불가능 함, 타입 파라미터에 들어갈 수 없음

        RabbitFarm<Rabbit> farm1 = new RabbitFarm<>(new Rabbit());
        RabbitFarm<Bunny>  farm2 = new RabbitFarm<>(new Bunny());
        RabbitFarm<DrunkenBunny> farm3 = new RabbitFarm<>(new DrunkenBunny());

        FarmManager farmManager = new FarmManager();

        farmManager.manageAnyFarm(farm1);
        farmManager.manageAnyFarm(farm2);
        farmManager.manageAnyFarm(farm3);

        //farmManager.manageBunnyFarm(farm1); // 컴파일 에러 발생 bunny 상속 받아옴
        farmManager.manageBunnyFarm(farm2);
        farmManager.manageBunnyFarm(farm3);

        farmManager.manageRabbitOrBunnyFarm(farm1);
        farmManager.manageRabbitOrBunnyFarm(farm2);

    }
}
