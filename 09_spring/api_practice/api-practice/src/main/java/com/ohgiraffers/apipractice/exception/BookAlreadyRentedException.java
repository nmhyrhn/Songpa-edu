package com.ohgiraffers.apipractice.exception;

public class BookAlreadyRentedException extends RuntimeException{
    public BookAlreadyRentedException(String message) {
        super(message);
    }
}
