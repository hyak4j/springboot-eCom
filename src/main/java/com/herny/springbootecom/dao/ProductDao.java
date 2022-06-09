package com.herny.springbootecom.dao;

import com.herny.springbootecom.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);
}
