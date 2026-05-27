package com.ohgiraffers.section01;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
//Spring이 실제 Service 객체 앞에 대리 객체를 세우고,
// 그 대리 객체가 메서드 실행 전후에 공통 기능을 끼워 넣는다
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ContextConfiguration {



}
