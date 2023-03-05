package com.ist.iagent.admin.datasource;

public class DBModel {

	private String databaseName;
	private String urlModel;
	private String driverClassName;
	private boolean userNameRequired;
	private boolean passwordRequired;

	public DBModel() {

	}

	public DBModel(String databaseName, String urlModel,
			String driverClassName, boolean userNameRequired,
			boolean passwordRequired) {
		this.databaseName = databaseName;
		this.urlModel = urlModel;
		this.driverClassName = driverClassName;
		this.userNameRequired = userNameRequired;
		this.passwordRequired = passwordRequired;
	}

	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @param databaseName
	 *            the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * @return the urlModel
	 */
	public String getUrlModel() {
		return urlModel;
	}

	/**
	 * @param urlModel
	 *            the urlModel to set
	 */
	public void setUrlModel(String urlModel) {
		this.urlModel = urlModel;
	}

	/**
	 * @return the driverClassName
	 */
	public String getDriverClassName() {
		return driverClassName;
	}

	/**
	 * @param driverClassName
	 *            the driverClassName to set
	 */
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	/**
	 * @return the userNameRequired
	 */
	public boolean getUserNameRequired() {
		return userNameRequired;
	}

	/**
	 * @param userNameRequired
	 *            the userNameRequired to set
	 */
	public void setUserNameRequired(boolean userNameRequired) {
		this.userNameRequired = userNameRequired;
	}

	/**
	 * @return the passwordRequired
	 */
	public boolean getPasswordRequired() {
		return passwordRequired;
	}

	/**
	 * @param passwordRequired
	 *            the passwordRequired to set
	 */
	public void setPasswordRequired(boolean passwordRequired) {
		this.passwordRequired = passwordRequired;
	}

}
