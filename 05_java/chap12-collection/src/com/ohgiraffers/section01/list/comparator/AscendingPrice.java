package com.ohgiraffers.section01.list.comparator;

import com.ohgiraffers.section01.list.dto.BookDTO;

import java.util.Comparator;

//Comparator: 클래스 밖에서 '별도의 정렬 기준'을 정의할 때 사용
public class AscendingPrice implements Comparator<BookDTO> {

    @Override
    public int compare(BookDTO o1, BookDTO o2) {
        return Integer.compare(o1.getPrice(), o2.getPrice());
    }
}
