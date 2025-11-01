package com.example.jsptut.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/custom")
public class CustomTagController {

    @GetMapping("/hello")
    public String helloTagDemo() {
        return "custom/hello-tag";
    }
}
