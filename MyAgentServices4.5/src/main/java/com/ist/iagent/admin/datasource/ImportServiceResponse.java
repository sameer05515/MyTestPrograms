package com.ist.iagent.admin.datasource;

import java.util.ArrayList;
import java.util.List;

import com.ist.iagent.admin.db.pojo.DataSource;
import com.ist.iagent.admin.db.pojo.IAgentService;
import com.ist.iagent.admin.db.pojo.JarDTO;
import com.ist.iagent.admin.db.pojo.WSdlDTO;

public class ImportServiceResponse {

	public static final int JAR_NAME_CONFLICT = 101;
	public static final int WSDL_NAME_CONFLICT = 102;
	public static final int DATA_SOURCE_NAME_CONFLICT = 103;
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;

	private int code;
	private String message;
	private List<IAgentService> successfullySavedServices = new ArrayList<IAgentService>();
	private List<IAgentService> nameConflictedServices = new ArrayList<IAgentService>();
	private List<JarDTO> conflictedJar = new ArrayList<JarDTO>();
	private List<WSdlDTO> conflictedWsdlName = new ArrayList<WSdlDTO>();
	private List<DataSource> conflictedDataSourceName = new ArrayList<DataSource>();

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the successfullySavedServices
	 */
	public List<IAgentService> getSuccessfullySavedServices() {
		return successfullySavedServices;
	}

	/**
	 * @param successfullySavedServices
	 *            the successfullySavedServices to set
	 */
	public void setSuccessfullySavedServices(
			List<IAgentService> successfullySavedServices) {
		this.successfullySavedServices = successfullySavedServices;
	}

	/**
	 * @return the nameConflictedServices
	 */
	public List<IAgentService> getNameConflictedServices() {
		return nameConflictedServices;
	}

	/**
	 * @param nameConflictedServices
	 *            the nameConflictedServices to set
	 */
	public void setNameConflictedServices(
			List<IAgentService> nameConflictedServices) {
		this.nameConflictedServices = nameConflictedServices;
	}

	/**
	 * @return the conflictedJar
	 */
	public List<JarDTO> getConflictedJar() {
		return conflictedJar;
	}

	/**
	 * @param conflictedJar
	 *            the conflictedJar to set
	 */
	public void setConflictedJar(List<JarDTO> conflictedJar) {
		this.conflictedJar = conflictedJar;
	}

	/**
	 * @return the conflictedWsdlName
	 */
	public List<WSdlDTO> getConflictedWsdlName() {
		return conflictedWsdlName;
	}

	/**
	 * @param conflictedWsdlName
	 *            the conflictedWsdlName to set
	 */
	public void setConflictedWsdlName(List<WSdlDTO> conflictedWsdlName) {
		this.conflictedWsdlName = conflictedWsdlName;
	}

	/**
	 * @return the conflictedDataSourceName
	 */
	public List<DataSource> getConflictedDataSourceName() {
		return conflictedDataSourceName;
	}

	/**
	 * @param conflictedDataSourceName
	 *            the conflictedDataSourceName to set
	 */
	public void setConflictedDataSourceName(
			List<DataSource> conflictedDataSourceName) {
		this.conflictedDataSourceName = conflictedDataSourceName;
	}

}
