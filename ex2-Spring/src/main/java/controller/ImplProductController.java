package controller;

import model.Producer;
import model.Product;
import model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import service.ProductService;

import java.util.List;

public class ImplProductController implements ProductController{

    @Autowired
    private ProductService productService;

    @Override
    public int saveProduct(Product product) {
        return productService.saveProduct(product);
    }

    @Override
    public int deleteProductByNumber(int productNumber) {
        return productService.deleteProductByNumber(productNumber);
    }

    @Override
    public int deleteProductById(int id) {
        return productService.deleteProductById(id);
    }

    @Override
    public Product findProductByNumber(int productNumber) {
        return productService.findProductByNumber(productNumber);
    }

    @Override
    public Product findProductById(int id) {
        return productService.findProductById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @Override
    public List<Product> getAllProductsByProducer(Producer producer) {
        return productService.getAllProductsByProducer(producer);
    }

    @Override
    public List<Product> getAllProductsByPurchase(Purchase purchase) {
        return productService.getAllProductsByPurchase(purchase);
    }
}
