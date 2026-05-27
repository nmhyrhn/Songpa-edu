package com.ohgiraffers.section02.initdestroy;

import com.ohgiraffers.common.Beverage;
import com.ohgiraffers.common.Bread;
import com.ohgiraffers.common.Product;
import com.ohgiraffers.common.ShoppingCart;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class ContextConfiguration {

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
    public ShoppingCart cart() {
        return new ShoppingCart();
    }


    //initMethod: 초기화 메서드(bean이 생성되고 필요한 준비가 끝난 뒤 실행되는 메서드)
    //destroyMethod: 소멸 메서드(Container가 종료되면서 Bean이 정리되기 직전에 실행되는 메서드)
    @Bean/*(initMethod = "openShop", destroyMethod = "closeShop")*/
    public Owner owner(){
        return new Owner();
    }

}
