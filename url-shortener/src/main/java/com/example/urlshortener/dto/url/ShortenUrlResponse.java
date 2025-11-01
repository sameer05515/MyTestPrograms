package com.example.urlshortener.dto.url;

import java.time.Instant;

public record ShortenUrlResponse(
        String shortCode,
        String shortUrl,
        String longUrl,
        Instant createdAt,
        Instant expiresAt
) {
}
