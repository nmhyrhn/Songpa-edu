package com.ohgiraffers.section06.singleton;

public class Application {
    public static void main(String[] args) {
        /*
        * 디자인 패턴이란?
        * 자주 등장하는 문제를 효과적으로 해결하기 위한 검증도니 설계 템플릿
        *
        * [싱글톤(singleton)패턴]
        * - 프로그램 전체에서 특정 클래스의 인스턴스가 단 하나만 존재하도록 보장한다
        * (ex. DB 연결 설정, 로그 시스템, 앱 전역 설정값 등)
        *
        * [핵심 구현 규칙]
        * 1. 외부에서 new로 마음대로 객체를 못 만들게 막는다 -> 생성자를 private
        * 2. 클래스 스스로 자기 객체를 딱 하나 만들어 보관한다 -> private static 필드
        * 3. 그 하나의 객체를 외부에 전달해줄 '창구'를 만든다 -> public static 메서드
        * */

        //이른 초기화
//        EagerSingleton eager1 = new EagerSingleton(); //컴파일 에러 발생 -> 생성자 private로 객체 생성 불가

        EagerSingleton eager1 = EagerSingleton.getInstance();
        EagerSingleton eager2 = EagerSingleton.getInstance(); //여러개를 만들어도 이미 만들어드 1개만 반환한다

        System.out.println(eager1.hashCode());
        System.out.println(eager2.hashCode()); //같은 주소값을 가짐

        System.out.println(eager1 == eager2);

        //게으른 초기화
        LazySingleton lazy1 = LazySingleton.getInstance();
        LazySingleton lazy2 = LazySingleton.getInstance();

        System.out.println(lazy1.hashCode());
        System.out.println(lazy2.hashCode());

        System.out.println(lazy1 == lazy2);

    }
}
