package com.ohgiraffers.jpql.section01.simple;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SimpleJPQLRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public String selectSingleMenuByTypedQuery(){
        String jpql = "SELECT m.menuName FROM Section01Menu as m WHERE m.menuCode = 8";
        TypedQuery<String> query = entityManager.createQuery(jpql, String.class);
        String resultMenuName = query.getSingleResult();

        return resultMenuName;
    }

    public List<Menu> selectMultiRowByTypedQuery(){
        String jpql = "SELECT m FROM Section01Menu as m ";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);
        List<Menu> resultMenuList = query.getResultList();

        return resultMenuList;
    }

    public List<Integer> selectUsingDistinct(){
        String jpql = "SELECT DISTINCT m.categoryCode FROM Section01Menu m";
        TypedQuery<Integer> query = entityManager.createQuery(jpql, Integer.class);
        List<Integer> resultCategoryList = query.getResultList();

        return resultCategoryList;
    }

    /* 11, 12 카테고리 코드를 가진 메뉴 목록 조회 (IN 연산자 사용) */
    public List<Menu> selectUsingIn(){
        String jpql = "SELECT m FROM Section01Menu as m WHERE m.categoryCode IN (11, 12)";
        TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);

        //return entityManager.createQuery(jpql, Menu.class).getResultList(); //더 간단한 방법
        return  query.getResultList();
    }


    /* "마늘" 이라는 문자열이 메뉴명에 포함되는 메뉴 목록 조회(LIKE 사용) */
    public List<Menu> selectUsingLike(){
            String jpql = "SELECT m FROM Section01Menu as m WHERE m.menuName LIKE '%마늘%'";
            TypedQuery<Menu> query = entityManager.createQuery(jpql, Menu.class);

        return query.getResultList();
    }

}
