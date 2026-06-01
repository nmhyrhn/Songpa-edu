package com.ohgiraffers.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    //관리자 API 요청에서 확인할 헤더 이름
    private static final String API_KEY_HEADER = "X_API_KEY";
    // 수업용
    private static final String VALID_API_KEY = "secret-key";

    private final ObjectMapper objectMapper;

    public ApiKeyInterceptor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        /*Controller 도착하기 전에 요청 헤더에서 API Key를 꺼낸다.*/
        String apiKey = request.getHeader(API_KEY_HEADER);

        if(VALID_API_KEY.equals(apiKey)){
            System.out.println("[ApiKey] authorized " + request.getMethod() + " " + request.getRequestURI());
            return true;
        }
        System.out.println("[ApiKey] unauthorized " + request.getMethod() + " " + request.getRequestURI());

        response.setStatus(HttpStatus.UNAUTHORIZED.value()); //401
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = new LinkedHashMap<>();
        errorResponse.put("status", HttpStatus.UNAUTHORIZED.value()); // 401
        errorResponse.put("error", HttpStatus.UNAUTHORIZED.name());
        errorResponse.put("message", "관리자 API를 호출하려면 X-API-KEY 헤더가 필요합니다.");
        errorResponse.put("path", request.getRequestURI());

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));

        return false;


    }
}
