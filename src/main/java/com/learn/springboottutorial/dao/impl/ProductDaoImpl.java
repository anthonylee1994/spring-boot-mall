package com.learn.springboottutorial.dao.impl;

import com.learn.springboottutorial.dao.ProductDao;
import com.learn.springboottutorial.model.Product;
import com.learn.springboottutorial.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author anthonylee
 */
@Repository
public class ProductDaoImpl implements ProductDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProductById(Integer id) {
        String sql = "select * from mall.product where product_id = :productId";

        Map<String, Object> params = new HashMap<>();
        params.put("productId", id);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, params, new ProductRowMapper());

        if (!productList.isEmpty()) {
            return productList.getFirst();
        } else {
            return null;
        }
    }
}
