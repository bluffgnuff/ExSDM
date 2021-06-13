package com.controller;

import com.model.Customer;
import com.security.AccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import com.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/customerView", method = RequestMethod.GET)
    public ModelAndView showView() {
        ModelAndView mav = new ModelAndView("customerView");
        mav.addObject("newCustomer", new Customer());
        mav.addObject( "customers", customerService.getAllCustomers());
        return  mav;
    }

    @AccessControl
    @RequestMapping(method=RequestMethod.POST, value="/insert-customer")
    public String addCustomer(@ModelAttribute("newCustomer") Customer customer) {
        customerService.saveCustomer(customer);
        return "home";
    }

    @AccessControl
    @RequestMapping(value = "/delete-customer/{id}")
    public String deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomerById(Integer.parseInt(id));
        return "home";
    }
}