package com.ist.iagent.admin.util;

import java.io.File;
import java.sql.Connection;

import org.apache.log4j.Logger;

import com.isuite.iagent.commons.servelet.ISuiteContextListener;
import com.isuite.ist.db.util.C3P0ConnectionUtility;

public class DBUtil {

	private static Logger log = Logger.getLogger(DBUtil.class);
	private static DBUtil thisInsatace;
	private static final String FILENAME = "/AdminUtility.properties";
	private static final String DATASOURCE_NAME = "iagent-service-db";

	private DBUtil() {
		C3P0ConnectionUtility.getInstance().initializeDatasource(
				DATASOURCE_NAME,
				new File(ISuiteContextListener.getConfFolderPath() + FILENAME),
				true);

	}

	public static DBUtil getInstance() {
		if (thisInsatace == null) {
			thisInsatace = new DBUtil();
		}
		return thisInsatace;
	}

	public Connection getConnection() {
		try {
			return C3P0ConnectionUtility.getInstance().getConnection(
					DATASOURCE_NAME);
		} catch (Exception e) {
			log.error("ERROR ON WHILE CONNECTION TO DATA SOURCE" + e);
			return null;
		}
	}

}