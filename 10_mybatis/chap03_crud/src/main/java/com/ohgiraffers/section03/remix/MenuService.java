package com.ohgiraffers.section03.remix;


import org.apache.ibatis.session.SqlSession;

import java.awt.*;
import java.util.List;

import static com.ohgiraffers.section03.remix.Template.getSqlSession;

public class MenuService {


    // 조회 : 트랜잭션 불필요
    public List<MenuDTO> selectAllMenu() {

        SqlSession sqlSession = getSqlSession();

        try {
            MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);
            return menuMapper.selectAllMenu();
        } finally {
            sqlSession.close();
        }
    }

    public MenuDTO selectMenuByCode(int code) {

        SqlSession sqlSession = getSqlSession();

        try{
            MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);
            return menuMapper.selectMenuByCode(code);
        } finally {
            sqlSession.close();
        }
    }

    public boolean registMenu(MenuDTO menu){
        SqlSession sqlSession = getSqlSession();

        try{
            MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);
            int result = menuMapper.insertMenu(menu);
            if (result > 0) {
                sqlSession.commit();         // 성공 -> DB 영구 반영
            } else {
                sqlSession.rollback();       // 실패 -> 원상 복구
            }
            return result > 0;
        } catch (RuntimeException e) {
            sqlSession.rollback();
            throw e;
        } finally {
            sqlSession.close();
        }
    }

    public boolean updateMenu(MenuDTO menu){
        SqlSession sqlSession = getSqlSession();

        try{
            MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);
            int result = menuMapper.updateMenu(menu);
            if (result > 0) {
                sqlSession.commit();         // 성공 -> DB 영구 반영
            } else {
                sqlSession.rollback();       // 실패 -> 원상 복구
            }
            return result > 0;
        } catch (RuntimeException e) {
            sqlSession.rollback();
            throw e;
        } finally {
            sqlSession.close();
        }
    }
    public boolean deleteMenu(int code){
        SqlSession sqlSession = getSqlSession();

        try{
            MenuMapper menuMapper = sqlSession.getMapper(MenuMapper.class);
            int result = menuMapper.deleteMenu(code);
            if (result > 0) {
                sqlSession.commit();         // 성공 -> DB 영구 반영
            } else {
                sqlSession.rollback();       // 실패 -> 원상 복구
            }
            return result > 0;
        } catch (RuntimeException e) {
            sqlSession.rollback();
            throw e;
        } finally {
            sqlSession.close();
        }
    }

}