package com.ohgiraffers.request_and_response.section02.parameter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/parameter")
public class ParameterTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //getParameter()는 언제나 문자열(String)을 돌려준다.
        String name = req.getParameter("name");
        System.out.println("클라이언트가 보낸 이름: " + name);
        //체크박스처럼 여러 개의 값을 한번에 싹 가져오려면 getParameterValues() 사용(배열 반환)
        String[] values = req.getParameterValues("value");

        for(String value : values){
            System.out.println(value);
        }

        System.out.println("User-Agent 헤더 정보: " + req.getHeader("User-Agent"));

        //1. 응답할 문서의 타입과 인코딩 설정
        resp.setContentType("text/html; charset=utf-8");

        //2. 클라이언트 브라우저와 연결된 스트림(길) 열기
        PrintWriter out = resp.getWriter();

        //2. 스트림을 통해 HTML 태그를 문자열로 밀어넣기
        out.print("<h1>안녕 서블릿! 반가워~</h1>");

        out.close();




    }
}
