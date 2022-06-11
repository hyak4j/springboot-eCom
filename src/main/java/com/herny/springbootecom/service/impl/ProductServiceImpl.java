package com.herny.springbootecom.service.impl;

import com.herny.springbootecom.dao.ProductDao;
import com.herny.springbootecom.dto.ProductRequest;
import com.herny.springbootecom.model.Product;
import com.herny.springbootecom.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }
}
