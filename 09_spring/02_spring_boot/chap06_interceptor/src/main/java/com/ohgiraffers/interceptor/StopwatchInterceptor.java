package com.ohgiraffers.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class StopwatchInterceptor implements HandlerInterceptor {

    /*preHandle에서 저장하고 afterCompletion에서 다시 꺼낼 요청 속성 이름*/
    private static final String START_TIME = "startTime";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME, startTime); //요청 객체 안에 시작시간을 임시 보관

        System.out.println("[Stopwatch] preHandle " + request.getMethod() + " " + request.getRequestURI());

        return true; //요청 계속 진행, false를 반환하면 Controller가 실행되지 않음

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

        /*같은 요청 객체에 저장해둔 시작 시간을 꺼낸다*/
        Object startTimeAttribute = request.getAttribute(START_TIME);

        // startTimeAttribute가 Long 타입이면 그 값을 Long으로 캐스팅해서 startTime 변수로 바로 사용
        if(startTimeAttribute instanceof Long startTime) {
            long elapsedTime = System.currentTimeMillis() - startTime;

            response.setHeader("X-Elapsed-Time", elapsedTime + "ms");

            System.out.println("[Stopwatch] afterCompletion " + request.getMethod()
                    + " " + request.getRequestURI()
                    + " " + elapsedTime + "ms");
        }
    }
}
