package com.learn.springboottutorial.rowmapper;

import com.learn.springboottutorial.model.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author anthonylee
 */
public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setTotalAmount(rs.getInt("total_amount"));
        order.setCreatedDate(rs.getDate("created_date"));
        order.setLastModifiedDate(rs.getDate("last_modified_date"));
        return order;
    }
}
