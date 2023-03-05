package com.ist.iagent.core.service.property.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.isuite.iagent.commons.servelet.ISuiteContextListener;

public class PropertyUtil {

	static Logger log = Logger.getLogger(PropertyUtil.class);
	static Properties properties = new Properties();
	private static PropertyUtil thisInsatace;

	public static PropertyUtil getInstance() {
		if (thisInsatace == null) {
			thisInsatace = new PropertyUtil();
		}
		return thisInsatace;
	}

	private PropertyUtil() {

		try {
			String propertyFilePath = ISuiteContextListener.getConfFolderPath()
					+ "/iagent-core.properties";
			log.info("Finding " + propertyFilePath + " ........");

			File propertyFile = new File(propertyFilePath);

			if (propertyFile.exists()) {
				FileInputStream in = new FileInputStream(propertyFile);
				properties.load(in);
			} else {
				log.error("Property file not found system may have not work properly");
			}
		} catch (FileNotFoundException e) {
			log.error("FileNotFoundException : ", e);
		} catch (IOException e) {
			log.error("IOException : ", e);
		} catch (Exception e) {
			log.error("Exception : ", e);
		}

	}

	public String getProperty(String key) {

		return properties.getProperty(key);
	}
}
