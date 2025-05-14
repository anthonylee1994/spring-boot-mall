package com.learn.springboottutorial.controller;

import com.learn.springboottutorial.dto.CreateOrderRequest;
import com.learn.springboottutorial.dto.OrderQueryParams;
import com.learn.springboottutorial.model.Order;
import com.learn.springboottutorial.service.OrderService;
import com.learn.springboottutorial.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author anthonylee
 */
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrderById(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "10") @Max(1000) @Min(0) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ) {
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setUserId(userId);
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);

        List<Order> orderList = orderService.getOrders(orderQueryParams);
        Integer count = orderService.countOrder(orderQueryParams);

        Page<Order> page = new Page<>();
        page.setTotal(count);
        page.setLimit(limit);
        page.setOffset(offset);
        page.setResults(orderList);

        return ResponseEntity.ok(page);
    }

    @PostMapping("/users/{userId}/orders")
    public ResponseEntity<Order> createOrder(@PathVariable Integer userId, @RequestBody @Valid CreateOrderRequest createOrderRequest) {
        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
