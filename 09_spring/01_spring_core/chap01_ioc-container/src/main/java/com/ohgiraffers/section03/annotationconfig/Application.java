package com.ohgiraffers.section03.annotationconfig;

import com.ohgiraffers.common.MemberDAO;
import com.ohgiraffers.common.MemberDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {
    public static void main(String[] args) {

        ApplicationContext context
                = new AnnotationConfigApplicationContext(ContextConfiguration.class);

        /*getBeanDefinitionNames: 스프링 컨테이너에서 생성 된 bean 이름을 배열로 반환*/
        String[] beanNames = context.getBeanDefinitionNames();
        for(String beanName : beanNames){
            System.out.println(beanName);
        }

        MemberDAO memberDAO = context.getBean("memberDAO", MemberDAO.class);

        System.out.println(memberDAO.selectMember(1));
        System.out.println(memberDAO.insertMember(new MemberDTO(3, "user03", "pass03", "판다")));
        System.out.println(memberDAO.selectMember(3));

    }
}
