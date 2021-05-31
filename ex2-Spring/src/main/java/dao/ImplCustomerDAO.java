package dao;

import model.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ImplCustomerDAO implements CustomerDAO {
    @PersistenceContext
    EntityManager em;

    @Override
    public int insertCustomer(Customer customer) {
        em.persist(customer);
        return customer.getId();
    }

    @Override
    public int removeCustomerByName(String name) {
        Customer customer;
        if (name != null && !name.equals("")) {
            customer = (Customer) em.createQuery("select c FROM Customer c WHERE c.name = :customerName").setParameter("customerName", name).getSingleResult();
            em.remove(customer);
            return customer.getId();
        } else
            return 0;
    }

    @Override
    public int removeCustomerById(int id) {
        Customer customer = em.find(Customer.class, id);
        if (customer != null) {
            em.remove(customer);
            return id;
        } else
            return 0;
    }

    @Override
    public Customer findCustomerByName(String name) {
        if (name != null && !name.equals("")) {
            return (Customer) em.createQuery("select c FROM Customer c where c.name = :customerName").
                    setParameter("customerName", name).getSingleResult();
        } else
            return null;
    }

    @Override
    public Customer findCustomerById(int id) {
        return em.find(Customer.class, id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return em.createQuery("select c FROM Customer c").getResultList();
    }
}
