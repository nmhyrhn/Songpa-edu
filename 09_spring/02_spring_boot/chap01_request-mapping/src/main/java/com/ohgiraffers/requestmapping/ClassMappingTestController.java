package com.ohgiraffers.requestmapping;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
public class ClassMappingTestController {

    @GetMapping
    public ResponseEntity<Map<String, Object>> findOrders(){
        Map<String, Object> response = Map.of(
                "message", "주문 목록 조회용 핸들러 메소드 호출",
                "orders", Map.of(
                        "sampleOrderNumber", 1,
                        "sampleProductName", "샘플 상품"
                )
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<Map<String, Object>> selectOrderDetail(@PathVariable int orderNumber){

        Map<String, Object> response = Map.of(
                "message", orderNumber + " 주문 상세 내용 조회용 핸들러 메소드 호출",
                "order", Map.of(
                        "orderId", orderNumber,
                        "productName", "샘플 상품",
                        "price", 40000
                )
        );

        return ResponseEntity.ok(response);
    }





}
