package com.restController;

import com.model.Product;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductRestController {

    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.GET, value = "insert-product/{name}")
    public void addProducts(Product newProduct) {
        productService.saveProduct(newProduct);
    }

    @RequestMapping("read-product")
    public List<Product> getProducts() {
        return productService.getAllProducts();
    }

    @RequestMapping(value = "restdelete-product/{id}")
    public void deleteProduct(@PathVariable String id) {
        productService.deleteProductById(Integer.parseInt(id));
    }
}