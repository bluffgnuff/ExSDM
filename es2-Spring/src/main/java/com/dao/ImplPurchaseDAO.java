package com.dao;

import com.model.Customer;
import com.model.Product;
import com.model.Purchase;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;

@Repository
public class ImplPurchaseDAO implements PurchaseDAO {
    @PersistenceContext
    private EntityManager em;

    public int insertPurchase(Purchase purchase) {
        HashSet<Product> products = new HashSet<>();
        products.addAll(purchase.getProducts());
        purchase.setProducts(new HashSet<>());

        //riattacco il customer al contesto di persistenza
        if (purchase.getCustomer() != null && purchase.getCustomer().getId() > 0)
            purchase.setCustomer(em.merge(purchase.getCustomer()));

        //riattacco i product al contesto di persistenza
        for (Product product : products)
            if (product != null && product.getId() > 0) {
                purchase.addProduct(em.merge(product));
            }

        em.persist(purchase);
        return purchase.getId();
    }

    @Override
    public int removePurchaseById(int id) {
        Purchase purchase = em.find(Purchase.class, id);
        if (purchase != null) {
            //Cancello le associazioni tra l'ordine da rimuovere e i prodotti inseriti
            //dalla tabella di associazione Purchase_Product
            em.createNativeQuery("DELETE FROM Purchase_Product WHERE purchase_id=" + purchase.getId() + " ;").executeUpdate();

            em.remove(purchase);

            return id;
        } else
            return 0;
    }

    @Override
    public Purchase findPurchaseByNumber(int purchaseNumber) {
        return (Purchase) em.createQuery("select p from Purchase p where p.purchaseNumber = :num").
                setParameter("num", purchaseNumber).getSingleResult();
    }

    @Override
    public Purchase findPurchaseById(int id) {
        return em.find(Purchase.class, id);
        /*
        return (Purchase) em.createQuery("FROM Purchase p WHERE p.id = :purchaseId").
			setParameter("purchaseId", id).getSingleResult();
         */
    }

    @Override
    public List<Purchase> getAllPurchases() {
        return em.createQuery("select p FROM Purchase p ").getResultList();
    }

    @Override
    public List<Purchase> findAllPurchasesByCustomer(Customer customer) {
        //Non è stato necessario usare una fetch join (nonostante Purchase.customer fosse mappato LAZY)
        //perché gli id delle entità LAZY collegate vengono comunque mantenuti e sono accessibili
        return em.createQuery("select p FROM Purchase p WHERE :customerId = p.customer.id").
                setParameter("customerId", customer.getId()).getResultList();
    }

    @Override
    public List<Purchase> findAllPurchasesByProduct(Product product) {
        if (product != null) {
            em.merge(product); // riattacco il product al contesto di persistenza con una merge
            return em.createQuery("SELECT DISTINCT (p) FROM Purchase p JOIN FETCH p.products JOIN FETCH p.customer WHERE :product MEMBER OF p.products").
                    setParameter("product", product).getResultList();
        } else
            return em.createQuery("SELECT DISTINCT (p) FROM Purchase p JOIN FETCH p.products JOIN FETCH p.customer").getResultList();
    }
}

