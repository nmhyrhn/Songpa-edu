package com.ohgiraffers.section02.uses;

public class Member {


    private int num;
    private String id;
    private String pwd;
    private int age;
    private char gender;

    //기본생성자
    public Member() {
    }

    //전체 필드 초기화 생성자
    public Member(int num, String id, String pwd, int age, char gender) {
        this.num = num;
        this.id = id;
        this.pwd = pwd;
        this.age = age;
        this.gender = gender;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getInformation() {
        return id + ", " + age + ", " + gender;
    }

}
