package com.ohgiraffers.section02.looping;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        Application app = new Application();
//        app.whyNeedLoops();
//        app.forLoopSum();
//        app.nestedForLoops();
//        app.nestedForLoop2();
//        app.whileLoop();
        app.whileLoop2();
    }

    public void whyNeedLoops() {

        Scanner sc = new Scanner(System.in);

        System.out.println("1번째 학생의 이름 : ");
        String student1 = sc.nextLine();
        System.out.println("1번째 학생의 이름은 " + student1 + "입니다.");

        System.out.println("2번째 학생의 이름 : ");
        String student2 = sc.nextLine();
        System.out.println("2번째 학생의 이름은 " + student2 + "입니다.");

        for (int i=1; i<=5; i++) {
            System.out.print(i + "번째 학생의 이름 : ");
            String student = sc.nextLine();
            System.out.println(i + "번째 학생의 이름은 " + student + "입니다.");
        }
    }

    //누적 합계 구하기
    public void forLoopSum() {
        //1부터 사용자가 입력한 숫자까지의 합계 구하기
        Scanner sc = new Scanner(System.in);
        System.out.print("합계를 구할 양의 정수를 입력 : ");
        int num = sc.nextInt();

        int sum = 0;
        for (int i=1; i<=num; i++) {
            sum += i; // sum = sum + 1
        }
        System.out.println("1부터 " + num + "까지의 합 : " + sum);


    }

    //중첩 for문 - 구구단
    public void nestedForLoops() {
        //바깥쪽 for문 - 단(2~9)을 제어
        for (int i = 2; i <= 9; i++) {
            System.out.println("---" + i + "단---");
            //안쪽 for문 - (1~9)을 제어
            for (int j = 1; j <= 9; j++) {
                System.out.println(i + " * " + j + " = " + i * j);
            }
        }
    }

        public void nestedForLoop2() {
            Scanner sc = new Scanner(System.in);
            System.out.print("출력할 줄 수 입력 : ");
            int row = sc.nextInt();
            for (int i = 1; i <= row; i++) {
                for (int j = 1; j <= i; j++) {
                    System.out.print("*");
                }
                System.out.println();
            }

        }

        //while
    public void whileLoop() {
        Scanner sc = new Scanner(System.in);
        String str = "";


        // equals() : 문자열의 내용이 같은지 비교하는 메소드
        while(!str.equals("exit")) {
            System.out.print("문자열을 입력하세요('exit' 입력 시 종료) : ");
            str = sc.nextLine();
            System.out.println("입력한 문자열 : " + str);
        }
        System.out.println("프로그램을 종료합니다.");
    }

    //do-while 문 - 최소 한 번은 실행해야 할 때
    public void whileLoop2() {
        Scanner sc = new Scanner(System.in);
        String str;

        //do 블럭 모드 먼저 실행 후, while 조건 검사
        do{
            System.out.print("문자열을 입력하세요('exit' 입력 시 종료) : ");
            str = sc.nextLine();
            System.out.println("입력한 문자열 : " + str);
        } while (!str.equals("exit"));
        System.out.println("프로그램을 종료합니다.");
    }

}
