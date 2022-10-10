package com.herny.springbootecom.controller;

import com.herny.springbootecom.dto.CreateOrderRequest;
import com.herny.springbootecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    // 帳號存在下，訂單功能才有意義
    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){
        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderId);
    }
}
