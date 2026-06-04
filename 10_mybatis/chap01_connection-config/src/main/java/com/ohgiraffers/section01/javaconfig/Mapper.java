package com.ohgiraffers.section01.javaconfig;

import org.apache.ibatis.annotations.Select;

//실행할 SQL을 가진 인터페이스 (자바 메서드와 SQL을 연결해주는 약속표)
public interface Mapper {

    //@Select: SQL을 어노테이션으로 직접 메서드에 붙인다
    //메서드의 이름이 이 쿼리의 식별자(ID)역할을 한다
    @Select("SELECT CURDATE()")
    java.util.Date selectSysdate();

}
