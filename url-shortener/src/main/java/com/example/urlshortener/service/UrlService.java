package com.example.urlshortener.service;

import com.example.urlshortener.config.AppProperties;
import com.example.urlshortener.dto.url.ShortenUrlRequest;
import com.example.urlshortener.dto.url.ShortenUrlResponse;
import com.example.urlshortener.dto.url.UrlStatsResponse;
import com.example.urlshortener.entity.AppUser;
import com.example.urlshortener.entity.UrlMapping;
import com.example.urlshortener.exception.ApiException;
import com.example.urlshortener.repository.UrlMappingRepository;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@Service
public class UrlService {

    private final UrlMappingRepository urlMappingRepository;
    private final ShortCodeGenerator shortCodeGenerator;
    private final HashService hashService;
    private final AppProperties appProperties;

    public UrlService(UrlMappingRepository urlMappingRepository,
                      ShortCodeGenerator shortCodeGenerator,
                      HashService hashService,
                      AppProperties appProperties) {
        this.urlMappingRepository = urlMappingRepository;
        this.shortCodeGenerator = shortCodeGenerator;
        this.hashService = hashService;
        this.appProperties = appProperties;
    }

    @Transactional
    public ShortenUrlResponse shorten(ShortenUrlRequest request, AppUser owner) {
        String normalizedLongUrl = normalize(request.longUrl());
        String longUrlHash = hashService.compactHash(normalizedLongUrl);

        UrlMapping existing = urlMappingRepository
                .findActiveByOwnerAndLongUrlHash(owner, longUrlHash, Instant.now())
                .orElse(null);
        if (existing != null) {
            return toResponse(existing);
        }

        UrlMapping mapping = new UrlMapping();
        mapping.setLongUrl(normalizedLongUrl);
        mapping.setLongUrlHash(longUrlHash);
        mapping.setOwner(owner);
        mapping.setExpiresAt(resolveExpiry(request.expiryDays()));

        int maxAttempts = 6;
        for (int i = 0; i < maxAttempts; i++) {
            try {
                mapping.setShortCode(shortCodeGenerator.generate());
                UrlMapping saved = urlMappingRepository.saveAndFlush(mapping);
                return toResponse(saved);
            } catch (DataIntegrityViolationException ignored) {
                if (i == maxAttempts - 1) {
                    throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE,
                            "Could not generate unique short code. Retry.");
                }
            }
        }

        throw new ApiException(HttpStatus.SERVICE_UNAVAILABLE, "Could not shorten URL");
    }

    @Cacheable(cacheNames = "short-url-cache", key = "#shortCode")
    public String resolveLongUrl(String shortCode) {
        UrlMapping mapping = urlMappingRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Short URL not found"));

        if (mapping.getExpiresAt() != null && mapping.getExpiresAt().isBefore(Instant.now())) {
            throw new ApiException(HttpStatus.GONE, "Short URL expired");
        }
        return mapping.getLongUrl();
    }

    @Transactional
    public void trackRedirect(String shortCode) {
        urlMappingRepository.incrementClickCount(shortCode);
    }

    public UrlStatsResponse getStats(String shortCode, AppUser owner) {
        UrlMapping mapping = urlMappingRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Short URL not found"));
        if (!mapping.getOwner().getId().equals(owner.getId())) {
            throw new ApiException(HttpStatus.FORBIDDEN, "Access denied");
        }
        return new UrlStatsResponse(
                mapping.getShortCode(),
                mapping.getLongUrl(),
                mapping.getClickCount(),
                mapping.getCreatedAt(),
                mapping.getExpiresAt()
        );
    }

    @CacheEvict(cacheNames = "short-url-cache", key = "#shortCode")
    public void invalidateCache(String shortCode) {
        // No body needed; annotation triggers cache eviction.
    }

    private String normalize(String url) {
        try {
            URI uri = new URI(url.trim());
            if (!uri.isAbsolute()) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "URL must be absolute");
            }
            String scheme = uri.getScheme() == null ? "" : uri.getScheme().toLowerCase(Locale.ROOT);
            if (!"http".equals(scheme) && !"https".equals(scheme)) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Only HTTP/HTTPS URLs are allowed");
            }
            return uri.normalize().toString();
        } catch (URISyntaxException e) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Invalid URL");
        }
    }

    private Instant resolveExpiry(Integer expiryDays) {
        if (expiryDays == null) {
            return null;
        }
        return Instant.now().plus(expiryDays, ChronoUnit.DAYS);
    }

    private ShortenUrlResponse toResponse(UrlMapping mapping) {
        return new ShortenUrlResponse(
                mapping.getShortCode(),
                appProperties.getBaseUrl() + "/" + mapping.getShortCode(),
                mapping.getLongUrl(),
                mapping.getCreatedAt(),
                mapping.getExpiresAt()
        );
    }
}
