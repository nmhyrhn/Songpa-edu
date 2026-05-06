package com.ohgiraffers.section06.finalkeyword;

public class Application {
    public static void main(String[] args) {

        //final 변수 - 읽기만 가능, 변경 불가
        System.out.println("원주율: " + MathConstant.PI);
//        MathConstant.PI = 3.14; //final 변수이기에 재할당 불가

    //final 인스턴스 필드
    //기본 생성자로 생성 불가, 왜냐 Person에 이미 파라미터가 값을 넣어둠 (필수 필드)
    Person person = new Person("123456-1234567", "호구");
        System.out.println(person.getName() + " " + person.getSsn());
    person.setName("호구주니어");
//    person.ssn("123123-1234567"); //final이기에 변경 불가
    System.out.println(person.getName() + " " + person.getSsn());

    //final 메서드 - 호출은 가능, 오버라이딩만 불가
    Child child = new Child();
    child.coreMethod();
    }
}
