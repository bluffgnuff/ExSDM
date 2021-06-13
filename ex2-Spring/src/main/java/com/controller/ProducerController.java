package com.controller;

import com.model.Producer;
import com.security.AccessControl;
import org.springframework.beans.factory.annotation.Autowired;
import com.service.ProducerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProducerController {

    @Autowired
    ProducerService producerService;

    @RequestMapping(value = "/producerView", method = RequestMethod.GET)
    public ModelAndView showView() {
        ModelAndView mav = new ModelAndView("producerView");
        mav.addObject("newProducer", new Producer());
        mav.addObject( "producers", producerService.getAllProducers());
        return  mav;
    }

    @AccessControl
    @RequestMapping(method = RequestMethod.POST, value = "/insert-producer")
    public String addProducer(@ModelAttribute("newProducer") Producer newProducer) {
        producerService.saveProducer(newProducer);
        return "home";
    }

    @AccessControl
    @RequestMapping(value = "/delete-producer/{id}")
    public String deleteProducer(@PathVariable String id) {
        producerService.deleteProducerById(Integer.parseInt(id));
        return "home";
    }
}