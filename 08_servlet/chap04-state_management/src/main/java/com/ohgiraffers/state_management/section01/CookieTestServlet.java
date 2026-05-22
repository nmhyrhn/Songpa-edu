package com.ohgiraffers.state_management.section01;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/cookie")
public class CookieTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //rememberId라는 이름으로 user01 값을 가진 쿠키 새성
        Cookie cookie = new Cookie("rememberId", "user01");

        //쿠키 유지 시간 설정
        cookie.setMaxAge(60 * 60 * 24); //24시간 설정

        //어떤 경로에서 쿠키가 전송되게 할 것 인지 설정
        cookie.setPath("/");

        //Http 요청으로만 접근 가능하게 설정(document.cookie로 직접 접근 차단)
        cookie.setHttpOnly(true);

        //응답에 Set-Cookie를 포함
        resp.addCookie(cookie);

        //쿠키는 응답 이후 브라우저에 저장되고,
        //다음 요청부터 서버로 다시 전달된다.
        //그래서 쿠키 확인용 서블릿으로 브라우저에게 '/redirect' 주소로 다시 요청을 지시한다.
        resp.sendRedirect("redirect");




    }
}
