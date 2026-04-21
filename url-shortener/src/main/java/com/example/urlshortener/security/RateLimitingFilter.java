package com.example.urlshortener.security;

import com.example.urlshortener.config.AppProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private static final String ANON_KEY = "anonymous";

    private final Map<String, Counter> counters = new ConcurrentHashMap<>();
    private final AppProperties appProperties;

    public RateLimitingFilter(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/auth") || path.startsWith("/h2-console");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String key = resolvePrincipal();
        long now = Instant.now().getEpochSecond();
        Counter counter = counters.computeIfAbsent(key, ignored -> new Counter(now));
        synchronized (counter) {
            long windowStart = counter.windowStart.get();
            long windowSeconds = appProperties.getRateLimit().getWindowSeconds();
            if (now - windowStart >= windowSeconds) {
                counter.windowStart.set(now);
                counter.requestCount.set(0);
            }
            if (counter.requestCount.incrementAndGet() > appProperties.getRateLimit().getRequestsPerWindow()) {
                writeTooManyRequests(response, "Rate limit exceeded");
                return;
            }
            if (counter.inFlight.incrementAndGet() > appProperties.getRateLimit().getMaxConcurrentRequests()) {
                counter.inFlight.decrementAndGet();
                writeTooManyRequests(response, "Too many concurrent requests");
                return;
            }
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            counter.inFlight.decrementAndGet();
        }
    }

    private String resolvePrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ANON_KEY;
        }
        return authentication.getName();
    }

    private void writeTooManyRequests(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"" + message + "\"}");
    }

    private static final class Counter {
        private final AtomicLong windowStart;
        private final AtomicInteger requestCount;
        private final AtomicInteger inFlight;

        private Counter(long now) {
            this.windowStart = new AtomicLong(now);
            this.requestCount = new AtomicInteger(0);
            this.inFlight = new AtomicInteger(0);
        }
    }
}
