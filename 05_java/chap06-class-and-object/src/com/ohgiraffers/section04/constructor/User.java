package com.ohgiraffers.section04.constructor;

import java.util.Date;

public class User {

    private String id;
    private String pwd;
    private String name;
    private java.util.Date enrollDate;

    /*
    * [생성자]
    * new 연산자를 통해 겍체가 메모리(heap)에 생성될 때, 가장 먼저 단 한번 호출되는 초기화 메소드
    * 객체의 필드를 원하는 갓으로 '초기화'
    *
    * [생성자 작성 규칙]
    * 1. 이름이 클래스명과 반드시 동일해야 한다.
    * 2. 반환타입(void, int 등)을 쓰지 않는다.
    *
    * 접근제한자 클래스명(매개변수) {
    *   필드 초기화 코
    * }
    * */

    //1. 기본 생성자 - 만약 매개변수 있는 생성자가 하나라도 있다면 컴파일러가 자동으로 생성 해주지 않는다
    public User() {
        System.out.println("User 클래스의 기본 생성자 호출됨");
    }

    //2. 매개변수 있는 생성자
    public User(String id, String pwd, String name) {
        //this : '생성도니 객체 자기 자신'을 가리키는 키워드
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        System.out.println("id, pwd, name을 초기화 하는 생성자 호출");
    }

    public User(String id, String pwd, String name, java.util.Date enrollDate) {
        /*
        * this() : 같은 클래스 내의 다른 생성자를 호출한다.
        * 반드시 생성자 내부의 '첫 번째 줄'에 작성해야 한다.
        * */
//        this.id = id;
//        this.pwd = pwd;
//        this.name = name;
        this(id, pwd, name);
        this.enrollDate = enrollDate;
        System.out.println("모든 필드를 초기화 하는 생성자 호출됨");
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }

    public String getInformation() {
        return "User [id=" + this.id + ", pwd=" + this.pwd + ", name=" + this.name + ", enrollDate=" + this.enrollDate + "]";
    }

}
