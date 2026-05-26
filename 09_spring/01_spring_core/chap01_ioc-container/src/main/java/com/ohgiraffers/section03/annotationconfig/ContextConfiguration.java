package com.ohgiraffers.section03.annotationconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/*ComponentScan?
* base package로 설정 된 하위 경로에 특정 어노테이션을 가지고 있는 클래스를 bean으로 등록하는 기능
* @Component, @Controller, @Service, @Repository, @Configuration 등*/
@Configuration
@ComponentScan(basePackages = "com.ohgiraffers.common")
public class ContextConfiguration {




}
