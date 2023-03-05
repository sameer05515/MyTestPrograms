package com.ist.iagent.admin.db.pojo;

public class ParameterDescDTO extends IAgentServiceParameter{
	
	private int paramId;
	private int serviceId;
	private String parameterName;
	private String parameterType;
	private String parameterDescr;
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterType() {
		return parameterType;
	}
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	public String getParameterDescr() {
		return parameterDescr;
	}
	public void setParameterDescr(String parameterDescr) {
		this.parameterDescr = parameterDescr;
	}
	public int getParamId() {
		return paramId;
	}
	public void setParamId(int paramId) {
		this.paramId = paramId;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	
}
