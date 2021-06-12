package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

//TODO implementare
@Controller
public class HomeController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHomePage(ModelMap model) {
        return "home";
    }
}