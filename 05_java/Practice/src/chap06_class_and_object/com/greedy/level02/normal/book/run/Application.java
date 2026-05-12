package chap06_class_and_object.com.greedy.level02.normal.book.run;

import chap06_class_and_object.com.greedy.level02.normal.book.model.dto.BookDTO;

public class Application {
    public static void main(String[] args) {

        BookDTO book = new BookDTO();

        //기본 생성자를 이용하여 인스턴스 생성 후 필드 값 출력
        book.getInfo();

        //필드 3가지를 초기화하는 생성자를 이용하여 인스턴스 생성 후 필드 값 출력
        book.setTitle("자바의 정석");
        book.setPublisher("도우출판");
        book.setAuthor("남궁성");
        book.getInfo();

        //모든 필드를 초기화하는 생성자를 이용하여 인스턴스 생성 후 필드 값 출력
        book.setTitle("홍길동전");
        book.setPublisher("활빈당");
        book.setAuthor("허균");
        book.setPrice(5000000);
        book.setDiscountRate(0.5);
        book.getInfo();

    }
}
