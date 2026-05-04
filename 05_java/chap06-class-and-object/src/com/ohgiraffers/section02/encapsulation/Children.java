package com.ohgiraffers.section02.encapsulation;

public class Children {

    /*
    * [접근제한자]
    * 클래스 혹은 클래스의 멤버(필드)에 참조 연산자로 접근할 수 있는 범위를 제한하기 위한 키워드
    *
    * (+) public : 모든 패키지에서 접근 허용
    * (#) protected : 동일 패키지에서 접근 허용 (단, 상속 관계에 있는 경우 다른 패키지에서도 접근 허용)
    * (~) default : 동일 패키지 내에서만 접근 허용 (작성하지 않는 것이 default)
    * (-) private : 해당 클래스 내부에서만 접근 허용
    *
    * 클래스, 생성자, 클래스의 멤버(필드, 메소드)에 모두 사용 가능하다.
    * 단, 클래스 선언 시 사용하는 접근제한자는 public과 default만 사용 가능하다.
    * */

    /*
    * [캡슐화]
    * 유지보수성을 증가시키기 위해 필드의 직접 접근은 제한하고
    * public 메소드를 이용해 접근하여 사용할 수 있도록 만든 기술이다.
    * 클래스 작성 시 특병한 목적이 아닌 이상 캡슐화가 기본 원칙으로 사용 되고 있다.
    * */

    private String nickname;
    private int age;

    /*
    * [Getter와 Setter 메소드]
    *
    * Getter(접근자): 내부 필드의 값을 읽어서 전달하는 역할을 하는 메소드 (읽기 전용)
    * Setter(변경자): 외부에서 전달된 값을 받아, 내부 필드의 값을 설정 또는 변경 하는 역할을 하는 메소드
    * */

    public void setAge(int age) {
        if(age >= 0){
            this.age = age;
        } else{
            System.out.println("나이는 음수일 수 없어!!");
            this.age = 0;

        }
    }

    public int getAge() {
        return age;
    }
}
