package controller;

import model.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import service.CustomerService;
import service.ProducerService;

import java.util.List;

public class ImplProducerController implements ProducerController{

    @Autowired
    private ProducerService producerService;

    @Override
    public int saveProducer(Producer producer) {
        return producerService.saveProducer(producer);
    }

    @Override
    public int deleteProducerByName(String name) {
        return producerService.deleteProducerByName(name);
    }

    @Override
    public int deleteProducerById(int id) {
        return producerService.deleteProducerById(id);
    }

    @Override
    public Producer findProducerById(int id) {
        return producerService.findProducerById(id);
    }

    @Override
    public Producer findProducerByName(String name) {
        return producerService.findProducerByName(name);
    }

    @Override
    public List<Producer> getAllProducers() {
        return producerService.getAllProducers();
    }
}
