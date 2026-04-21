package com.example.urlshortener.service;

import com.example.urlshortener.dto.auth.AuthResponse;
import com.example.urlshortener.dto.auth.LoginRequest;
import com.example.urlshortener.dto.auth.RegisterRequest;
import com.example.urlshortener.entity.AppUser;
import com.example.urlshortener.entity.UserRole;
import com.example.urlshortener.exception.ApiException;
import com.example.urlshortener.repository.AppUserRepository;
import com.example.urlshortener.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(AppUserRepository appUserRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (appUserRepository.existsByUsername(request.username())) {
            throw new ApiException(HttpStatus.CONFLICT, "Username already exists");
        }
        AppUser user = new AppUser();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(UserRole.USER);
        appUserRepository.save(user);
        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }

    public AuthResponse login(LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
            String username = auth.getName();
            AppUser user = appUserRepository.findByUsername(username)
                    .orElseThrow(() -> new ApiException(HttpStatus.UNAUTHORIZED, "User not found"));
            String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
            return new AuthResponse(token, user.getUsername(), user.getRole().name());
        } catch (AuthenticationException ex) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
    }
}
