package service;

import model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    public int saveCustomer(Customer customer);

    public int deleteCustomerByName(String name);

    public int deleteCustomerById(int id);

    public Customer findCustomerById(int id);

    public Customer findCustomerByName(String name);

    public List<Customer> getAllCustomers();

}
