package com.ohgiraffers.springmybatis.menu.model.dao;

import com.ohgiraffers.springmybatis.menu.model.dto.MenuDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {


    List<MenuDTO> findAllMenu();
}
