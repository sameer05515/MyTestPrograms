package com.ist.iagent.core.service.db.pojo;

public class ServiceLog {

	private int serviceLogID;//service_logs_id
	private String agentID;//agent_id
	private String callID;//call_id
	private String serviceName;//service_name
	private String request;//request
	private String response;//response
	private String status;//status
	private int timeInterval;//time_interval
	private long serviceLogTime;//time
	private String customerID;//cust_id
	/**
	 * @return the serviceLogsID
	 */
	public int getServiceLogID() {
		return serviceLogID;
	}
	/**
	 * @param serviceLogsID the serviceLogsID to set
	 */
	public void setServiceLogID(int serviceLogsID) {
		this.serviceLogID = serviceLogsID;
	}
	/**
	 * @return the agentID
	 */
	public String getAgentID() {
		return agentID;
	}
	/**
	 * @param agentID the agentID to set
	 */
	public void setAgentID(String agentID) {
		this.agentID = agentID;
	}
	/**
	 * @return the callID
	 */
	public String getCallID() {
		return callID;
	}
	/**
	 * @param callID the callID to set
	 */
	public void setCallID(String callID) {
		this.callID = callID;
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
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}
	/**
	 * @param request the request to set
	 */
	public void setRequest(String request) {
		this.request = request;
	}
	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the timeInterval
	 */
	public int getTimeInterval() {
		return timeInterval;
	}
	/**
	 * @param timeInterval the timeInterval to set
	 */
	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}
	/**
	 * @return the serviceLogTime
	 */
	public long getServiceLogTime() {
		return serviceLogTime;
	}
	/**
	 * @param serviceLogTime the serviceLogTime to set
	 */
//	public void setServiceLogTime(long serviceLogTime) {
//		this.serviceLogTime = serviceLogTime;
//	}
	/**
	 * @return the customerID
	 */
	public String getCustomerID() {
		return customerID;
	}
	/**
	 * @param customerID the customerID to set
	 */
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}
	
	
}
