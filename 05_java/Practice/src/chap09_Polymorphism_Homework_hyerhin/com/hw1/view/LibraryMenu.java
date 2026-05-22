package chap09_Polymorphism_Homework_hyerhin.com.hw1.view;

import chap09_Polymorphism_Homework_hyerhin.com.hw1.controller.LibraryManager;
import chap09_Polymorphism_Homework_hyerhin.com.hw1.model.dto.Book;
import chap09_Polymorphism_Homework_hyerhin.com.hw1.model.dto.Member;

import java.util.Scanner;

public class LibraryMenu {
    private LibraryManager lm = new LibraryManager();
    Scanner sc = new Scanner(System.in);

    public void mainMenu() {
        Member member = new Member();

        System.out.println("이름 : ");
        member.setName(sc.nextLine());

        System.out.println("나이 : ");
        member.setAge(sc.nextInt());

        System.out.println("성별 (M/F) :");
        member.setGender(sc.next().charAt(0));

        lm.insertMember(member);

        while (true) {
            System.out.println("==== 메뉴 ====");
            System.out.println("1. 마이페이지");
            System.out.println("2. 도서 전체 조회");
            System.out.println("3. 도서 검색");
            System.out.println("4. 도서 대여하기");
            System.out.println("0. 프로그램 종료하기");

            int no = sc.nextInt();
            sc.nextLine();

            switch (no) {
                case 1:
                    System.out.println(lm.myInfo());
                    break;
                case 2:
                    selectAll();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    rentBook();
                    break;
                case 0:
                    System.out.println("프로그램을 종료합니다.");sc.close();return;
                default:
                    System.out.println("잘못된 번호입니다.");break;
            }
        }

    }

    public void selectAll() {
        Book[] bList = lm.selectAll();
        for (int i=0; i < bList.length; i++) {
            System.out.println(i+"번도서: " + bList[i].getTitle() + " / " + bList[i].getAuthor() + " / " + bList[i].getPublisher());
        }
    }
    public void searchBook() {
        System.out.println("검색할 제목 키워드 : ");
        String keyword = sc.next();
        Book[] searchList = lm.searchBook(keyword);
        for (Book book : searchList) {
            if (null != book) {
                System.out.println(book.getTitle() + " / " + book.getAuthor() + " / " + book.getPublisher());
            }
        }

    }
    public void rentBook() {
        lm.selectAll();
        System.out.println("대여할 도서 번호 선택 : ");
        int result = lm.rentBook(sc.nextInt());
        switch (result) {
            case 0 :
                System.out.println("성공적으로 대여되었습니다.");
                break;
            case 1 :
                System.out.println("나이 제한으로 대여 불가능입니다.");
                break;
            case 2 :
                System.out.println("성공적으로 대여되었습니다. 요리학원 쿠폰이 발급되었습니다. 마이페이지를 통해 확인하세요");
                break;
        }
    }
}
