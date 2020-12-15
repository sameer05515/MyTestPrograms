package com.p.model;

public class Api_Request {

	private String msgId;
	private String dataFormat = "CSV:CSV";
	private String tradesInquiry;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public String getTradesInquiry() {
		return tradesInquiry;
	}

	public void setTradesInquiry(String tradesInquiry) {
		this.tradesInquiry = tradesInquiry;
	}

}
