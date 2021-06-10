package com.dao;

import com.model.Producer;

import java.util.List;

public interface ProducerDAO {

    public int insertProducer(Producer producer);

    public int removeProducerByName(String name);

    public int removeProducerById(int id);

    public Producer findProducerById(int id);

    public Producer findProducerByName(String name);

    public List<Producer> getAllProducers();
}
