package com.ohgiraffers.section03.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class EntityLifeCycleTests {

    private EntityLifeCycle lifeCycle;

    @BeforeEach //테스트 전에 수행
    void setUp(){
        this.lifeCycle = new EntityLifeCycle();
    }

    @DisplayName("비영속 테스트")
    @ParameterizedTest
    @ValueSource(ints = {2})
    void testTransient(int menuCode){
        //when
        Menu foundMenu = lifeCycle.findMenuByMenuCode(menuCode);
        Menu newMenu = new Menu(
                foundMenu.getMenuCode(),
                foundMenu.getMenuName(),
                foundMenu.getMenuPrice(),
                foundMenu.getCategoryCode(),
                foundMenu.getOrderableStatus()
        );

        EntityManager entityManager = lifeCycle.getManagerInstance();

        //then
        assertNotEquals(foundMenu, newMenu);
        assertTrue(entityManager.contains(foundMenu));
        assertFalse(entityManager.contains(newMenu));

    }

    @DisplayName("다른 엔티티 매니저가 관리하는 엔티티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints = {3})
    void testManagedOtherEntityManager(int menuCode){

        //when
        Menu menu1 = lifeCycle.findMenuByMenuCode(menuCode);
        Menu menu2 = lifeCycle.findMenuByMenuCode(menuCode);
        //then
        assertNotEquals(menu1, menu2);

    }

    @DisplayName("같은 엔티티 매니저가 관리하는 엔티티의 영속성 테스트")
    @ParameterizedTest
    @ValueSource(ints = {3})
    void testManageSameEntityManager(int menuCode){

        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();

        //when
        Menu menu1 = entityManager.find(Menu.class, menuCode);
        Menu menu2 = entityManager.find(Menu.class, menuCode);
        //then
        assertEquals(menu1, menu2);

    }

    @DisplayName("준영속화 detach 테스트")
    @ParameterizedTest
    @CsvSource("11, 1000")
    void testDetachEntity(int menuCode, int menuPrice){
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        //when
        entityTransaction.begin();
        Menu foudMenu = entityManager.find(Menu.class, menuCode);
        //detach: 특정 엔티티만 준영속 상태(영속성 컨텍스트가 관리하던 객체를 관리하지 않는 상태)로 만든다.
        entityManager.detach(foudMenu);
        foudMenu.setMenuPrice(menuPrice);
        //flush: 영속성 컨텍스트의 상태를 DB에 내보낸다. commit 하지 않은 상태이므로 rollback 가능
        entityManager.flush();

        //then
        assertNotEquals(menuPrice, entityManager.find(Menu.class, menuCode).getMenuPrice());
        entityTransaction.rollback();
    }

    @DisplayName("준영속화 detach 후 다시 영속화 테스트")
    @ParameterizedTest
    @CsvSource("11, 1000")
    void testDetachAndMerge(int menuCode, int menuPrice){
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        //when
        entityTransaction.begin();
        Menu foudMenu = entityManager.find(Menu.class, menuCode);
        //detach: 특정 엔티티만 준영속 상태(영속성 컨텍스트가 관리하던 객체를 관리하지 않는 상태)로 만든다.
        entityManager.detach(foudMenu);
        foudMenu.setMenuPrice(menuPrice);

        entityManager.merge(foudMenu);
        entityManager.flush();

        //then
        assertEquals(menuPrice, entityManager.find(Menu.class, menuCode).getMenuPrice());
        entityTransaction.rollback();
    }

    @DisplayName("detach후 merge한 데이터 update 테스트")
    @ParameterizedTest
    @CsvSource("11, 전복죽")
    void testDetachAneMerge(int menuCode, String menuName){
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);
        entityManager.detach(foundMenu);

        //when
        foundMenu.setMenuName(menuName);
        Menu refoundMenu = entityManager.find(Menu.class, menuCode);

        entityManager.merge(foundMenu);

        //then
        assertEquals(menuName, refoundMenu.getMenuName());
    }

    @DisplayName("준영속화 clear 테스트")
    @ParameterizedTest
    @ValueSource(ints = {6})
    void testClearPersistenceContext(int menuCode){
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        //when
        //clear: 영속성 컨텍스트 초기화 한다 -> 영속성 컨텍스트 내의 모든 엔티티를 준영속
        entityManager.clear();

        //then
        Menu expectedMenu = entityManager.find(Menu.class, menuCode);

        assertNotEquals(foundMenu, expectedMenu);
    }

    @DisplayName("준영속화 close 테스트")
    @ParameterizedTest
    @ValueSource(ints = {6})
    void testClosePersistenceContext(int menuCode){
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        //when
        //close: 영속성 컨텍스트를 종룔한다.
        entityManager.close();

        //then
        assertThrows(
                IllegalStateException.class,
                () -> entityManager.find(Menu.class, menuCode)
        );
    }

    @DisplayName("엔티티 remove 테스트")
    @ParameterizedTest
    @ValueSource(ints = {6})
    void testRemoveEntity(int menuCode){
        //given
        EntityManager entityManager = EntityManagerGenerator.getInstance();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        Menu foundMenu = entityManager.find(Menu.class, menuCode);

        //when
        entityTransaction.begin();
        //remove: 엔티티를 영속성 컨텍스르 및 데이터베이스에서 삭제
        //단, 트랜잭션을 제어하지 않으면 DB에 영구 반영되지는 않음
        //커밋하는 순간 영속성 컨텍스트에서 관리하는 엔티티 객체가 데이터베이스에 반영된다.
        entityManager.remove(foundMenu);

        entityManager.flush(); //영속성 컨텍스트 내용을 DB에 동기화 하는 작업

        //then
        Menu refoundMenu = entityManager.find(Menu.class, menuCode);
        assertNull(refoundMenu);
        entityTransaction.rollback();
    }

}
