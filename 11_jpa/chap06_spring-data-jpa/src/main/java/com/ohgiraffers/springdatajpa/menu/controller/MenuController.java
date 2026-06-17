package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.menu.dto.MenuResponseDTO;
import com.ohgiraffers.springdatajpa.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/{menuCode}")
    public ResponseEntity<MenuResponseDTO> findMenuByCode(@PathVariable int menuCode) {
        MenuResponseDTO resultMenu = menuService.findMenuByMenuCode(menuCode);

        return ResponseEntity.ok(resultMenu);
    }

    @GetMapping
    public ResponseEntity<Page<MenuResponseDTO>> findMenuList(
            @PageableDefault(size = 30, sort = "menuCode") Pageable pageable
            ) {
        Page<MenuResponseDTO> menuList = menuService.findMenuList(pageable);

        return ResponseEntity.ok(menuList);
    }

    /* 메뉴 가격으로 메뉴 목록 조회 */
//    @GetMapping("/search")
//    public ResponseEntity<List<MenuResponseDTO>> findByMenuPrice(@RequestParam Integer menuPrice) {
//        List<MenuResponseDTO> menuList = menuService.findByMenuPrice(menuPrice);
//
//        return ResponseEntity.ok(menuList);
//    }

    /* 메뉴가격으로 메뉴 목록 조회(페이징 적용) */
    @GetMapping("/search")
    public ResponseEntity<Page<MenuResponseDTO>> findByMenuPrice(@RequestParam Integer menuPrice, Pageable pageable) {
        Page<MenuResponseDTO> menuList = menuService.findByMenuPriceSort(menuPrice, pageable);

        return ResponseEntity.ok(menuList);
    }


}