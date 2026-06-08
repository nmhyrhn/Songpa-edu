package com.ohgiraffers.apipractice.dto;

import com.ohgiraffers.apipractice.type.RentalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "대여 DTO")
public class RentalDTO {

    @Schema(description = "대여 번호", example = "1")
    private Integer rentalNo;
    @Schema(description = "회원 번호", example = "1")
    private Integer memberNo;
    @Schema(description = "도서 번호", example = "2")
    private Integer bookNo;
    @Schema(description = "대여일", example = "2026-06-08")
    private LocalDate rentedAt;
    @Schema(description = "반납 예정일", example = "2026-06-22")
    private LocalDate dueDate;
    @Schema(description = "반납일", example = "2026-06-10")
    private LocalDate returnedAt;
    @Schema(description = "대여 상태", example = "RENTED")
    private RentalStatus status;

}
