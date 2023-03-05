package com.ist.iagent.admin.db.pojo;

public class DataSource {
	
	private int dbID;
	private String dbAlias;
	private String dbUsername;
	private String dbPassword;
	private String dbDriverClassName;
	private String dbURL;

	
	/**
	 * @return the dbID
	 */
	public int getDbID() {
		return dbID;
	}
	/**
	 * @param dbID the dbID to set
	 */
	public void setDbID(int dbID) {
		this.dbID = dbID;
	}
	/**
	 * @return the dbAlias
	 */
	public String getDbAlias() {
		return dbAlias;
	}
	/**
	 * @param dbAlias the dbAlias to set
	 */
	public void setDbAlias(String dbAlias) {
		this.dbAlias = dbAlias;
	}
	/**
	 * @return the dbUsername
	 */
	public String getDbUsername() {
		return dbUsername;
	}
	/**
	 * @param dbUsername the dbUsername to set
	 */
	public void setDbUsername(String dbUsername) {
		this.dbUsername = dbUsername;
	}
	/**
	 * @return the dbPassword
	 */
	public String getDbPassword() {
		return dbPassword;
	}
	/**
	 * @param dbPassword the dbPassword to set
	 */
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	/**
	 * @return the dbDriverClassName
	 */
	public String getDbDriverClassName() {
		return dbDriverClassName;
	}
	/**
	 * @param dbDriverClassName the dbDriverClassName to set
	 */
	public void setDbDriverClassName(String dbDriverClassName) {
		this.dbDriverClassName = dbDriverClassName;
	}
	/**
	 * @return the dbURL
	 */
	public String getDbURL() {
		return dbURL;
	}
	/**
	 * @param dbURL the dbURL to set
	 */
	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}	
}
