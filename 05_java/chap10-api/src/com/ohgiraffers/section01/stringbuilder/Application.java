package com.ohgiraffers.section01.stringbuilder;

public class Application {
    public static void main(String[] args) {

        long startTime = System.nanoTime();//나노초 단위로 현재 시간 기록

        String str = "";
        for(int i=0; i<50000; i++) {
            str += i;
        }

        long endTime = System.nanoTime();//작업이 끝난 시간 기록

        System.out.println("String 합치기 5만번: " + (endTime - startTime));

        startTime = System.nanoTime();

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<50000; i++) {
            sb.append(i);
        }

        endTime = System.nanoTime();
        //반복문 안에서 문자열을 더해야 한다면 StringBuilder 사용
        System.out.println("StringBuilder 합치기 5만번: " + (endTime - startTime));

        StringBuilder sb2 = new StringBuilder("javamysql");
        System.out.println("sb2 = " + sb2);

        //delete(시작_인덱스, 끝_인덱스) : 특정 부분 삭제
        System.out.println(sb2.delete(4, 9));

        //insert(인덱스, 값) : 원하는 위치에 문자열 삽입
        System.out.println(sb2.insert(0,"my"));

        //reverse() : 문자열의 순서를 뒤집는다.
        System.out.println(sb2.reverse());

        //최종 결과물을 우리한테 익숙한 String 타입으로 변환
        String result = sb2.toString();
        System.out.println(result);
    }
}
