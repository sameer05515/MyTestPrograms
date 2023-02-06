package com.ist.iagent.core.service.message;

import java.io.Serializable;
import java.util.Arrays;

public class IAgentServiceRequest implements ISuiteServiceRequest, Serializable {
	
	private static final long serialVersionUID = -3064131692634229369L;
	private IAgentSessionObject sessionObject;
	private String serviceName;
	private Object[] parameters;

	public IAgentSessionObject getSessionObject() {
		return sessionObject;
	}

	public void setSessionObject(IAgentSessionObject sessionObject) {
		this.sessionObject = sessionObject;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		return "IAgentServiceRequest [sessionObject=" + sessionObject
				+ ", serviceName=" + serviceName + ", parameters="
				+ Arrays.toString(parameters) + "]";
	}

}
