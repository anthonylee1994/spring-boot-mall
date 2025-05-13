package com.learn.springboottutorial.service.impl;

import com.learn.springboottutorial.constant.ProductCategory;
import com.learn.springboottutorial.dao.ProductDao;
import com.learn.springboottutorial.dto.ProductRequest;
import com.learn.springboottutorial.model.Product;
import com.learn.springboottutorial.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author anthonylee
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductCategory productCategory, String search) {
        return productDao.getProducts(productCategory, search);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {
        productDao.deleteProductById(productId);
    }
}
