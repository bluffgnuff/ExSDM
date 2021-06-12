package com.restController;

import com.model.Customer;
import com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerRestController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET, value = "insert-customer/{name}")
    public void addCustomer(Customer newCustomer) {
        customerService.saveCustomer(newCustomer);
    }

    @RequestMapping("read-customer")
    public List<Customer> getCustomer() {
        return customerService.getAllCustomers();
    }

    @RequestMapping(value = "restdelete-customer/{id}")
    public void deleteCustomer(@PathVariable String id) {
        customerService.deleteCustomerById(Integer.parseInt(id));
    }
}