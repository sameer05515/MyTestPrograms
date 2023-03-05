package com.ist.iagent.core.service.access.exception;

public class IAgentAccessException extends Exception {

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	
	public IAgentAccessException(String message){
		super(message);
	}



	public IAgentAccessException(String msg,Throwable ex) {
		super(msg,ex);
	}
	

}
