package com.ohgiraffers.section02.uses;

import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        MemberService memberService = new MemberService();

        while(true) {
            System.out.println("===회원 관리 프로그램===");
            System.out.println("1. 회원 등록");
            System.out.println("2. 회원 전체 조회");
            System.out.println("3. 회원 아이디 조회");
            System.out.println("9. 프로그램 종료");
            System.out.print("메뉴 선택 : ");

            int no = sc.nextInt();
            sc.nextLine();//버퍼 비우기 - 개행 문자 제거

            switch (no) {
                case 1:
                    memberService.signUpMembers();
                    break;
                case 2:
                    memberService.showAllMembers();
                    break;
                case 3:
                    memberService.searchMemberById(sc);
                    break;
                case 9:
                    System.out.println("프로그램을 종료합니다.");
                    sc.close();
                    return;
                default:
                    System.out.println("존재하지 않는 메뉴입니다.");
                    break;
            }


        }
    }

}
