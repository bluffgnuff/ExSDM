package com.controller;

import com.model.Product;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping("/products-read")
    public String getProducts(Model model) {
        model.addAttribute("products",productService.getAllProducts());
        return "productView";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/insert-product")
    public String addProduct(@ModelAttribute("newProduct") Product product) {
        productService.saveProduct(product);
        return "productView";
    }

    @RequestMapping(value = "/delete-products/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProductById(Integer.parseInt(id));
        return "productView";
    }
}