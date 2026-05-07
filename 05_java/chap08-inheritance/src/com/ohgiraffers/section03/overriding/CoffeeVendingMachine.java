package com.ohgiraffers.section03.overriding;

public class CoffeeVendingMachine extends VendingMachine{

    /*
    * 오버라이딩 성공 조건
    * 메소드 이름, 반환 타입, 매개변수 목록이 모두 부모와 동일해야 한다.
    * 접근제한자는 부모와 같거나 더 넓어야 한다.
    *
    * (private < default < protected < public)
    * */

    @Override
    public String vend(){
        return "따뜻한 아메리카노";
    }

    //메소드 이름이 다른 경우(단순히 새로운 메소드 추가)
//    @Override
//    public String vendMachine(){
//        return "음료수";
//    }

    //반환 타입이 다른 경우
//    @Override
//    public int vend() {
//        return 100;
//    }

    //매개변수(파라미터)가 다른 경우
//    @Override
//    public String vend(String type) {
//        return type;
//    }

    //private 메소드는 오버라이딩 할 수 없다
//    @Override
//    private void checkStock() {}

    //final 메소드는 '변경불가'를 의미하므로 오버라이딩 불가
//    @Override
//    public final void powerOn() {}

    @Override
    protected void clean() {}

    //접근제한자는 부모보다 접은 범위로 변경할 수 없다
//    @Override
//    void clean() {}

//    @Override
//    public void clean() {}

}
