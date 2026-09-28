package com.example.urlshortener.dto.url;

import java.time.Instant;

public record UrlStatsResponse(
        String shortCode,
        String longUrl,
        long clickCount,
        Instant createdAt,
        Instant expiresAt
) {
}
