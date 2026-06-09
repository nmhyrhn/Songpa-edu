package com.ohgiraffers.springmybatis.menu.model.dao;

import com.ohgiraffers.springmybatis.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springmybatis.menu.model.dto.MenuDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Mapper
public interface MenuMapper {

    List<MenuDTO> findAllMenu();

    MenuDTO findMenuByCode(@Param("menuCode") int menuCode);

    List<CategoryDTO> findAllCategory();

    int registMenu(MenuDTO newMenu);

    int modifyMenu(MenuDTO menu);

    int deleteMenu(@Param("menuCode") int menuCode);
}