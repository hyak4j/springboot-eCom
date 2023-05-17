package com.herny.springbootecom.service;

import com.herny.springbootecom.dto.CreateOrderRequest;
import com.herny.springbootecom.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    // 建立訂單
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
