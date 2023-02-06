package com.ist.iagent.admin.db.pojo;

import java.util.ArrayList;
import java.util.List;

public class WSService extends IAgentService{

	private int serviceId = 0;
	private String operationName = "";
	private String serviceName = "";
	private boolean saveLastRecord = false;
	private int linkedWSId = 0;
	private String portName="";
	private boolean loggingAllowed;
	private String parameterSchema;
	
	private List<IAgentServiceParameter> inputType=new ArrayList<IAgentServiceParameter>();
	private List<IAgentServiceParameter> returnType=new ArrayList<IAgentServiceParameter>();

	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public boolean isSaveLastRecord() {
		return saveLastRecord;
	}

	public void setSaveLastRecord(boolean saveLastRecord) {
		this.saveLastRecord = saveLastRecord;
	}

	public int getLinkedWSId() {
		return linkedWSId;
	}

	public void setLinkedWSId(int linkedWSId) {
		this.linkedWSId = linkedWSId;
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

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	@Override
	public boolean loggingAllowed() {

		return loggingAllowed;
	}
	
	public void setLoggingAllowed(boolean b) {
		loggingAllowed = b;
	}

	/**
	 * @return the parameterSchema
	 */
	public String getParameterSchema() {
		return parameterSchema;
	}

	/**
	 * @param parameterSchema the parameterSchema to set
	 */
	public void setParameterSchema(String parameterSchema) {
		this.parameterSchema = parameterSchema;
	}
	
}
