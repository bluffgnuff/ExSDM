package com.service;

import com.dao.PurchaseDAO;
import com.model.Customer;
import com.model.Product;
import com.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImplPurchaseService implements PurchaseService {

    @Autowired
    PurchaseDAO purchaseDAO;

    @Transactional
    @Override
    public int savePurchase(Purchase purchase) {
        return purchaseDAO.insertPurchase(purchase);
    }

    @Transactional
    @Override
    public int deletePurchaseById(int id) {
        return purchaseDAO.removePurchaseById(id);
    }

    @Transactional
    @Override
    public Purchase findPurchaseByNumber(int purchaseNumber) {
        return purchaseDAO.findPurchaseByNumber(purchaseNumber);
    }

    @Transactional
    @Override
    public Purchase findPurchaseById(int id) {
        return purchaseDAO.findPurchaseById(id);
    }

    @Transactional
    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseDAO.getAllPurchases();
    }

    @Transactional
    @Override
    public List<Purchase> findAllPurchasesByCustomer(Customer customer) {
        return purchaseDAO.findAllPurchasesByCustomer(customer);
    }

    @Transactional
    @Override
    public List<Purchase> findAllPurchasesByProduct(Product product) {
        return purchaseDAO.findAllPurchasesByProduct(product);
    }
}
