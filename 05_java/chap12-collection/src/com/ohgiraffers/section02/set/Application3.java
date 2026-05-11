package com.ohgiraffers.section02.set;

import com.ohgiraffers.section01.list.dto.BookDTO;

import java.util.Set;
import java.util.TreeSet;

public class Application3 {
    public static void main(String[] args) {

        //TreeSet:중복 불가 + 자동 오름차순 정렬
        //조건: 요소가 Comparable을 구현해야 자동 정렬 가능
        //String, Integer 등 기본 Wrapper 클래스는 이미 Comparable 구현 완료
        TreeSet<String> tset = new TreeSet<>();
        tset.add("java");
        tset.add("html");
        tset.add("css");
        tset.add("mysql");
        System.out.println(tset);

        //BookDTO는 Comparable로 구현 -> number 기준 자동 정렬
        Set<BookDTO> bookSet = new TreeSet<>();
        bookSet.add(new BookDTO(2, "목민심서", "정약용", 30000));
        bookSet.add(new BookDTO(1, "홍길동전", "허균", 50000));
        bookSet.add(new BookDTO(3, "동의보감", "허준", 40000));

        for(BookDTO book : bookSet) {
            System.out.println(book);
        }

        //중복없는 로또 번호 생성(오름차순)
        Set<Integer> lotto = new TreeSet<>();
        while(lotto.size() < 6) {
            lotto.add((int)(Math.random() * 45) + 1); //1 ~ 45 랜덤, 중복은 자동 제거
        }
        System.out.println(lotto);
    }
}
