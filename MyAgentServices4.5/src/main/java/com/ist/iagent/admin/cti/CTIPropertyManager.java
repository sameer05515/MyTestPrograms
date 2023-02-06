package com.ist.iagent.admin.cti;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.util.PropertyUtil;

public class CTIPropertyManager {

	private static CTIPropertyManager ctiPropertyManagerInstance;
	static Logger log = Logger.getLogger(CTIPropertyManager.class);
	private PropertyUtil propertyUtil = PropertyUtil.getInstance();
	private Properties ctiPropertes = new Properties();

	private CTIPropertyManager() {

		log.debug("Instantiating CTI Manager");
		log.debug("ctiapplet_property_file_path:"
				+ propertyUtil.getValueForKey("ctiapplet_property_file"));
		log.debug("loading applet  properties");
		loadCTIProperties(new File(propertyUtil
				.getValueForKey("ctiapplet_property_file")));

		Enumeration<Object> propertyKeyEnumeration = getAllKeys();

		String keys = "";
		while (propertyKeyEnumeration.hasMoreElements()) {
			keys = keys + "[" + (String) propertyKeyEnumeration.nextElement()
					+ "]";
		}

		log.debug("Applet properties loaded with keys:" + keys);
	}

	public String getCTIType() {
		log.debug("cti_os_type:" + propertyUtil.getValueForKey("cti_os_type"));
		return propertyUtil.getValueForKey("cti_os_type");

	}

	public void loadCTIProperties(File ctipropertyFile) {

		try {
			ctiPropertes.load(new FileInputStream(ctipropertyFile));

		} catch (FileNotFoundException e) {

			log.error(e);
		} catch (IOException e) {

			log.error(e);
		}

	}

	public static CTIPropertyManager getInstance() {

		if (ctiPropertyManagerInstance == null) {

			ctiPropertyManagerInstance = new CTIPropertyManager();
		}

		return ctiPropertyManagerInstance;
	}

	public Enumeration<Object> getAllKeys() {

		return ctiPropertes.keys();
	}

	public Object getProperty(String key) {

		return ctiPropertes.get(key);
	}

	public void writePropertiesInHTMLTemplet(
			ArrayList<PropertyKeyValuePair> propertyList,
			ArrayList<PropertyKeyValuePair> notReadyCodes,
			ArrayList<PropertyKeyValuePair> logoutCodes,
			ArrayList<PropertyKeyValuePair> wrapupCodes) {

		if (propertyList == null) {

			return;
		}

		StringBuffer textToSave = new StringBuffer();

		BufferedReader lineReader = null;
		try {
			lineReader = new BufferedReader(new FileReader(new File(
					propertyUtil.getValueForKey("html_template_wrapper_file"))));
			String line = null;
			while ((line = lineReader.readLine()) != null) {

				for (PropertyKeyValuePair properyKeyValuObject : propertyList) {
					line = line.replace("--" + properyKeyValuObject.getKey()
							+ "--", properyKeyValuObject.getValue());

				}
                
				line=line.replace("<!--@NOT_READY_CODES@-->", getStringForAllCodes(notReadyCodes));
				line=line.replace("<!--@LOGOUT_CODES@-->", getStringForAllCodes(logoutCodes));
				line=line.replace("<!--@WRAPUP_CODES@-->", getStringForAllCodes(wrapupCodes));
				
				textToSave.append(line + "\n");

			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (lineReader != null)
					lineReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		File htmlFile = new File(propertyUtil
				.getValueForKey("html_wrapper_file"));
		FileWriter fileWriter = null;
		try {

			fileWriter = new FileWriter(htmlFile);

			fileWriter.write(textToSave.toString());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e);
			}
		}

	}

	private String getStringForAllCodes(
			ArrayList<PropertyKeyValuePair> reasonCodesList) {

		
		StringBuffer returnString = new StringBuffer();

		if(reasonCodesList==null){
			
			return returnString.toString();
			
		}
		for (PropertyKeyValuePair properyKeyValuObject : reasonCodesList) {
			returnString.append("<PARAM NAME=\""
					+ properyKeyValuObject.getKey() + "\" VALUE=\""
					+ properyKeyValuObject.getValue() + "\"/>");
			returnString.append("\n");
		}

		return returnString.toString();
	}

	public ArrayList<PropertyKeyValuePair> readPropertiesFromHtmlTemplet() {

		ArrayList<PropertyKeyValuePair> propertyList = new ArrayList<PropertyKeyValuePair>();

		BufferedReader lineReader = null;
		try {
			lineReader = new BufferedReader(new FileReader(new File(
					propertyUtil.getValueForKey("html_wrapper_file"))));
			String line = null;
			while ((line = lineReader.readLine()) != null) {

				Enumeration<Object> propertyKeyEnumeration = getAllKeys();

				while (propertyKeyEnumeration.hasMoreElements()) {
					String key = (String) propertyKeyEnumeration.nextElement();

					String[] searchStrings = ("" + getProperty(key)).split(",");
					String searchString1 = "";
					String searchString2 = "";
					if (searchStrings.length > 1) {
						searchString1 = searchStrings[0];
						searchString2 = searchStrings[1];

					}

					if (line.contains(key.replace("--", ""))) {
						line = line.replace(searchString1, "");
						line = line.replace(searchString2, "");

						PropertyKeyValuePair keyValuPair = new PropertyKeyValuePair();
						keyValuPair.setKey(key.replace("--", "").trim());
						keyValuPair.setValue(line.trim());
						propertyList.add(keyValuPair);
					}

				}

			}
		} catch (FileNotFoundException ex) {
			log.error(ex);
		} catch (IOException ex) {
			log.error(ex);
		} finally {
			try {
				if (lineReader != null)
					lineReader.close();
			} catch (IOException ex) {
				log.error(ex);
			}
		}

		return propertyList;
	}

	public ArrayList<PropertyKeyValuePair> readReasonCodesFromHtmlTemplet(
			String reasonCodeTypeString) {

		ArrayList<PropertyKeyValuePair> propertyList = new ArrayList<PropertyKeyValuePair>();

		BufferedReader lineReader = null;
		try {
			lineReader = new BufferedReader(new FileReader(new File(
					propertyUtil.getValueForKey("html_wrapper_file"))));
			String line = null;
			int promptNumber = 1;

			String descriptionString = "";
			String codeString = "";

			String descriptionStartStringToReplace = "";
			String codeStartStringToReplace = "";
			String endStringToReplace = "\"/>";
			boolean codeLineFlag = false;

			while ((line = lineReader.readLine()) != null) {

				descriptionString = reasonCodeTypeString + "_desc_"
						+ promptNumber;
				codeString = reasonCodeTypeString + "_code_" + promptNumber;

				descriptionStartStringToReplace = "<PARAM NAME=\""
						+ descriptionString + "\" VALUE=\"";
				codeStartStringToReplace = "<PARAM NAME=\"" + codeString
						+ "\" VALUE=\"";

				if (line.contains(descriptionStartStringToReplace)) {
					line = line.replace(descriptionStartStringToReplace, "");
					line = line.replace(endStringToReplace, "");
					PropertyKeyValuePair keyValuPair = new PropertyKeyValuePair();
					keyValuPair.setKey(descriptionString);
					keyValuPair.setValue(line.trim());
					propertyList.add(keyValuPair);

				}
				if (line.contains(codeStartStringToReplace)) {
					line = line.replace(codeStartStringToReplace, "");
					line = line.replace(endStringToReplace, "");
					PropertyKeyValuePair keyValuPair = new PropertyKeyValuePair();
					keyValuPair.setKey(codeString);
					keyValuPair.setValue(line.trim());
					propertyList.add(keyValuPair);
					promptNumber++;
				}

			}

		} catch (FileNotFoundException ex) {
			log.error(ex);
		} catch (IOException ex) {
			log.error(ex);
		} finally {
			try {
				if (lineReader != null)
					lineReader.close();
			} catch (IOException ex) {
				log.error(ex);
			}
		}

		return propertyList;
	}

	public static void main(String[] args) {
		CTIPropertyManager propManger = CTIPropertyManager.getInstance();
		System.out.println(propManger.readPropertiesFromHtmlTemplet());

		PropertyKeyValuePair keyp = new PropertyKeyValuePair();
		keyp.setKey("ctios_side_a");
		keyp.setValue("192.168.1.777");

		ArrayList<PropertyKeyValuePair> list = new ArrayList<PropertyKeyValuePair>();
		list.add(keyp);
		// propManger.writePropertiesInHTMLTemplet(list);

	}
}
