package com.ohgiraffers.section06.statickeyword;

public class UniversityStudent {

    //non-static 필드 : "개인 사물함"
    //new로 객체를 만들 때마다 Heap에 새로 생성된다. 객체마다 독립적이다.
    private int personalLocker;

    //static 필드 : "공용 사물함"
    //프로그램 시작 시 static 영역에 단 하나만 생성된다. 모든 객체가 공유한다.
    private static int sharedLocker;


    public int getPersonalLocker() {
        return this.personalLocker;
    }

    //static 필드는 클래스명.필드명 으로 접근해야 한다.
    public int getSharedLocker() {
        return UniversityStudent.sharedLocker;
    }

    public void increasePersonalLocker(){
        this.personalLocker++;
    }

    public void increaseSharedLocker(){
        UniversityStudent.sharedLocker++;
    }


}
