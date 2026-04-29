package com.ohgiraffers.section03.constant;

public class Application1 {
    public static void main(String[] args) {
        /*
        * 상수란?
        *  변하지 않는 값을(항상 고정된 값을) 저장해두기 위한 메모리상의 공간을 상수라고 한다.
        * */

        //상수 선언 시 자료형 앞에 final 키워드를 붙인다.
        final int AGE;

        AGE = 20; //초기화
//        AGE = 20; //초기화 이후 값 재대입 불가로 컴파일 에러 발생

        int sum = AGE;
//        int AGE = AGE + 10; //불가함

        /*상수의 명명 규칙
        * 변수의 명명 규칙과 컴파일 에러를 발생시키는 규칙은 동일
        *
        * 암묵적인 규칙
        * 1. 모든 문자는 영문자 대문자 혹은 숫자만 사용
        * 2. 단어와 단어 연결은 언더스코어(_)를 사용
        * */

        final int AGE2;
//        final int age2; //가능하지만 사용하지 않음

        final int MAX_AGE = 50;

    }
}
