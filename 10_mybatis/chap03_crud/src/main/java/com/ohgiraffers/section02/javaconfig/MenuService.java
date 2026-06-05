package com.ohgiraffers.section02.javaconfig;

import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.ohgiraffers.section02.javaconfig.Template.getSqlSession;

public class MenuService {

    public List<MenuDTO> selectAllMenu() {
        SqlSession sqlSession = getSqlSession();

        try{
            MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);

            return menuMapper.selectAllMenu();
        } finally {
            sqlSession.close();
        }
    }

}
