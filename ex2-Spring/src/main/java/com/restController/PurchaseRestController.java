package com.restController;

import com.model.Purchase;
import com.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PurchaseRestController {

    @Autowired
    PurchaseService purchaseService;

    @RequestMapping(method = RequestMethod.GET, value = "insert-purchase/{number}")
    public void addPurchases(Purchase newPurchase) {
        purchaseService.savePurchase(newPurchase);
    }

    @RequestMapping("read-purchase")
    public List<Purchase> getPurchases() {
        return purchaseService.getAllPurchases();
    }

    @RequestMapping(value = "restdelete-purchase/{id}")
    public void deletePurchase(@PathVariable String id) {
        purchaseService.deletePurchaseById(Integer.parseInt(id));
    }
}