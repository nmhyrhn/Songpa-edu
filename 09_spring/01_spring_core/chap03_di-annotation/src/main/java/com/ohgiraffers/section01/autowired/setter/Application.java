package com.ohgiraffers.section01.autowired.setter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context
                = new AnnotationConfigApplicationContext("com.ohgiraffers");

        BookService bookService = context.getBean("bookServiceSetter", BookService.class);

        /* 전체 도서 목록 조회 */
        bookService.selectAllBooks().forEach(System.out::println);

        System.out.println(bookService.searchBookBySequence(2));
    }
}
