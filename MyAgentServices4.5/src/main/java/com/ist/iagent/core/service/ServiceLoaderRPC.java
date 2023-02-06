package com.ist.iagent.core.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.pojo.IAgentService;
import com.ist.iagent.admin.db.pojo.JavaService;
import com.ist.iagent.admin.db.pojo.QueryService;
import com.ist.iagent.admin.db.pojo.WSService;
import com.ist.iagent.core.service.message.IAgentServiceRequest;
import com.ist.iagent.core.service.message.IAgentServiceResponse;
import com.ist.iagent.core.service.util.ServiceConfigInfo;

@WebService
public class ServiceLoaderRPC {

	private static DateFormat formatter = new SimpleDateFormat(
			"dd-MM-yyyy  HH:mm:ss");
	private static final Logger log = Logger.getLogger(ServiceLoaderRPC.class);

	/**
	 * loads all IAgentServices from database
	 * 
	 * @return String response of loading
	 * */
	public String loadAllServices() {
		return ServiceLoader.getInstance().loadAllServices();
	}

	/**
	 * @returns all IAgentServices as List
	 * */
	public List<IAgentService> getAllServices() {
		return ServiceLoader.getInstance().getAllServices();
	}

	/**
	 * @returns all javaServices as List
	 * */
	public List<JavaService> getJavaServices() {
		List<JavaService> javaServices = ServiceLoader.getInstance()
				.getJavaServices();
		return javaServices;
	}

	/**
	 * @returns all WSService as List
	 * */
	public List<WSService> getWSServices() {
		List<WSService> wsServices = ServiceLoader.getInstance()
				.getWSServices();
		return wsServices;
	}

	/**
	 * @returns an IAgentService object from database for a given serviceName
	 * */
	public IAgentService getService(String ServiceName) {
		return ServiceLoader.getInstance().getService(ServiceName);
	}

	/**
	 * executes a given serviceName with required arguments
	 * 
	 * @returns Object result of given service
	 * */
	public IAgentServiceResponse execute(
			IAgentServiceRequest agentServiceRequest) throws Exception {

		ServiceExecutor serExec = new ServiceExecutor();
		Object retObj = null;
		retObj = serExec.execute(agentServiceRequest);
		IAgentServiceResponse response = new IAgentServiceResponse();
		response.setAgentServiceRequest(agentServiceRequest);
		response.setResponseObject(retObj);
		return response;
	}

	/**
	 * executes a given serviceName with required arguments
	 * 
	 * if web service channel is used
	 * 
	 * @returns Object result of given service
	 * */

	// public IAgentServiceResponse executeWithWsChannel(
	// IAgentServiceRequest agentServiceRequest) throws Exception {
	//
	// ServiceExecutor serExec = new ServiceExecutor();
	// Object retObj = serExec.executeWithWsChannel(agentServiceRequest);
	// IAgentServiceResponse response = new IAgentServiceResponse();
	// response.setAgentServiceRequest(agentServiceRequest);
	// log.debug("Result : " + retObj);
	// String str = ISuiteObjectUtil.convertToXMLString(retObj);
	// response.setResponseObject(str);
	// // print(str, "C:/Documents and Settings/Admin/Desktop/output.xml");
	// if (retObj != null)
	// log.debug("Returning result : "
	// + response.getResponseObject().toString());
	// return response;
	// }

	// private void print(String text, String filename) {
	// try {
	// File file = new File(filename);
	// if (!file.exists()) {
	// if (!file.getParentFile().exists()) {
	// file.getParentFile().mkdirs();
	// }
	// file.createNewFile();
	// }
	// PrintStream printStream = new PrintStream(
	// new FileOutputStream(file));
	// printStream.print(text);
	// printStream.close();
	// System.out.println("file has been written");
	// } catch (Throwable e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * @returns all QueryService as List
	 * */
	public List<QueryService> getQueryServices() {
		List<QueryService> wsServices = ServiceLoader.getInstance()
				.getQueryServices();
		return wsServices;
	}

	public ServiceConfigInfo getDBBackUP(List<String> ServiceList) {

		return ServiceLoader.getInstance().getDBBackUP(ServiceList);
	}

}
