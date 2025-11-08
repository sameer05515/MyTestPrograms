package com.example.jsptut.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/xml")
public class XmlDemoController {

    @GetMapping("/import")
    public String importDemo() {
        return "xml/import";
    }

    @GetMapping("/transform")
    public String transformDemo() {
        return "xml/transform";
    }

    @GetMapping("/include")
    public String includeDemo() {
        return "xml/include";
    }

    @GetMapping(value = "/response", produces = MediaType.APPLICATION_XML_VALUE)
    public String xmlResponse(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_XML_VALUE);
        return "xml/response";
    }
}
