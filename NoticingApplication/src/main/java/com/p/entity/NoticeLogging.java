package com.p.entity;

import java.sql.Timestamp;

public class NoticeLogging {

	private String REQ_MESSAGEID;

	private String REQ_TYPE;

	private int SEQ_NO;

	private String RESP_MESSAGESCODE;

	private String RESP_MESSAGEID;

	private String STATUS;

	private Timestamp INSERTEDDATE;
	private String comments;

	public String getREQ_MESSAGEID() {
		return REQ_MESSAGEID;
	}

	public void setREQ_MESSAGEID(String rEQ_MESSAGEID) {
		REQ_MESSAGEID = rEQ_MESSAGEID;
	}

	public String getREQ_TYPE() {
		return REQ_TYPE;
	}

	public void setREQ_TYPE(String rEQ_TYPE) {
		REQ_TYPE = rEQ_TYPE;
	}

	public int getSEQ_NO() {
		return SEQ_NO;
	}

	public void setSEQ_NO(int sEQ_NO) {
		SEQ_NO = sEQ_NO;
	}

	public String getRESP_MESSAGESCODE() {
		return RESP_MESSAGESCODE;
	}

	public void setRESP_MESSAGESCODE(String rESP_MESSAGESCODE) {
		RESP_MESSAGESCODE = rESP_MESSAGESCODE;
	}

	public String getRESP_MESSAGEID() {
		return RESP_MESSAGEID;
	}

	public void setRESP_MESSAGEID(String rESP_MESSAGEID) {
		RESP_MESSAGEID = rESP_MESSAGEID;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public Timestamp getINSERTEDDATE() {
		return INSERTEDDATE;
	}

	public void setINSERTEDDATE(Timestamp iNSERTEDDATE) {
		INSERTEDDATE = iNSERTEDDATE;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}
