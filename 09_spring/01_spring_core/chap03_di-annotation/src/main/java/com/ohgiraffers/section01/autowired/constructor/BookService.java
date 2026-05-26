package com.ohgiraffers.section01.autowired.constructor;

import com.ohgiraffers.common.BookDAO;
import com.ohgiraffers.common.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/* @Component의 세분화 어노테이션의 한 종류로 Service 계층에서 사용 */
@Service("bookServiceConstructor")
public class BookService {

    private final BookDAO bookDAO;

    // 생성자 주입 (실무에서 가장 기본 주입 방식이다!!)
    // BookDAO 타입의 Bean 객체를 생성자에 자동으로 주입해준다
//    생성자가 하나뿐이면 @Autowired 를 생략해도 자동으로 생상자 주입이 동작한다
    public BookService(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public List<BookDTO> selectAllBooks() {
        return bookDAO.selectBookList();
    }

    public BookDTO searchBookBySequence(int sequence) {
        return bookDAO.selectOneBook(sequence);
    }
}
