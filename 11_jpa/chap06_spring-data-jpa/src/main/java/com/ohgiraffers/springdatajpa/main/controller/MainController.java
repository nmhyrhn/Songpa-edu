package com.ohgiraffers.springdatajpa.main.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MainController {

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> main(){
        return ResponseEntity.ok(Map.of("message", "메인 API 페이지에 오신 것을 환영합니다."));
    }

}
