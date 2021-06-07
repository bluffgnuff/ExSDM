package com.service;

import com.dao.PurchaseDAO;
import com.model.Customer;
import com.model.Product;
import com.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplPurchaseService implements PurchaseService {

    @Autowired
    PurchaseDAO purchaseDAO;

    @Override
    public int savePurchase(Purchase purchase) {
        return purchaseDAO.insertPurchase(purchase);
    }

    @Override
    public int deletePurchaseById(int id) {
        return purchaseDAO.removePurchaseById(id);
    }

    @Override
    public Purchase findPurchaseByNumber(int purchaseNumber) {
        return purchaseDAO.findPurchaseByNumber(purchaseNumber);
    }

    @Override
    public Purchase findPurchaseById(int id) {
        return purchaseDAO.findPurchaseById(id);
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseDAO.getAllPurchases();
    }

    @Override
    public List<Purchase> findAllPurchasesByCustomer(Customer customer) {
        return purchaseDAO.findAllPurchasesByCustomer(customer);
    }

    @Override
    public List<Purchase> findAllPurchasesByProduct(Product product) {
        return purchaseDAO.findAllPurchasesByProduct(product);
    }
}
