package com.ohgiraffers.section02.javaconfig;

public class Application {
    public static void main(String[] args) {

        MenuService menuService = new MenuService();
        menuService.selectAllMenu().forEach(System.out::println);

    }
}
