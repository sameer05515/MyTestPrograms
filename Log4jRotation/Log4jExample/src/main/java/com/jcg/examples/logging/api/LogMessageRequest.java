package com.jcg.examples.logging.api;

import jakarta.validation.constraints.NotBlank;

public record LogMessageRequest(@NotBlank(message = "message must not be blank") String message, String level) {
}

