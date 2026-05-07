package com.ohgiraffers.section02.abstractclass;

public class Application {
    public static void main(String[] args) {

        Warrior warrior = new Warrior();
        Wizard wizard = new Wizard();

        warrior.attack();
        wizard.attack();
        wizard.levelUp();

        //Player player = new Player(); // 객체(new)로 생성 불가 -> 추상클래스이기 때문

        Player[] players = new Player[2];
        players[0] = new Warrior();
        players[1] = new Wizard();

        for( Player player : players ){
            player.attack();
        }

    }
}
