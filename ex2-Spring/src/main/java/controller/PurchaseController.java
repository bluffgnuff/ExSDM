package controller;

import model.Customer;
import model.Product;
import model.Purchase;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface PurchaseController {

    public int savePurchase(Purchase purchase);

    public int deletePurchaseById(int id);

    public Purchase findPurchaseByNumber(int purchaseNumber);

    public Purchase findPurchaseById(int id);

    public List<Purchase> getAllPurchases();

    public List<Purchase> findAllPurchasesByCustomer(Customer customer);

    public List<Purchase> findAllPurchasesByProduct(Product product);

}
