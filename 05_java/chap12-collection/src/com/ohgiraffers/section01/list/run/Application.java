package com.ohgiraffers.section01.list.run;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Application {
    public static void main(String[] args) {

        /*
        * 컬렉션 프레임워크란
        * 여러 개의 데이터(객체)를 효과적으로 관리하기 위해 자바에서 제공하는 클래스들의 묶음
        * 크게 List, Set, Map 인터페이스로 분류된다
        * */

        //역할 ... = new 구현체();
        //ArrayList로 역할을 지정해도되지만 상위 List를 사용하면 추후 LinkedList로 변경 용이함
        //제네릭 없는 리스트, 타입 지정이 안되어 어떤것든 add 할 수 있음
        List alist = new ArrayList();
        alist.add("apple");
        alist.add(123);
        alist.add(45.23);
        alist.add(false);

        System.out.println(alist);

        String fruit = (String)alist.get(0); //타입 미지정으로 Object인데 String으로 꺼내올려해서 컴파일 에러가 남, 강제 형변환 해야함(String)
        System.out.println(fruit);

        List<String> stringList = new ArrayList<>(); //String 타입의 List 선언
        //stringList.add(123); //String 아닌걸 대입 시 컴파일 에러 발생
        stringList.add("banana");
        stringList.add("apple");
        stringList.add("apple");
        stringList.add("grape");

        System.out.println(stringList);
        //List 특징 : 순서를 유지함(추가한 순서대로 출력)
        // 순서를 유지하기에 중복이 허용돰

        //set(인덱스, 값) 수정
        stringList.set(2, "mango");

        System.out.println(stringList);

        //remove(인덱스 또는 값) - 삭제
        stringList.remove(1);
        System.out.println("remove 인덱스 삭제 : " + stringList);
        stringList.remove("grape");
        System.out.println("remove 값 삭제 : " + stringList);

        //size() - 현재 몇 개 들어있는지 확인
        System.out.println("size() : " + stringList.size());

        stringList.add("pear");
        stringList.add("apple");
        stringList.add("banana");

        System.out.println("오름차순 정렬 전" + stringList);

        Collections.sort(stringList);//오름차순 정렬
        System.out.println("오름차순 정렬 후: " + stringList);


    }
}
