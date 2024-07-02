package com.SpringBoot.EcommerceSiteProject.Payment.Controller;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller("home")
public class HomeController {

    @GetMapping("/")
    public String viewHomePage(Model model) {
        return "stripe";
    }

}