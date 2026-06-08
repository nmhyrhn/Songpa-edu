package com.ohgiraffers.section03.remix;

import java.util.List;

public interface MenuMapper {

    /*
    * MenuMapper.java -> MenuMapper.xml 연결 규칙
    * - XNL이 인터페이스와 동일한 패키지 경로에 위치해야 한다.
    * - XML의 namespace = 인터페이스의 풀 클래스명
    * - XML의 파일명 = 인터페이스 파일명과 일치
    * - XML의 id = 인터페이스의 메서드명과 일치
    * */


    List<MenuDTO> selectAllMenu();

    MenuDTO selectMenuByCode(int code);

    int insertMenu(MenuDTO menu);

    int updateMenu(MenuDTO menu);

    int deleteMenu(int code);
}