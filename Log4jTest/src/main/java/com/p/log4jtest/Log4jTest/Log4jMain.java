package com.p.log4jtest.Log4jTest;
import org.apache.log4j.Logger;


public class Log4jMain {
	private final static Logger logger = Logger.getLogger(Log4jMain.class);

	public static void main(String args[]) {
		ExternalService extService = new ExternalService();
		extService.setInput("JavaVillage");
		extService.service();
		logger.info("Response from External Service: "+extService.getOutput());
		
		InternalService intService = new InternalService();
		intService.setInput("Soha");
		intService.service();
		logger.info("Response from Internal Service: "+intService.getOutput());
	}
}