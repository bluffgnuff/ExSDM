package com.dao;

import com.model.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDAO {

    public int insertCustomer(Customer customer);

    public int removeCustomerByName(String name);

    public int removeCustomerById(int id);

    public Customer findCustomerByName(String name);

    public Customer findCustomerById(int id);

    public List<Customer> getAllCustomers();
}
