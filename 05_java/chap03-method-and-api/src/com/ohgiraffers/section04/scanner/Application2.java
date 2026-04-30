package com.ohgiraffers.section04.scanner;

import java.util.Scanner;

public class Application2 {
    public static void main(String[] args) {
        /*
        * 숫자 입력 후 문자열 입력 시 발생하는 문제점
        * nextInt(), nextDouble(), next() 등은 입력된 값에서 해당 부분만 가져가고
        * 마지막에 입력된 '개행문자(\n)'는 입력 버퍼에 그대로 남겨둔다.
        *
        * 이 상태에서 nextLine()을 호출하면, 버퍼에 남아있는 개행문자를 읽고 즉시 종료해버려
        * 우리가 원하는 문자열을 입력할 수 없게 된다.
        * */

        Scanner sc = new Scanner(System.in);

        System.out.print("나이를 입력하세요 : ");
        int age = sc.nextInt();
        System.out.println(age);

        sc.nextLine();//버퍼에 남아있는 개행문자 제거

        System.out.print("사는 곳을 입력하세요 : ");
        String address = sc.nextLine();
        System.out.println(address);

        //안정적인 입력받기 = nextLine()으로 통일
        System.out.print("나이를 입력하세요 : ");
        String ageInput = sc.nextLine();
        int age1 = Integer.parseInt(ageInput); //문자열을 숫자로 변환
        System.out.println(ageInput);

        System.out.print("사는 곳을 입력하세요 : ");
        String address1 = sc.nextLine();
        System.out.println(address1);

        sc.close();

    }
}
