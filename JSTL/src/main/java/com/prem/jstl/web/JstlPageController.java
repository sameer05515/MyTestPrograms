package com.prem.jstl.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class JstlPageController {

    @GetMapping("/")
    public String landing() {
        return "home";
    }

    @GetMapping("/quiz/result")
    public String quizResult(@RequestParam(name = "opt", required = false) String answer, Model model) {
        model.addAttribute("answer", answer);
        return "quiz-result";
    }

    @GetMapping("/numbers")
    public String numberFormattingSample() {
        return "numFormat";
    }

    @GetMapping("/colorbox")
    public String colorboxDemo() {
        return "colorbox";
    }
}

