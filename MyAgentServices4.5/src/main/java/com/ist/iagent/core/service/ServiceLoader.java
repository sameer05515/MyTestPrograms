package com.ist.iagent.core.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.ServiceManagementRPC;
import com.ist.iagent.admin.db.pojo.IAgentService;
import com.ist.iagent.admin.db.pojo.JavaService;
import com.ist.iagent.admin.db.pojo.QueryService;
import com.ist.iagent.admin.db.pojo.WSService;
import com.ist.iagent.core.service.util.ServiceConfigInfo;

public class ServiceLoader {
	private static final Logger log = Logger.getLogger(ServiceLoader.class);
	private static ServiceLoader thisInsatace;
	private List<JavaService> javaServices = new ArrayList<JavaService>();
	private List<WSService> wsServices = new ArrayList<WSService>();
	private List<QueryService> queryServices = new ArrayList<QueryService>();
	private List<IAgentService> iagentService = new ArrayList<IAgentService>();

	/** Singleton Pattern */
	private ServiceLoader() {
		loadAllServices();
	}

	public static ServiceLoader getInstance() {
		if (thisInsatace == null) {
			thisInsatace = new ServiceLoader();
			log.debug("new instance of ServiceLoader class created");
		}
		return thisInsatace;
	}

	/** non static methods */

	private List<JavaService> getAllJarServices() {
		ServiceManagementRPC objSerMgmtRPC = new ServiceManagementRPC();
		try {
			javaServices = objSerMgmtRPC.getServiceList();
		} catch (SQLException e) {
			log.debug(e.getMessage());
		}
		return javaServices;
	}

	private List<WSService> getAllWSServices() {
		ServiceManagementRPC objSerMgmtRPC = new ServiceManagementRPC();
		try {
			wsServices = objSerMgmtRPC.getWSServiceList();
		} catch (SQLException e) {
			log.debug(e.getMessage());
		}
		return wsServices;
	}

	private List<QueryService> getAllQueryServices() {
		ServiceManagementRPC objSerMgmtRPC = new ServiceManagementRPC();
		try {
			queryServices = objSerMgmtRPC.getQueryServiceList();
		} catch (SQLException e) {
			log.debug(e.getMessage());
		}
		return queryServices;
	}

	public String loadAllServices() {

		javaServices.clear();
		wsServices.clear();
		queryServices.clear();
		iagentService.clear();

		javaServices = getAllJarServices();
		wsServices = getAllWSServices();
		queryServices = getAllQueryServices();
		for (IAgentService ser : javaServices) {
			iagentService.add(ser);
		}
		for (IAgentService ser : wsServices) {
			iagentService.add(ser);
		}
		for (IAgentService ser : queryServices) {
			iagentService.add(ser);
		}
		String msg = "Total services added :- " + iagentService.size()
				+ "\n no of java Services :- " + javaServices.size()
				+ "\n no of web Services :- " + wsServices.size()
				+ "\n no of query Services :- " + queryServices.size();
		return msg;
	}

	public List<IAgentService> getAllServices() {
		return iagentService;
	}

	public List<JavaService> getJavaServices() {
		return javaServices;
	}

	public List<WSService> getWSServices() {
		return wsServices;
	}

	public List<QueryService> getQueryServices() {
		return queryServices;
	}

	public IAgentService getService(String ServiceName) {
		for (IAgentService ser : iagentService) {
			if (ser.getServiceName().equals(ServiceName)) {
				return ser;
			}
		}
		return null;
	}

	// private String getJarNameForService(String serviceName) {
	// ServiceManagementRPC objSerMgmtRPC = new ServiceManagementRPC();
	// for (IAgentService ser : iagentService) {
	// if (ser.getServiceName().equals(serviceName)) {
	// if (ser instanceof JavaService) {
	// int jarId = ((JavaService) ser).getLinkedJarId();
	// try {
	// return objSerMgmtRPC.fetchJarName(jarId);
	// } catch (SQLException e) {
	// log.debug("Error during fetch jar name for service : "
	// + serviceName, e);
	// }
	// }
	// }
	// }
	// return null;
	// }

	public ServiceConfigInfo getDBBackUP(List<String> serviceList) {

		ServiceConfigInfo objConfigInfo = new ServiceConfigInfo();
		List<String> jarNames = new ArrayList<String>();
		ServiceManagementRPC objSerMgmtRPC = new ServiceManagementRPC();
		for (String serviceName : serviceList) {
			for (IAgentService ser : iagentService) {
				if (ser.getServiceName().equals(serviceName)) {
					/** __ if service if JavaService __ */
					if (ser instanceof JavaService) {
						int jarId = ((JavaService) ser).getLinkedJarId();
						try {
							String jarName = objSerMgmtRPC.fetchJarName(jarId);
							if (jarName != null) {
								jarNames.add(jarName);
							}
						} catch (SQLException e) {
							log.debug(
									"Error during fetch jar name for service : "
											+ serviceName, e);
						}
					}
					/** __ if service if WSService __ */
					if (ser instanceof WSService) {
						int wsId = ((WSService) ser).getLinkedWSId();
						String stubJarName = objSerMgmtRPC
								.getStubJarOfWSdlDTOForID(wsId);
						if (stubJarName != null) {
							jarNames.add(stubJarName);
						}
					}

					/** __ if service if QueryService __ */

				}
			}
		}
		objConfigInfo.setJarNames(jarNames);
		return objConfigInfo;
	}
}
