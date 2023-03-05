package com.ist.iagent.admin.db.pojo;

public class WSdlDTO {
	private int wsId;
	private String wsName;
	private String wsURL;
	private String nameSpace;
	private String usePrefix;
	
	/**
	 * private var _soapBindingInterface:String=""; private var
	 * _serviceClass:String=""; private var _wsPackageName:String="";
	 */

	private String soapBindingInterface;
	private String serviceClass;
	private String wsPackageName;
	
	
	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public int getWsId() {
		return wsId;
	}

	public void setWsId(int wsId) {
		this.wsId = wsId;
	}

	public String getWsName() {
		return wsName;
	}

	public void setWsName(String wsName) {
		this.wsName = wsName;
	}

	public String getWsURL() {
		return wsURL;
	}

	public void setWsURL(String wsURL) {
		this.wsURL = wsURL;
	}

	public String getUsePrefix() {
		return usePrefix;
	}

	public void setUsePrefix(String usePrefix) {
		this.usePrefix = usePrefix;
	}

	/**
	 * @return the soapBindingInterface
	 */
	public String getSoapBindingInterface() {
		return soapBindingInterface;
	}

	/**
	 * @param soapBindingInterface the soapBindingInterface to set
	 */
	public void setSoapBindingInterface(String soapBindingInterface) {
		this.soapBindingInterface = soapBindingInterface;
	}

	/**
	 * @return the serviceClass
	 */
	public String getServiceClass() {
		return serviceClass;
	}

	/**
	 * @param serviceClass the serviceClass to set
	 */
	public void setServiceClass(String serviceClass) {
		this.serviceClass = serviceClass;
	}

	/**
	 * @return the wsPackageName
	 */
	public String getWsPackageName() {
		return wsPackageName;
	}

	/**
	 * @param wsPackageName the wsPackageName to set
	 */
	public void setWsPackageName(String wsPackageName) {
		this.wsPackageName = wsPackageName;
	}
}
