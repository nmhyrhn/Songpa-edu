package com.ohgiraffers.section02.javaconfig;

import com.ohgiraffers.common.MemberDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {

        //java Configuration 기반 Bean 등록 예제
        //생성자에 @Configuration 어노테이션이 달린 설정 클래스의 메타 정보를 전달
        ApplicationContext context = new AnnotationConfigApplicationContext(Application.class);

        MemberDTO member = context.getBean("member",  MemberDTO.class);

        System.out.println(member);


    }


}
