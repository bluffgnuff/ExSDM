package com.service;

import com.dao.ProductDAO;
import com.model.Producer;
import com.model.Product;
import com.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImplProductService implements ProductService {

    @Autowired
    ProductDAO productDAO;

    @Transactional
    @Override
    public int saveProduct(Product product) {
        return productDAO.insertProduct(product);
    }

    @Transactional
    @Override
    public int deleteProductByNumber(int productNumber) {
        return productDAO.removeProductByNumber(productNumber);
    }

    @Transactional
    @Override
    public int deleteProductById(int id) {
        return productDAO.removeProductById(id);
    }

    @Transactional
    @Override
    public Product findProductByNumber(int productNumber) {
        return productDAO.findProductByNumber(productNumber);
    }

    @Transactional
    @Override
    public Product findProductById(int id) {
        return productDAO.findProductById(id);
    }

    @Transactional
    @Override
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    @Transactional
    @Override
    public List<Product> getAllProductsByProducer(Producer producer) {
        return productDAO.getAllProductsByProducer(producer);
    }

    @Transactional
    @Override
    public List<Product> getAllProductsByPurchase(Purchase purchase) {
        return productDAO.getAllProductsByPurchase(purchase);
    }
}
