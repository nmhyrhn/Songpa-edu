package com.ohgiraffers.section05.parameter;

import java.util.Arrays;

public class Application {
    public static void main(String[] args) {

        MethodParameterTest pt =  new MethodParameterTest();


        //기본 자료형 테스트 (값 자체가 복사되어 메서드 안에서 아무리 바꿔도 원본은 불변)
        int num = 20;
        System.out.println("호출 전 main의 num : " + num);
        pt.testPrimitiveType(num);
        System.out.println("호출 후 main의 num : " + num);

        //배열 테스트 (주소값 복사되어 원본에도 영향을 준다)
        int[] iarr = {1, 2, 3, 4, 5};
        System.out.println("호출 전 : " + Arrays.toString(iarr));
        pt.testArrayParameter(iarr);
        System.out.println("호출 후 : " + Arrays.toString(iarr));

        //객체 테스트
        Rectangle rect = new Rectangle(5, 6);
        System.out.println("호출 전 : " + rect.getWidth());
        pt.testObjectParameter(rect);
        System.out.println("호출 후 : " + rect.getWidth());

        //가변인자 테스트
        pt.orderPizza("김문어");
        pt.orderPizza("김문어", "불고기");
        pt.orderPizza("김문어", "불고기", "치즈", "올리브");

    }
}
