package com.example.TaskScheduler;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableScheduling
public class TaskSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskSchedulerApplication.class, args);
	}
	
	@Scheduled(fixedRate = 2000)
	public void run() {
		System.out.println(new Date());
	}

}
