package com.ist.iagent.admin.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.isuite.iagent.commons.servelet.ISuiteContextListener;

public class PropertyUtil {

	static Logger log = Logger.getLogger(PropertyUtil.class);
	static Properties propCommonUtility = new Properties();
	private static PropertyUtil thisInsatace;

	static {
		try {
			String fileName = ISuiteContextListener.getConfFolderPath()
					+ "/AdminUtility.properties";
			File f = new File(fileName);

			if (f.exists()) {
				FileInputStream in = new FileInputStream(f);
				propCommonUtility.load(in);
			} else {
				log.debug("property file not found");
			}
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException : ", e);
		} catch (IOException e) {
			log.error("IOException : ", e);
		} catch (Exception e) {
			log.error("Exception : ", e);
		}
	}

	public static PropertyUtil getInstance() {
		if (thisInsatace == null) {
			thisInsatace = new PropertyUtil();
		}
		return thisInsatace;
	}

	private PropertyUtil() {

	}

	public String getValueForKey(String key) {
		/**
		 * String currentDir = System.getProperty("user.dir");
		 * log.debug("Current dir using System:" + currentDir);
		 */
		String p = propCommonUtility.getProperty(key);
		//log.debug(key + " : " + p);
		return p;
	}
}
