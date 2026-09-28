package com.jcg.examples.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PeriodicLogEmitter {

	private static final Logger logger = LogManager.getLogger(PeriodicLogEmitter.class);

	private final boolean enabled;
	private final String message;

	PeriodicLogEmitter(@Value("${logging.demo.enabled:true}") boolean enabled,
			@Value("${logging.demo.message:This is a test log}") String message) {
		this.enabled = enabled;
		this.message = message;
	}

	@Scheduled(fixedDelayString = "${logging.demo.interval-ms:5000}")
	void emit() {
		if (!enabled) {
			return;
		}
		logger.info(message);
	}
}

