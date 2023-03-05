package com.isuite.iagent.commons.servelet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class ISuiteContextListener implements ServletContextListener {

	private static String confFolderPath;
	private static String contextPath;

	static Logger log = Logger.getLogger(ISuiteContextListener.class);

	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent evt) {

		ServletContext context = evt.getServletContext();

		confFolderPath = context.getRealPath("/WEB-INF/conf");
		contextPath = context.getRealPath("/");

		log.debug("Context path: " + contextPath);
		log.debug("Conf folder path: " + confFolderPath);

	}

	public static String getConfFolderPath() {
		return confFolderPath;
	}

	public static String getContextPath() {
		return contextPath;
	}

}
