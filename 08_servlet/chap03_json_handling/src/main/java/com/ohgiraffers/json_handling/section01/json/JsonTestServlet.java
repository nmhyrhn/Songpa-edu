package com.ohgiraffers.json_handling.section01.json;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/json")
public class JsonTestServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. 클라이언트가 보낸 JSON 문자열 덩어리(Body)를 읽어오기 위한 통로 열기
        BufferedReader reader = req.getReader();
        StringBuilder sb = new StringBuilder();

        String line;

        while((line = reader.readLine()) != null) {
            sb.append(line);
        }
        System.out.println("프론트엔드가 보낸 원본 JSON 문자열 : " + sb);

        // mvn repository에서 jackson databind 라이브러리 build.gradle에 추가 필요!!
        // Jackson 라이브러리의 ObjectMapper를 사용해 JSON 문자열을 자바 객체(UserDTO)로 변환 (역직렬화)
        ObjectMapper mapper = new ObjectMapper();

        // readValue(변환할 문자열, 변환할 자바 클래스 타입)
        UserDTO user = mapper.readValue(sb.toString(), UserDTO.class);

        System.out.println("자바 객체로 잘 변환 되었나? : " + user);

        // 응답 헤더 세팅
        resp.setContentType("application/json; charset=UTF-8");

        // 브라우저로 내보낼 스트림 열기
        PrintWriter out = resp.getWriter();

        // 자바 객체를 다시 JSON 문자열로 변환 (직렬화)
        String jsonResponse = mapper.writeValueAsString(user);

        out.print(jsonResponse);

        // 사용한 스트림 닫아주기
        out.close();

        /* 브라우저 콘솔 테스트
        * 크롬 보안상 붙여넣기가 안될때는 allow pasting 입력 후 붙여넣기 가능!!
        * fetch('http://localhost:8080/json', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                "id": "user01",
                "nickname": "홍길동",
                "age": 20
            })
        })
        .then(res => res.json())
        .then(data => console.log("서버가 돌려준 응답:", data));
        * */

    }
}
