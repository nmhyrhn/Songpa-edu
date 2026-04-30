package com.ohgiraffers.section01.array;

public class Application {
    public static void main(String[] args) {

        /*
         * 배열이란?
         * 동일한 자료형의 묶음(연속된 메모리 공간에 값을 저장하고 사용하기 위한 용도)이다.
         * 배열은 heap 영역에 new 연산자를 이용하여 할당한다.
         * */

        int score1 = 80;
        int score2 = 90;
        int score3 = 40;
        int score4 = 70;

        int sumOfScores = score1 + score2 + score3 + score4;
        double avgOfScores = sumOfScores / 4.0;

        System.out.println(sumOfScores);
        System.out.println(avgOfScores);

        /* 배열의 선언 및 할당 */
        int[] scores = new int[5];  // 5개의 int를 담을 수 있는 배열 생성

        /* 배열의 각 공간(인덱스)에 값 대입 */
        scores[0] = 90;
        scores[1] = 97;
        scores[2] = 60;
        scores[3] = 80;
        scores[4] = 59;

        int sum2 = 0;
        for(int i = 0; i < scores.length; i++) {
            sum2 += scores[i];
        }

        double avg2 = sum2 / (double) scores.length;

        System.out.println(sum2);
        System.out.println(avg2);
    }
}