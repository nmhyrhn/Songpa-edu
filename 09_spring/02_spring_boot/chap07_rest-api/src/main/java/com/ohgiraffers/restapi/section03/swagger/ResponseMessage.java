package com.ohgiraffers.restapi.section03.swagger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseMessage {

    //성공 응답 body를 일정한 구조로 내려구지 위한 DTO
    //실제 데이터는 results Map 안에 users, user 같은 이름으로 담는다

    private int httpStatus;
    private String message;
    private Map<String, Object> results;


}
