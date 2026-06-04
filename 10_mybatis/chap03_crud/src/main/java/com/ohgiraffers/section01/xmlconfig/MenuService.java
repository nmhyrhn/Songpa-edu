package com.ohgiraffers.section01.xmlconfig;

import org.apache.ibatis.session.SqlSession;

import java.util.List;

import static com.ohgiraffers.section01.xmlconfig.Template.getSqlSession;

public class MenuService {

    private final MenuDAO menuDAO;

    public MenuService(){
        menuDAO = new MenuDAO();
    }


    //조회: 트랜잭션 불필요
    public List<MenuDTO> selectAllMenu(){
        SqlSession sqlSession = getSqlSession();

        try{
            return menuDAO.selectAllMenu(sqlSession);
        } finally {
            sqlSession.close();
        }
    }

    public MenuDTO selectMenuByCode(int code){
        SqlSession sqlSession = getSqlSession();

        try {
            return menuDAO.selectMenuByCode(sqlSession, code);
        } finally {
            sqlSession.close();
        }
    }


    public boolean registMenu(MenuDTO menuDTO){
        SqlSession sqlSession = getSqlSession();

        try {
            int reuslt = menuDAO.insertMenu(sqlSession, menuDTO);

            if(reuslt > 0) sqlSession.commit(); //성공 -> DB 영구 반영
            else sqlSession.rollback(); //실패 -> 원상 복구

            return reuslt > 0;

        } catch (RuntimeException e) {
            sqlSession.rollback();
            throw e;
        } finally {
            sqlSession.close();
        }
    }




}
