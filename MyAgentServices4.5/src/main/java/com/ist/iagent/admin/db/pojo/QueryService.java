package com.ist.iagent.admin.db.pojo;

import java.util.ArrayList;
import java.util.List;

public class QueryService extends IAgentService{
	
	private int serviceId = 0;
	private int linkedDbID = 0;
	private String serviceName = "";
	private boolean storedProc = false;	
	private String queryText="";
	private String queryResultType="";
	private boolean loggingAllowed;
	private String queryType="";
	
	private List<IAgentServiceParameter> inputType=new ArrayList<IAgentServiceParameter>();
	private List<IAgentServiceParameter> returnType=new ArrayList<IAgentServiceParameter>();
	/**
	 * @return the serviceId
	 */
	public int getServiceId() {
		return serviceId;
	}
	/**
	 * @param serviceId the serviceId to set
	 */
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	/**
	 * @return the linkedDbID
	 */
	public int getLinkedDbID() {
		return linkedDbID;
	}
	/**
	 * @param linkedDbID the linkedDbID to set
	 */
	public void setLinkedDbID(int linkedDbID) {
		this.linkedDbID = linkedDbID;
	}
	/**
	 * @return the serviceName
	 */
	public String getServiceName() {
		return serviceName;
	}
	/**
	 * @param serviceName the serviceName to set
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	/**
	 * @return the isStoredProc
	 */
	public boolean getStoredProc() {
		return storedProc;
	}
	/**
	 * @param isStoredProc the isStoredProc to set
	 */
	public void setStoredProc(boolean isStoredProc) {
		this.storedProc = isStoredProc;
	}
	/**
	 * @return the queryText
	 */
	public String getQueryText() {
		return queryText;
	}
	/**
	 * @param queryText the queryText to set
	 */
	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}
	/**
	 * @return the queryResultType
	 */
	public String getQueryResultType() {
		return queryResultType;
	}
	/**
	 * @param queryResultType the queryResultType to set
	 */
	public void setQueryResultType(String queryResultType) {
		this.queryResultType = queryResultType;
	}
	/**
	 * @return the loggingAllowed
	 */
	public boolean loggingAllowed() {
		return loggingAllowed;
	}
	/**
	 * @param loggingAllowed the loggingAllowed to set
	 */
	public void setLoggingAllowed(boolean loggingAllowed) {
		this.loggingAllowed = loggingAllowed;
	}
	/**
	 * @return the queryType
	 */
	public String getQueryType() {
		return queryType;
	}
	/**
	 * @param queryType the queryType to set
	 */
	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}
	/**
	 * @return the inputType
	 */
	public List<IAgentServiceParameter> getInputType() {
		return inputType;
	}
	/**
	 * @param inputType the inputType to set
	 */
	public void setInputType(List<IAgentServiceParameter> inputType) {
		this.inputType = inputType;
	}
	/**
	 * @return the returnType
	 */
	public List<IAgentServiceParameter> getReturnType() {
		return returnType;
	}
	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(List<IAgentServiceParameter> returnType) {
		this.returnType = returnType;
	}
	
}
