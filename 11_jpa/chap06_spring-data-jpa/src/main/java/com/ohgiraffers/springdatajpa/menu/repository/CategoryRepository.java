package com.ohgiraffers.springdatajpa.menu.repository;

import com.ohgiraffers.springdatajpa.menu.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    //@Query("SELECT c FROM Category c ORDER BY c.categoryCode") //JPQL 방식
    //@Query(value = "SELECT categoryCode, category_name, ref_category_code FROM tbl_category ORDER BY category_code", nativeQuery = true)
    @NativeQuery("SELECT categoryCode, category_name, ref_category_code FROM tbl_category ORDER BY category_code")
    List<Category> findAllCategory();
}