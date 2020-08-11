package com.p.log4jtest.Log4jTest;
import java.util.Date;

import org.apache.log4j.Logger;

import com.p.log4jtest.Log4jTest.external.ExternalService;
import com.p.log4jtest.Log4jTest.internal.InternalService;


public class Log4jMain {
	private final static Logger logger = Logger.getLogger(Log4jMain.class);

	public static void main(String args[]) throws InterruptedException {
		ExternalService extService = new ExternalService();
		extService.setInput("JavaVillage");
		extService.service();
		
		
		InternalService intService = new InternalService();
		intService.setInput("Soha");
		intService.service();
		while(true) {
			for(int i=0;i<10000;i++) {
				logger.info("Response from External Service : "+new Date()+" : "+extService.getOutput());
				logger.info("Response from Internal Service : "+new Date()+" : "+intService.getOutput());
				logger.info("Response from External Service : "+new Date()+" : "+extService.getOutput());
				logger.info("Response from Internal Service : "+new Date()+" : "+intService.getOutput());
			}
//			Thread.sleep(1000);
		}
	}
}