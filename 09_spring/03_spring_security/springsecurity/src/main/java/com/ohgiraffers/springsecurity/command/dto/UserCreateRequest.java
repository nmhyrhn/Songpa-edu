package com.ohgiraffers.springsecurity.command.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class UserCreateRequest {

    private final String username;
    private final String password;
    //추가 회원 가입시 필요한 데이터들을 더 추가

}
