package com.learn.springboottutorial.service;

import com.learn.springboottutorial.dto.CreateOrderRequest;
import com.learn.springboottutorial.model.Order;

/**
 * @author anthonylee
 */
public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);
}
