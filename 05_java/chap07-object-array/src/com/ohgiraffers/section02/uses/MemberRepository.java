package com.ohgiraffers.section02.uses;

public class MemberRepository {

    //static 배열: 프로그램 전체에서 하나의 저장소를 공유
    private final static Member[] members;
    private static int count;

    static{
        members = new Member[10];

    }

    //회원의 배열을 저장소에 저장
    public static boolean store(Member[] newMembers){
        for(int i=0; i< newMembers.length; i++) {
            members[count++] = newMembers[i];
        }
        return true;
    }

    //저장된 전체 회원 배열 반환
    public static Member[] findAllMembers() {
        return members;
    }




}
