package com.herny.springbootecom.dao;

import com.herny.springbootecom.dto.ProductRequest;
import com.herny.springbootecom.model.Product;

public interface ProductDao {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
}
