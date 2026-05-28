package com.ohgiraffers.section01;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service //DAO를 호출해서 사용할 서비스 로직을 작성할 클래스
//Target: 공통 기능이 적용될 실제 대상 객체
//Proxy: Target 앞에서 요청을 가로채는 대리 객체
public class MemberService {

    private final MemberDAO memberDAO;

    //생성자 하나니까 @Autowired 생략 가능
    public MemberService(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    public Map<Long, MemberDTO> selectMembers(){
        System.out.println("selectMembers 메소드 실행");
        return memberDAO.selectMembers();
    }

    public MemberDTO selectMember(Long id) {
        System.out.println("selectMember 메소드 실행");
        return memberDAO.selectMember(id);
    }

}
