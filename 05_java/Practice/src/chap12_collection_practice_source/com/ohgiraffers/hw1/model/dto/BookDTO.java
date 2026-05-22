package chap12_collection_practice_source.com.ohgiraffers.hw1.model.dto;

public class BookDTO {

    private int bNo;
    private int category;
    private String title;
    private String author;

    public BookDTO() {
    }

    public BookDTO(String author, int bNo, int category, String title) {
        this.author = author;
        this.bNo = bNo;
        this.category = category;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getbNo() {
        return bNo;
    }

    public void setbNo(int bNo) {
        this.bNo = bNo;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "author='" + author + '\'' +
                ", bNo=" + bNo +
                ", category=" + category +
                ", title='" + title + '\'' +
                '}';
    }
}
