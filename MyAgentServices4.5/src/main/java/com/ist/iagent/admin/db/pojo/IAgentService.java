package com.ist.iagent.admin.db.pojo;

import java.util.List;

public abstract class IAgentService {

	public abstract String getServiceName();
	public abstract List<IAgentServiceParameter> getInputType();
	public abstract List<IAgentServiceParameter> getReturnType();
	public abstract boolean loggingAllowed();
}
