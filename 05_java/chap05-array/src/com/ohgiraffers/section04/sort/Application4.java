package com.ohgiraffers.section04.sort;

import java.util.Arrays;

public class Application4 {
    public static void main(String[] args) {
        /*
        *
        * */

        int[] arr = {2, 4, 5, 1, 3, 6};

        //Arrays.sort() : 자바 개발자들이 만들어 놓은 매우 효율적인 정렬 기능

        Arrays.sort(arr);

        System.out.println(Arrays.toString(arr));

    }
}
