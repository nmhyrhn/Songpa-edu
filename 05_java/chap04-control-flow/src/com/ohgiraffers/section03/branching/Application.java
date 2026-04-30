package com.ohgiraffers.section03.branching;

public class Application {
    public static void main(String[] args) {

        Application app = new Application();
//        app.simpleBreak();
        app.nestedBreak();
//        app.simpleContinue();
//        app.nestedContinue();

    }

    public void simpleBreak() {

        int sum = 0;
        int i = 1;

        while(true) {
            sum += i;
            System.out.println(sum);

            if(i == 10) {
                System.out.println("i가 10이 되어 반복문 탈출");
                break;  // 가장 가까운 반복문 중단하고 탈출
            }
            i++;
        }
        System.out.println("최종 합 : " + sum);

    }

    public void nestedBreak() {
        // 바깥쪽 for문 - 단(2~9)을 제어
        ohgiraffers:    // 이름표 붙임(추천X)
        for (int dan = 2; dan <= 9; dan++) {
            System.out.println("---" + dan + "단---");
            // 안쪽 for문: 곱하는 수(1~9)를 제어
            for(int su = 1; su <= 9; su++) {
                if(su > 5) {
//                    break ; // 안쪽 for문만 탈출
                    break ohgiraffers;  // 이름표 붙인 반복문 탈출(추천X)
                }
                System.out.println(dan + " * " + su + " = " + dan * su);
            }
            System.out.println();
        }
    }
    public void simpleContinue() {

        for(int i = 1; i <= 100; i++) {
            // 4의 배수이면서 동시에 5의 배수가 아니라면
            if(!(i % 4 == 0 && i % 5 == 0)) {
                continue;   // 이번 반복 회차를 건너뛰고 증감식으로 이동
            }
            System.out.println(i);
        }
    }

    public void nestedContinue() {
        // 바깥쪽 for문 - 단(2~9)을 제어
        for (int dan = 2; dan <= 9; dan++) {
            System.out.println("---" + dan + "단---");
            // 안쪽 for문: 곱하는 수(1~9)를 제어
            for(int su = 1; su <= 9; su++) {
                if(su % 2 == 0) {
                    continue;  // 안쪽 for문만 건너뛴다
                }
                System.out.println(dan + " * " + su + " = " + dan * su);
            }
            System.out.println();
        }
    }

    // Flag 변수 활용하여 반복문 제어
    public void handleNestedBreak() {

        boolean isBreak = false;    // 탈출 신호를 보낼 Flag 변수

        for(int dan = 2; dan < 10; dan++) {
            for(int su = 1; su < 10; su++) {
                if(dan == 3 && su == 5) {
                    isBreak = true;
                    break;  // 우선 가까운 반복문 탈출
                }
                System.out.println(dan + " * " + su + " = " + dan * su);
            }
            if (isBreak) {
                break;  // 탈출신호를 확인하고 바깥쪽 반복문도 탈출
            }
            System.out.println();
        }
    }

}