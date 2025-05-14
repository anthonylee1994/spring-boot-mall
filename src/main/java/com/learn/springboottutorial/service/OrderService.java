package com.learn.springboottutorial.service;

import com.learn.springboottutorial.dto.CreateOrderRequest;
import com.learn.springboottutorial.dto.OrderQueryParams;
import com.learn.springboottutorial.model.Order;

import java.util.List;

/**
 * @author anthonylee
 */
public interface OrderService {
    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);

    Order getOrderById(Integer orderId);

    List<Order> getOrders(OrderQueryParams orderQueryParams);

    Integer countOrder(OrderQueryParams orderQueryParams);
}
