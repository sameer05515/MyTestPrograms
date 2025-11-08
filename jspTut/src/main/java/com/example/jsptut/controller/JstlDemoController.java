package com.example.jsptut.controller;

import com.example.jsptut.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jstl")
public class JstlDemoController {

    private final EmployeeService employeeService;

    public JstlDemoController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("empList", employeeService.getSampleEmployees());
        model.addAttribute("htmlTagData", "<br/> creates a new line.");
        model.addAttribute("url", "/");
        model.addAttribute("idValue", 5);
        return "jstl/home";
    }
}
