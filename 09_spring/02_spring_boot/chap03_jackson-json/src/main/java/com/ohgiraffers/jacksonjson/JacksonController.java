package com.ohgiraffers.jacksonjson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.sql.Date;
import java.util.*;

@RestController
@RequestMapping("/api/v1/members")
public class JacksonController {

    private final List<MemberDTO> memberList;

    public JacksonController() {
        memberList = new ArrayList<>();
        memberList.add(new MemberDTO(1, "판다", 5, new Date(System.currentTimeMillis())));
        memberList.add(new MemberDTO(2, "코알라", 3, new Date(System.currentTimeMillis())));
        memberList.add(new MemberDTO(3, "원숭이", 7, new Date(System.currentTimeMillis())));
    }

    /*Java List<MemberDTO>를 JSON 배열로 응답*/
    @GetMapping
    public ResponseEntity<List<MemberDTO>> getMembers(){

        return ResponseEntity.ok(memberList);
    }

    @GetMapping("/{memberNo}")
    public ResponseEntity<MemberDTO> findMember(
            @PathVariable int memberNo
    ){
        MemberDTO foundMember = memberList.stream()
                .filter(member -> member.getNo() ==  memberNo)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 번호의 회원이 없음"));

        return ResponseEntity.ok(foundMember);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createMember(@RequestBody MemberDTO member){

        memberList.add(member);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "회원 등록 요청 성공");
        response.put("member", member);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/response-wrapper")
    public ResponseEntity<Map<String, Object>> findMembersWithWrapper(){
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "회원 목록 조회 성공");
        response.put("count", memberList.size());
        response.put("data", memberList);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/object-mapper")
    public  ResponseEntity<Map<String, Object>> objectMapperTest(){

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(memberList); //JAVA 객체를 JSON 모양의 문자열로 바꿈

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "ObjectMapper 직접 변환 결과");
        response.put("jsonString", jsonString);

        return ResponseEntity.ok(response);
    }






}
