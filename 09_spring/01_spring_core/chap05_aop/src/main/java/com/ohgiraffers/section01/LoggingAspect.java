package com.ohgiraffers.section01;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect //AOP용 클래스 표시
@Component
public class LoggingAspect {

    //Pointcut: 어떤 메서드에 공통 기능을 적용할지 고르는 조건
    //com.ohgiraffers.section01 패키지 안의 이름이 Service로 끝나는 클래스의 모든 메서드 실행지점
    @Pointcut("execution(* com.ohgiraffers.section01.*Service.*(..))")
    public void logPointcut(){

    }

    @Before("LoggingAspect.logPointcut()")
    //JoinPoint: 현재 실행 지점의 정보를 담고 있는 객체
    public void logBefore(JoinPoint joinPoint){
        System.out.println("Before joinPoint.getTarget(): " + joinPoint.getTarget());
        System.out.println("Before joinPoint.getSignature(): " + joinPoint.getSignature());
    }




}
