package com.ohgiraffers.common;

public class BookDTO {

    private int sequence;
    private int isbn;
    private String title;

    public BookDTO(int sequence, int isbn, String title) {
        this.sequence = sequence;
        this.isbn = isbn;
        this.title = title;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "sequence=" + sequence +
                ", isbn=" + isbn +
                ", title='" + title + '\'' +
                '}';
    }
}
