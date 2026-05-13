package com.ohgiraffers.section02.reference;

public class Account {

    private String ownerName;
    private int balance;

    public Account() {
        System.out.println("기본 생성자 호출됨");
    }

    public Account(String ownerName) {
        this.ownerName = ownerName;
        System.out.println("String 생성자 호출됨");
    }

    public Account(String ownerName, int balance) {
        this.ownerName = ownerName;
        this.balance = balance;
        System.out.println("String, int 생성자 호출됨");
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "ownerName='" + ownerName + '\'' +
                ", balance=" + balance +
                '}';
    }
}
