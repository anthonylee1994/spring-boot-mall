package com.learn.springboottutorial.service.impl;

import com.learn.springboottutorial.dao.ProductDao;
import com.learn.springboottutorial.model.Product;
import com.learn.springboottutorial.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author anthonylee
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
