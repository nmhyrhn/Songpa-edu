package com.ohgiraffers.apipractice.dto;

import com.ohgiraffers.apipractice.type.RentalStatus;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RentalDTO {

    private int rentalNo;
    private int memberNo;
    private int bookNo;
    private LocalDate rentedAt;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private RentalStatus status;

}
