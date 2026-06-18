package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuRequestDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuResponseDTO;
import com.ohgiraffers.springdatajpa.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.RemoteRef;
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

    /* 모든 카테고리 목록 조회 */
    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> findCategoryList() {
        List<CategoryDTO> categoryList = menuService.findAllCategory();

        return ResponseEntity.ok(categoryList);
    }

    /* 메뉴 등록 */
    @PostMapping
    public ResponseEntity<MenuResponseDTO> registMenu(@RequestBody MenuRequestDTO requestDTO) {

        MenuResponseDTO newMenu = menuService.registMenu(requestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(newMenu);
    }

    /* 메뉴 수정 */
    @PutMapping("/{menuCode}")
    public ResponseEntity<MenuResponseDTO> modifyMenu(
            @PathVariable int menuCode,
            @RequestBody MenuRequestDTO requestDTO
    ){
        MenuResponseDTO updatedMenu = menuService.modifyMenu(menuCode, requestDTO);

        return ResponseEntity.ok(updatedMenu);
    }

    /* 메뉴 삭제 */
    @DeleteMapping("/{menuCode}")
    public ResponseEntity<Void> deleteMenu(@PathVariable int menuCode) {

        menuService.deleteMenu(menuCode);

        return ResponseEntity.noContent().build();
    }


}