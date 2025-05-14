package com.learn.springboottutorial.dao;

import com.learn.springboottutorial.dto.OrderQueryParams;
import com.learn.springboottutorial.model.Order;
import com.learn.springboottutorial.model.OrderItem;

import java.util.List;

/**
 * @author anthonylee
 */
public interface OrderDao {
    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Integer countOrder(OrderQueryParams orderQueryParams);

    List<Order> getOrders(OrderQueryParams orderQueryParams);
}
