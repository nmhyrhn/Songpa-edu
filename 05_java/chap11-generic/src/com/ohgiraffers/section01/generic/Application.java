package com.ohgiraffers.section01.generic;

public class Application {
    public static void main(String[] args) {

        NormalBox normalBox = new NormalBox();
        normalBox.setContent("안녕하세요");

        //String content = normalBox.getContent(); //Object 선언했는데 String으로 진행해서 오류됨
        //꺼낼 때 반드시 강제 형변환
        String content = (String) normalBox.getContent(); //다운캐스팅 하면 가능함 (String)

        normalBox.setContent(123); // 실수로 숫자를 넣어도 컴파일 에러 안남

        //String wrongContent = (String) normalBox.getContent();


        GenericBox<String> stringBox = new GenericBox<>();

        stringBox.setContent("hello world");
        String strContent = stringBox.getContent();
        System.out.println("strContent: " + strContent);


        //제네릭에 들어가는 타입은 반드시 '객체 타입(참조형)'이어야 한다.
        //내부적으로 타입을 Object로 다루기 때문에, 기본형은 Object로 업캐스팅 할 수 없다.
        GenericBox<Integer> integerBox = new GenericBox<>();
        integerBox.setContent(123);

        int intContent = integerBox.getContent(); //auto-unboxing으로 int 변수에 담기 가능

    }
}
