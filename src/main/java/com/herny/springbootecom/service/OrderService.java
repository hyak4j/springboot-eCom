package com.herny.springbootecom.service;

import com.herny.springbootecom.dto.CreateOrderRequest;
import com.herny.springbootecom.dto.OrderQueryParams;
import com.herny.springbootecom.model.Order;

import java.util.List;

public interface OrderService {

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Order getOrderById(Integer orderId);

    // 建立訂單
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
}
