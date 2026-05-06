package com.ohgiraffers.section06.statickeyword;

public class Application {
    public static void main(String[] args) {

        System.out.println("첫번째 학생");
        UniversityStudent student1 = new UniversityStudent();
        System.out.println("개인 : " + student1.getPersonalLocker());
        System.out.println("공용 : " + student1.getSharedLocker());

        student1.increasePersonalLocker();
        student1.increaseSharedLocker();

        System.out.println("개인 : " + student1.getPersonalLocker());
        System.out.println("공용 : " + student1.getSharedLocker());

        System.out.println();

        System.out.println("두번째 학생");
        UniversityStudent student2 = new UniversityStudent();
        System.out.println("개인 : " + student2.getPersonalLocker());
        System.out.println("공용 : " + student2.getSharedLocker());

        student2.increasePersonalLocker();
        student2.increaseSharedLocker();

        System.out.println("개인 : " + student2.getPersonalLocker());
        System.out.println("공용 : " + student2.getSharedLocker());

        System.out.println();

        //static: 클래스명으로 바로 호출
        CampusActivity.libraryAnnouncement(); //static은 객체 생성 없이도 사용 가능

        //non-static: 반드시 객체 먼저
        CampusActivity student =  new CampusActivity(); //non-static이므로 객체 생성
        student.openPersonalLocker();
        student.openPersonalLocker();

    }
}
