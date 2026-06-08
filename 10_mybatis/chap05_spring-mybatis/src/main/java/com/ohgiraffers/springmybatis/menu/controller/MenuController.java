package com.ohgiraffers.springmybatis.menu.controller;

import com.ohgiraffers.springmybatis.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springmybatis.menu.model.dto.MenuDTO;
import com.ohgiraffers.springmybatis.menu.service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*") // 브라우저에서 다른 주소의 api를 호출 허용 설정
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService){
        this.menuService = menuService;
    }

    @GetMapping("/menus")
    public ResponseEntity<List<MenuDTO>> findMenuList() {
        return ResponseEntity.ok(menuService.findAllMenu());
    }

    @GetMapping("menus/{menuCode}")
    public ResponseEntity<MenuDTO> findMenuByCode(@PathVariable int menuCode) {
        MenuDTO menu = menuService.findMenuByCode(menuCode);

        if(menu == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(menu);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> findCategoryList(){
        return null;
    }
}