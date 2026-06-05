package com.ohgiraffers.section02.javaconfig;

import org.apache.ibatis.annotations.Property;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuMapper {

    @Results(id = "menuResultMap", value = {
            @Result(id = true, property = "code", column = "menu_code"),
            @Result(property = "name", column = "menu_name"),
            @Result(property = "price", column = "menu_price"),
            @Result(property = "categoryCode", column = "category_code"),
            @Result(property = "orderableStatus", column = "orderable_status")
    })
    @Select("SELECT menu_code, menu_name, menu_price, category_code, orderable_status " +
            "FROM tbl_menu " +
            "WHERE orderable_status = 'Y' " +
            "ORDER BY menu_code")
    List<MenuDTO> selectAllMenu();


}
