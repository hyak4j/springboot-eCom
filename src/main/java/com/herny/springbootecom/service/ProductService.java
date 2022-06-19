package com.herny.springbootecom.service;

import com.herny.springbootecom.constant.ProductCategory;
import com.herny.springbootecom.dao.ProductQueryParams;
import com.herny.springbootecom.dto.ProductRequest;
import com.herny.springbootecom.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);
}
