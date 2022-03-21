package com.p.Exporter.pojo;

public class DataSourcePojo {
	
	private String dbUniqueName;
	private String userName;
	private String password;
	private String url;
	private String driverClassName;
	public String getDbUniqueName() {
		return dbUniqueName;
	}
	public void setDbUniqueName(String dbUniqueName) {
		this.dbUniqueName = dbUniqueName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	@Override
	public String toString() {
		return "DataSourcePojo [dbUniqueName=" + dbUniqueName + ", userName=" + userName + ", password=" + password
				+ ", url=" + url + ", driverClassName=" + driverClassName + "]";
	}

}
