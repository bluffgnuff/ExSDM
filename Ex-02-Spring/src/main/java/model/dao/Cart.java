package model.dao;

import java.util.Set;

public interface Cart {
    Set<Product> getProducts();

    void setProducts(Set<Product> products);

    void addProduct(Product product);

    Customer getCustomer();

    void setCustomer(Customer customer);

    double getTotalPrice();
}
