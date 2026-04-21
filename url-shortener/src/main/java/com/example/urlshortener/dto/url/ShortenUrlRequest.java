package com.example.urlshortener.dto.url;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ShortenUrlRequest(
        @NotBlank
        @Pattern(
                regexp = "^(https?://).+",
                message = "URL must start with http:// or https://"
        )
        String longUrl,
        @Min(1) @Max(365) Integer expiryDays
) {
}
