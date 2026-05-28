package com.ohgiraffers.handlermethod;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.RemoteRef;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/menus")
public class MenuRequestController {

    // GET /api/v1/menus?keyword=coffee&categoryCode=2&page=1
    @GetMapping
    public ResponseEntity<Map<String, Object>> findMenus(
            //@RequestParam: 쿼리스트링 값을 컨트롤러 메소드 매개변수의 파라미터로 받아옴
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int categoryCode,
            @RequestParam(defaultValue = "1") int page
    ) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "메뉴 목록 조회 요청 파라미터 확인");
        response.put("keyword",  keyword);
        response.put("categoryCode", categoryCode);
        response.put("page", page);


        return ResponseEntity.ok(response);
    }


    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchMenus(
            //@RequestParam: 쿼리스트링 값을 컨트롤러 메소드 매개변수의 파라미터로 받아옴
            @RequestParam Map<String, String> parameters
    ){

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "검색 조건 전체 확인");
        response.put("parameters", parameters);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{menuCode}")
    public ResponseEntity<Map<String, Object>> findMenu(
            //@PathVariable: URL 경로 일부를 메서드 파라미터로 받는다
            @PathVariable int menuCode
    ) {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", menuCode + "번 메뉴 조회 요청");
        response.put("menuCode", menuCode);


        return ResponseEntity.ok(response);
    }

    //@RequestBody로 JSON요청 본문을 DTO로 받기
    @PostMapping
    public ResponseEntity<Map<String, Object>> createMenu(
            //요청 본문의 JSON을 읽어서 MenuDTO 객체로 변환하여 파라미터로 넣어줘
            @RequestBody MenuDTO menu
    ) {

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "메뉴 등록 요청 성공");
        response.put("menu", menu);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/raw")
    public ResponseEntity<Map<String, Object>> readRawBody(
            @RequestBody String Body,
            @RequestHeader("content-type") String contentType
    ) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "요청 본문 문자열 확인");
        response.put("contentType", contentType);
        response.put("Body", Body);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/request-info")
    public ResponseEntity<Map<String, Object>> requestInfo(
            //요청 Header 값을 받는다
            @RequestHeader(value="user-agent", required = false) String userAgent,
            @RequestHeader Map<String , String> headers,
            //Cookie 값을 받는다
            @CookieValue(value="JSESSIONID", required = false) String sessionId
    ){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "요청 헤더와 쿠키 확인");
        response.put("userAgent", userAgent);
        response.put("sessionId", sessionId);
        response.put("headerCount", headers.size());

        return ResponseEntity.ok(response);
    }

    //session에 값 저장하기
    @PostMapping("/session")
    public ResponseEntity<Map<String, Object>> saveSession(
            HttpSession session,  //현재 요청의 session 객체를 받는다
            @RequestParam String id
    ){
        session.setAttribute("id", id);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "session에 id 저장");
        response.put("id", id);
        response.put("sessionId", session.getId());

        return ResponseEntity.ok(response);
    }

    //session 값 조회하기
    @GetMapping("/session")
    public ResponseEntity<Map<String, Object>> readSession(HttpSession session){

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "session에 저장된 아이디 조회");
        response.put("id", session.getAttribute("id"));
        response.put("sessionId", session.getId());

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/session")
    public ResponseEntity<Map<String, Object>> removeSession(HttpSession session){

        session.invalidate(); //세션 만료

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "Session 만료");

        return  ResponseEntity.ok(response);
    }




}
