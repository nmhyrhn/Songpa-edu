package com.ohgiraffers.section01.scope.prototype;

import com.ohgiraffers.common.Beverage;
import com.ohgiraffers.common.Bread;
import com.ohgiraffers.common.Product;
import com.ohgiraffers.common.ShoppingCart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Date;

public class contextConfiguration {

    @Bean
    public Product carpBread(){
        return new Bread("붕어빵", 1000, new Date());
    }

    @Bean
    public Product milk() {
        return new Beverage("딸기우유", 1500, 500);
    }

    @Bean
    public Product water(){
        return new Beverage("지리산암반수", 3000, 500);
    }

    @Bean
    @Scope("prototype") //이 Bean은 getBean()으로 요청할 때마다 새 객체를 만들어 반환한다.
    public ShoppingCart cart() {
        return new ShoppingCart();
    }

}
