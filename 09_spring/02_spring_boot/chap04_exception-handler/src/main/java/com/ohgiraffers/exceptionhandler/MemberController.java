package com.ohgiraffers.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {

    private final List<MemberDTO> members = new ArrayList<>();

    public MemberController() {
        members.add(new MemberDTO(1, "판다", 5));
        members.add(new MemberDTO(2, "호랑이", 7));
        members.add(new MemberDTO(3, "가젤", 4));
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> findMembers() {

        return ResponseEntity.ok(members);
    }

    @GetMapping("/{memberNo}")
    public ResponseEntity<MemberDTO> findMember(@PathVariable int memberNo) {
        MemberDTO foundMember = members.stream()
                .filter(member -> member.getNo() == memberNo)
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException(memberNo + "번 회원을 찾을 수 없습니다."));

        return ResponseEntity.ok(foundMember);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> registMember(@RequestBody MemberDTO member) {

        if(member.getName() == null || member.getName().isBlank()) {
            throw new InvalidMemberRequestException("회원 이름은 비어 있을 수 없습니다.");
        }

        if(member.getAge() < 0) {
            throw new InvalidMemberRequestException("회원 나이는 0 이상이어야 합니다.");
        }

        members.add(member);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "회원 등록 성공");
        response.put("member", member);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @GetMapping("/server-error")
    public ResponseEntity<Void> serverError() {
        String text = null;
        text.length(); //NPE(MullPointerException) 발생

        //위에서 예외가 발생하므로 이 return문은 실행되지 않음
        return ResponseEntity.ok().build();
    }




}
