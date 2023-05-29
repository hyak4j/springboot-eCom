package com.herny.springbootecom.service.impl;

import com.herny.springbootecom.dao.OrderDao;
import com.herny.springbootecom.dao.ProductDao;
import com.herny.springbootecom.dao.UserDao;
import com.herny.springbootecom.dto.BuyItem;
import com.herny.springbootecom.dto.CreateOrderRequest;
import com.herny.springbootecom.model.Order;
import com.herny.springbootecom.model.OrderItem;
import com.herny.springbootecom.model.Product;
import com.herny.springbootecom.model.User;
import com.herny.springbootecom.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    // 注入多個Dao方法
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Order getOrderById(Integer orderId){
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        //合併數據 orderItemList => order
        order.setOrderItemList(orderItemList);

        return order;
    }

    // 萬一執行過程出現Exception  Transactional可還原資料庫變更
    // (兩資料庫同時發生，或同時不發生; 避免數據不一致)
    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {
        // 先檢查user是否存在
        User user = userDao.getUserById(userId);

        if (user == null){
            log.warn("此 userId {} 不存在", userId);
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem : createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductById(buyItem.getProductId());

            // 檢查 產品是否存在、庫存是否足夠
            if (product == null){
                log.warn("商品 {} 不存在", buyItem.getProductId());
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }else if (product.getStock() < buyItem.getQuantity()){
                log.warn("商品 {} 庫存數不足，無法購買。剩餘庫存 {}， 欲購買數量 {}",
                        buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw  new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 扣除商品庫存
            productDao.updateStock(product.getProductId(), product.getStock() - buyItem.getQuantity());

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
