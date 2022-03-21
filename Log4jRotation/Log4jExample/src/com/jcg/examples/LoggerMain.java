package com.jcg.examples;

import org.apache.log4j.Logger;

public class LoggerMain {

	public static final Logger logger = Logger.getLogger(LoggerMain.class);

	public static void main(String[] args) {
		while (true) {
			logger.info("This is a test log");
		}
	}

}
