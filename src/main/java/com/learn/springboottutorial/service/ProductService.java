package com.learn.springboottutorial.service;

import com.learn.springboottutorial.model.Product;

/**
 * @author anthonylee
 */
public interface ProductService {
    Product getProductById(Integer productId);
}
