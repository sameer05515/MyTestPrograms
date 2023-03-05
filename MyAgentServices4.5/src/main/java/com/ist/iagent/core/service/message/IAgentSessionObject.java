package com.ist.iagent.core.service.message;

import java.io.Serializable;
import java.util.List;

public class IAgentSessionObject implements Serializable{

	private static final long serialVersionUID = -8176865530035411886L;
	private String agentId;
	private String callId;
	private String customerUniqueId;
	private List<?> callVariables;

	private String extension;

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getCustomerUniqueId() {
		return customerUniqueId;
	}

	public void setCustomerUniqueId(String customerUniqueId) {
		this.customerUniqueId = customerUniqueId;
	}

	public List<?> getCallVariables() {
		return callVariables;
	}

	public void setCallVariables(List<?> callVariables) {
		this.callVariables = callVariables;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return "IAgentSessionObject [agentId=" + agentId + ", callId=" + callId
				+ ", customerUniqueId=" + customerUniqueId + ", callVariables="
				+ callVariables + ", extension=" + extension + "]";
	}
}
