package com.ohgiraffers.section05.parameter;

import java.util.Arrays;

public class MethodParameterTest {

    //기본 자료형: '값'이 복사되어 넘어간다 -> 원본 불변
    public void testPrimitiveType(int num) {
        System.out.println("전달받은 num : " + num);
        num = 99;
        System.out.println("변경 후 num : " + num);
    }

    //배열(참조 자료형) : '주소값'이 복사 되어 넘어간다 -> 원본 변경됨
    public void testArrayParameter(int[] arr) {
        System.out.println("전달 받은 arr = " + Arrays.toString(arr));
        arr[0] = 99;
        System.out.println("변경 후 arr = " + Arrays.toString(arr));
    }

    //객체(참조 자료형): '주소값'이 복사 되어 넘어간다 -> 원본 변경됨
    public void testObjectParameter(Rectangle rect) {
        System.out.println("변경 전 사각형 너비 = " + rect.getWidth());
        rect.setWidth(100);
        System.out.println("변경 후 사각형 너비 = " + rect.getWidth());
    }

    /*가변 인자: 파라미터의 개수가 유동적일 때 사용한다.*/
    //toppings: 0개 이상의 선택 파라미터, 메소드 내부에서 배열로 취급된다. (Strig...) <- 가변인자
    public void orderPizza(String customerName, String... toppings) {
        System.out.println(customerName + "고객님!");
        System.out.println("주문하신 피자 토핑 : " + Arrays.toString(toppings));
    }

}
