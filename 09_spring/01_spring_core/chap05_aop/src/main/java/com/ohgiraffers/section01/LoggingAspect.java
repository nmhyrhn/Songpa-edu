package com.ohgiraffers.section01;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;

/*
* Aspect: 공통 관심사를 모아둔 클래스
* */
@Aspect //AOP용 클래스 표시
@Component
public class LoggingAspect {

    //Pointcut: 어떤 메서드에 공통 기능을 적용할지 고르는 조건, Advice를 어디에 적용할지 고르는 조건
    //com.ohgiraffers.section01 패키지 안의 이름이 Service로 끝나는 클래스의 모든 메서드 실행지점
    @Pointcut("execution(* com.ohgiraffers.section01.*Service.*(..))")
    public void logPointcut(){

    }

    /*
    * Advice: 실제로 실행될 공통 기능 메서드
    * Weaving: Target에 Advice가 적용되는 과정
    * Target: 공통 기능이 적용될 실제 대상 객체
    * Proxy: Target 앞에서 요청을 가로채는 대리 객체
    * */
    //@Before(포인트컷): 포인트컷으로 설정된 메서드가 실행되기 전에 끼워넣는다.
    @Before("LoggingAspect.logPointcut()")
    //JoinPoint: 현재 실행 지점의 정보를 담고 있는 객체(Advice가 끼어들 수 있는 실행 지점)
    public void logBefore(JoinPoint joinPoint){
        System.out.println("Before joinPoint.getTarget(): " + joinPoint.getTarget());
        System.out.println("Before joinPoint.getSignature(): " + joinPoint.getSignature());

        if(joinPoint.getArgs().length > 0) {
            System.out.println("getArgs() : " + joinPoint.getArgs()[0]);
        }
    }

    //포인트 컷을 동일한 클래스 내에서 사용하는 것이면 클래스명 생략 가능
    @After("logPointcut()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("After: " + joinPoint.getTarget());
        System.out.println("After: " + joinPoint.getSignature());
    }

    //returning 속성은 리턴값으로 반환되는 애를 객체로 받는것이다.
    //returning 속성값의 이름이 매개변수로 받아주는것의 이름과 일치해야 한다.
    //또한, joinPoint는 밭드시 첫 번째 매개변수로 선언해야 한다.
    @AfterReturning(pointcut = "logPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinpoint, Object result) {
        System.out.println("After Returning result: " + result);

        /*리턴할 결과값을 가공해 줄 수 도 있다.*/
        if(result != null && result instanceof Map) {
            ((Map<Long, MemberDTO>) result).put(100L, new MemberDTO(100L, "반환 값 가공"));
        }
    }

    //throwing 속성의 이름과 매개변수 이름이 동일해야 하며
    //발생한 예외의 내용을 Throwable 객체로 받아 볼 수 있다.
    @AfterThrowing(pointcut = "logPointcut()", throwing = "exception")
    public void logAfterThrowing(Throwable exception) {
        System.out.println("After Throwing exception: " + exception);
    }

    //대상 메서드를 감싸고 실행 여부까지 제어할 수 있다.
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around Before " + joinPoint.getSignature().getName());

        //원본 조인포인트를 실행합니다.
        Object result = joinPoint.proceed();

        System.out.println("Around After " + joinPoint.getSignature().getName());
        return result;
    }



}
