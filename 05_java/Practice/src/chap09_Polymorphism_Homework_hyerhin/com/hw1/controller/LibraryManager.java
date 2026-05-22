package chap09_Polymorphism_Homework_hyerhin.com.hw1.controller;

import chap09_Polymorphism_Homework_hyerhin.com.hw1.model.dto.AniBook;
import chap09_Polymorphism_Homework_hyerhin.com.hw1.model.dto.Book;
import chap09_Polymorphism_Homework_hyerhin.com.hw1.model.dto.CookBook;
import chap09_Polymorphism_Homework_hyerhin.com.hw1.model.dto.Member;

public class LibraryManager {

    private Member mem = null;
    private Book[] bList = new Book[5];
    {
        bList[0] = new CookBook("백종원의 집밥", "백종원", "tvN", true);
        bList[1] = new AniBook("한번 더 해요", "미티", "원모어", 19);
        bList[2] = new AniBook("루피의 원피스", "루피", "japan", 12);
        bList[3] = new CookBook("이혜정의 얼마나 맛있게요", "이혜정", "문학", false);
        bList[4] = new CookBook("최현석 날 따라해봐", "최현석", "소금책", true);
    }

    public void insertMember(Member mem) {
        this.mem = mem;
    }

    public Member myInfo() {
        return mem;
    }

    public Book[] selectAll() {
        return bList;
    }

    public Book[] searchBook(String keyword) {

        Book[] result = new Book[5];

        boolean isFound = false;

        int count = 0;
        for (Book book : bList) {
            if (book.getTitle().contains(keyword)) {
                result[count] = book;
                count++;
            }
        }
        return result;
    }

    public int rentBook(int index) {
        int result = 0;

        Book book = bList[index];

        if (book instanceof AniBook) {
            if (mem.getAge() < ((AniBook)book).getAccessAge()) {
                result = 1;
            }
        } else if (book instanceof CookBook) {
            if (((CookBook)book).isCoupon()) {
                int currentCount = mem.getCouponCount();
                mem.setCouponCount(currentCount + 1);
                result = 2;
            }
        }

        return result;
    }
}
