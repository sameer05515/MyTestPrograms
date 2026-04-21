package com.example.urlshortener.dto.auth;

public record AuthResponse(String token, String username, String role) {
}
