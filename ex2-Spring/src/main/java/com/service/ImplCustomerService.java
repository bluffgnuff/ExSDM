package com.service;

import com.dao.CustomerDAO;
import com.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImplCustomerService implements CustomerService {

    @Autowired
    private CustomerDAO customerDAO;

    @Override
    @Transactional
    public int saveCustomer(Customer customer) {
        return customerDAO.insertCustomer(customer);
    }

    @Override
    @Transactional
    public int deleteCustomerByName(String name) {
        return customerDAO.removeCustomerByName(name);
    }

    @Override
    @Transactional
    public int deleteCustomerById(int id) {
        return customerDAO.removeCustomerById(id);
    }

    @Override
    @Transactional
    public Customer findCustomerById(int id) {
        return customerDAO.findCustomerById(id);
    }

    @Override
    @Transactional
    public Customer findCustomerByName(String name) {
        return customerDAO.findCustomerByName(name);
    }

    @Override
    @Transactional
    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }
}
