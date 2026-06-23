package com.ohgiraffers.springsecurity.command.controller;

import com.ohgiraffers.springsecurity.command.dto.UserCreateRequest;
import com.ohgiraffers.springsecurity.command.service.UserCommandService;
import com.ohgiraffers.springsecurity.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserCommandController {

    private final UserCommandService userCommandService;

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<Void>> register(@RequestBody UserCreateRequest request) {
        userCommandService.registUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(null));
    }

}
