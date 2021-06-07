package com.dao;

import com.model.Producer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ImplProducerDAO implements ProducerDAO {
    @PersistenceContext
    EntityManager em;

    @Override
    public int insertProducer(Producer producer) {
        em.persist(producer);
        return producer.getId();
    }

    @Override
    public int removeProducerByName(String name) {

        Producer producer;
        if (name != null && !name.equals("")) {
            producer = (Producer) em.createQuery("select p FROM Producer p WHERE p.name = :producerName")
                    .setParameter("producerName", name)
                    .getSingleResult();
            em.remove(producer);
            return producer.getId();
        } else
            return 0;
    }

    @Override
    public int removeProducerById(int id) {
        Producer producer = em.find(Producer.class, id);
        if (producer != null) {
            em.remove(producer);
            return id;
        } else
            return 0;
    }

    @Override
    public Producer findProducerByName(String name) {
        if (name != null && !name.equals("")) {
            return (Producer) em.createQuery("select p FROM Producer p where p.name = :producerName").
                    setParameter("producerName", name).getSingleResult();
        } else
            return null;
    }

    @Override
    public Producer findProducerById(int id) {
        return em.find(Producer.class, id);
    }

    @Override
    public List<Producer> getAllProducers() {
        return em.createQuery("select p FROM Producer p").getResultList();
    }
}
