package com.ohgiraffers.section01.list.run;

import java.util.LinkedList;
import java.util.List;

public class Application3 {
    public static void main(String[] args) {
        /*
        * LinkedList: 각 요소가 앞뒤 요소를 '링크'로 연결
        * 추가/삭제 : 링크만 수정하면 되므로 중간 삽입/삭제가 빠름
        * 조회: 처음부터 순서대로 찾아야 하므로 느림
        * */

        List<String> linkedList = new LinkedList<>();

        linkedList.add("apple");
        linkedList.add("banana");
        linkedList.add("grape");

        System.out.println(linkedList);
        System.out.println(linkedList.get(0));

        linkedList.remove(1);
        System.out.println(linkedList);

        linkedList.clear();
        System.out.println(linkedList.isEmpty());

    }
}
