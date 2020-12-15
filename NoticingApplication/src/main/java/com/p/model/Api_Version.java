package com.p.model;

public class Api_Version {

	private String version;
	Api_Request data;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Api_Request getRequest() {
		return data;
	}

	public void setRequest(Api_Request request) {
		this.data = request;
	}

}
