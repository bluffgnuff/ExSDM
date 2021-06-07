package com.controller;

import com.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import com.service.CustomerService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//TODO implementare
@RestController
public class ImplCustomerController{

    @Autowired
    private CustomerService customerService;

    public int saveCustomer(Customer customer) {
        return customerService.saveCustomer(customer);
    }

    public int deleteCustomerById(int id) {
        return customerService.deleteCustomerById(id);
    }

    public Customer findCustomerById(int id) {
        return customerService.findCustomerById(id);
    }

    public Customer findCustomerByname(String name) {
        return customerService.findCustomerByName(name);
    }

    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}