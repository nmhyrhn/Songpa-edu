package com.ohgiraffers.interceptor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class MenuController {

    private final List<MenuDTO> menus = new ArrayList<>();

    public MenuController() {
        menus.add(new MenuDTO(1, "아메리카노", 3000));
        menus.add(new MenuDTO(2, "카페라떼", 3500));
        menus.add(new MenuDTO(3, "바닐라라떼", 4500));
    }

    @GetMapping("/menus")
    public ResponseEntity<List<MenuDTO>> findMenus() throws InterruptedException {
        Thread.sleep(3000);
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/menus/{menuCode}")
    public ResponseEntity<MenuDTO> findMenu(@PathVariable int menuCode) throws InterruptedException {
        Thread.sleep(2000);

        MenuDTO  foundMenu = menus.stream()
                .filter(menu -> menu.getMenuCode() == menuCode)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(menuCode + "번 메뉴가 존재하지 않음"));

        return ResponseEntity.ok(foundMenu);
    }

    @PostMapping("/admin/menus")
    public ResponseEntity<Map<String, Object>> registMenu(@RequestBody MenuDTO menu) {

        menus.add(menu);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", "관리자 메뉴 등록 성공");
        response.put("memu", menu);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }



}
