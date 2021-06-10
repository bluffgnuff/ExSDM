package com.service;

import com.model.Producer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProducerService {

    public int saveProducer(Producer producer);

    public int deleteProducerByName(String name);

    public int deleteProducerById(int id);

    public Producer findProducerById(int id);

    public Producer findProducerByName(String name);

    public List<Producer> getAllProducers();
}
