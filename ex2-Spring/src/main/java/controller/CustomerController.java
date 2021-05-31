package controller;

import model.Customer;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface CustomerController {

    public int saveCustomer(Customer customer);

    public int deleteCustomerById(int id);

    public Customer findCustomerById(int id);

    public Customer findCustomerByname(String name);

    public List<Customer> getAllCustomers();

}






