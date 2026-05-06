package com.ohgiraffers.section06.statickeyword;

public class CampusActivity {

    //non-static : 개인
    private int personLocker;

    //non-static 메소드
    public void openPersonalLocker(){
        this.personLocker++;
        System.out.println("개인 사물함을 열었습니다. 사용 횟수 : " + this.personLocker);
    }

    //static 메소드
    public static void libraryAnnouncement(){
//        this.personLocker++; //static은 this가 존재하지 않기 때문에 쓸 수 없다.
        System.out.println("도서관 공지 방송입니다.");
    }

}
