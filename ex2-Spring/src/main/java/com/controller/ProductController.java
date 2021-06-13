package com.controller;

import com.model.Product;
import com.security.AccessControl;
import com.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/productView", method = RequestMethod.GET)
    public ModelAndView showView() {
        ModelAndView mav = new ModelAndView("productView");
        mav.addObject("newProduct", new Product());
        mav.addObject( "products", productService.getAllProducts());
        return mav;
    }

    @AccessControl
    @RequestMapping(method = RequestMethod.POST, value = "/insert-product")
    public String addProduct(@ModelAttribute("newProduct") Product product) {
        productService.saveProduct(product);
        return "home";
    }

    @AccessControl
    @RequestMapping(value = "/delete-product/{id}")
    public String deleteProduct(@PathVariable String id) {
        productService.deleteProductById(Integer.parseInt(id));
        return "home";
    }
}