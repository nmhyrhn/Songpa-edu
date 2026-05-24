package chap12_collection_practice_source.com.ohgiraffers.hw1.controller;

import chap12_collection_practice_source.com.ohgiraffers.hw1.comparator.AscBookNo;
import chap12_collection_practice_source.com.ohgiraffers.hw1.comparator.AscBookTitle;
import chap12_collection_practice_source.com.ohgiraffers.hw1.comparator.DescBookNo;
import chap12_collection_practice_source.com.ohgiraffers.hw1.comparator.DescBookTitle;
import chap12_collection_practice_source.com.ohgiraffers.hw1.model.dto.BookDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookManager {
    private ArrayList<BookDTO> booklist;
    private Scanner sc;

    public BookManager() {
        this.booklist = new ArrayList<>();
        this.sc = new Scanner(System.in);
    }

    public void addBook(BookDTO book) {
        booklist.add(book);
    }

    public void deleteBook(int index) {
        booklist.remove(index);
    }

    public void searchBook(String title) {
        boolean isFound = false;

        for (BookDTO bookDTO : booklist) {
            if (bookDTO.getTitle().contains(title)) {
                System.out.println(bookDTO.toString());
                isFound = true;
            }
        }

        if (!isFound) {
            System.out.println("조회된 도서가 목록에 없습니다.");
        }
    }

    public void displayAll() {
        for (BookDTO bookDTO : booklist) {
            System.out.println(bookDTO.toString());
        }
    }

    public List<BookDTO> sortedBookList(int type) {
        switch (type) {
            case 1:
                booklist.sort(new AscBookNo());
                break;
            case 2:
                booklist.sort(new DescBookNo());
                break;
            case 3:
                booklist.sort(new AscBookTitle());
                break;
            case 4:
                booklist.sort(new DescBookTitle());
                break;
            default:
                System.out.println("잘못된 번호입니다.");
                return booklist;
        }
        return booklist;
    }

    public void printBookList(List<BookDTO> printList) {
        if (printList == null || printList.isEmpty()) {
            System.out.println("출력할 도서 목록이 없습니다.");
            return;
        }

        for (BookDTO book : printList) {
            System.out.println(book);
        }
    }
}