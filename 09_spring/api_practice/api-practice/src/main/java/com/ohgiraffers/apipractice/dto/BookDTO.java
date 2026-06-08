package com.ohgiraffers.apipractice.dto;

import com.ohgiraffers.apipractice.type.BookStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "도서 DTO")
public class BookDTO {

    @Schema(description = "도서 번호", example = "1")
    private Integer bookNo;

    @Schema(description = "도서 제목", example = "Spring REST API 입문")
    @NotBlank(message = "도서 제목은 필수입니다.")
    private String title;

    @Schema(description = "저자", example = "오지라퍼")
    @NotBlank(message = "저자는 필수입니다.")
    private String author;

    @Schema(description = "ISBN", example = "979-11-1111-111-1")
    @NotBlank(message = "ISBN은 필수입니다.")
    private String isbn;

    @Schema(description = "도서 상태", example = "AVAILABLE")
    private BookStatus status;

    @Schema(description = "출판일", example = "2025-03-10")
    @PastOrPresent(message = "출판일은 오늘 또는 과거 날짜만 입력할 수 있습니다.")
    private LocalDate publishedAt;

}
