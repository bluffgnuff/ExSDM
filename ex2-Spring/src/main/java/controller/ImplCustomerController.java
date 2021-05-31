package controller;

import dao.CustomerDAO;
import model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import service.CustomerService;

import java.util.List;

public class ImplCustomerController implements CustomerController{

    @Autowired
    private CustomerService customerService;

    @Override
    public int saveCustomer(Customer customer) {
        return customerService.saveCustomer(customer);
    }

    @Override
    public int deleteCustomerById(int id) {
        return customerService.deleteCustomerById(id);
    }

    @Override
    public Customer findCustomerById(int id) {
        return customerService.findCustomerById(id);
    }

    @Override
    public Customer findCustomerByname(String name) {
        return customerService.findCustomerByName(name);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }
}
