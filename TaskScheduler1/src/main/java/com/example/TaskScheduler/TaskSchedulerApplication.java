package com.example.TaskScheduler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableScheduling
public class TaskSchedulerApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TaskSchedulerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TaskSchedulerApplication.class, args);
	}

	@Value("${console.batchFilePath}")
	private String batchFilePath;

	@Value("${console.cronExpression}")
	private String cronExpression;

	// @Scheduled(fixedRateString = "${console.fetchMetrics}")
	@Scheduled(cron = "${console.cronExpression}")
	public void run() {
		System.out.println(new Date() + " : Running : batchFile  " + batchFilePath
				+ " :\nAs per cron expression : " + cronExpression);

		ProcessBuilder processBuilder = new ProcessBuilder(batchFilePath);

		try {

			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
				LOGGER.debug(line);
			}

//			int exitVal = process.waitFor();
//			if (exitVal == 0) {
//				System.out.println(output);
//				// System.exit(0);
//			} else {
//				// abnormal...
//			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

}
