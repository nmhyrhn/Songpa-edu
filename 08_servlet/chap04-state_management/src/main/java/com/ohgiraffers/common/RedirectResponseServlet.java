package com.ohgiraffers.common;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/redirect")
public class RedirectResponseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        String rememberId = "쿠키 없음";

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if("rememberId".equals(cookie.getName())) {
                    rememberId = cookie.getValue();
                    break;
                }
            }
        }

        //session
        HttpSession session = req.getSession();
        String loginUser = session == null ? null : (String) session.getAttribute("loginUser");

        if(loginUser == null){
            loginUser = "세션 정보 없음";
        }



        resp.setContentType(("text/html;charset=UTF-8"));
        PrintWriter out = resp.getWriter();

        out.print("<!DOCTYPE html>");
        out.print("<html lang='ko'><head><meta charset='UTF-8'><title>State Result</title></head><body>");
        out.print("<h1>Cookie / Session 확인</h1>");
        out.print("<section style='border:1px solid #777; padding:12px; margin-bottom:12px;'>");
        out.print("<h2>Cookie</h2>");
        out.print("<p>rememberId: <b>" + rememberId + "</b></p>");
        out.print("<p>쿠키 값은 브라우저에 저장되고, 같은 서버 요청에 자동으로 함께 전송됩니다.</p>");
        out.print("</section>");
        out.print("<section style='border:1px solid #777; padding:12px;'>");
        out.print("<h2>Session</h2>");
        out.print("<p>loginUser: <b>" + loginUser + "</b></p>");
        out.print("<p>세션의 실제 값은 서버에 저장되고, 브라우저는 JSESSIONID 쿠키만 가지고 다닙니다.</p>");
        out.print("</section>");
        out.print("<p><a href='/'>돌아가기</a></p>");
        out.print("</body></html>");





    }
}
