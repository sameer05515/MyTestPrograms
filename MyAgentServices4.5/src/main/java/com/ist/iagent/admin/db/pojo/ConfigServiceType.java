package com.ist.iagent.admin.db.pojo;

public class ConfigServiceType {

	private int configSerTypeID;//conf_ser_type_id
	private String configSerTypeName;//conf_ser_type_name
	private IAgentServiceChannel defaultChannel;//default_channel_id
	
	/**
	 * @return the configSerTypeID
	 */
	public int getConfigSerTypeID() {
		return configSerTypeID;
	}
	/**
	 * @param configSerTypeID the configSerTypeID to set
	 */
	public void setConfigSerTypeID(int configSerTypeID) {
		this.configSerTypeID = configSerTypeID;
	}
	/**
	 * @return the configSerTypeName
	 */
	public String getConfigSerTypeName() {
		return configSerTypeName;
	}
	/**
	 * @param configSerTypeName the configSerTypeName to set
	 */
	public void setConfigSerTypeName(String configSerTypeName) {
		this.configSerTypeName = configSerTypeName;
	}
	/**
	 * @return the defaultChannel
	 */
	public IAgentServiceChannel getDefaultChannel() {
		return defaultChannel;
	}
	/**
	 * @param defaultChannel the defaultChannel to set
	 */
	public void setDefaultChannel(IAgentServiceChannel defaultChannel) {
		this.defaultChannel = defaultChannel;
	}
	
}
