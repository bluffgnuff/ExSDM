package controller;

import model.Producer;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public interface ProducerController {

    public int saveProducer(Producer producer);

    public int deleteProducerByName(String name);

    public int deleteProducerById(int id);

    public Producer findProducerById(int id);

    public Producer findProducerByName(String name);

    public List<Producer> getAllProducers();
}
