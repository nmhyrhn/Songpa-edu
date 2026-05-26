package com.ohgiraffers.common;

import java.util.List;

public interface BookDAO {

    List<BookDTO> selectBookList();

    BookDTO selectOneBook(int sequence);
}
