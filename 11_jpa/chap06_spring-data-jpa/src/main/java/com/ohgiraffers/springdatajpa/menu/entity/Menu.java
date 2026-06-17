package com.ohgiraffers.springdatajpa.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tbl_menu")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuCode;
    private String menuName;
    private int menuPrice;

    @ManyToOne
    @JoinColumn(name="category_code")
    private Category category;
    private String orderableStatus;

    public void modify(String menuName, int menuPrice, Category category, String orderableStatus) {
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.category = category;
        this.orderableStatus = orderableStatus;
    }

    public void modifyMenuPrice(int menuPrice){
        this.menuPrice = menuPrice;
    }

    public void modifyMenuName(String menuName){
        this.menuName = menuName;
    }
}