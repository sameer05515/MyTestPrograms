package com.p.Exporter.response;

public class DBQueryResponse {
	
	private String status;
	private String message;
	private DbQueryRespData data;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public DbQueryRespData getData() {
		return data;
	}
	public void setData(DbQueryRespData data) {
		this.data = data;
	}
	public DBQueryResponse(String status, String message, DbQueryRespData data) {
		super();
		this.status = status;
		this.message = message;
		this.data = data;
	}
	public DBQueryResponse() {
		super();
	}
	@Override
	public String toString() {
		return "DBQueryResponse [status=" + status + ", message=" + message + ", data=" + data + "]";
	}
	

}
