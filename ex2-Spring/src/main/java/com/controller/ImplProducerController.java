package com.controller;

import com.model.Producer;
import com.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
//TODO implementare
@RestController
public class ImplProducerController{

    @Autowired
    ProducerService producerService;


    public int saveProducer(Producer producer) {
        return producerService.saveProducer(producer);
    }

    public int deleteProducerByName(String name) {
        return producerService.deleteProducerByName(name);
    }

    public int deleteProducerById(int id) {
        return producerService.deleteProducerById(id);
    }

    public Producer findProducerById(int id) {
        return producerService.findProducerById(id);
    }

    public Producer findProducerByName(String name) {
        return producerService.findProducerByName(name);
    }

    public List<Producer> getAllProducers() {
        return producerService.getAllProducers();
    }
}
