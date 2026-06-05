package com.ohgiraffers.apipractice.dto;

import lombok.*;

import java.util.Map;

//응답을 공통 구조로 내려주기 위한 응답 DTO
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMessage {

    private int status;
    private String message;
    private Map<String, Object> results;

}
