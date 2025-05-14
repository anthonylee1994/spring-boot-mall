package com.learn.springboottutorial.dao.impl;

import com.learn.springboottutorial.dao.OrderDao;
import com.learn.springboottutorial.dto.OrderQueryParams;
import com.learn.springboottutorial.model.Order;
import com.learn.springboottutorial.model.OrderItem;
import com.learn.springboottutorial.rowmapper.OrderItemRowMapper;
import com.learn.springboottutorial.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author anthonylee
 */
@Repository
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = "select * from `order` where order_id = :orderId";
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        List<Order> orderList = namedParameterJdbcTemplate.query(sql, params, new OrderRowMapper());

        if (orderList.isEmpty()) {
            return null;
        } else {
            return orderList.getFirst();
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {
        String sql = """
                SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, p.product_name, p.image_url
                FROM order_item as oi
                         LEFT JOIN product as p ON oi.product_id = p.product_id
                WHERE oi.order_id = :orderId
                """;
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);

        return namedParameterJdbcTemplate.query(sql, params, new OrderItemRowMapper());
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        String sql = "select count(*) from `order` where 1=1";
        Map<String, Object> params = new HashMap<>();
        sql = addFilteringSql(sql, params, orderQueryParams);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Integer.class);

    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        String sql = "select * from `order` where 1=1";
        Map<String, Object> params = new HashMap<>();
        sql = addFilteringSql(sql, params, orderQueryParams);
        sql += " ORDER BY created_date DESC";
        sql += " LIMIT :limit OFFSET :offset";
        params.put("limit", orderQueryParams.getLimit());
        params.put("offset", orderQueryParams.getOffset());

        return namedParameterJdbcTemplate.query(sql, params, new OrderRowMapper());
    }

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {
        String sql = "INSERT INTO `order` (user_id, total_amount, created_date, last_modified_date) VALUES (:userId, :totalAmount, :createdDate, :lastModifiedDate)";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("totalAmount", totalAmount);

        Date now = new Date();
        params.put("createdDate", now);
        params.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder);

        return Objects.requireNonNull(keyHolder.getKey()).intValue();
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {
        String sql = "INSERT INTO order_item (order_id, product_id, quantity, amount) VALUES (:orderId, :productId, :quantity, :amount)";

        MapSqlParameterSource[] parameterSources = new MapSqlParameterSource[orderItemList.size()];

        for (int i = 0; i < orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);
            parameterSources[i] = new MapSqlParameterSource();
            parameterSources[i].addValue("orderId", orderId);
            parameterSources[i].addValue("productId", orderItem.getProductId());
            parameterSources[i].addValue("quantity", orderItem.getQuantity());
            parameterSources[i].addValue("amount", orderItem.getAmount());
        }

        namedParameterJdbcTemplate.batchUpdate(sql, parameterSources);
    }

    private String addFilteringSql(String sql, Map<String, Object> params, OrderQueryParams orderQueryParams) {
        if (orderQueryParams.getUserId() != null) {
            sql += " and user_id = :userId";
            params.put("userId", orderQueryParams.getUserId());
        }

        return sql;
    }
}
