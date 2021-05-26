package model.dao;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Purchase implements Serializable {

    private static final long serialVersionUID = 4612874195612951296L;

    protected int id;
    protected int purchaseNumber;
    protected Customer customer;
    protected Set<Product> products;

    public Purchase() {
        this.products = new HashSet<>();
    }

    public Purchase(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
        this.products = new HashSet<>();
    }

    public Purchase(int purchaseNumber, Customer customer) {
        this.purchaseNumber = purchaseNumber;
        this.customer = customer;
        this.products = new HashSet<>();
    }

    public Purchase(int purchaseNumber, Customer customer, Set<Product> products) {
        this.purchaseNumber = purchaseNumber;
        this.customer = customer;
        this.products = new HashSet<>();
        this.products.addAll(products);
        for (Product p : products)
            p.setPurchase(this);
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(unique = true)
    public int getPurchaseNumber() {
        return id;
    }

    public void setPurchaseNumber(int purchaseNumber) {
        this.purchaseNumber = purchaseNumber;
    }

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY
    )
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @OneToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            fetch = FetchType.LAZY,
            mappedBy = "purchase"
    )
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products.clear();
        this.products.addAll(products);
        for (Product p : products) {
            p.setPurchase(this);
        }
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.setPurchase(this);
    }
}