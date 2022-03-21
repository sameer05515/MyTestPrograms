package com.empl.coding.exercise.coding.ex.exception;

public class InvalidInputSuppliedException extends Exception {

	private String customMessage;

	public InvalidInputSuppliedException(String message) {
		this.customMessage=message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8635315043557666718L;

	public String getCustomMessage() {
		return customMessage;
	}

}
