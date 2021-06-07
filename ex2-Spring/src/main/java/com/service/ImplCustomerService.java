package com.service;

import com.dao.CustomerDAO;
import com.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplCustomerService implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    public int saveCustomer(Customer customer) {
        return customerDAO.insertCustomer(customer);
    }

    @Override
    public int deleteCustomerByName(String name) {
        return customerDAO.removeCustomerByName(name);
    }

    @Override
    public int deleteCustomerById(int id) {
        return customerDAO.removeCustomerById(id);
    }

    @Override
    public Customer findCustomerById(int id) {
        return customerDAO.findCustomerById(id);
    }

    @Override
    public Customer findCustomerByName(String name) {
        return customerDAO.findCustomerByName(name);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }
}
