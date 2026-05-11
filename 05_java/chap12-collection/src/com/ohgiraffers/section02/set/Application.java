package com.ohgiraffers.section02.set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Application {
    public static void main(String[] args) {

        /*
        * Set
        * - 중복 불가
        * - 순서 없음
        * */

        Set<String> hset = new HashSet<>();
        hset.add("html");
        hset.add("css");
        hset.add("java");
        boolean isAdded = hset.add("html"); //중복이면 무시되고 false 반환

        System.out.println(hset);
        System.out.println(isAdded);

        /*Set은 순서(index)가 없기 때문에, List처럼 get()을 사용하여 요소를 꺼낼 수 없다.
        * 따라서 전체 요소를 대상으로 연속 처리를 하고 싶을 때는 아래의 방법을 사용한다.*/

        //1. toArray(): Set을 배열로 바꿔서 반복
        Object[] arr = hset.toArray();
        for(int i=0; i<arr.length; i++){
            System.out.println(arr[i]);
        }
        //2. iterator():반복자를 사용하여 연속 처리 (가장 표준 방법)
        //Iterator는 어떤 컬렉션이든 동일한 방식으로 순회할 수 있게 해주는 '반복자' 객체
        Iterator<String> iter = hset.iterator();
        while(iter.hasNext()) { //꺼낼 것이 남아있으면 true
            System.out.println(iter.next()); // next(): 다음 요소를 꺼
        }



    }
}
