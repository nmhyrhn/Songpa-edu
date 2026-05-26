package com.ohgiraffers.section01.autowired.field;

import com.ohgiraffers.common.BookDAO;
import com.ohgiraffers.common.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* @Component의 세분화 어노테이션의 한 종류로 Service 계층에서 사용 */
@Service("bookServiceField")
public class BookService {

    // 필드 주입 방식
    // BookDAO 타입의 Bean 객체를 이 프로퍼티에 자동 주입
    // -> 의존관계에 있는 BookDAO라는 타입과 일치하는 빈이 있다면 넣어줘!!
    @Autowired
    private BookDAO bookDAO;

    public List<BookDTO> selectAllBooks() {
        return bookDAO.selectBookList();
    }

    public BookDTO searchBookBySequence(int sequence) {
        return bookDAO.selectOneBook(sequence);
    }
}
