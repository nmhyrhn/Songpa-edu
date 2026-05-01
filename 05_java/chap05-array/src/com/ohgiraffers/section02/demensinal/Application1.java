package com.ohgiraffers.section02.demensinal;

public class Application1 {
    public static void main(String[] args) {
        /*
        * 다차원 배열
        * 2차원 이상의 배열을 의미하며, 배열의 인덱스마다 또 다른 배열 주소를 보관하는 배열이다.
        * 즉, 2차원 배열은 1차원 배열 여러 개를 하나로 묶어 관리하는 배열이다.
        * */

        //2차원 배열 선언 및 할당
        int[][] iarr = new int[3][5]; //정변 배열

        int[][] iarr2 = {{1,2,3,4,5}, {6,7,8,9}, {10,11,12}}; //가변 배열


        //중첩 반복문을 이용한 값 대입
        int value = 1;

        for(int i=0; i<iarr.length; i++){
            for(int j=0; j<iarr[i].length; j++){
                iarr[i][j] = value;
            }
        }

        for(int i=0; i<iarr.length; i++){
            for(int j=0; j<iarr[i].length; j++){
                System.out.print(iarr[i][j] + " ");
            }
            System.out.println();
        }
    }
}
