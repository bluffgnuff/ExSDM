package dao;

import model.Producer;
import model.Product;
import model.Purchase;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDAO {

    public int insertProduct(Product product);

    public int removeProductByNumber(int productNumber);

    public int removeProductById(int id);

    public Product findProductByNumber(int productNumber);

    public Product findProductById(int id);

    public List<Product> getAllProducts();

    public List<Product> getAllProductsByProducer(Producer producer);

    public List<Product> getAllProductsByPurchase(Purchase purchase);

}
