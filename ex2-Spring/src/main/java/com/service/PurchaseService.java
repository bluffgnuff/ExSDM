package com.service;

import com.model.Customer;
import com.model.Product;
import com.model.Purchase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PurchaseService {

    public int savePurchase(Purchase purchase);

    public int deletePurchaseById(int id);

    public Purchase findPurchaseByNumber(int purchaseNumber);

    public Purchase findPurchaseById(int id);

    public List<Purchase> getAllPurchases();

    public List<Purchase> findAllPurchasesByCustomer(Customer customer);

    public List<Purchase> findAllPurchasesByProduct(Product product);
}
