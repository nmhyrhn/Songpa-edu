package com.ohgiraffers.associationmapping.section01.manytoone;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ManyToOneRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Menu find(int MenuCode) {
        return entityManager.find(Menu.class, MenuCode);
    }

    public String findCategoryName(int menuCode) {
        String jpql = "SELECT c.categoryName FROM menu_and_category m JOIN m.category c WHERE m.menuCode = :menuCode";

        return entityManager.createQuery(jpql, String.class)
                .setParameter("menuCode", menuCode).getSingleResult().toString();
    }

    public void regist(Menu menu) {
        entityManager.persist(menu);
    }

}
