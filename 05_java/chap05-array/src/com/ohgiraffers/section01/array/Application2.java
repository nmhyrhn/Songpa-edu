package com.ohgiraffers.section01.array;

public class Application2 {
    public static void main(String[] args) {

        // 배열 선언(stack 영역에 배열의 주소를 보관할 수 있는 레퍼런스 변수를 만드는 것)
        int[] iarr; // 더 권장되고 많이 사용되는 방식
        char carr[];

        // 배열 할당(heap 영역에 실제 데이터 공간을 할당하고, 그 주소값 반환)
        iarr = new int[5];

        // 선언과 동시에 할당
        int[] iarr2 = new int[5];

        // 블럭{}을 사용하여 값 할당
        int[] iarr3 = new int[]{11, 22, 33, 44, 55};
        int[] iarr4 = {11, 22, 33, 44, 55}; // 선언과 동시에 할당하는 경우만 new 생략 가능

        /*
         * 값을 넣지 않으면 기본값으로 채워진다.
         * 정수는 '0', 실수는 '0.0', 논리형은 'false' 문자형은 (\u0000), 참조형은 null */

        for(int i = 0; i < iarr.length; i++) {
            System.out.println(i + "번 인덱스의 값 : " + iarr[i]);
        }

        iarr[0] = 10;
        iarr[1] = 20;
        iarr[2] = 30;
        iarr[3] = 40;
        iarr[4] = 50;
//        iarr[5] = 60; // 5번 인덱스는 존재하지 않아 에러 발생

        for(int i = 0; i < iarr.length; i++) {
            System.out.println(i + "번 인덱스의 값 : " + iarr[i]);
        }

        String[] sarr = {"apple", "banana", "orange"};

        for(int i = 0; i < sarr.length; i++) {
            System.out.println(i + "번 인덱스의 값 : " + sarr[i]);
        }
    }
}