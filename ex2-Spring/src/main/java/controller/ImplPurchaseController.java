package controller;

import model.Customer;
import model.Product;
import model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import service.PurchaseService;

import java.util.List;

public class ImplPurchaseController implements  PurchaseController{

    @Autowired
    private PurchaseService purchaseService;

    @Override
    public int savePurchase(Purchase purchase) {
        return purchaseService.savePurchase(purchase);
    }

    @Override
    public int deletePurchaseById(int id) {
        return purchaseService.deletePurchaseById(id);
    }

    @Override
    public Purchase findPurchaseByNumber(int purchaseNumber) {
        return purchaseService.findPurchaseByNumber(purchaseNumber);
    }

    @Override
    public Purchase findPurchaseById(int id) {
        return purchaseService.findPurchaseById(id);
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return purchaseService.getAllPurchases();
    }

    @Override
    public List<Purchase> findAllPurchasesByCustomer(Customer customer) {
        return purchaseService.findAllPurchasesByCustomer(customer);
    }

    @Override
    public List<Purchase> findAllPurchasesByProduct(Product product) {
        return purchaseService.findAllPurchasesByProduct(product);
    }
}
