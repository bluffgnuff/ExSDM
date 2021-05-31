package controller;

import model.Customer;
import model.Product;
import model.Purchase;

import java.util.List;

public class ImplPurchaseController implements  PurchaseController{
    @Override
    public int savePurchase(Purchase purchase) {
        return 0;
    }

    @Override
    public int deletePurchaseById(int id) {
        return 0;
    }

    @Override
    public Purchase findPurchaseByNumber(int purchaseNumber) {
        return null;
    }

    @Override
    public Purchase findPurchaseById(int id) {
        return null;
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return null;
    }

    @Override
    public List<Purchase> findAllPurchasesByCustomer(Customer customer) {
        return null;
    }

    @Override
    public List<Purchase> findAllPurchasesByProduct(Product product) {
        return null;
    }
}
