package com.herny.springbootecom.service.impl;

import com.herny.springbootecom.dao.OrderDao;
import com.herny.springbootecom.dao.ProductDao;
import com.herny.springbootecom.dto.BuyItem;
import com.herny.springbootecom.dto.CreateOrderRequest;
import com.herny.springbootecom.model.OrderItem;
import com.herny.springbootecom.model.Product;
import com.herny.springbootecom.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    // 萬一執行過程出現Exception  Transactional可還原資料庫變更
    // (兩資料庫同時發生，或同時不發生; 避免數據不一致)
    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            //計算總價
            int amount = buyItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            //轉換 BuyItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        //創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
