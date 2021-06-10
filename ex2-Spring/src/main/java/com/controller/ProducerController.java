package com.controller;

import com.model.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import com.service.ProducerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProducerController {

    @Autowired
    ProducerService producerService;

    @RequestMapping(method = RequestMethod.POST, value = "/insert-producer")
    public String addProducer(@ModelAttribute("newProducer") Producer producer) {
        producerService.saveProducer(producer);
        return "producerView";
    }

    @RequestMapping("/producers-read")
    public String getProducers(Model model) {
        model.addAttribute("producers",producerService.getAllProducers());
        return "producerView";
    }

    @RequestMapping(value = "/delete-producer/{id}")
    public String deleteProducer(@PathVariable String id) {
        producerService.deleteProducerById(Integer.parseInt(id));
        return "producerView";
    }
}