package com.ohgiraffers.section03.grammer;

public enum UserRole {

    //각 상수별로 고유한 데이터("설명") 정의
    GUEST("게스트"),
    CONSUMER("구매자"),
    PRODUCER("판매자"),
    ADMIN("관리자");

    //그 데이터를 저장할 필드 선언
    private final String description;

    //enum 생성자는 외부에서 new로 호출할 수 없기 때문에, 접근제한자는 자동으로 private이 된다.
    private UserRole(String description) {
        System.out.println("UserRole 생성자 호출 " + description);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getNameToLowerCase() {
        return this.name().toLowerCase();
    }

}
