package com.jcg.examples.logging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Log4jExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(Log4jExampleApplication.class, args);
	}
}

