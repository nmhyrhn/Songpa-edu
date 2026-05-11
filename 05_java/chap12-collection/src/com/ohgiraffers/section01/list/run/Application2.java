package com.ohgiraffers.section01.list.run;

import com.ohgiraffers.section01.list.comparator.AscendingPrice;
import com.ohgiraffers.section01.list.dto.BookDTO;

import java.util.*;

public class Application2 {
    public static void main(String[] args) {

        List<BookDTO> bookList = new ArrayList<>();
        bookList.add(new BookDTO(3, "홍길동전", "허균", 50000));
        bookList.add(new BookDTO(2, "삼국사기", "김부식", 36000));
        bookList.add(new BookDTO(1, "동의보감", "허준", 40000));

        /*
        * BookDTO 클래스에 Comparable 인터페이스를 구현하고 compareTo 메소드를 오버라이딩 했기 때문에
        * BookDTO 객체들은 기본 정렬 기준(책 번호 오름차순)을 가지게 되었다
        * Collections.sort() 메소드는 별도의 정렬기준을 주지 않으면, 이 기본 정렬 기준을 사용한다.
        * */
        Collections.sort(bookList);

        /*
        * Comparable = 객체 안에 기본 정렬 기준 1개를 삼는다. - BookDTO안의 compareTo() 사용
        * Comparator = 정렬할 때마다 외부에서 기준을 갈아 끼운다. - 외부 클래스 사용
        * */

        System.out.println("오름차순 정렬");
        for(BookDTO book : bookList){
            System.out.println(book);
        }


        System.out.println();

        /*ArrayList.sort()에 Comparator 인터페이스를 구현한 구현체를 전달*/
        System.out.println("외부 정렬 기준");
        bookList.sort(new AscendingPrice());
        for(BookDTO book : bookList){
            System.out.println(book);
        }

        //익명 클래스 사용 (가격 내림차순)
        bookList.sort(new Comparator<BookDTO>() {
            @Override
            public int compare(BookDTO o1, BookDTO o2) {
                return Integer.compare(o2.getPrice(), o1.getPrice()); //내림차순
            }
        });
        System.out.println("가격 내림차순");
        for(BookDTO book : bookList){
            System.out.println(book);
        }

        System.out.println();
        //람다식 사용 (제목 오름차순)
        System.out.println("람다식");
        /*인터페이스에 메소드가 하나만 있는 경우, 익명 클래스 대신 람다식 사용 가능*/
        bookList.sort((b1, b2) -> b1.getTitle().compareTo(b2.getTitle()));
        for(BookDTO book : bookList){
            System.out.println(book);
        }



    }
}
