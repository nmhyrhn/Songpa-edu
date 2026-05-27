package com.ohgiraffers.section02.initdestroy;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class Owner {

    @PostConstruct
    private void openShop(){
        System.out.println("가게 문을 열었습니다.");
    }

    @PreDestroy
    private void closeShop(){
        System.out.println("가게 문을 닫았습니다.");
    }
}
