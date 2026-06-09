package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EntityManagerGeneratorTests {

    @Test
    @DisplayName("엔티티 매니저 생성 확인")
    void generateEntityManagerTest(){
        //given

        //when
        EntityManager entityManager = EntityManagerGenerator.getInstance();

        //then
        assertNotNull(entityManager);

    }

    @Test
    @DisplayName("엔티티 매니저 스코프 확인")
    void  testEntityManagerLifeCycle(){
        //given

        //when
        EntityManager entityManager1 = EntityManagerGenerator.getInstance();
        EntityManager entityManager2 = EntityManagerGenerator.getInstance();

        //then
        assertNotEquals(entityManager1, entityManager2);
    }

}
