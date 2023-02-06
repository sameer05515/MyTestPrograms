package com.ist.iagent.admin.db.pojo;

import java.util.ArrayList;
import java.util.List;

public class JavaService extends IAgentService{

	private int serviceId;
	private String className;
	private String methodName;
	private String serviceName;
	private Boolean saveLastRecord;
	private int linkedJarId;
	private boolean loggingAllowed;
	private List<IAgentServiceParameter> inputType=new ArrayList<IAgentServiceParameter>();
	private List<IAgentServiceParameter> returnType=new ArrayList<IAgentServiceParameter>();
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public Boolean getSaveLastRecord() {
		return saveLastRecord;
	}
	public void setSaveLastRecord(Boolean saveLastRecord) {
		this.saveLastRecord = saveLastRecord;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public int getLinkedJarId() {
		return linkedJarId;
	}
	public void setLinkedJarId(int linkedJarId) {
		this.linkedJarId = linkedJarId;
	}
	public List<IAgentServiceParameter> getInputType() {
		return inputType;
	}
	public void setInputType(List<IAgentServiceParameter> inputType) {
		this.inputType = inputType;
	}
	public List<IAgentServiceParameter> getReturnType() {
		return returnType;
	}
	public void setReturnType(List<IAgentServiceParameter> returnType) {
		this.returnType = returnType;
	}
	@Override
	public boolean loggingAllowed() {
		return loggingAllowed;
	}
	public void setLoggingAllowed(boolean b) {
		loggingAllowed = b;		
	}
}
