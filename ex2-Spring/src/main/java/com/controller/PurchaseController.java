package com.controller;

import com.model.Purchase;
import com.security.AccessControl;
import com.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PurchaseController {

    @Autowired
    PurchaseService purchaseService;

    @RequestMapping(value = "/purchaseView", method = RequestMethod.GET)
    public ModelAndView showView() {
        ModelAndView mav = new ModelAndView("purchaseView");
        mav.addObject("newPurchase", new Purchase());
        mav.addObject( "purchases", purchaseService.getAllPurchases());
        return mav;
    }

    @AccessControl
    @RequestMapping(method = RequestMethod.POST, value = "/insert-purchase")
    public String addPurchase(@ModelAttribute("newPurchase") Purchase purchase) {
        purchaseService.savePurchase(purchase);
        return "home";
    }

    @AccessControl
    @RequestMapping(value = "/delete-purchase/{id}")
    public String deletePurchase(@PathVariable String id) {
        purchaseService.deletePurchaseById(Integer.parseInt(id));
        return "home";
    }
}