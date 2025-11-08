package com.jcg.examples.logging.api;

import java.time.Instant;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.StandardLevel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/logs")
public class LogController {

	private static final Logger logger = LogManager.getLogger(LogController.class);

	@PostMapping
	ResponseEntity<LogResponse> createLog(@Valid @RequestBody LogMessageRequest request) {
		Level level = resolveLevel(request.level());
		log(level, request.message());
		return ResponseEntity.accepted().body(new LogResponse(level.name(), request.message(), Instant.now()));
	}

	private Level resolveLevel(String level) {
		if (!StringUtils.hasText(level)) {
			return Level.INFO;
		}
		try {
			return Level.valueOf(level.trim().toUpperCase());
		}
		catch (IllegalArgumentException ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Unsupported log level: " + level + ". Use one of TRACE, DEBUG, INFO, WARN, ERROR, FATAL.");
		}
	}

	private void log(Level level, String message) {
		switch (level.getStandardLevel()) {
			case TRACE -> logger.trace(message);
			case DEBUG -> logger.debug(message);
			case INFO -> logger.info(message);
			case WARN -> logger.warn(message);
			case ERROR -> logger.error(message);
			case FATAL -> logger.fatal(message);
			default -> logger.info(message);
		}
	}

	@ExceptionHandler(ResponseStatusException.class)
	ResponseEntity<String> handleInvalidLevel(ResponseStatusException ex) {
		return ResponseEntity.status(ex.getStatusCode()).body(ex.getReason());
	}

	record LogResponse(String level, String message, Instant timestamp) {
	}
}

