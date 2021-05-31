package service;

import dao.CustomerDAO;
import dao.PurchaseDAO;
import model.Customer;
import model.Product;
import model.Purchase;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ImplPurchaseService implements  PurchaseService{

    @Autowired
    private PurchaseDAO purchaseDAO;

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
