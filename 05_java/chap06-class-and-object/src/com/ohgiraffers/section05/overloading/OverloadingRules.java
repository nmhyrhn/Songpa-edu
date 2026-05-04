package com.ohgiraffers.section05.overloading;

public class OverloadingRules {

    /*
    * [메서드 시그니처]
    * 메서드를 식별하는 교유한 바코드 = '메서드 이름' + '매개변수 타입, 개수, 순서'
    *
    * 오버로딩 : 이름은 같지만 시그니처가 다른 메서드를 여러 개 정의하는 기술
    * */

    public void test() {}
    //매개변수 '개수'가 다른 경우 성립
    public void test(int num) {}

    //매개변수 '타입'이 다른 경우 성립
    public void test(String str) {}

    //매개변수 '순서'가 다른 경우 성립
    public void test(int num, String str) {}
    public void test(String str, int num) {}

    //불성립 : 반환타입만 다른 경우 (시그니처 동일 -> 컴파일 에러)
//    public int test() {}

    //불성립 : 접근 제한자만 다른 경우 (시그니처 동일)
//    private void test() {}

    //불성립 : 매개변수 '이름'만 다른 경우 (시그니처 동일)
//    public void test(int num2) {}
}
