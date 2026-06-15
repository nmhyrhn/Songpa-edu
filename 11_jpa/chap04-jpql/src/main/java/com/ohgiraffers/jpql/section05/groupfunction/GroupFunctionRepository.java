package com.ohgiraffers.jpql.section05.groupfunction;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupFunctionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public long countMenuOfCategory(int categoryCode) {
        String jpql = "SELECT COUNT(m.menuCode) FROM Section05Menu m WHERE m.categoryCode = :categoryCode";

        long countMenu = entityManager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categoryCode)
                .getSingleResult();

        return countMenu;
    }

    public Long otherWithNoResult(int categoryCode) {
        String jpql = "SELECT SUM(m.menuPrice) FROM Section05Menu m WHERE m.categoryCode = :categoryCode";

        Long sumOfMenu = entityManager.createQuery(jpql, Long.class)
                .setParameter("categoryCode", categoryCode)
                .getSingleResult();

        return sumOfMenu;
    }

    public List<Object[]> selectByGroupByHaving(long minPrice) {
        String jpql = "SELECT m.categoryCode, SUM(m.menuPrice) FROM Section05Menu m "
                + "GROUP BY m.categoryCode HAVING SUM(m.menuPrice) >= :minPrice";

        List<Object[]> sumPriceOfCategoryList = entityManager.createQuery(jpql)
                .setParameter("minPrice", minPrice)
                .getResultList();

        return sumPriceOfCategoryList;
    }


}
