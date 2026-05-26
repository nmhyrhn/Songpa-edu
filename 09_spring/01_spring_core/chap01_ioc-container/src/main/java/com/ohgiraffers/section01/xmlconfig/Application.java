package com.ohgiraffers.section01.xmlconfig;

import com.ohgiraffers.common.MemberDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Application {
    public static void main(String[] args) {

        // XML을 읽는 컨테이너 생성
        ApplicationContext context = new GenericXmlApplicationContext("section01/xmlconfig/spring-context.xml");

        //MemberDTO member = (MemberDTO) context.getBean("member");
        //bean의 id와 클래스 메타 정보를 전달하며 빈을 가져온다
        MemberDTO member = context.getBean("member", MemberDTO.class);

        System.out.println(member);


    }
}
