package com.ohgiraffers.state_management.section03.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;


@WebServlet(urlPatterns = {"/api/auth/login", "/api/auth/me", "/api/auth/logout"})
public class AuthApiServlet extends HttpServlet {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json; charset=UTF-8");

        if(!"/api/auth/me".equals(req.getServletPath())){
            sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Unknown API path");
            return;
        }

        //기존 세션만 확인하고, 없으면 새로 만들지 않음
        HttpSession session = req.getSession(false);
        String loginUser = session == null ? null : (String) session.getAttribute("loginUser");

        //로그인 상태가 아닐 때
        if(loginUser == null){
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //401 응답
            mapper.writeValue(resp.getWriter(), new AuthResponse(false, null));
            return;
        }

        mapper.writeValue(resp.getWriter(), new AuthResponse(true, loginUser));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        String path = req.getServletPath();

        if("/api/auth/login".equals(path)){
            login(req, resp);
            return;
        }

        if("/api/auth/logout".equals(path)){
            logout(req, resp);
            return;
        }

        sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Unknown API path");


    }

    private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        LoginRequest loginRequest = mapper.readValue(req.getReader(), LoginRequest.class);

        if(!"user01".equals(loginRequest.getId()) || !"pass01".equals(loginRequest.getPassword())) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            mapper.writeValue(resp.getWriter(), new ErrorResponse("id 또는 password가 잘못되었습니다."));
            return;
        }

        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(60 * 30);
        //서버 세션에 로그인 사용자 저장
        session.setAttribute("loginUser", loginRequest.getId());

        //프론트가 화면 상태를 바꿀 수 있도록 로그인 성공 JSON 응답
        mapper.writeValue(resp.getWriter(), new AuthResponse(true, loginRequest.getId()));
    }

    private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);

        if(session != null){
            session.invalidate(); //서버에 저장된 세션 폐기
        }

        //로그아웃 성공, 응답 본문 없음
        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    //에러 응답을 공통으로 만들어주는 메서드
    private void sendError(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);
        mapper.writeValue(resp.getWriter(), new ErrorResponse(message));
    }

    public static class LoginRequest{
        private String id;
        private String password;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    //프론트에게 '현재 로그인 상태인지'와 '로그앤 했다면 사용자 ID가 무엇인지' 알려주는 응답
    public static class AuthResponse{
        private final boolean authenticated;
        private final String userId;

        public AuthResponse(boolean authenticated, String userId) {
            this.authenticated = authenticated;
            this.userId = userId;
        }

        public boolean isAuthenticated() {
            return authenticated;
        }

        public String getUserId() {
            return userId;
        }
    }

    //에러 메세지를 객체로 감싸서 JSON으로 응답 하기 위한 클래스
    public static class ErrorResponse {
        private final String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

}
