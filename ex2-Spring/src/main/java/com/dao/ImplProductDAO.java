package com.dao;

import com.model.Producer;
import com.model.Product;
import com.model.Purchase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@ComponentScan(basePackages = {"com"})
@Repository
public class ImplProductDAO implements ProductDAO {
    @PersistenceContext(unitName = "entityManagerFactory")
    private EntityManager em;

    @Override
    public int insertProduct(Product product) {
        if (product.getProducer() != null && product.getProducer().getId() > 0)
            product.setProducer(em.merge(product.getProducer()));

        em.persist(product);
        return product.getId();
    }

    @Override
    public int removeProductByNumber(int productNumber) {

        Product product = (Product) em.createQuery("select p from Product p where p.name = :num").
                setParameter("num", productNumber).getSingleResult();
        if (product != null) {
            int id = product.getId();
            //Cancello le associazioni tra il prodotto da rimuovere e gli ordini in cui è contenuto
            //dalla tabella di associazione Purchase_Product
            em.createNativeQuery("DELETE FROM Purchase_Product WHERE product_id=" + id + " ;").executeUpdate();

            em.remove(product);

            return id;
        } else
            return 0;
    }

    @Override
    public int removeProductById(int id) {
        Product product = em.find(Product.class, id);
        if (product != null) {
            //Cancello le associazioni tra il prodotto da rimuovere e gli ordini in cui è contenuto
            //dalla tabella di associazione Purchase_Product
            em.createNativeQuery("DELETE FROM Purchase_Product WHERE product_id=" + product.getId() + " ;").executeUpdate();

            em.remove(product);

            return id;
        } else
            return 0;
    }

    @Override
    public Product findProductByNumber(int productNumber) {
        return (Product) em.createQuery("select p from Product p where p.productNumber = :num").
                setParameter("num", productNumber).getSingleResult();
    }

    @Override
    public Product findProductById(int id) {
        return em.find(Product.class, id);
    }

    @Override
    public List<Product> getAllProducts() {
        return em.createQuery("select p from Product p join fetch p.producer").getResultList();
    }

    @Override
    public List<Product> getAllProductsByProducer(Producer producer) {
        //Non è stato necessario usare una fetch join (nonostante Product.producer fosse mappato LAZY)
        //perché gli id delle entità LAZY collegate vengono comunque mantenuti e sono accessibili
        return em.createQuery("select p FROM Product p WHERE :producerId = p.producer.id").
                setParameter("producerId", producer.getId()).getResultList();
    }

    @Override
    public List<Product> getAllProductsByPurchase(Purchase purchase) {
        // riattacco il product al contesto di persistenza con una merge
        return em.createQuery("select p FROM Product p WHERE :purchaseId = p.purchase.id").
                setParameter("purchaseId", purchase.getId()).getResultList();
    }
}

