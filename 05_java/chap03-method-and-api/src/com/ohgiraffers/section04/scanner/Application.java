package com.ohgiraffers.section04.scanner;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        /*
        * java.util.scanner를 이용해 다양한 자료형 값 입력받기
        * */

        //1. Scanner 객체 생성
        Scanner sc = new Scanner(System.in);

        //nextLine() : 엔터 키 이전까지의 한 줄 전체를 문자열로 읽음
        System.out.print("이름을 입력하세요 : ");
        String name = sc.nextLine();
        System.out.println("입력한 이름은 " + name + "입니다.");

        //next() : 공백문자나 개행문자 전까지를 문자열로 읽음
        System.out.print("인사말을 입력해주세요. : ");
        String greeting = sc.next();
        System.out.println(greeting);

        //nestInt() : 공 백 이전까지의 정수 값을 읽음
        System.out.print("나이를 입력하세요 : ");
        int age = sc.nextInt();
        System.out.println(age);

        //nextDouble() : 공백 이전까지의 실수 값을 읽음
        System.out.print("키를 입력해주세요 : ");
        double height = sc.nextDouble();
        System.out.println(height);

        /* 문자를 직접 입력 받는 기능은 없다.
        * 문자열로 입력 받고, 원하는 순번째 문자를 분리해서 사용해야 한다.
        * charAt(int index)를 사용한다
        * */

        sc.nextLine();
        System.out.print("아무 문자나 입력 해주세요~ : ");
        char ch = sc.nextLine().charAt(0);
        System.out.println("입력한 문자는 " + ch + "입니다.");



    }
}
