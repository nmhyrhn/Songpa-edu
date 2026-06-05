package com.ohgiraffers.section01;

import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ohgiraffers.section01.Template.getSqlSession;

public class MenuService {

    public void selectMenuByPrice(int price) {
        SqlSession sqlSession = getSqlSession();

        try {

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

        try {
            DynamicSqlMapper mapper = sqlSession.getMapper(DynamicSqlMapper.class);

            printMenuList(mapper.searchMenu(searchCriteria));
        } finally {
            sqlSession.close();
        }

    }

    private void printMenuList(List<MenuDTO> menuList) {

        if(menuList == null || menuList.size() > 0){
            for(MenuDTO menu : menuList){
                System.out.println(menu);
            }
        }else {
            System.out.println("검색 결과가 존재하지 않습니다.");
        }
    }

}
