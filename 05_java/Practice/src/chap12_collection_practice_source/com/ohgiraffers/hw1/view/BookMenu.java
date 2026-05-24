package chap12_collection_practice_source.com.ohgiraffers.hw1.view;

import chap12_collection_practice_source.com.ohgiraffers.hw1.controller.BookManager;
import chap12_collection_practice_source.com.ohgiraffers.hw1.model.dto.BookDTO;

import java.util.List;
import java.util.Scanner;

public class BookMenu {
    private Scanner sc;
    private BookManager bm;
    private int bNo = 1;

    public BookMenu() {
        this.sc = new Scanner(System.in);
        this.bm = new BookManager();
    }

    public void mainMenu() {
        while (true) {
            System.out.println("---도서 관리 프로그램---");
            System.out.println("1. 새 도서 추가");
            System.out.println("2. 도서정보 정렬 후 출력");
            System.out.println("3. 도서 삭제");
            System.out.println("4. 도서 검색출력");
            System.out.println("5. 전체 출력");
            System.out.println("6. 끝내기");

            int no = sc.nextInt();
            sc.nextLine();

            switch (no) {
                case 1:
                    bm.addBook(inputBook());
                    break;
                case 2:
                    System.out.println("정렬 방식을 선택하세요.");
                    System.out.println("1. 도서번호(ISBN)으로 오름차순정렬");
                    System.out.println("2. 도서번호(ISBN)으로 내림차순정렬");
                    System.out.println("3. 책 제목으로 오름차순 정렬");
                    System.out.println("4. 책 제목으로 내림차순 정렬");

                    int type = sc.nextInt();
                    sc.nextLine();
                    bm.printBookList(selectSortedBook(type));
                    break;
                case 3:
                    bm.deleteBook(inputBookNo());
                    break;
                case 4:
                    bm.searchBook(inputBookTitle());
                    break;
                case 5:
                    bm.displayAll();
                    break;
                case 6:
                    System.out.println("프로그램을 종료합니다.");sc.close();return;
                default:
                    System.out.println("잘못된 번호입니다.");break;
            }
        }
    }

    public BookDTO inputBook() {
        BookDTO bookDTO = new BookDTO();
        System.out.println("도서 제목 : ");
        bookDTO.setTitle(sc.nextLine());

        int category = 0;
        while (true) {
            try {
                System.out.print("도서 장르 (1:인문 / 2:자연과학 / 3:의료 / 4:기타): ");
                category = sc.nextInt();

                if (category >= 1 && category <= 4) {
                    break;
                } else {
                    System.out.println("1~4 사이의 숫자를 입력해주세요.");
                }

            } catch (java.util.InputMismatchException e) {
                System.out.println("에러: 숫자만 입력 가능합니다.");
                sc.nextLine();
            }
        }
        bookDTO.setCategory(category);

        System.out.println("도서 저자 : ");
        sc.nextLine();
        bookDTO.setAuthor(sc.nextLine());
        bookDTO.setbNo(bNo);
        bNo++;
        return bookDTO;
    }

    public int inputBookNo() {
        System.out.println("도서 번호: ");
        return sc.nextInt();
    }

    public String inputBookTitle() {
        System.out.println("도서 제목: ");
        return sc.nextLine();
    }

    public List<BookDTO> selectSortedBook(int no) {
        return bm.sortedBookList(no);
    }
}