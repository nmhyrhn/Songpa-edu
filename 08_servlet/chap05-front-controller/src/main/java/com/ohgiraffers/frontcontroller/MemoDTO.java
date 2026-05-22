package com.ohgiraffers.frontcontroller;

public class MemoDTO {

    private int id;
    private String content;

    public MemoDTO() {
    }

    public MemoDTO(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MemoDTO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
