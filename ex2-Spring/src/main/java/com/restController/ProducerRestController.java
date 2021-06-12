package com.restController;

import com.model.Producer;
import com.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProducerRestController {

    @Autowired
    ProducerService producerService;

    @RequestMapping(method = RequestMethod.GET, value = "insert-producer/{name}")
    public void addProducer(Producer newProducer) {
        producerService.saveProducer(newProducer);
    }

    @RequestMapping("read-producer")
    public List<Producer> getProducers() {
        return producerService.getAllProducers();
    }

    @RequestMapping(value = "restdelete-producer/{id}")
    public void deleteProducer(@PathVariable String id) {
        producerService.deleteProducerById(Integer.parseInt(id));
    }
}