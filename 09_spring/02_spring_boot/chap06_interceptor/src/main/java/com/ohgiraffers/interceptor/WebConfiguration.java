package com.ohgiraffers.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final StopwatchInterceptor stopwatchInterceptor;
    private final ApiKeyInterceptor apiKeyInterceptor;


    //생성자 주입
    public WebConfiguration(StopwatchInterceptor stopwatchInterceptor, ApiKeyInterceptor apiKeyInterceptor) {
        this.stopwatchInterceptor = stopwatchInterceptor;
        this.apiKeyInterceptor = apiKeyInterceptor;
    }

    //인터셉터 적용 설정 (경로, 순서 등)
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(stopwatchInterceptor)
                .addPathPatterns("/api/v1/**") //적용 경로
                .excludePathPatterns("/error") //제외 경로
                .order(1); //인터셉터 실행순서, 숫자가 작을수록 먼저 실행


        registry.addInterceptor(apiKeyInterceptor)
                .addPathPatterns("/api/v1/admin/**")
                .excludePathPatterns("/error")
                .order(2);
    }


}
