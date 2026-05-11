package com.ohgiraffers.section01.list.dto;

//Comparable<BookDTO>: 이 클래스는 BookDTO끼리 크기 비교가 가능하다는 계약
public class BookDTO implements Comparable<BookDTO> {

    private int number;
    private String title;
    private String author;
    private int price;



    public BookDTO() {
    }

    public BookDTO(int number, String title, String author, int price) {
        this.number = number;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    //정렬 기준 정의 (내부 정렬)
    //반환값 규칙: 음수 -> 현재(this)객체가 앞으로 / 양수 -> 뒤로 / 0 -> 동등
    @Override
    public int compareTo(BookDTO o) {
        return Integer.compare(this.number, o.getNumber());
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "number=" + number +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }


}
