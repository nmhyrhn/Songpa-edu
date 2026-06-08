package com.ohgiraffers.springmybatis.menu.service;

import com.ohgiraffers.springmybatis.menu.model.dao.MenuMapper;
import com.ohgiraffers.springmybatis.menu.model.dto.MenuDTO;
import org.springframework.stereotype.Service;

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
}