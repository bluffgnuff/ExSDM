package com.controller;

import com.model.Purchase;
import com.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @RequestMapping("/purchases-read")
    public String getPurchases(Model model) {
        model.addAttribute("purchases",purchaseService.getAllPurchases());
        return "purchaseView";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/insert-purchase")
    public String addPurchase(@ModelAttribute("newPurchase") Purchase purchase) {
        purchaseService.savePurchase(purchase);
        return "purchaseView";
    }

    @RequestMapping(value = "/delete-purchases/{id}")
    public String deletePurchase(@PathVariable String id) {
        purchaseService.deletePurchaseById(Integer.parseInt(id));
        return "purchaseView";
    }
}