package com.herny.springbootecom.service;

import com.herny.springbootecom.dto.ProductRequest;
import com.herny.springbootecom.model.Product;

public interface ProductService {

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
