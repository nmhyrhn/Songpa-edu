package com.ohgiraffers.section02.crud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityManagerCRUDTests {

    private EntityManagerCRUD crud;

    @BeforeEach
    void initManager() {
        this.crud = new EntityManagerCRUD();
    }

    @ParameterizedTest
    @DisplayName("메뉴 코드로 메뉴 조회 테스트")
    @CsvSource({"2, 2", "3, 3"})
    void testFindMenuByMenuCode(int menuCode, int expected) {
        //given

        //when
        Menu foundMenu = crud.findMenuByMenuCode(menuCode);

        //then
        assertEquals(expected, foundMenu.getMenuCode());
        System.out.println("foundMenu: " + foundMenu);
    }

}
