package com.ohgiraffers.apipractice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

//응답을 공통 구조로 내려주기 위한 응답 DTO
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "공통 성공 응답 DTO")
public class ResponseMessage {

    @Schema(description = "HTTP 상태 코드", example = "200")
    private int status;
    @Schema(description = "응답 메시지", example = "조회 성공")
    private String message;
    @Schema(description = "응답 결과 데이터")
    private Map<String, Object> results;

}
