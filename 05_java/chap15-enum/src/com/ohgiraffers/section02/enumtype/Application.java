package com.ohgiraffers.section02.enumtype;

public class Application {
    public static void main(String[] args) {

        //해결 1: 타입이 달라서 비교 자체가 의미있음
        Subjects subject1 = Subjects.JAVA; //Subjects 타입의 JAVA 인스턴스
        Subjects subject2 = Subjects.HTML; //Subjects 타입의 HTML 인스턴스

        if(subject1 == subject2) {
            System.out.println("같은 과목");
        } else {
            System.out.println("다른 과목");
        }

        //해결 2: 타입 안정성 - Subjects 외의 값은 컴파일 에러
        //printSubject(2);
        //printSubject("HTML");
        printSubject(Subjects.HTML);

        //해결 3: values()로 전체 순회 가능
        System.out.println("전체 과목 목록");
        for (Subjects s : Subjects.values()) {
            //ordinal() : 선언 순서 (0부터)
            //name() : 상수 이름 자체를 문자열로 반환
            System.out.println((s.ordinal()+1) + ", " + s.name());
        }

        }

        //Subjects 타입만 받는다, 타입 안정성이 높아짐
        public static void printSubject(Subjects subject) {
        //toString()은 name()과 동일 - 상수의 이름을 문자열로 꺼내는 메서드
            System.out.println("전달받은 과목 : " + subject.toString());
    }

}
