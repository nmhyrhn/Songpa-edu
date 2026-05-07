package com.ohgiraffers.section02.uses;

import java.util.Scanner;

public class MemberService {

    //회원 5명 일괄 등록
    public void signUpMembers() {
        Member[] members = {
                new Member(1, "id01", "pass01", 23, '여'),
                new Member(2, "id02", "pass02", 25, '여'),
                new Member(3, "id03", "pass03", 26, '남'),
                new Member(4, "id04", "pass04", 26, '남'),
                new Member(5, "id05", "pass05", 24, '남')

        };

        if (MemberRepository.store(members)) {
            System.out.println("총 " + members.length + "명 등록 성공");
        } else {
            System.out.println("정원 초과로 등록 실패");
        }

    }

    //전체 회원 조회
    public void showAllMembers() {
        Member[] allMembers = MemberRepository.findAllMembers();
        for (Member member : allMembers) {
            if (member != null) {
                System.out.println(member.getInformation());
            }
        }
    }


    //아이디로 회원 검색
    public void searchMemberById(Scanner sc) {
        System.out.println("검색할 아이디를 입력하세요: ");
        String searchId = sc.nextLine();

        Member[] foundmember = MemberRepository.findAllMembers();
        boolean isFound = false; //플래그 변수 설정

        for(Member member : foundmember){
            if(member != null) {
                if(searchId.equals(member.getId())){
                    System.out.println("-----검색 결과------");
                    System.out.println(member.getInformation());
                    isFound = true;
                    break;
                }
            }
        }
      if(!isFound){
          System.out.println("일치하는 회원이 없습니다.");
      }
    }

}