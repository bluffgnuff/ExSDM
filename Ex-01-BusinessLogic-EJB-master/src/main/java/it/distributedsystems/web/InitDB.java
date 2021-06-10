package it.distributedsystems.web;

import it.distributedsystems.model.dao.Customer;
import it.distributedsystems.model.dao.Producer;
import it.distributedsystems.model.dao.Product;
import it.distributedsystems.model.dao.Purchase;
import it.distributedsystems.util.HibernateUtil;
import it.distributedsystems.model.ejb.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;

@WebServlet(name = "HibernateServlet", urlPatterns = {"initdb"}, loadOnStartup = 1)
public class InitDB extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        initSessionFactory(response);
    }

    private void initSessionFactory(HttpServletResponse response) throws IOException {

        Session session = HibernateUtil.getSessionFactory().openSession();
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
        for (Product p : purchase.getProducts())
            p.setPurchase(purchase);

        purchases.add(purchase);
        //Purchase Customer
        customer.setPurchases(purchases);

        session.beginTransaction();
        session.save(customer);
        session.save(producer);
        session.save(producer2);
        session.save(product10);
        session.save(product11);
        session.save(product20);
        session.save(product21);
        session.save(purchase);
        session.getTransaction().commit();

        session.disconnect();
        session.close();
        HibernateUtil.shutdown();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        initSessionFactory(response);
    }


}
