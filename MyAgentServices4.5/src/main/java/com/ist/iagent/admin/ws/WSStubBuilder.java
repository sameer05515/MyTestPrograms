package com.ist.iagent.admin.ws;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.extensions.soap.SOAPOperation;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;

import org.apache.log4j.Logger;

import com.ibm.wsdl.factory.WSDLFactoryImpl;
import com.ist.iagent.admin.db.pojo.WSdlDTO;
import com.ist.iagent.admin.util.PropertyUtil;
import com.isuite.iagent.commons.servelet.ISuiteContextListener;

public class WSStubBuilder {

	private static Logger log = Logger.getLogger(WSStubBuilder.class);

	private static final String SOURCE_DIR_KEY = "wssource.dir";
	private static final String PACKAGE_NAME_KEY = "wspackage.name";
	private static final String CLASS_DIR_KEY = "wsclass.dir";
	private static final String WEB_SERVICE_JAR_NAME_KEY = "webservice.jar.name";
	private static final String WSDL_URL_KEY = "wswsdl.url.path";

	private static final String CXF_FOLDER_KEY = "cxf.folder";

	private static final String SERVER_DIR_LIB_KEY = "server.directory.lib";

	private static final String WXF_FOLDER_PATH_KEY = "wsCxfFolderPath";

	/**
	 * Adds serviceclassname and soapbindinginterface from given wsdlDTO's wsdl
	 * url
	 * 
	 * @param WSdlDTO
	 *            objWSdlDTO
	 * 
	 * @return WSdlDTO
	 * 
	 * @throws WSDLException
	 */

	public WSdlDTO setServiceClassAndBindingInterface(WSdlDTO objWSdlDTO)
			throws WSDLException {

		Definition implDef = null;
		String wsdl = objWSdlDTO.getWsURL();
		log.debug("Finding details for wsdlURL : " + wsdl);
		/** first get the definition object got the WSDL impl */
		try {
			WSDLFactory factory = new WSDLFactoryImpl();
			WSDLReader reader = factory.newWSDLReader();
			implDef = reader.readWSDL(wsdl);
			/**
			 * ###############
			 * 
			 * SERVICES
			 * 
			 * ###############
			 */
			Map serviceMap = implDef.getServices();
			Set serviceKeySet = serviceMap.keySet();
			if (serviceKeySet != null) {
				Iterator itr = serviceKeySet.iterator();
				while (itr.hasNext()) {
					javax.wsdl.QName objj = (javax.wsdl.QName) itr.next();
					com.ibm.wsdl.ServiceImpl serviceImpl = (com.ibm.wsdl.ServiceImpl) serviceMap
							.get(objj);

					Map m = serviceImpl.getPorts();
					if (m.keySet() != null) {
						Iterator ii = m.keySet().iterator();
						if (ii != null) {
							while (ii.hasNext()) {
								String oo = (String) ii
										.next();
								com.ibm.wsdl.PortImpl objPortImpl = (com.ibm.wsdl.PortImpl) m
										.get(oo);
								List binding = objPortImpl.getBinding()
										.getBindingOperations();
								if (binding != null) {
									javax.wsdl.BindingOperation bindingOperationImpl = (com.ibm.wsdl.BindingOperationImpl) binding
											.get(0);

									List extList = bindingOperationImpl
											.getExtensibilityElements();
									if (extList != null) {
										ExtensibilityElement extElement = (ExtensibilityElement) extList
												.get(0);
										if (extElement instanceof SOAPOperation) {
											log.debug("SoapBindingInterface == "
													+ oo);
											objWSdlDTO
													.setSoapBindingInterface(oo);
										}
									}
								}
							}
						}
					}
					log.debug("serviceClass == "
							+ serviceImpl.getQName().getLocalPart());
					objWSdlDTO.setServiceClass(serviceImpl.getQName()
							.getLocalPart());
				}
			}
		} catch (WSDLException e) {
			log.error("Unable to retreive Details for WSDL " + wsdl, e);
			throw e;
		}
		return objWSdlDTO;
	}

	/**
	 * After setting values in host.properties
	 * 
	 * (1)creates stub ,
	 * 
	 * (2)makes jar and
	 * 
	 * (3) deploys jar into context libs
	 * 
	 * @param String
	 *            sourceDir
	 * @param String
	 *            packageName
	 * @param String
	 *            classDir
	 * @param String
	 *            webServiceJarName
	 * @param String
	 *            wsdlUrlPath
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws Throwable
	 */
	public void createStub(String sourceDir, String packageName,
			String classDir, String webServiceJarName, String wsdlUrlPath)
			throws FileNotFoundException, IOException, Throwable {
		try {
			String wsCxfFolderPath = PropertyUtil.getInstance()
					.getValueForKey(WXF_FOLDER_PATH_KEY).replace("\\", "/");
			log.debug("wsCxfFolderPath ==" + wsCxfFolderPath);
			String fileName = PropertyUtil.getInstance()
					.getValueForKey(WXF_FOLDER_PATH_KEY).replace("\\", "/")
					+ File.separator + "host.properties";
			File file = new File(fileName);
			if (!file.exists()) {
				log.error("File not found =" + file.toString());
			} else {
				log.debug("file found " + file.toString());
			}
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			StringBuffer strBuff = new StringBuffer("");
			String str;
			while ((str = br.readLine()) != null) {
				if (str.contains("=")) {
					if (str.contains("#")) {
						strBuff.append(str);
						strBuff.append('\n');
						/**
						 * int k = str.indexOf("="); int l = str.indexOf("#");
						 * String keyName = str.substring(l + 1, k); if
						 * ((keyName.trim())
						 * .equalsIgnoreCase(PropertyFileConstants
						 * .EMAIL_SERVER)) { strBuff = setValue(strBuff,
						 * keyName, emailServer); } else { strBuff.append(str);
						 * strBuff.append('\n'); }
						 */
					} else {
						int k = str.indexOf("=");
						String keyName = str.substring(0, k);
						if ((keyName.trim()).equalsIgnoreCase(SOURCE_DIR_KEY)) {
							if (sourceDir != null
									&& !sourceDir.trim().equalsIgnoreCase("")) {
								strBuff = setValue(strBuff, keyName, sourceDir);
							} else {
								throw new Exception(
										"please provide sourceDir value");
							}
						} else if ((keyName.trim())
								.equalsIgnoreCase(PACKAGE_NAME_KEY)) {

							if (packageName != null
									&& !packageName.trim().equalsIgnoreCase("")) {
								strBuff = setValue(strBuff, keyName,
										packageName.toLowerCase());
							} else {
								throw new Exception(
										"please provide packageName value");
							}
						} else if ((keyName.trim())
								.equalsIgnoreCase(CLASS_DIR_KEY)) {

							if (classDir != null
									&& !classDir.trim().equalsIgnoreCase("")) {
								strBuff = setValue(strBuff, keyName, classDir);
							} else {
								throw new Exception(
										"please provide classDir value");
							}
						} else if ((keyName.trim())
								.equalsIgnoreCase(WEB_SERVICE_JAR_NAME_KEY)) {
							if (webServiceJarName != null
									&& !webServiceJarName.trim()
											.equalsIgnoreCase("")) {
								strBuff = setValue(strBuff, keyName,
										webServiceJarName.toLowerCase());
							} else {
								throw new Exception(
										"please provide webServiceJarName value");
							}
						} else if ((keyName.trim())
								.equalsIgnoreCase(WSDL_URL_KEY)) {

							if (wsdlUrlPath != null
									&& !wsdlUrlPath.trim().equalsIgnoreCase("")) {
								strBuff = setValue(strBuff, keyName,
										wsdlUrlPath);
							} else {
								throw new Exception(
										"please provide wsdlUrlPath value");
							}
						} else if ((keyName.trim())
								.equalsIgnoreCase(CXF_FOLDER_KEY)) {

							wsCxfFolderPath = PropertyUtil.getInstance()
									.getValueForKey(WXF_FOLDER_PATH_KEY);
							if (wsCxfFolderPath != null
									&& !wsCxfFolderPath.trim()
											.equalsIgnoreCase("")) {
								wsCxfFolderPath = PropertyUtil.getInstance()
										.getValueForKey(WXF_FOLDER_PATH_KEY)
										.replace("\\", "/");
								strBuff = setValue(strBuff, keyName,
										wsCxfFolderPath);
							} else {
								throw new Exception(
										"please provide wsCxfFolderPath value");
							}
						} else if ((keyName.trim())
								.equalsIgnoreCase(SERVER_DIR_LIB_KEY)) {
							String serverLibDirFolder = ISuiteContextListener
									.getContextPath().replace("\\", "/")
									+ "WEB-INF/lib";

							if (serverLibDirFolder != null
									&& !serverLibDirFolder.trim()
											.equalsIgnoreCase("")) {
								strBuff = setValue(strBuff, keyName,
										serverLibDirFolder.replace("\\", "/"));
							} else {
								throw new Exception(
										"please provide serverLibDirFolder value");
							}
						} else {
							strBuff.append(str);
							strBuff.append('\n');
						}
					}
				} else {
					strBuff.append(str);
					strBuff.append('\n');
				}
			}
			in.close();

			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write(strBuff.toString());
			out.flush();
			out.close();

			/**
			 * ## refreshing the property file ###
			 */
			runBuildFile();

		} catch (FileNotFoundException e) {
			log.error("File not found", e);
			throw e;
		} catch (IOException e) {
			log.error("Unable to read file", e);
			throw e;
		} catch (Throwable e) {
			log.error("Unable to read file", e);
			throw e;
		}
	}

	private StringBuffer setValue(StringBuffer strBuff, String keyName,
			String value) {
		if (strBuff != null && keyName != null
				&& (!keyName.trim().equalsIgnoreCase("")) && value != null) {
			strBuff.append(keyName.trim());
			strBuff.append("=");
			strBuff.append(value);
			strBuff.append('\n');
		}
		return strBuff;
	}

	private void runBuildFile() throws IOException {

		Properties propCommonUtility = new Properties();
		String wsCxfFolderPath = PropertyUtil.getInstance()
				.getValueForKey(WXF_FOLDER_PATH_KEY).replace("\\", "/");
		String fileName = wsCxfFolderPath + File.separator + "host.properties";
		File f = new File(fileName);
		if (f.exists()) {
			FileInputStream in = new FileInputStream(f);
			propCommonUtility.load(in);
			log.debug("sourceDir = "
					+ propCommonUtility.getProperty(SOURCE_DIR_KEY)
					+ "\npackageName = "
					+ propCommonUtility.getProperty(PACKAGE_NAME_KEY)
					+ "\nclassDir = "
					+ propCommonUtility.getProperty(CLASS_DIR_KEY)
					+ "\nwebServiceJarName = "
					+ propCommonUtility.getProperty(WEB_SERVICE_JAR_NAME_KEY)
					+ "\nwsdlUrlPath = "
					+ propCommonUtility.getProperty(WSDL_URL_KEY));
			log.debug("wsCxfFolderPath == " + wsCxfFolderPath);
			Runtime pr = Runtime.getRuntime();
			Process p = pr.exec("ant.bat -buildfile " + wsCxfFolderPath);
			final InputStream inp = p.getInputStream();
			int ch;
			StringBuffer sb = new StringBuffer();
			while ((ch = inp.read()) != -1) {
				sb.append((char) ch);
			}
			log.debug(sb.toString());
		} else {
			log.error("property file not found");
		}
	}

	/**
	 * @param args
	 * @throws Throwable
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	// public static void main(String[] args) throws FileNotFoundException,
	// IOException, Throwable {
	//
	// Runtime pr = Runtime.getRuntime();
	// Process p = pr
	// .exec("ant.bat -buildfile "
	// +
	// "D:\\rpctest\\iagentAdminServiceMGMTNew\\apache-tomcat-6.0.16\\webapps\\jarupload\\wss");
	// final InputStream inp = p.getInputStream();
	// int ch;
	// StringBuffer sb = new StringBuffer();
	// while ((ch = inp.read()) != -1) {
	// sb.append((char) ch);
	// }
	//
	// log.debug(sb.toString());
	//
	// }
}
