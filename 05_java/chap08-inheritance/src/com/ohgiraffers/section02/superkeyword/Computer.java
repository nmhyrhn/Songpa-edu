package com.ohgiraffers.section02.superkeyword;

import java.util.Date;

public class Computer extends Product {

    private String cpu;
    private int ram;

    public Computer() {
//        super();
        System.out.println("Computer 클래스의 기본 생성자 호출");
    }

    public Computer(String code, String brand, String name, int price, Date manufacturingDate, String cpu, int ram) {
        super(code, brand, name, price, manufacturingDate); //생성자의 첫 줄에 와야함! 부모의 생성자를 호출하는 구문
        this.cpu = cpu;
        this.ram = ram;
        System.out.println("Computer 클래스의 모든 필드 초기화 생성자 호출");
    }

    @Override
    public String getInformation() {
        String parentInfo =  super.getInformation();

        String computerInfo = ", Computer [ cpu=" + cpu + ", ram=" + ram + " ]";

        return parentInfo + computerInfo;
    }
}
