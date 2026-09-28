package com.example.jsptut.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.util.Date;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/samples")
public class SamplesController {

    @GetMapping("/scriptlet")
    public String scriptletDemo(Model model, HttpServletRequest request) {
        model.addAttribute("currentTime", Date.from(Instant.now()));
        model.addAttribute("remoteHost", request.getRemoteHost());
        model.addAttribute("numbers", IntStream.rangeClosed(1, 20).boxed().toList());
        model.addAttribute("showHello", Boolean.TRUE);
        return "samples/scriptlet-demo";
    }
}
