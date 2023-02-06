package com.ist.iagent.core.service.db.pojo;

public class LoginLog {
	private int loginLogID;//login_logs_id
	private long loginLogTime;//time
	private String agentID;//agent_id
	private String adUserId;//call_id
	private String extn;//call_id
	private String status;//status
	private String adStatus;//ad_status
	private String ctiStatus;//cti_status
	private String reserved_1;//reserved_1
	private String reserved_2;//reserved_2
	private String reserved_3;//reserved_3
	
	/**
	 * @return the loginLogID
	 */
	public int getLoginLogID() {
		return loginLogID;
	}
	/**
	 * @param loginLogID the loginLogsID to set
	 */
	public void setLoginLogID(int loginLogID) {
		this.loginLogID = loginLogID;
	}
	
	/**
	 * @return the loginLogTime
	 */
	public long getLogTime() {
		return loginLogTime;
	}
	/**
	 * @param loginLogTime the loginLogTime to set
	 */
//	public void setLoginLogTime(long loginLogTime) {
//		this.loginLogTime = loginLogTime;
//	}
	
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
	
	
	
	public String getAdUserId() {
		return adUserId;
	}
	public void setAdUserId(String adUserId) {
		this.adUserId = adUserId;
	}
	public String getExtn() {
		return extn;
	}
	public void setExtn(String extn) {
		this.extn = extn;
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
	 * @return the adStatus
	 */
	public String getAdStatus() {
		return adStatus;
	}
	/**
	 * @param adStatus the adStatus to set
	 */
	public void setAdStatus(String adStatus) {
		this.adStatus = adStatus;
	}
	
	/**
	 * @return the ctiStatus
	 */
	public String getCtiStatus() {
		return ctiStatus;
	}
	/**
	 * @param ctiStatus the ctiStatus to set
	 */
	public void setCtiStatus(String ctiStatus) {
		this.ctiStatus = ctiStatus;
	}
	
	/**
	 * @return the reserved_1
	 */
	public String getReserved_1() {
		return reserved_1;
	}
	/**
	 * @param reserved_1 the reserved_1 to set
	 */
	public void setReserved_1(String reserved_1) {
		this.reserved_1 = reserved_1;
	}
	
	/**
	 * @return the reserved_2
	 */
	public String getReserved_2() {
		return reserved_2;
	}
	/**
	 * @param reserved_2 the reserved_2 to set
	 */
	public void setReserved_2(String reserved_2) {
		this.reserved_2 = reserved_2;
	}
	
	/**
	 * @return the reserved_3
	 */
	public String getReserved_3() {
		return reserved_3;
	}
	/**
	 * @param reserved_3 the reserved_3 to set
	 */
	public void setReserved_3(String reserved_3) {
		this.reserved_3 = reserved_3;
	}

}
