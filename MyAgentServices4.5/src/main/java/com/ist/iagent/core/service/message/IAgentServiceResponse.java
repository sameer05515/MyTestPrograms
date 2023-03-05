package com.ist.iagent.core.service.message;

public class IAgentServiceResponse implements ISuiteServiceResponse {

	private Object responseObject;
	private IAgentServiceRequest agentServiceRequest;

	public Object getResponseObject() {
		return responseObject;
	}

	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}

	public IAgentServiceRequest getAgentServiceRequest() {
		return agentServiceRequest;
	}

	public void setAgentServiceRequest(IAgentServiceRequest agentServiceRequest) {
		this.agentServiceRequest = agentServiceRequest;
	}

	@Override
	public String toString() {
		return "IAgentServiceResponse [responseObject=" + responseObject
				+ ", agentServiceRequest=" + agentServiceRequest + "]";
	}
}
