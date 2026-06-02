package com.ohgiraffers.restapi.section02.valid;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/validation")
public class ValidTestController {

    @GetMapping("users/{userNo}")
    public ResponseEntity<Void> findUserByNo(@PathVariable int userNo) {
        // 1번 회원만 존재한다고 가정하고, 나머지는 404 예외로 처리하기 위함
        if(userNo != 1 ) {
            throw new IllegalArgumentException(userNo + "번 회원 정보를 찾을 수 없습니다.");
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users")
    // @Valid: @RequestBody가 바꿔준 UserDTO 객체가 검증 규칙을 지키는지 검사
    // 검증에 실패하면 MethodArgumentNotValidException을 발생시킴 (Controller 진입X)
    public ResponseEntity<Void> registUser(@Valid @RequestBody UserDTO user) {

        return ResponseEntity
                .created(URI.create("/api/v1/validation/users/1"))
                .build();
    }
}