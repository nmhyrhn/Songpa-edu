package com.ohgiraffers.apipractice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "공통 에러 응답 DTO")
public class ErrorResponse {

    @Schema(description = "HTTP 상태 코드", example = "404")
    private int status;
    @Schema(description = "에러 코드", example = "MEMBER_NOT_FOUND")
    private String errorCode;
    @Schema(description = "에러 메시지", example = "회원을 찾을 수 없습니다.")
    private String message;
    @Schema(description = "필드별 검증 실패 메시지")
    private Map<String, Object> errors;

    public ErrorResponse(int status, String errorCode, String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}
