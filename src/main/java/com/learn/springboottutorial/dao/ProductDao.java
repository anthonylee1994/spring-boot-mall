package com.learn.springboottutorial.dao;

import com.learn.springboottutorial.dto.ProductRequest;
import com.learn.springboottutorial.model.Product;

/**
 * @author anthonylee
 */
public interface ProductDao {
    Product getProductById(Integer id);

    Integer createProduct(ProductRequest productRequest);
}
