package com.ist.iagent.core.service.exception;

public class IAgentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8383624543480320146L;
	
	
	
	public IAgentException(String message){
		super(message);
	}



	public IAgentException(String msg,Throwable ex) {
		super(msg,ex);
	}

}
