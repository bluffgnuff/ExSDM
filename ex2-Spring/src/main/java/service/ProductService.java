package service;

import model.Producer;
import model.Product;
import model.Purchase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    public int saveProduct(Product product);

    public int deleteProductByNumber(int productNumber);

    public int deleteProductById(int id);

    public Product findProductByNumber(int productNumber);

    public Product findProductById(int id);

    public List<Product> getAllProducts();

    public List<Product> getAllProductsByProducer(Producer producer);

    public List<Product> getAllProductsByPurchase(Purchase purchase);

}
