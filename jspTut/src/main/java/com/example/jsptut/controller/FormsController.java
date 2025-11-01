package com.example.jsptut.controller;

import com.example.jsptut.util.ProjectConstants;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/forms")
public class FormsController {

    @GetMapping("/name")
    public String nameForm() {
        return "forms/name-form";
    }

    @PostMapping("/name")
    public String handleName(@RequestParam(ProjectConstants.REQUEST_PARAMETER_USERNAME) String username,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        if (!StringUtils.hasText(username)) {
            redirectAttributes.addFlashAttribute("error", "Please provide your name.");
            return "redirect:/forms/name";
        }
        String trimmed = username.trim();
        session.setAttribute(ProjectConstants.SESSION_ATTRIBUTE_THE_NAME, trimmed);
        return "redirect:/forms/name/result";
    }

    @GetMapping("/name/result")
    public String nameResult(HttpSession session, Model model) {
        Object name = session.getAttribute(ProjectConstants.SESSION_ATTRIBUTE_THE_NAME);
        if (name == null) {
            return "redirect:/forms/name";
        }
        model.addAttribute("name", name);
        return "forms/name-result";
    }
}
