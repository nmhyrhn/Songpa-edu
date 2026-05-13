package com.ohgiraffers.section03;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.function.*;

public class functionalInterface {
    public static void main(String[] args) {

        /*표준 함수형 인터페이스*/

        //1. Consumer<T>: T 타입의 인자를 받아 '소비'만 한다(반환값 없음)
        Consumer<String> printer = message -> System.out.println(message);
        printer.accept("hello, Consumer");

        //2. Supplier<T>: 아무것도 받지 않고, T 타입의 객체를 '공급'한다
        Supplier<LocalDateTime> timeSupplier = () -> LocalDateTime.now();
        System.out.println("현재 시간 : " + timeSupplier.get());

        //3. Function<T, R>: T 타입의 인자를 받아, R 타입의 객체로 '변환'하여 반환
        Function<String, Integer> length = str -> str.length();
        System.out.println(length.apply("hello, Function"));

        Function<String, Integer> parseInt = Integer::parseInt;
        System.out.println(parseInt.apply("hello, Function"));
        System.out.println(parseInt.apply("4123"));

        //4. Operator 계열: 입력 타입과 반환 타입이 같음 Function 계열
        //UnaryOperator<T>: 동일안 T타입 인자를 1개 받아 같은 T 타입으로 반환
        UnaryOperator<String> toUpper = str -> str.toUpperCase();
        System.out.println(toUpper.apply("hello"));

        //BinaryOperator<T>: 동일한 T타입 인자를 2개 받아 같은 T 타입으로 반환
        BinaryOperator<Integer> sumOperator = (num1, num2) -> num1 + num2;
        System.out.println(sumOperator.apply(10, 20));

        //BiFunction<T, T, T>의 특수 형태라 똑같이 apply 사용
        System.out.println(sumOperator.apply(10, 20));

        //5. Predicate<T>: T 타입의 인자를 받아 '검사'하고, boolean으로 '판단'히여 반환
        Predicate<String> isNotEmpty = str -> str != null &&!str.isEmpty();
        System.out.println(isNotEmpty.test("hello"));

    }
}
