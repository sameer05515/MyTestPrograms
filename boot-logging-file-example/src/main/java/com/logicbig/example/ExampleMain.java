package com.logicbig.example;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableScheduling
public class ExampleMain {
    private static final Logger logger = LoggerFactory.getLogger(ExampleMain.class);

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Current Directory = " + System.getProperty("user.dir"));
        SpringApplication.run(ExampleMain.class, args);
        logger.info("just a test info log");
    }
    
    @Scheduled(cron = "${console.cronExpression}")
	public void run() {
    	logger.info("info message : "+new Date());
    	logger.debug("debug message : "+new Date());
    	System.out.println("sysout message : "+new Date());
    }
}