package com.ist.iagent.admin.db.pojo;

public class IAgentServiceChannel {

	private int serviceChannelID;// channel_id
	private String serviceChannelName;// channel_name
	private String requestName;// request_name
	private String destination;// destination
	private IAgentServiceChannelType channelType;// channel_type_id

	public int getServiceChannelID() {
		return serviceChannelID;
	}

	public void setServiceChannelID(int serviceChannelID) {
		this.serviceChannelID = serviceChannelID;
	}

	public String getServiceChannelName() {
		return serviceChannelName;
	}

	public void setServiceChannelName(String serviceChannelName) {
		this.serviceChannelName = serviceChannelName;
	}

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public IAgentServiceChannelType getChannelType() {
		return channelType;
	}

	public void setChannelType(IAgentServiceChannelType channelType) {
		this.channelType = channelType;
	}
}
