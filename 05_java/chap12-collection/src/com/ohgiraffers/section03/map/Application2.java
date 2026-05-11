package com.ohgiraffers.section03.map;

import java.util.Properties;

public class Application2 {
    public static void main(String[] args) {
        /*
        * Properties : HashMap을 상속받은 특수 map
        * key와 value 모두 반드시 String
        * 주 용도: 프러그램 설정 정보(DB 연결정보, 환경설정 값) 관리
        * */

        Properties prop = new Properties();

        prop.setProperty("driver", "com.mysql.cj.jdbc.Driver");
        prop.setProperty("url", "jdbc:mysql://localhost/menu");
        prop.setProperty("user", "ohgiraffers");
        prop.setProperty("password", "ohgiraffers");

        System.out.println(prop);

        String driver = prop.getProperty("driver");
        String user = prop.getProperty("user");
        System.out.println("driver = " + driver);
        System.out.println("user = " + user);
        System.out.println("없는 설정 : " + prop.getProperty("없는설정키", "기본설정"));

    }
}
