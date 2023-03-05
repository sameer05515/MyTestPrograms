package com.isuite.iagent.commons;

import java.util.List;

import org.apache.log4j.Logger;

import com.ist.isuite.license.ISuiteLiceneController;
import com.isuite.iagent.commons.db.IAgentModuleDAO;
import com.isuite.iagent.commons.db.IAgentModuleVO;

public class IAgentModulesLoader {

	private IAgentModuleDAO agentModuleDAO = new IAgentModuleDAO();
	private static Logger log = Logger.getLogger(IAgentModulesLoader.class);

	public void loadModules(ServerDetails serverDetails) {
		log.debug("loadModules");

		List<IAgentModuleVO> modules = agentModuleDAO.getEnabledIAgentModules();
		log.info("There are " + modules.size()
				+ " enabled iagent modules. Going initialize them.");

		for (IAgentModuleVO moduleVO : modules) {
			String className = moduleVO.getModuleClassName();
			try {
				Class clazz = Class.forName(className);
				Object moduleObj = clazz.newInstance();
				if (moduleObj instanceof IAgentModule) {
					IAgentModule module = (IAgentModule) moduleObj;
					log.info("Going to check licensning validity of "
							+ module.getAppType());
					if (ISuiteLiceneController.isValidPackage((module)
							.getAppType())) {
						log.debug("About to call init on "
								+ module.getAppType() + " with "
								+ serverDetails);
						module.init(serverDetails);
					} else {
						log.info(module.getAppType()
								+ " is not licensed to run.");
					}

				}
			} catch (ClassNotFoundException e) {
				log.error("Iagent module class " + className
						+ " is not found in classpath. ", e);
			} catch (InstantiationException e) {
				log.error("Iagent module class " + className
						+ " is not able to initialize. ", e);
			} catch (IllegalAccessException e) {
				log.error("Iagent module class " + className
						+ " is not able to initialize(Illegal Access). ", e);
			} catch (Throwable th) {
				log.error("Iagent module class " + className
						+ " is not loaded. ", th);
			}
		}
	}
}
