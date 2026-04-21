package com.example.urlshortener.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String baseUrl;
    private Jwt jwt = new Jwt();
    private RateLimit rateLimit = new RateLimit();

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Jwt getJwt() {
        return jwt;
    }

    public void setJwt(Jwt jwt) {
        this.jwt = jwt;
    }

    public RateLimit getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(RateLimit rateLimit) {
        this.rateLimit = rateLimit;
    }

    public static class Jwt {
        private String secret;
        private int expirationMinutes;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public int getExpirationMinutes() {
            return expirationMinutes;
        }

        public void setExpirationMinutes(int expirationMinutes) {
            this.expirationMinutes = expirationMinutes;
        }
    }

    public static class RateLimit {
        private int requestsPerWindow;
        private int windowSeconds;
        private int maxConcurrentRequests;

        public int getRequestsPerWindow() {
            return requestsPerWindow;
        }

        public void setRequestsPerWindow(int requestsPerWindow) {
            this.requestsPerWindow = requestsPerWindow;
        }

        public int getWindowSeconds() {
            return windowSeconds;
        }

        public void setWindowSeconds(int windowSeconds) {
            this.windowSeconds = windowSeconds;
        }

        public int getMaxConcurrentRequests() {
            return maxConcurrentRequests;
        }

        public void setMaxConcurrentRequests(int maxConcurrentRequests) {
            this.maxConcurrentRequests = maxConcurrentRequests;
        }
    }
}
