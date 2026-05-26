package com.ohgiraffers.common;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("bookDAO")
public class BookDAOImpl implements BookDAO{

    private Map<Integer, BookDTO> bookList;

    public BookDAOImpl() {
        bookList = new HashMap<>();
        bookList.put(1, new BookDTO(1, 123456, "자바의 정석"));
        bookList.put(2, new BookDTO(2, 654321, "칭찬은 고래도 춤추게 한다"));
    }

    @Override
    public List<BookDTO> selectBookList() {
        // values(): 목록들만 컬렉션 타입으로 반환, ArrayList 생성자에 넣으면 ArrayList 타입으로 반환
        return new ArrayList<>(bookList.values());
    }

    @Override
    public BookDTO selectOneBook(int sequence) {
        return bookList.get(sequence);
    }
}
