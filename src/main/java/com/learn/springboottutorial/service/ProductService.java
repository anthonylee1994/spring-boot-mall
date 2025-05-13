package com.learn.springboottutorial.service;

import com.learn.springboottutorial.dto.ProductRequest;
import com.learn.springboottutorial.model.Product;

/**
 * @author anthonylee
 */
public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
