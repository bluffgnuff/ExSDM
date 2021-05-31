package service;

import dao.ProducerDAO;
import model.Producer;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ImplProducerService implements  ProducerService{

    @Autowired
    private ProducerDAO producerDAO;

    @Override
    public int saveProducer(Producer producer) {
        return producerDAO.insertProducer(producer);
    }

    @Override
    public int deleteProducerByName(String name) {
        return producerDAO.removeProducerByName(name);
    }

    @Override
    public int deleteProducerById(int id) {
        return producerDAO.removeProducerById(id);
    }

    @Override
    public Producer findProducerById(int id) {
        return producerDAO.findProducerById(id);
    }

    @Override
    public Producer findProducerByName(String name) {
        return producerDAO.findProducerByName(name);
    }

    @Override
    public List<Producer> getAllProducers() {
        return producerDAO.getAllProducers();
    }
}
