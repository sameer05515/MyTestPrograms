package com.ist.iagent.admin.db.pojo;

public class JarDTO {

	int jar_id;
	String jar_name;
	boolean jarExistsOnServer;
	
	public int getJar_id() {
		return jar_id;
	}	
	
	public void setJar_id(int jar_id) {
		this.jar_id = jar_id;
	}	
	
	public String getJar_name() {
		return jar_name;
	}	
	
	public void setJar_name(String jar_name) {
		this.jar_name = jar_name;
	}

	public boolean isJarExistsOnServer() {
		return jarExistsOnServer;
	}

	public void setJarExistsOnServer(boolean jarExistsOnServer) {
		this.jarExistsOnServer = jarExistsOnServer;
	}
}
