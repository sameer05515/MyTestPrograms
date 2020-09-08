package com.lankydan;

import com.lankydan.service.MyService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableScheduling
public class Application implements CommandLineRunner {

  @Autowired private MyService myService;

  public static void main(String args[]) {
    SpringApplication.run(Application.class);
  }

  @Override
  public void run(String args[]) {
  	//myService.doStuff("value");
	  System.out.println("Application started");
	}
  
  @Scheduled(cron = "${console.cronExpression}")
	public void run() {
//  	logger.info("info message : "+new Date());
//  	logger.debug("debug message : "+new Date());
//  	System.out.println("sysout message : "+new Date());
	  System.out.println("================================");
	  System.out.println(new Date());
	  System.out.println("================================");
	  myService.doStuff("value");
  }
}
