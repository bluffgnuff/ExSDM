package com.service;

import com.dao.ProducerDAO;
import com.model.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ImplProducerService implements ProducerService {

    @Autowired
    ProducerDAO producerDAO;

    @Transactional
    @Override
    public int saveProducer(Producer producer) {
        return producerDAO.insertProducer(producer);
    }

    @Transactional
    @Override
    public int deleteProducerByName(String name) {
        return producerDAO.removeProducerByName(name);
    }

    @Transactional
    @Override
    public int deleteProducerById(int id) {
        return producerDAO.removeProducerById(id);
    }

    @Transactional
    @Override
    public Producer findProducerById(int id) {
        return producerDAO.findProducerById(id);
    }

    @Transactional
    @Override
    public Producer findProducerByName(String name) {
        return producerDAO.findProducerByName(name);
    }

    @Transactional
    @Override
    public List<Producer> getAllProducers() {
        return producerDAO.getAllProducers();
    }
}
