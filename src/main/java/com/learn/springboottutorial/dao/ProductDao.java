package com.learn.springboottutorial.dao;

import com.learn.springboottutorial.dto.ProductQueryParams;
import com.learn.springboottutorial.dto.ProductRequest;
import com.learn.springboottutorial.model.Product;

import java.util.List;

/**
 * @author anthonylee
 */
public interface ProductDao {
    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer id);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    Integer countProduct(ProductQueryParams productQueryParams);
}
