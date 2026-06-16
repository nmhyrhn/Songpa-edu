package com.ohgiraffers.nativequery.section01.simple;

import jakarta.persistence.*;

@SqlResultSetMappings(
        value = {
                /*Native SQL의 결과를 어떻게 매핑할지 정의하는 어노테이션*/
                @SqlResultSetMapping(
                        name = "categoryCountAutoMapping",
                        entities = {@EntityResult(entityClass = Category.class)},
                        columns = {@ColumnResult(name = "menu_count")}
                )
        }
)
@Entity(name = "Section01Category")
@Table(name = "tbl_category")
public class Category {

    @Id
    private int categoryCode;
    private String categoryName;
    private Integer refCategoryCode;

    @Override
    public String toString() {
        return "Category{" +
                "categoryCode=" + categoryCode +
                ", categoryName='" + categoryName + '\'' +
                ", refCategoryCode=" + refCategoryCode +
                '}';
    }
}
