package com.ohgiraffers.frontcontroller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

//이 서블릿이 /api로 시작하는 모든 요청을 받는다
@WebServlet("/api/*")
public class ApiDispatcherServlet extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();
    private final MemoController memoController = new MemoController();

    //GET, POST 같은 여러 HTTP method 요청이 들어왔을 때 공통으로 실행되는 메서드
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        String method = req.getMethod();
        String path = req.getPathInfo();

        System.out.println(method + " " + path);

        if(path == null) {
            sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Unknown API path");
            return;
        }

        if("GET".equals(method) && "/memos".equals(path)) {
            memoController.findAll(resp);
            return;
        }
        if("POST".equals(method) && "/memos".equals(path)) {
            memoController.regist(req, resp);
            return;
        }
        if("POST".equals(method) && "/memos/delete".equals(path)) {
            memoController.remove(req, resp);
            return;
        }

        sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Unknown API path: " + method + " " + path);


    }


    private void sendError(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        mapper.writeValue(resp.getWriter(), new ErrorResponse(message));
    }
}
