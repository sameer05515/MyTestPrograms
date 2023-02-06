package com.ist.iagent.core.service.access.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;

import com.ist.iagent.core.service.access.exception.IAgentAccessException;
import com.ist.iagent.core.service.property.util.PropertyUtil;
import com.isuite.encryption.DataENCHelper;
import com.isuite.iagent.commons.servelet.ISuiteContextListener;

public class ModuleLoader {

	private static final Logger log = Logger.getLogger(ModuleLoader.class);

	public byte[] getModule(String moduleName, String key)
			throws IAgentAccessException {

		String contextPath = ISuiteContextListener.getContextPath();
		
		log.info("Context Path found is:"+contextPath);
		
		PropertyUtil propertyUtil = PropertyUtil.getInstance();

		byte[] bytesToReturn;

		try {

			long timeStamp = Long.parseLong(key);
			if ((System.currentTimeMillis() - timeStamp) > 60000) {

				throw new IAgentAccessException(
						"Access Denied.Delayed Information Received");

			}

		} catch (NumberFormatException e) {

			throw new IAgentAccessException("Invalid Access Key:"
					+ e.getMessage());

		}

		Path templetPath = null;
		if ("admin".equals(moduleName)) {

			templetPath = Paths.get(contextPath + "/templet/ad_templet");
		} else if ("client".equals(moduleName)) {

			templetPath = Paths.get(contextPath + "/templet/cl_templet");
		} else {

			throw new IAgentAccessException("Invalid Module Requested");

		}

		try {

			String isuitePass = propertyUtil.getProperty("isuite_data_algorithm");
			
			Path keyFilePath = Paths.get(propertyUtil
					.getProperty("isuite_key_path"));

		//	log.debug("#RAJAT:TEMP#"+isuitePass+":"+Files.readAllBytes(keyFilePath).length+"#"+Files.readAllBytes(templetPath).length);
			
			
			bytesToReturn = DataENCHelper.decodeDataFromFile(isuitePass,
					keyFilePath, templetPath);

		} catch (IOException e) {

			log.error(e);
			throw new IAgentAccessException(
					"Access Denied.Module Could Not Be Loaded IO Error.");
		} catch (InvalidKeyException e) {
			log.error(e);
			throw new IAgentAccessException("Invalid Key Exception");
		} catch (NoSuchAlgorithmException e) {
			log.error(e);
			throw new IAgentAccessException("Invalid Key Algorithm Exception");

		} catch (InvalidKeySpecException e) {
			log.error(e);
			throw new IAgentAccessException("Invalid Key spec Exception");
		} catch (NoSuchPaddingException e) {
			log.error(e);
			throw new IAgentAccessException("Invalid padding Exception");
		} catch (InvalidAlgorithmParameterException e) {
			log.error(e);
			throw new IAgentAccessException(
					"Invalid Key Algorithm Parameter Exception");
		} catch (IllegalBlockSizeException e) {
			log.error(e);
			throw new IAgentAccessException("Invalid Block Size Exception");
		} catch (BadPaddingException e) {
			log.error(e);
			throw new IAgentAccessException("Bad Padding Exception");
		}

		return bytesToReturn;

	}

}
