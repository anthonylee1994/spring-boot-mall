package com.learn.springboottutorial.service;

import com.learn.springboottutorial.constant.ProductCategory;
import com.learn.springboottutorial.dto.ProductRequest;
import com.learn.springboottutorial.model.Product;

import java.util.List;

/**
 * @author anthonylee
 */
public interface ProductService {
    List<Product> getProducts(ProductCategory productCategory, String search);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
