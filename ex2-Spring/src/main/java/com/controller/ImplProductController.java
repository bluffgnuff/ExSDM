package com.controller;

import com.model.Producer;
import com.model.Product;
import com.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import com.service.ProductService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//TODO implementare
@RestController
public class ImplProductController{

    @Autowired
    private ProductService productService;

    public int saveProduct(Product product) {
        return productService.saveProduct(product);
    }

    public int deleteProductByNumber(int productNumber) {
        return productService.deleteProductByNumber(productNumber);
    }

    public int deleteProductById(int id) {
        return productService.deleteProductById(id);
    }

    public Product findProductByNumber(int productNumber) {
        return productService.findProductByNumber(productNumber);
    }

    public Product findProductById(int id) {
        return productService.findProductById(id);
    }

    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    public List<Product> getAllProductsByProducer(Producer producer) {
        return productService.getAllProductsByProducer(producer);
    }

    public List<Product> getAllProductsByPurchase(Purchase purchase) {
        return productService.getAllProductsByPurchase(purchase);
    }
}
