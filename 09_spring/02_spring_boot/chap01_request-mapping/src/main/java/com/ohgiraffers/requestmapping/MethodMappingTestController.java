package com.ohgiraffers.requestmapping;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/*
* @Controller -> HTML 응답시
* @RestController -> JSON 응답시
* */
@RestController
@RequestMapping("/api/v1/menus") //이 Controller 안의 모든 메서드에 붙는 URL prefix
public class MethodMappingTestController {

    /*ResponseEntity: HTTP 응답을 표현하는 객체*/

    @GetMapping //HTTP GET 요청 처리
    public ResponseEntity<Map<String, String>> findMenus(){

        Map<String, String> response = Map.of("message", "메뉴 목록 조회용 핸들러 메소드 호출");

        return ResponseEntity.ok(response);

    }

    @PostMapping
    public ResponseEntity<Map<String, String>> registMenus(){
        Map<String, String> response = Map.of("message", "신규 메뉴 등록용 핸들러 메소드 호출");

        return ResponseEntity.status(201).body(response);
    }


    @PutMapping("/{menuCode}")
    public ResponseEntity<Map<String, String>> modifyMenus(@PathVariable int menuCode){
        Map<String, String> response = Map.of("message", menuCode + "번 메뉴 수정용 핸들러 메소드 호출");

       return  ResponseEntity.ok(response);
    }

    @DeleteMapping("/{menuCode}")
    public ResponseEntity<Map<String, String>> deleteMenus(@PathVariable int menuCode){
        Map<String, String> response = Map.of("message", menuCode + "번 메뉴 삭제용 핸들러 메소드 호출");

        return ResponseEntity.ok(response);
    }





}
