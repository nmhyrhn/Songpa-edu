package chap12_collection_practice_source.com.ohgiraffers.hw1.view;

import chap12_collection_practice_source.com.ohgiraffers.hw1.controller.BookManager;
import chap12_collection_practice_source.com.ohgiraffers.hw1.model.dto.BookDTO;

import java.util.List;
import java.util.Scanner;

public class BookMenu {

    private Scanner sc;
    private BookManager bm;

    public BookMenu() {
        this.sc = new Scanner(System.in);
        this.bm = new BookManager();
    }

    public void mainMenu() {

    }

    public BookDTO inputBook() {

    }

    public int inputBookNo() {

    }

    public String inputBookTitle() {

    }

    public List<BookDTO> selectSortedBook() {

    }
}
