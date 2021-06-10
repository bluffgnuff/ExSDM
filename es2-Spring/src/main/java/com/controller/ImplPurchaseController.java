package com.controller;

import com.model.Customer;
import com.model.Product;
import com.model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import com.service.PurchaseService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//TODO implementare
@RestController
public class ImplPurchaseController{

    @Autowired
    private PurchaseService purchaseService;

    public int savePurchase(Purchase purchase) {
        return purchaseService.savePurchase(purchase);
    }

    public int deletePurchaseById(int id) {
        return purchaseService.deletePurchaseById(id);
    }

    public Purchase findPurchaseByNumber(int purchaseNumber) {
        return purchaseService.findPurchaseByNumber(purchaseNumber);
    }

    public Purchase findPurchaseById(int id) {
        return purchaseService.findPurchaseById(id);
    }

    public List<Purchase> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }

    public List<Purchase> findAllPurchasesByCustomer(Customer customer) {
        return purchaseService.findAllPurchasesByCustomer(customer);
    }

    public List<Purchase> findAllPurchasesByProduct(Product product) {
        return purchaseService.findAllPurchasesByProduct(product);
    }
}