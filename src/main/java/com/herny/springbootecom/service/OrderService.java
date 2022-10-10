package com.herny.springbootecom.service;

import com.herny.springbootecom.dto.CreateOrderRequest;

public interface OrderService {

    // 建立訂單
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
