package com.dao;

import com.model.Customer;
import com.model.Product;
import com.model.Purchase;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseDAO {

    public int insertPurchase(Purchase purchase);

    //public int removePurchaseByNumber(int purchaseNumber);

    public int removePurchaseById(int id);

    public Purchase findPurchaseByNumber(int purchaseNumber);

    public Purchase findPurchaseById(int id);

    public List<Purchase> getAllPurchases();

    public List<Purchase> findAllPurchasesByCustomer(Customer customer);

    public List<Purchase> findAllPurchasesByProduct(Product product);
}
