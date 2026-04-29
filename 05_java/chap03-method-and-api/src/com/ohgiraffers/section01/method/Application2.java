package com.ohgiraffers.section01.method;

public class Application2 {
    public static void main(String[] args) {
        /*전달인자(argument)와 매개변수(parameter)
        * - 메소드를 호출할 때 넘겨주는 값을 '전달인자'라고 하며
        * - 메소드에서 이 값을 받기 위해 선언된 변수를 '매개변수'라고 한다.
        * */

        Application2 app2 = new Application2();
        app2.printAge(13);

        int myAge = 25;
        app2.printAge(myAge);

        //매개변수 타입, 개수, 순서를 정확히 맞춰서 전달해야 한다.
        app2.printUserInfo("판다", 45, 'M');

        //void가 아닌 다른 타입이 명시된 메소드는 반드시 해당 타입의 값을 반환(return)해야 한다.
        //반환된 값은 변수에 저장하거나 다른 메소드의 전달인자로 즉시 사용할 수 있다.
        String message = app2.createProfile("파판다", 29);
        System.out.println("message = " + message);
    }

    public void printAge(int age) {
        System.out.println("당신의 나이는 " + age + "세 입니다.");
    }

    public void printUserInfo(String name, int age, char gender) {
        System.out.println("이름: " + name + ", 나이: " + age + "세, 성별: " + gender);
    }

    public String createProfile(String name, int age) {

        String profile = name + "님의 나이는 " + age + "세 입니다.";
        return profile;
    }
}
