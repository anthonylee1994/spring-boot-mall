package com.learn.springboottutorial.service;

import com.learn.springboottutorial.dto.ProductRequest;
import com.learn.springboottutorial.model.Product;

import java.util.List;

/**
 * @author anthonylee
 */
public interface ProductService {
    List<Product> getProducts();

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
