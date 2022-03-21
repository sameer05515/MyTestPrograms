package com.p.log4jtest.Log4jTest.internal;

import org.apache.log4j.Logger;

public class InternalService {
	private final static Logger logger = Logger.getLogger(InternalService.class);
	private String input;
	private String output;
	
	/**
	 * @return the input
	 */
	public String getInput() {
		return input;
	}
	/**
	 * @param input the input to set
	 */
	public void setInput(String input) {
		this.input = input;
	}
	/**
	 * @return the output
	 */
	public String getOutput() {
		return output;
	}
	/**
	 * @param output the output to set
	 */
	public void setOutput(String output) {
		this.output = output;
	}
	public void service(){
		logger.info("Internal Service Request: "+input);
		setOutput("Hi "+input); // Here your Service call to Internal Service
		logger.info("Internal Service Response: "+input);
	}
}
