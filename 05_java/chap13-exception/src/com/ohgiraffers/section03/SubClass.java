package com.ohgiraffers.section03;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SubClass extends SuperClass {

    /*
    * 오버라이딩 시 예외 처리 규칙
    * 1. 부모와 동일한 예외 가능
    * 2. 부모가 던지는 예외의 자식타입(구체적 예외) 가능
    * 3. 예외를 던지지 않는 것 가능
    * 4. 하지만, 부모가 던지는 예외의 부모타입(포괄적 예외)나,
    *   전혀 상관 없는 새로운 예외 던지는 것은 불가능
    * */

    //예외 없이 오버라이딩 가능
//    @Override
//    public void method() {
//
//    }

    //같은 예외 던지는거 가능
//    @Override
//    public void method() throws IOException {
//    }

    //부모 예외처리 클래스보다 더 구체적인 경우 가능
    @Override
    public void method() throws FileNotFoundException {
    }

//    @Override
//    public void method() throws Exception {
//    }

}
