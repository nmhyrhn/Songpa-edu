package com.ohgiraffers.jsontest.section01.api;

public class MemoDTO {

    // 필드명과 JSON key 이름이 일치해야 한다
    private int id;
    private String content;

    // Jackson이 JSON을 java객체로 만들 때 필요
    public MemoDTO() {
    }

    public MemoDTO(int id, String content) {
        this.id = id;
        this.content = content;
    }
    // getter/setter : Jackson이 필드값을 읽고 쓰는 데 사용
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
