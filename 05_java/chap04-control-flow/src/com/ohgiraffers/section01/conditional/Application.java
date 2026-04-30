package com.ohgiraffers.section01.conditional;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        Application app = new Application();
//        app.testSimpleIf();
//        app.testNestedIf();
//        app.testSimpleIfElse();
//        app.testGradeCalculation();
        app.testSwitch();

    }
        //1. if
        public void testSimpleIf(){
            Scanner sc = new Scanner(System.in);

            System.out.print("숫자 한 개를 입력하세요 : ");
            int num = sc.nextInt();

            if(num % 2 == 0) {
                //조건 true일 때 이 블록 실행
                System.out.println("입력한 숫자는 짝수입니다.");
            }
            System.out.println("프로그램을 종료합니다.");
        }

        //2. 중첩 if문과 논리 연산자
        public void testNestedIf(){
            Scanner sc = new Scanner(System.in);

            System.out.print("숫자 한 개를 입력하세요 : ");
            int num = sc.nextInt();

            //첫 번째 조건: 양수인가?
            if(num > 0) {
                //두 번째 조건: 짝수인가?
                if(num % 2 == 0) {
                    //조건 true일 때 이 블록 실행
                    System.out.println("입력한 숫자는 짝수입니다.");
                }
            }
            if(num > 0 && num % 2 == 0) {
                System.out.println("입력한 숫자는 양수이면서 짝수입니다!!!");
            }
            System.out.println("프로그램을 종료합니다.");
        }

        //
        public void testSimpleIfElse(){
            Scanner sc = new Scanner(System.in);

            System.out.print("숫자 한 개를 입력하세요 : ");
            int num = sc.nextInt();

            if(num % 2 == 0) {
                //조건 true일 때 이 블록 실행
                System.out.println("입력한 숫자는 짝수입니다.");
            } else {
                System.out.println("입력한 정수는 홀수입니다.");
            }
            System.out.println("프로그램을 종료합니다.");
        }

        //4. if-else-if
        public void testGradeCalculation() {

        Scanner sc = new Scanner(System.in);
        System.out.print("학생의 점수를 입력하세요 : ");

        int point = sc.nextInt();
        String grade = "";

        if(point >= 90) {
            grade = "A";
            if(point >= 95) {grade += "+";}
        } else if(point >= 80) {
            grade = "B";
        } else if(point >= 70) {
            grade = "C";
        }  else if(point >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }
            System.out.println("점수는 " + grade + "입니다.");
        }

        //5. switch문
        public void testSwitch() {
            Scanner sc = new Scanner(System.in);

            System.out.print("첫 번째 정수 : ");
            int first = sc.nextInt();
            System.out.print("두 번째 정수 : ");
            int second = sc.nextInt();
            System.out.println("연산 기호 입력(+,-,*,/,%) : ");

//        char op = sc.nextLine().charAt(0); //버퍼에 남아있는 개행문자를 만나 읽기 종료되어 에러 발생
            char op = sc.next().charAt(0); //next()는 버퍼에 남아있는 \n(개행문자)를 무시하고 다음 단어를 읽

            int result = 0;
            switch (op) {
                case '+':
                    result = first + second;
                    break;
                case '-':
                    result = first - second;
                    break;
                case '*':
                    result = first * second;
                    break;
                case '/':
                    result = first / second;
                    break;
                case '%':
                    result = first % second;
                    break;
                default: //위 case문 아무것도 해당되지 않을 때
                    System.out.println("잘못된 연산 기호를 입력했습니다.");
                    return;
            }
            System.out.println(first + " " + op + " " + second + " = " + result);


            // 향샹된 switch문 예시
            int modernResult = switch (op) {
                case '+' -> first + second;
                case '-' -> first - second;
                case '*' -> first * second;
                case '/' -> first / second;
                case '%' -> first % second;
                default -> {
                    System.out.println("잘못된 기호입니다.");
                    yield 0; //값 반납
                }
            };
            System.out.println(first + " " + op + " " + second + " = " + modernResult);

    }
}
