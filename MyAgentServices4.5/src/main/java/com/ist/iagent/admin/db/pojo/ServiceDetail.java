package com.ist.iagent.admin.db.pojo;

public class ServiceDetail {

	private int serviceId;
	private String serviceName;
	private String serviceType;
	private String serviceTableName;
	private boolean serviceStatus;
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
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	/**
	 * @return the serviceTableName
	 */
	public String getServiceTableName() {
		return serviceTableName;
	}
	/**
	 * @param serviceTableName the serviceTableName to set
	 */
	public void setServiceTableName(String serviceTableName) {
		this.serviceTableName = serviceTableName;
	}
	/**
	 * @return the serviceStatus
	 */
	public boolean getServiceStatus() {
		return serviceStatus;
	}
	/**
	 * @param serviceStatus the serviceStatus to set
	 */
	public void setServiceStatus(boolean serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	
}
