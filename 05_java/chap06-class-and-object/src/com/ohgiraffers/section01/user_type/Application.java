package com.ohgiraffers.section01.user_type;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Application {
    public static void main(String[] args) {

        //이전까지 사용하던 자바의 기본 자료형
        int age = 20;
        String name = "홍길동";

        /*사용자 정의 자료형 사용하기
        * 1. 클래스(설계도) 만든다. (Member.java)
        * 2. 'new' 연산자를 통해 설계도대로 실제 객체를 메모리에 생성한다.
        * 3. 생성된 객체의 주소값을 담을 변수(참조 변수)를 선언하고, 주소값을 저장한다.
        *
        * 자료형 변수명 = new 클래스명();
        * */

        Member member = new Member();

        member.id = "user01";
        member.pwd = "pass01";
        member.age = 27;
        member.gender = '남';
        member.hobby = new String[]{"축구", "볼링", "테니스"};

        System.out.println(member.id);
        System.out.println(member.pwd);
        System.out.println(member.age);
        System.out.println(member.gender);
        System.out.println(Arrays.toString(member.hobby));
        for(int i=0;i<member.hobby.length;i++){
            System.out.println(member.hobby[i] + " ");
        }


    }
}
