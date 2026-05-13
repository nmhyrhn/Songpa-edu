package com.ohgiraffers.section01.intenum;

public class Application {
    public static void main(String[] args) {

        int subject1 = Subject.JAVA; //0
        int subject2 = Subject.HTML; //0

        //문제 1: 값이 같아서 다른 과목인데 '같다'고 판단
        if(subject1 == subject2){
            System.out.println("두 과목은 같은 과목입니다.");
        }

        //문제 2: 타입 안정성 없음
        printSubject(2);
        printSubject(Subject.MYSQL);
        printSubject(100);
        //잘못된 정보를 입력해도 에러가 없음

        /*
        * 문제상황 : 이름이 겹칠경우 접두어를 붙여야해서 코드가 지저분해짐
        * BACKEND_JAVASCRIPT
        * FRONTEND_JAVASCRIPT
        * */



    }


        public static void printSubject(int subjectNumber) {
            String subject = "";

            switch (subjectNumber) {
                case Subject.JAVA -> subject = "Java";
                case Subject.MYSQL -> subject = "MYSQL";
                case Subject.JDBC -> subject = "JDBC";

            }
            System.out.println("전달받은 과목 : " + subject);
        }


}
