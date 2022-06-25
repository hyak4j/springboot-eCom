package com.herny.springbootecom.controller;

import com.herny.springbootecom.constant.ProductCategory;
import com.herny.springbootecom.dao.ProductQueryParams;
import com.herny.springbootecom.dto.ProductRequest;
import com.herny.springbootecom.model.Product;
import com.herny.springbootecom.service.ProductService;
import com.herny.springbootecom.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(
            // 查詢條件 filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            // 排序 sorting
            @RequestParam(defaultValue = "created_date") String orderBy,
            @RequestParam(defaultValue = "desc") String sort,  // desc, asc

            // 分頁 (保護後端存取資料庫的效能)      *** 驗證要在class上加 @Validated註解 才會生效 ***
            @RequestParam(defaultValue = "5") @Max(1000) @Min(0) Integer limit,    // 取得商品數，避免傳入負數
            @RequestParam(defaultValue = "0") @Min(0) Integer offset    // 跳過前幾筆
    ){
        // 未來新增查詢條件於 ProductQueryParams
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        // 查詢商品列表
        List<Product> productList = productService.getProducts(productQueryParams);

        // 取得商品總數
        Integer total = productService.countProduct(productQueryParams);

        // 分頁資訊回應
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);
        if (product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){
        // 有設定驗證值 @NotNull時  =>  務必加上 @Valid
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){
        // 確認 Product是否存在
        Product product = productService.getProductById(productId);

        if (product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // 更新商品資訊
        productService.updateProduct(productId, productRequest);

        Product updatedProduct = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){
        /*
          不用檢查，直接刪除?
          1. 商品存在，成功的刪除
          2. 商品本來就不存在
             => 只要最後沒商品就好
         */

        productService.deleteProductById(productId);

        // 204
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
