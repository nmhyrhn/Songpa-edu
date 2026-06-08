package com.ohgiraffers.apipractice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

//도서 대여 요청에서 클라이언트가 보내는 memberNo , bookNo 만 받기 위한 요청 전용 DTO
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "도서 대여 요청 DTO")
public class RentalRequest {

    @Schema(description = "회원 번호", example = "1")
    @NotNull(message = "회원 번호는 필수입니다.")
    private Integer memberNo;

    @Schema(description = "도서 번호", example = "2")
    @NotNull(message = "도서 번호 필수입니다.")
    private Integer bookNo;

}
