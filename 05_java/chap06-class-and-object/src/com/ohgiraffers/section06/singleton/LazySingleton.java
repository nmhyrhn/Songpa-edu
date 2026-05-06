package com.ohgiraffers.section06.singleton;

//게으른 초기화
public class LazySingleton {

    private static LazySingleton lazy; //처음에는 비워둔다 (null 상태)

    private LazySingleton() {}

    public static LazySingleton getInstance() {
        //처음 호출될 때만 만들고, 이후에는 이미 만들어진 것을 반환
        if(lazy == null) {
            lazy = new LazySingleton();
        }
        return lazy;
    }
}
