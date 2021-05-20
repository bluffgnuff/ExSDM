package it.distributedsystems.model.ejb;

import it.distributedsystems.model.dao.*;

import javax.ejb.Local;
import javax.ejb.Stateful;
import java.util.HashSet;
import java.util.Set;

@Local
@Stateful
public class EJB3Cart implements Cart {
    private Customer customer;
    private Set<Product> products = new HashSet<>();

    public EJB3Cart() {
    }

    public EJB3Cart(Customer customer) {
        this.customer = customer;
    }

    public EJB3Cart(Customer customer, Set<Product> products) {
        this.customer = customer;
        this.products = products;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public double getTotalPrice() {
        double totalPrice = 0;
        for (Product p : products) {
            totalPrice += p.getPrice();
        }
        return totalPrice;
    }
}
