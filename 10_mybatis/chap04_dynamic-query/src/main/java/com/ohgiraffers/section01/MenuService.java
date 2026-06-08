package com.ohgiraffers.section01;

import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ohgiraffers.section01.Template.getSqlSession;

public class MenuService {

    public void selectMenuByPrice(int price) {
        SqlSession sqlSession = getSqlSession();

        try{
            DynamicSqlMapper mapper = sqlSession.getMapper(DynamicSqlMapper.class);

            Map<String, Integer> map = new HashMap<>();
            map.put("price", price);

            printMenuList(mapper.selectMenuByPrice(map));
        } finally {
            sqlSession.close();
        }
    }

    public void searchMenu(SearchCriteria searchCriteria) {

        SqlSession sqlSession = getSqlSession();

        try{
            DynamicSqlMapper mapper = sqlSession.getMapper(DynamicSqlMapper.class);
            printMenuList(mapper.searchMenu(searchCriteria));
        } finally {
            sqlSession.close();
        }
    }

    private void printMenuList(List<MenuDTO> menuList) {

        if(menuList != null && menuList.size() > 0) {
            for(MenuDTO menu : menuList) {
                System.out.println(menu);
            }
        } else {
            System.out.println("검색 결과가 존재하지 않습니다.");
        }
    }

    public void searchMenuBySupCategory(SearchCriteria searchCriteria) {
        SqlSession sqlSession = getSqlSession();

        try {
            DynamicSqlMapper mapper = sqlSession.getMapper(DynamicSqlMapper.class);
            printMenuList(mapper.searchMenuBySupCategory(searchCriteria));
        } finally {
            sqlSession.close();
        }
    }

    public void searchMenuByRandomMenuCode(List<Integer> randomMenuCodeList) {

        SqlSession sqlSession = getSqlSession();

        try{
            DynamicSqlMapper mapper = sqlSession.getMapper(DynamicSqlMapper.class);

            Map<String, List<Integer>> criteria = new HashMap<>();
            // Map의 Key 이름 = foreach의 collection 이름
            criteria.put("randomMenuCodeList", randomMenuCodeList);

            printMenuList(mapper.searchMenuByRandomMenuCode(criteria));
        } finally {
            sqlSession.close();
        }
    }

    public void searchMenuByCodeOrSearchAll(SearchCriteria searchCriteria) {

        SqlSession sqlSession = getSqlSession();

        try{
            DynamicSqlMapper mapper = sqlSession.getMapper(DynamicSqlMapper.class);
            printMenuList(mapper.searchMenuByCodeOrSearchAll(searchCriteria));
        } finally {
            sqlSession.close();
        }
    }

    public void searchMenuByNameOrCategory(Map<String, Object> criteria) {

        SqlSession sqlSession = getSqlSession();

        try{
            DynamicSqlMapper mapper = sqlSession.getMapper(DynamicSqlMapper.class);
            printMenuList(mapper.searchMenuByNameOrCategory(criteria));
        } finally {
            sqlSession.close();
        }
    }

    public void modifyMenu(Map<String, Object> criteria) {
        SqlSession sqlSession = getSqlSession();

        try{
            DynamicSqlMapper mapper = sqlSession.getMapper(DynamicSqlMapper.class);
            int result = mapper.modifyMenu(criteria);

            if(result > 0) {
                System.out.println("메뉴 정보 변경 성공");
                sqlSession.commit();
            } else {
                System.out.println("메뉴 정보 변경 실패");
                sqlSession.rollback();
            }
        } catch (RuntimeException e) {
            sqlSession.rollback();
            throw e;
        } finally {
            sqlSession.close();
        }
    }
}