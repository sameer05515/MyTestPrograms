package com.isuite.iagent.commons;

import com.ist.isuite.license.ISuiteApps;

public interface IAgentModule {
	
	/**
	 * 
	 * Get IAgent module type.
	 */
	public ISuiteApps getAppType();

	/**
	 * 
	 * Guranteed call.
	 */
	public void init(ServerDetails serverDetails);

	/**
	 * 
	 * Not Guranteed if it will be called.
	 */
	public void destory();

}
