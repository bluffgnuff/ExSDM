package com.controller;

import com.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
//TODO implementare
@Controller
public class ImplHomeController {
    @Autowired
    CustomerService customerService;

    @RequestMapping(value = "/")
    public String showLoginPage(ModelMap model) {
        //int id = Integer.parseInt(request.getParameter("idCustomer"));
        //    model.addAttribute("customers",customerService.getAllCustomers());
        //ModelAndView mav = new ModelAndView("home");
        return "home";
    }
}