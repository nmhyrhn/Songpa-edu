package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EntityManagerFactoryGeneratorTests {

    @Test
    @DisplayName("엔티티 매니저 팩토리 생성 확인")
    void testGenerateEntityManagerFactory(){

        //given

        //when
        EntityManagerFactory factory = EntityManagerFactoryGenerator.getInstance();

        //then
        assertNotNull(factory);

    }

    @Test
    @DisplayName("엔티티 매니저 싱글톤 인스턴스 확인")
    void testIsEntityManagerFactorySingletonInstance(){

        //given

        //when
        EntityManagerFactory factory1 = EntityManagerFactoryGenerator.getInstance();
        EntityManagerFactory factory2 = EntityManagerFactoryGenerator.getInstance();

        //then
        assertEquals(factory1, factory2);

    }
}
