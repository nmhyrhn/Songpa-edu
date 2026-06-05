package com.ohgiraffers.apipractice.dto;

import com.ohgiraffers.apipractice.type.BookStatus;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookDTO {

    private int bookNo;
    private String title;
    private String author;
    private String isbn;
    private BookStatus status;
    private LocalDate publishedAt;

}
