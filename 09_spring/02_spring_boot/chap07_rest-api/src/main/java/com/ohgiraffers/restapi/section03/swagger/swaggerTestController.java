package com.ohgiraffers.restapi.section03.swagger;

import com.ohgiraffers.restapi.section01.responseentity.ResponseMessage;
import com.ohgiraffers.restapi.section01.responseentity.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Tag(name="user 관련 api", description = "회원 조회, 등록, 수정, 삭제 API")
@RestController
@RequestMapping("/api/v1/swagger/response")
public class swaggerTestController {

    private final List<com.ohgiraffers.restapi.section01.responseentity.UserDTO> users;

    public swaggerTestController() {
            users = new ArrayList<>();

            users.add(new com.ohgiraffers.restapi.section01.responseentity.UserDTO(1, "user01", "pass01", "판다", new Date(), "active"));
            users.add(new com.ohgiraffers.restapi.section01.responseentity.UserDTO(2, "user02", "pass02", "고릴라", new Date(), "active"));
            users.add(new com.ohgiraffers.restapi.section01.responseentity.UserDTO(3, "user03", "pass03", "원숭이", new Date(), "inactive"));


    }

        @Operation(summary = "회원 목록 조회/검색", description = "전체 회원을 조회하거나 name, status query으로 회원 검색")
        @ApiResponses({
                @ApiResponse(responseCode = "200", description = "회원 목록 조회 성공")
        })
        @GetMapping("/users")
        public ResponseEntity<ResponseMessage> findUserByConditions(
                @RequestParam(required = false) String name,
                @RequestParam(required = false) String status
        ){
            //응답 header를 담는 객체
            HttpHeaders headers= new HttpHeaders();
            //Content-Type: "application/json; charset=UTF-8
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

            List<com.ohgiraffers.restapi.section01.responseentity.UserDTO> foundusers = users.stream()
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
    @Operation(summary = "회원 번호로 회원 조회", description = "회원 번호를 통해 해당하는 회원 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 단건 조회 성공"),
            @ApiResponse(responseCode = "404", description = "회원 정보 없음")
    })
    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        com.ohgiraffers.restapi.section01.responseentity.UserDTO foundUser = users.stream()
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

    @Operation(summary = "신규 회원 등록")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원 등록 성공")
    })
    @PostMapping("/users")
    //ResponseEntity<Void>: 응답 body가 없 -> .build()로 응답 완성
    public ResponseEntity<Void> registUser(@RequestBody com.ohgiraffers.restapi.section01.responseentity.UserDTO newUser){

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
    @Operation(summary = "회원 정보 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "회원 수정 성공"),
            @ApiResponse(responseCode = "404", description = "회원 정보 없음")
    })
    @PutMapping("/users/{userNo}")
    public  ResponseEntity<Void> modifyUser(@PathVariable int userNo,
                                            @RequestBody com.ohgiraffers.restapi.section01.responseentity.UserDTO modifyInfo){
        com.ohgiraffers.restapi.section01.responseentity.UserDTO foundUser = users.stream()
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

    @Operation(summary = "회원 정보 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "회원 정보 삭제 성공"),
            @ApiResponse(responseCode = "404", description = "회원 정보 없음")
    })
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
