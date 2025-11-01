package com.example.urlshortener.controller;

import com.example.urlshortener.dto.url.ShortenUrlRequest;
import com.example.urlshortener.dto.url.ShortenUrlResponse;
import com.example.urlshortener.dto.url.UrlStatsResponse;
import com.example.urlshortener.entity.AppUser;
import com.example.urlshortener.service.CurrentUserService;
import com.example.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;
    private final CurrentUserService currentUserService;

    public UrlController(UrlService urlService, CurrentUserService currentUserService) {
        this.urlService = urlService;
        this.currentUserService = currentUserService;
    }

    @PostMapping
    public ShortenUrlResponse shorten(@Valid @RequestBody ShortenUrlRequest request,
                                      @AuthenticationPrincipal UserDetails userDetails) {
        AppUser owner = currentUserService.mustGet(userDetails);
        return urlService.shorten(request, owner);
    }

    @GetMapping("/{shortCode}/stats")
    public UrlStatsResponse getStats(@PathVariable String shortCode,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        AppUser owner = currentUserService.mustGet(userDetails);
        return urlService.getStats(shortCode, owner);
    }
}
