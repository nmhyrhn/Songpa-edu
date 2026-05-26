package com.ohgiraffers.section02.javaconfig;

import com.ohgiraffers.common.MemberDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*해당 클래스가 빈을 생성하는 설정 클래스임을 표기*/
@Configuration
public class ContextConfiguration {

    /*해당 메소드의 반환 값을 스프링 컨테이너에 빈으로 등록하는 의미
    * 이름을 별도 지정하지 않으면 메소드 이름을 bean의 id로 자동 인식*/
    @Bean(name="member")
    public MemberDTO getMember(){
        return new MemberDTO(1, "user01", "pass01", "홍길동");
    }


}
