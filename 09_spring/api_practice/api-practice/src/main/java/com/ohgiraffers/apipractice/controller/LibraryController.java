package com.ohgiraffers.apipractice.controller;

import com.ohgiraffers.apipractice.dto.BookDTO;
import com.ohgiraffers.apipractice.dto.MemberDTO;
import com.ohgiraffers.apipractice.dto.RentalDTO;
import com.ohgiraffers.apipractice.type.BookStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/library")
public class LibraryController {

    private final List<MemberDTO> members = new ArrayList<>();
    private final List<BookDTO> books = new ArrayList<>();
    private final List<RentalDTO> rentals = new ArrayList<>();

    public LibraryController() {
        members.add(new MemberDTO(1, "reader01", "홍길동", "reader01@test.com", LocalDate.now()));
        members.add(new MemberDTO(2, "reader02", "김영희", "reader02@test.com", LocalDate.now()));

        books.add(new BookDTO(1, "Spring REST API 입문", "오지라퍼", "070-11-1111-111-1", BookStatus.AVAILABLE, LocalDate.of(2025, 3, 10)));
        books.add(new BookDTO(2, "Java 기초", "개발자", "070-11-2222-222-2", BookStatus.AVAILABLE, LocalDate.of(2024, 5, 20)));

    }

}
