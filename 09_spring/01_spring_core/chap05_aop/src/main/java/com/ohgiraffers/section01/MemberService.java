package com.ohgiraffers.section01;

import org.springframework.stereotype.Service;

@Service //DAO를 호출해서 사용할 서비스 로직을 작성할 클래스
public class MemberService {

    private final MemberDAO memberDAO;

    //생성자 하나니깐 @Autowired 생략 가능
    public MemberService(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }



}
