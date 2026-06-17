package com.ohgiraffers.springdatajpa.menu.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MenuResponseDTO {

    private int menuCode;
    private String menuName;
    private int menuPrice;
    private String orderableStatus;
    private CategoryDTO category;
}