package com.ohgiraffers.section02.reference;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Application {
    public static void main(String[] args) {

        //1. 정적 메소드 참조
        Function<String, Integer> parseInt = str -> Integer.parseInt(str);

        //[메소드 참조]
        //클래스이름::정적메소드이름
        Function<String, Integer> ref_parseInt = Integer::parseInt;

        System.out.println(parseInt.apply("123") * 2);
        System.out.println(ref_parseInt.apply("123") * 2);

        //2. 특정 객체의 인스턴스 메서드 참조
        //객체참조변수::인스턴스메서드이름
        Consumer<String> println = str -> System.out.println(str);
        Consumer<String> ref_println = System.out::println;

        println.accept("hello");
        ref_println.accept("hello lambda");

        //forEach와 메서드 참조
        List<String> subject = Arrays.asList("java", "mysql", "jdbc");

        //subject -> System.out.println(subject); //받은 값을 그대로 넘기기만 한다면 메소드 참조로 줄일 수 잇음
        subject.forEach(System.out::println); //메소드 참조

        //불특정 다수 객체의 인스턴스 메소드 참조
        BiFunction<String, String, Boolean> equals = (s1, s2) -> s1.equals(s2);

        //클래스이름::인스턴스메소드이름
        //첫 번째 String이 주어가 되고, 두 번째 String이 비교대상이 된다.
        BiFunction<String, String, Boolean> ref_equals = String::equals;

        System.out.println(equals.apply("hello", "hello"));
        System.out.println(ref_equals.apply("hello", "world"));

        //생성자 참조
        //객체를 만드는 람다식을 줄이는 문법
        //클래스이름::new

        //Supplier<Account> : 아무 값도 받지 않고 Account 객체를 공급
        Supplier<Account> newAccount = Account::new;
        System.out.println(newAccount.get()); //.get(): 생성자 실행

        Function<String, Account> newAccountWithName = Account::new;
        System.out.println(newAccountWithName.apply("홍길동"));

        BiFunction<String, Integer, Account> newAccountFull = Account::new;
        System.out.println(newAccountFull.apply("유관순", 1000000));




    }
}
