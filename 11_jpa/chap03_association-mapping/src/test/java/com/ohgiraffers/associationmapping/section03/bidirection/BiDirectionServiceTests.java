package com.ohgiraffers.associationmapping.section03.bidirection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BiDirectionServiceTests {

    @Autowired
    private BiDirectionService biDirectionService;

    @DisplayName("양방향 연관 관계 매칭 조회(연관관계 주인")
    @Test
    void bidirectionFindTest1(){
        //given
        int menuCode = 10;

        //when
        Menu foundMenu = biDirectionService.findMenu(menuCode);

        //then
        assertEquals(menuCode, foundMenu.getMenuCode());

    }

    @DisplayName("양방향 연관 관계 매칭 조회(연관관계 주인이 아님")
    @Test
    void bidirectionFindTest2(){
        //given
        int categoryCode = 10;

        //when
        Category foundCategory = biDirectionService.findCategory(categoryCode);

        //then
        assertEquals(categoryCode, foundCategory.getCategoryCode());

    }

}
