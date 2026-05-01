package com.ohgiraffers.section04.sort;

import java.util.Arrays;

public class Application5 {
    public static void main(String[] args) {

        /*배열과 정렬을 응용하여 중복 없는 로또 번호 생성하기*/

        //1. 6개의 숫자를 담을 배열 생성
        int[] arr = new int[6];

        //2. 중복되지 않는 난수를 생성하여 배열에 담기
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random() * 45) + 1;

            //중복 검사
            for (int j=0; j<i; j++) {
                if(arr[i] == arr[j]) {
                    i--; // 이번 회차 무료 (다시 뽑기)
                    break;
                }
            }
        }

        //3. 정렬하여 출력하기
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
