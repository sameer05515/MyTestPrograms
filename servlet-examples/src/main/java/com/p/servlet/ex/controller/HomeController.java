package com.p.servlet.ex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/processForm")
    public String processForm(@RequestParam int num1, @RequestParam int num2, RedirectAttributes redirectAttributes) {
        // Redirect to the add URL with the numbers as path variables
        return "redirect:/v1/add/" + num1 + "/" + num2;
    }
}
