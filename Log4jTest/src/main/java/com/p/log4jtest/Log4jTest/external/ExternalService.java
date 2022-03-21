package com.p.log4jtest.Log4jTest.external;
import org.apache.log4j.Logger;

public class ExternalService {
	private final static Logger logger = Logger.getLogger(ExternalService.class);
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
		logger.info("External Service Request: "+input);
		setOutput("Hi "+input); // Here your SErvice call to External Service
		logger.info("External Service Response: "+output);
	}
}  
