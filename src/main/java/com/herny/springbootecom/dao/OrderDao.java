package com.herny.springbootecom.dao;

import com.herny.springbootecom.model.OrderItem;

import java.util.List;

public interface OrderDao {

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);
}
