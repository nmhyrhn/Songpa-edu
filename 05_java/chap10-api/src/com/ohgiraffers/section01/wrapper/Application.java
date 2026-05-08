package com.ohgiraffers.section01.wrapper;

public class Application {
    public static void main(String[] args) {

        String heightStr = "170.5";
        String weightStr = "68.2";
        System.out.println(heightStr + weightStr); //문자열 합치기가 됨 170.568.2

        //파싱 : 문자열 데이터를 실제 해당 타입의 기본 자료형으로 변환
        double height = Double.parseDouble(heightStr);
        double weight = Double.parseDouble(weightStr);

        //bmi 지수 계산
        double bmi = weight / ((height/100) * (height/100));


        //int 20을 Integer 객체로 포장(Boxing)
        Integer age = 20; //Integer.valueOf(20) 오토 박싱

        int unboxedAge = age; //age.intValue() 오토언박싱

        //주의사항! 래퍼 클래스는 '객체'이기 때문에, 값 비교를 할 때 == 연산자를 쓰면 위험
        Integer num3 = 128;
        Integer num4 = 128;
        System.out.println(num3 == num4);
        //래퍼 클래스 객체의 값이 같은지 비교할 때는 반드시 equals 사용
        System.out.println(num3.equals(num4));

        /*
        * 숫자를 다시 문자열로
        * 계산이 끝난 데이터를 화면에 표시하거나, 저장하려면 다시 문자열로 반환해야 한다.
        * 1. String.valueOf(값)
        * 2. 값 + "" (간편한 방법)
        * */
        String bmiStr1 = String.valueOf(bmi);
        String bmiStr2 = bmi + "";

        System.out.println("당신의 BMI는 " + bmiStr1 + "입니다.");
    }

}
