package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RedirectController {

    private final UrlService urlService;

    public RedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/{shortCode:[a-zA-Z0-9]{8}}")
    public void redirect(@PathVariable String shortCode, HttpServletResponse response) throws IOException {
        String longUrl = urlService.resolveLongUrl(shortCode);
        urlService.trackRedirect(shortCode);
        response.sendRedirect(longUrl);
    }
}
