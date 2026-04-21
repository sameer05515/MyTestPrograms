package com.example.urlshortener.service;

import com.example.urlshortener.entity.AppUser;
import com.example.urlshortener.exception.ApiException;
import com.example.urlshortener.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final AppUserRepository appUserRepository;

    public CurrentUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser mustGet(UserDetails userDetails) {
        return appUserRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "User not found"));
    }
}
