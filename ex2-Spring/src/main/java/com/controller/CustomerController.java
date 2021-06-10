package com.controller;

import com.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import com.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequestMapping("/customers-read")
    public String getCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        return "/customerView";
    }

    @RequestMapping(value = "/insert-customer", method = RequestMethod.POST)
    public String addCustomer(@ModelAttribute("customer") Customer customer, BindingResult result) {
        customerService.saveCustomer(customer);
        return "customerView";
    }

    @RequestMapping(value = "/delete-customers/{id}")
    public String deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomerById(Integer.parseInt(id));
        return "customerView";
    }
}