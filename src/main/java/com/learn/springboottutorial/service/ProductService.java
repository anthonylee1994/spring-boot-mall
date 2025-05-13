package com.learn.springboottutorial.service;

import com.learn.springboottutorial.dto.ProductRequest;
import com.learn.springboottutorial.model.Product;
import jakarta.validation.Valid;

/**
 * @author anthonylee
 */
public interface ProductService {
    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
