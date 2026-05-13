package com.ohgiraffers.section01.intro;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class Application {
    public static void main(String[] args) {

        /*람다식은 메소드가 하나뿐인 인터페이스의 구현을 짧게 쓰는 문법이다*/

        //전통 방식 사용
        Calculator c1 = new CalculatorImpl();
        System.out.println(c1.sumTwoNumber(10, 20));

        //익명 클래스 방식
        Calculator c2 = new CalculatorImpl() {
            @Override
            public int sumTwoNumber(int a, int b) {
                return a+b;
            }
        };
        //람다식 사용
        Calculator c3 = (x, y) -> x+y;
        System.out.println(c3.sumTwoNumber(10, 20));

        //int와 int를 받아 int를 반환하는 것처럼 흔한 함수모양은
        //java.util.function 패키지에 표준 함수형 인터페이스로 준비되어 있다.

        //BiFunction<T, U, R> : T, U 두 인수를 받아 R 타입 반환
        BiFunction<Integer, Integer, Integer> sum = (x, y) -> x + y;
        System.out.println(sum.apply(100, 200));

        //Consumer<T>: T를 받아 소비(처리)만 하고 반환 없음
        List<String> names = Arrays.asList("홍길동", "유관순", "이순신");

        for(String name : names) {
            System.out.println(name);
        }

        Consumer<String> pintName = name -> System.out.println("환영합니다 " + name + "님");
        //forEach: 리스트의 각 요소를 하나씩 꺼내 전달받은 Consumer에 넘겨준다.
        //printName에 담긴 람다식이 각 이름마다 실행됨
        names.forEach(pintName);

        //한줄로 줄이기
        names.forEach(name-> System.out.println("환영합니다!" + name + "님"));

        //외부 지역 변수 사용
        String prefix = "[회원]";
        names.forEach(name -> System.out.println(prefix + name));

        //prefix = "[고객]"; //재할당 시도 시 위쪽의 prefix에 컴파일 에러가 발생
        //람다 안에서 사용하는 외부 지역 변수는 final 또는 effectively final 이어야 함

    }
}
