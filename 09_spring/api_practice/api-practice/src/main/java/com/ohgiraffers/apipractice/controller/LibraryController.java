package com.ohgiraffers.apipractice.controller;

import com.ohgiraffers.apipractice.dto.*;
import com.ohgiraffers.apipractice.exception.BookAlreadyRentedException;
import com.ohgiraffers.apipractice.exception.BookNotFoundException;
import com.ohgiraffers.apipractice.exception.MemberNotFoundException;
import com.ohgiraffers.apipractice.exception.RentalNotFoundException;
import com.ohgiraffers.apipractice.type.BookStatus;
import com.ohgiraffers.apipractice.type.RentalStatus;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/library")
public class LibraryController {

    private final List<MemberDTO> members = new ArrayList<>();
    private final List<BookDTO> books = new ArrayList<>();
    private final List<RentalDTO> rentals = new ArrayList<>();

    // 데이터
    public LibraryController() {
        members.add(new MemberDTO(1, "reader01", "홍길동", "reader01@test.com", LocalDate.now()));
        members.add(new MemberDTO(2, "reader02", "김영희", "reader02@test.com", LocalDate.now()));

        books.add(new BookDTO(1, "Spring REST API 입문", "오지라퍼", "070-11-1111-111-1", BookStatus.AVAILABLE, LocalDate.of(2025, 3, 10)));
        books.add(new BookDTO(2, "Java 기초", "개발자", "070-11-2222-222-2", BookStatus.AVAILABLE, LocalDate.of(2024, 5, 20)));

    }

    //회원 목록 조회
    @GetMapping("/members")
    public ResponseEntity<ResponseMessage> findMembers(
            @RequestParam(required = false) String name
    ){
        List<MemberDTO> foundMembers = members.stream()
                .filter(member -> {
                    if(name != null && !member.getName().contains(name)) {
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());

        Map<String, Object> results = new LinkedHashMap<>();
        results.put("members", foundMembers);

        ResponseMessage responseMessage = new ResponseMessage(
                200,
                "회원 목록 조회 성공",
                results
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    //회원 단건 조회
    @GetMapping("/members/{memberNo}")
    public ResponseEntity<ResponseMessage> findMemberByNo(@PathVariable int memberNo) {
        MemberDTO foundMember = members.stream()
                .filter(member -> member.getMemberNo() == memberNo)
                .findFirst()
                .orElseThrow(()-> new MemberNotFoundException("회원을 찾을 수 없습니다."));

        Map<String, Object> results = new LinkedHashMap<>();
        results.put("member", foundMember);

        ResponseMessage responseMessage = new ResponseMessage(
                200,
                "회원 단건 조회 성공",
                results
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        }

    //회원 등록 API
    @PostMapping("/members")
    public ResponseEntity<Void> registMember(@Valid @RequestBody MemberDTO newMember) {
        int lastMemberNo = members.get(members.size() - 1).getMemberNo();

        newMember.setMemberNo(lastMemberNo + 1);
        newMember.setJoinedAt(LocalDate.now());

        members.add(newMember);

        return ResponseEntity
                .created(URI.create("/api/v1/library/members/" + newMember.getMemberNo()))
                .build();
    }

    //도서 목록 조회
    @GetMapping("/books")
    public ResponseEntity<ResponseMessage> findBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) BookStatus status
    ){
        List<BookDTO> foundBooks = books.stream()
                .filter(book -> {
                    if(title != null && !book.getTitle().contains(title)) {
                        return false;
                    }
                    if(status != null && !book.getStatus().equals(status)) {
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());

        Map<String, Object> results = new LinkedHashMap<>();
        results.put("books", foundBooks);

        ResponseMessage responseMessage = new ResponseMessage(
                200,
                "도서 목록 조회 성공",
                results
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    //도서 단건 조회
    @GetMapping("/books/{bookNo}")
    public ResponseEntity<ResponseMessage> findBookByNo(@PathVariable int bookNo) {
        BookDTO foundBook = books.stream()
                .filter(book -> book.getBookNo() == bookNo)
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("도서를 찾을 수 없습니다."));

        Map<String, Object> results = new LinkedHashMap<>();
        results.put("book", foundBook);

        ResponseMessage responseMessage = new ResponseMessage(
                200,
                "도서 단건 조회 성공",
                results
        );
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    //도서 등록
    @PostMapping("/books")
    public ResponseEntity<Void> registBook(@Valid @RequestBody BookDTO newBook) {
        int lastBookNo = books.get(books.size() - 1).getBookNo();

        newBook.setBookNo(lastBookNo + 1);
        newBook.setStatus(BookStatus.AVAILABLE);

        books.add(newBook);

        return ResponseEntity
                .created(URI.create("/api/v1/library/books/" + newBook.getBookNo()))
                .build();
    }

    //도서 대여
    @PostMapping("/rentals")
    public ResponseEntity<Void> rentBook(@Valid @RequestBody RentalRequest rentalRequest) {
        members.stream()
                .filter(member -> member.getMemberNo() == rentalRequest.getMemberNo())
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException("회원을 찾을 수 없습니다."));

        BookDTO foundBook = books.stream()
                .filter((book -> book.getBookNo() == rentalRequest.getBookNo()))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("도서를 찾을 수 없습니다."));

        if(foundBook.getStatus() == BookStatus.RENTED) {
            throw new BookAlreadyRentedException("이미 대여 중인 도서입니다.");
        }

        int newRentalNo = rentals.isEmpty()
                ? 1
                : rentals.get(rentals.size() - 1).getRentalNo() + 1;

        RentalDTO newRental = new RentalDTO(
                newRentalNo,
                rentalRequest.getMemberNo(),
                rentalRequest.getBookNo(),
                LocalDate.now(),
                LocalDate.now().plusDays(14),
                null,
                RentalStatus.RENTED
        );

        rentals.add(newRental);
        foundBook.setStatus(BookStatus.RENTED);

        return ResponseEntity
                .created(URI.create("/api/v1/library/rentals/" + newRental.getRentalNo()))
                .build();

    }

    //대여 단건 조회
    @GetMapping("/rentals/{rentalNo}")
    public ResponseEntity<ResponseMessage> findRentalByNo(@PathVariable int rentalNo) {
        RentalDTO foundRental = rentals.stream()
                .filter(rental -> rental.getRentalNo() == rentalNo)
                .findFirst()
                .orElseThrow(() -> new RentalNotFoundException("대여 정보를 찾을 수 없습니다."));

        Map<String, Object> results = new LinkedHashMap<>();
        results.put("rental", foundRental);

        ResponseMessage responseMessage = new ResponseMessage(
                200,
                "대여 단건 조회 성공",
                results
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }

    //도서 반납
    @PatchMapping("/rentals/{rentalNo}/return")
    public ResponseEntity<Void> returnBook(@PathVariable int rentalNo) {
        RentalDTO foundRental = rentals.stream()
                .filter(rental -> rental.getRentalNo() == rentalNo)
                .findFirst()
                .orElseThrow(() -> new RentalNotFoundException("대여 정보를 찾을 수 없습니다."));

        if(foundRental.getStatus() == RentalStatus.RETURNED) {
            throw new IllegalArgumentException("이미 반납된 대여입니다.");
        }
        BookDTO foundBook = books.stream()
                .filter(book -> book.getBookNo() == foundRental.getBookNo())
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("도서를 찾을 수 없습니다."));

        foundRental.setStatus(RentalStatus.RENTED);
        foundRental.setReturnedAt(LocalDate.now());
        foundBook.setStatus(BookStatus.AVAILABLE);

        return ResponseEntity.noContent().build();
    }


}

