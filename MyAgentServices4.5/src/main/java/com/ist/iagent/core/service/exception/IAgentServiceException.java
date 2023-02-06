package com.ist.iagent.core.service.exception;

import com.ist.iagent.core.service.exception.IAgentException;

public class IAgentServiceException extends IAgentException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4662235778078704262L;

	public IAgentServiceException(String message) {
		super(message);
	}
	
	public IAgentServiceException(String msg,Throwable ex) {
		super(msg,ex);
	}
	
}
