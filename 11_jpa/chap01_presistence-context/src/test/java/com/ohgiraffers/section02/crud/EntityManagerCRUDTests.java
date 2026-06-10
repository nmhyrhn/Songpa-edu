package com.ohgiraffers.section02.crud;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

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

    private static Stream<Arguments> newMenu(){
        return Stream.of(Arguments.of("신메뉴", 35000, 4, "Y"));
    }

    @DisplayName("새로운 메뉴 추가 테스트")
    @ParameterizedTest
    @MethodSource("newMenu")
    void testRegist(String menuName, int menuPrice, int categoryCode, String orderableStatus) {
        //given
        //when
        Menu newMenu = new Menu(menuName, menuPrice, categoryCode, orderableStatus);
        Long count = crud.saveAndReturnAllCount(newMenu);

        //then
        assertEquals(28, count);
    }


    @DisplayName("메뉴 이름 수정 테스트")
    @ParameterizedTest
    @CsvSource("2, 변경 된 이름")
    void testModifyMenuName(int menuCode, String menuName) {
        //when
        Menu modifyMenu = crud.modifyMenuName(menuCode, menuName);
        //then
        assertEquals(menuName, modifyMenu.getMenuName());
    }

    @DisplayName("메뉴 삭제 테스트")
    @ParameterizedTest
    @ValueSource(ints = {31})
    void testRemoveMenu(int menuCode) {
        //when
        Long count = crud.removeAndReturnAllCount(menuCode);

        //then
        assertEquals(27, count);
    }

}
