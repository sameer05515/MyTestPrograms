package com.ist.isuite.iagent.dummy.pojo;

public class ReturnMessage {

	private String code;
	private String message;
	private Object data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	
	@Override
	public String toString() {
	
		
		
		// TODO Auto-generated method stub
		return "[code:"+code+"][messge:"+message+"]";
	}
}
