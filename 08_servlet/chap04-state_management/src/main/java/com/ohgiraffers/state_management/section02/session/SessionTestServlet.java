package com.ohgiraffers.state_management.section02.session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


@WebServlet("/session")
public class SessionTestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //true: 현재 요청에 연결된 세션을 가져온다(없으면 새로 생성)
        HttpSession session = req.getSession(false); //false 옵션: 세션이 있으면 가져오고 없으면 새로 만들지 않음

        //세션 만료 시간 설정
        session.setMaxInactiveInterval(60 * 10);

        //서버 세션 공간에 loginUser 값 저장
        session.setAttribute("loginUser", "user01");


        resp.sendRedirect("redirect");





    }
}
