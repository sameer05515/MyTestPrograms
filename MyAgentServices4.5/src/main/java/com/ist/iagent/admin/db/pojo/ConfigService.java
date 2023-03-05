package com.ist.iagent.admin.db.pojo;

public class ConfigService {

	private int linkedServiceId;//linked_ser_id
	private int configServiceSrNo;//config_service_srno
	private int configServiceTypeId;//ser_type_id
	private IAgentServiceChannel serviceChannel;//service_channel_id
	private IAgentService service;	
	/**
	 * @return the linkedServiceId
	 */
	public int getLinkedServiceId() {
		return linkedServiceId;
	}
	/**
	 * @param linkedServiceId the linkedServiceId to set
	 */
	public void setLinkedServiceId(int linkedServiceId) {
		this.linkedServiceId = linkedServiceId;
	}
	/**
	 * @return the configServiceSrNo
	 */
	public int getConfigServiceSrNo() {
		return configServiceSrNo;
	}
	/**
	 * @param configServiceSrNo the configServiceSrNo to set
	 */
	public void setConfigServiceSrNo(int configServiceSrNo) {
		this.configServiceSrNo = configServiceSrNo;
	}
	/**
	 * @return the configServiceTypeId
	 */
	public int getConfigServiceTypeId() {
		return configServiceTypeId;
	}
	/**
	 * @param configServiceTypeId the configServiceTypeId to set
	 */
	public void setConfigServiceTypeId(int configServiceTypeId) {
		this.configServiceTypeId = configServiceTypeId;
	}
	/**
	 * @return the service
	 */
	public IAgentService getService() {
		return service;
	}
	/**
	 * @param service the service to set
	 */
	public void setService(IAgentService service) {
		this.service = service;
	}
	/**
	 * @return the serviceChannel
	 */
	public IAgentServiceChannel getServiceChannel() {
		return serviceChannel;
	}
	/**
	 * @param serviceChannel the serviceChannel to set
	 */
	public void setServiceChannel(IAgentServiceChannel serviceChannel) {
		this.serviceChannel = serviceChannel;
	}
}
