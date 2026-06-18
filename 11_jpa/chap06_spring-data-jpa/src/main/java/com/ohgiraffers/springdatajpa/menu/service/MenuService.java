package com.ohgiraffers.springdatajpa.menu.service;

import com.ohgiraffers.springdatajpa.menu.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuRequestDTO;
import com.ohgiraffers.springdatajpa.menu.dto.MenuResponseDTO;
import com.ohgiraffers.springdatajpa.menu.entity.Category;
import com.ohgiraffers.springdatajpa.menu.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.repository.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /* 카테고리 조회 */
    public List<CategoryDTO> findAllCategory(){
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();
    }

    /* 메뉴 등록 */
    @Transactional
    public MenuResponseDTO registMenu(MenuRequestDTO requestDTO){

        Category category = categoryRepository.findById(requestDTO.getCategoryCode())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        Menu menu = Menu.builder()
                .menuName(requestDTO.getMenuName())
                .orderableStatus(requestDTO.getOrderableStatus())
                .menuPrice(requestDTO.getMenuPrice())
                .category(category)
                .build();

        //내부적으로 EntityManager.persist() 호출되어 영속성 컨텍스트로 들어간다
        Menu savedMenu = menuRepository.save(menu);

        //저장 후, 생성된 Entity를 다시 DTO로 변환하여 반환
        return modelMapper.map(savedMenu, MenuResponseDTO.class);
    }

    /* 메뉴 수정 */
    @Transactional
    public MenuResponseDTO modifyMenu(int menuCode, MenuRequestDTO requestDTO){

        Menu foundMenu = menuRepository.findById(menuCode)
                .orElseThrow(() -> new IllegalArgumentException("수정할 메뉴가 존재하지 않습니다."));

        //변경할 카테고리 조회
        Category newCategory = categoryRepository.findById(requestDTO.getCategoryCode())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리 입니다."));

        foundMenu.modify(
                requestDTO.getMenuName(),
                requestDTO.getMenuPrice(),
                newCategory,
                requestDTO.getOrderableStatus()
        );
        return modelMapper.map(foundMenu, MenuResponseDTO.class);
    }

    /* 메뉴 삭제 */
    @Transactional
    public void deleteMenu (int menuCode){

        if(!menuRepository.existsById(menuCode)){
            throw new IllegalArgumentException("삭제할 메뉴가 존재하지 않습니다.");
        }
        menuRepository.deleteById(menuCode);
    }


}