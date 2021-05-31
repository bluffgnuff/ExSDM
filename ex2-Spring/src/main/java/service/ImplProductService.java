package service;

import dao.ProductDAO;
import model.Producer;
import model.Product;
import model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ImplProductService implements  ProductService{

    @Autowired
    private ProductDAO productDAO;

    @Override
    public int saveProduct(Product product) {
        return productDAO.insertProduct(product);
    }

    @Override
    public int deleteProductByNumber(int productNumber) {
        return productDAO.removeProductByNumber(productNumber);
    }

    @Override
    public int deleteProductById(int id) {
        return productDAO.removeProductById(id);
    }

    @Override
    public Product findProductByNumber(int productNumber) {
        return productDAO.findProductByNumber(productNumber);
    }

    @Override
    public Product findProductById(int id) {
        return productDAO.findProductById(id);
    }

    @Override
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }

    @Override
    public List<Product> getAllProductsByProducer(Producer producer) {
        return productDAO.getAllProductsByProducer(producer);
    }

    @Override
    public List<Product> getAllProductsByPurchase(Purchase purchase) {
        return productDAO.getAllProductsByPurchase(purchase);
    }
}
