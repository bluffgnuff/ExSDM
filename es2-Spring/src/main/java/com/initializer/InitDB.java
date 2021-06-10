package com.initializer;

import com.dao.ImplCustomerDAO;
import com.dao.ImplProducerDAO;
import com.dao.ImplProductDAO;
import com.dao.ImplPurchaseDAO;
import com.model.Customer;
import com.model.Producer;
import com.model.Product;
import com.model.Purchase;
import com.util.HibernateUtil;
import org.hibernate.Session;

import java.util.HashSet;

public class InitDB{

    public static void initializeDB() {
        ImplProducerDAO producerDAO = new ImplProducerDAO();
        ImplCustomerDAO customerDAO = new ImplCustomerDAO();
        ImplPurchaseDAO purchaseDAO = new ImplPurchaseDAO();
        ImplProductDAO productDAO = new ImplProductDAO();
        /*Session session = HibernateUtil.getSessionFactory().openSession();*/
        HashSet<Purchase> purchases = new HashSet();
        HashSet<Product> set = new HashSet();
        HashSet<Product> set2 = new HashSet();
        Producer producer = new Producer("AMD");
        Producer producer2 = new Producer("NVIDIA");
        Product product10 = new Product("RX580");
        Product product11 = new Product("RX590");

        Product product20 = new Product("GTX 1660");
        Product product21 = new Product("GTX 3090");

        Customer customer = new Customer("Maria");
        Purchase purchase = new Purchase();

        product10.setProducer(producer);
        product10.setProductNumber(546);
        product10.setPrice(200);
        product11.setProducer(producer);
        product11.setProductNumber(547);
        product11.setPrice(300);

        product20.setProducer(producer2);
        product20.setProductNumber(549);
        product20.setPrice(200);
        product21.setProducer(producer2);
        product20.setProductNumber(550);
        product21.setPrice(300);

        set.add(product10);
        set.add(product11);
        set2.add(product20);
        set2.add(product21);

        producer.setProducts(set);
        producer2.setProducts(set2);

        //Purchase
        purchase.setPurchaseNumber(123);
        purchase.setCustomer(customer);
        purchase.setProducts(set);
        //Purchase Prodotto
        for(Product p : purchase.getProducts())
            p.setPurchase(purchase);

        purchases.add(purchase);
        //Purchase Customer
        customer.setPurchases(purchases);
        System.out.println("Begin DB init");
        /*session.beginTransaction();*/
        customerDAO.insertCustomer(customer);
        /*session.save(customer);*/
        producerDAO.insertProducer(producer);
        producerDAO.insertProducer(producer2);
        /*session.save(producer);*/
        /*session.save(producer2);*/
        productDAO.insertProduct(product10);
        productDAO.insertProduct(product11);
        productDAO.insertProduct(product20);
        productDAO.insertProduct(product21);
        /*session.save(product10);
        session.save(product11);
        session.save(product20);
        session.save(product21);*/
        purchaseDAO.insertPurchase(purchase);
/*        session.save(purchase);
        session.getTransaction().commit();*/
        System.out.println("End DB init");
        /*session.disconnect();
        session.close();
        HibernateUtil.shutdown();*/
    }
}