package com.ohgiraffers.restapi.section01.responseentity;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/response")
public class ResponseEntityTestController {

    private final List<UserDTO> users;

    public ResponseEntityTestController() {
            users = new ArrayList<>();

            users.add(new UserDTO(1, "user01", "pass01", "판다", new Date(), "active"));
            users.add(new UserDTO(2, "user02", "pass02", "고릴라", new Date(), "active"));
            users.add(new UserDTO(3, "user03", "pass03", "원숭이", new Date(), "inactive"));


    }


        @GetMapping("/users")
        public ResponseEntity<ResponseMessage> findUserByConditions(
                @RequestParam(required = false) String name,
                @RequestParam(required = false) String status
        ){
            //응답 header를 담는 객체
            HttpHeaders headers= new HttpHeaders();
            //Content-Type: "application/json; charset=UTF-8
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

            List<UserDTO> foundusers = users.stream()
                    .filter(user -> {
                        //status 조건이 들어왔는데 회원의 상태가 다르면 제외
                        if(status != null && !user.getStatus().equals(status)){
                            return false;
                        }
                        //name 조건이 들어왔는데 회원 이름에 해당 글자가 포함되어 있지 않으면 제외
                        if(name != null && !user.getName().contains(name)){
                            return false;
                        }
                        return true;
                    }).collect(Collectors.toList()); //필터링된 결과를 List로 만든다

            Map<String, Object> response = new LinkedHashMap<>();
            response.put("users", foundusers);
            ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", response);

            return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);

    }

    //단건 조회
    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        UserDTO foundUser = users.stream()
                .filter(user -> user.getNo() ==  userNo)
                .findFirst()
                .get();

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("user", foundUser);

        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공", response);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(responseMessage);
    }

    @PostMapping("/users")
    //ResponseEntity<Void>: 응답 body가 없 -> .build()로 응답 완성
    public ResponseEntity<Void> registUser(@RequestBody UserDTO newUser){

        int lastUserNo = users.get(users.size() -1).getNo();
        newUser.setNo(lastUserNo + 1);
        newUser.setEnrollDate(new Date());
        users.add(newUser);

        return ResponseEntity
                //HTTP 에서는 새 리소스를 만들었을 때 Location header로 그 리소스의 위치를 알려줄 수 있다.
                .created(URI.create("/api/v1/response/users" + newUser.getNo()))
                .build();
    }

    //수정
    @PutMapping("/users/{userNo}")
    public  ResponseEntity<Void> modifyUser(@PathVariable int userNo,
                                            @RequestBody UserDTO modifyInfo){
        UserDTO foundUser = users.stream()
                .filter(user -> user.getNo() == userNo)
                .findFirst()
                .get();


        foundUser.setId(modifyInfo.getId());
        foundUser.setPwd(modifyInfo.getPwd());
        foundUser.setName(modifyInfo.getName());

        return ResponseEntity
                .noContent() // 204 No Content
                .build();
    }

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<Void> removeUser(@PathVariable int userNo){

        UserDTO foundUser = users.stream()
                .filter(user -> user.getNo() == userNo)
                .findFirst()
                .get();

        users.remove(foundUser);

        return ResponseEntity
                .noContent()
                .build();

    }



}
