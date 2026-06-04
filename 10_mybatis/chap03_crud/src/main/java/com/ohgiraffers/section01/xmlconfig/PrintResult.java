package com.ohgiraffers.section01.xmlconfig;

import java.util.List;

public class PrintResult {

    public void printMenuList(List<MenuDTO> menuList){
        menuList.forEach(System.out::println); //MenuDTO.toString() 출력
    }

    public void printMenu(MenuDTO menu) {
        System.out.println(menu);
    }

    public void printSuccessMessage(String successCode) {

        String successMessage = "";
        switch(successCode){
            case "insert": successMessage = "신규 메뉴 등록 성공" ; break;
            case "update": successMessage = "신규 수정 성공" ; break;
            case "delete": successMessage = "신규 삭제 성공" ; break;
        }

        System.out.println(successMessage);
    }

    public void printErrorMessage(String errorCode) {

        String errorMessage = "";
        switch(errorCode){
            case "insert": errorMessage = "메뉴 등록 실패" ; break;
            case "update": errorMessage = "신규 수정 실패" ; break;
            case "delete": errorMessage = "신규 삭제 실패" ; break;
        }

        System.out.println(errorMessage);
    }
}
