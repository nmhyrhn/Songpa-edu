package com.ohgiraffers.section02.crud;

import com.ohgiraffers.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;

public class EntityManagerCRUD {

    private EntityManager entityManager;

    /* 특정 메뉴 코드로 메뉴 조회 */
    public Menu findMenuByMenuCode(int menuCode){
        entityManager = EntityManagerGenerator.getInstance();
        // find(엔티티타입, PK)
        return entityManager.find(Menu.class, menuCode);
    }

}
