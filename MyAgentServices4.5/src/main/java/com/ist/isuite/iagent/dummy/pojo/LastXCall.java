package com.ist.isuite.iagent.dummy.pojo;

import java.sql.Date;

public class LastXCall {

	private String id;
	private String customerId;
	private String ani;
	private Date dateTime;
	private String agentId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAni() {
		return ani;
	}

	public void setAni(String ani) {
		this.ani = ani;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

}
