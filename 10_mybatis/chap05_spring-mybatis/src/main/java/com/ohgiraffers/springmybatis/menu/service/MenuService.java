package com.ohgiraffers.springmybatis.menu.service;

import com.ohgiraffers.springmybatis.menu.model.dao.MenuMapper;
import com.ohgiraffers.springmybatis.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springmybatis.menu.model.dto.MenuDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;

@Service
public class MenuService {

    private final MenuMapper menuMapper;

    public MenuService(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    public List<MenuDTO> findAllMenu() {
        return menuMapper.findAllMenu();
    }

    public MenuDTO findMenuByCode(int menuCode) {
        return menuMapper.findMenuByCode(menuCode);
    }

    public @Nullable List<CategoryDTO> findAllCategory() {
        return menuMapper.findAllCategory();
    }

    @Transactional
    public MenuDTO registNewMenu(MenuDTO newMenu) {
        menuMapper.registMenu(newMenu);
        return newMenu;
    }

    @Transactional
    public boolean modifyMenu(MenuDTO menu) {
        return menuMapper.modifyMenu(menu) > 0;
    }

    public boolean deleteMenu(int menuCode) {
        return menuMapper.deleteMenu(menuCode) > 0;
    }
}