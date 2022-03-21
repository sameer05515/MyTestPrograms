package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.util.SampleFileRunner;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	
	private static Logger LOG = LoggerFactory
		      .getLogger(DemoApplication.class);

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(DemoApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}

	@Override
    public void run(String... args) {
        LOG.info("EXECUTING : command line runner");
 
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; ++i) {
				LOG.info("args[{}]: {}", i, args[i]);
			}
			
			SampleFileRunner.run("C:\\Users\\premendra\\Desktop\\arguments.bat", args);
		}else {
			LOG.info("EXITING : no arguments passed");
		}
    }

}
