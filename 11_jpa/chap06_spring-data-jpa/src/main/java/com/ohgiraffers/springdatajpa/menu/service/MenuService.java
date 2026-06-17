package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.MenuResponseDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor    // final 필드를 파라미터롤 받는 생성자를 자동 생성(Lombok)
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    /* 1. 메뉴 코드로 상세 조회 */
    public MenuResponseDTO findMenuByMenuCode(int menuCode) {
        Menu foundMenu = menuRepository.findById(menuCode).orElseThrow(
                () -> new IllegalArgumentException("메뉴가 존재하지 않습니다.")
        );

        // foundMenu의 값을 MenuResponseDTO에 복사해서 새 DTO 객체를 만들어 준다
        return modelMapper.map(foundMenu, MenuResponseDTO.class);
    }

    /* 전체 메뉴 목록 조회(페이징 처리) */
    public Page<MenuResponseDTO> findMenuList(Pageable pageable) {

        Page<Menu> menuList = menuRepository.findAll(pageable);

        return menuList.map(menu -> modelMapper.map(menu, MenuResponseDTO.class));
    }

    /* 가격으로 검색 (쿼리 메소드) */
    public List<MenuResponseDTO> findByMenuPrice(Integer menuPrice) {
        //쿼리 메소드 호출
        //List<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice);
        List<Menu> menuList = menuRepository.findByMenuPriceGreaterThanOrderByMenuPriceDesc(menuPrice); // 내림차순 정렬까지 추가

        return menuList.stream().map(menu -> modelMapper.map(menu, MenuResponseDTO.class))
                .collect(Collectors.toList());
    }


    /* 가격으로 검색 (페이징, 동적 정렬 조건) */
    public Page<MenuResponseDTO> findByMenuPriceSort(Integer menuPrice, Pageable pageable) {
        Page<Menu> menuList = menuRepository.findByMenuPriceGreaterThan(menuPrice, pageable);

        return menuList.map(menu -> modelMapper.map(menu, MenuResponseDTO.class));
    }


}