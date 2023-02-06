package com.ist.iagent.admin.xml;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.util.PropertyUtil;
import com.ist.iagent.admin.xml.response.XMLSaveResponse;

public class XMLRPCHandler {

	static String destDir = PropertyUtil.getInstance().getValueForKey(
			"serverXMLFolderPath");
	static Logger log = Logger.getLogger(XMLRPCHandler.class);

	/**
	 * Gives names of all XML files saved
	 * 
	 * @return List of all required XML Files' name
	 */
	public List<String> getAllXmlNames() {
		List<String> list = new ArrayList<String>();
		File f = new File(destDir);
		File[] files = f.listFiles();
		for (int count = 0; count < files.length; count++) {
			list.add(files[count].getName());
		}
		return list;
	}

	/**
	 * Gives content of given XML File name
	 * 
	 * @param fileName
	 *            name of given XML file
	 * @return List of all required classes' name
	 */
	public String getXMLContent(String fileName) {

		fileName = destDir + "/" + fileName;
		File file = new File(fileName);
		if (file.exists()) {
			String output = "";
			FileInputStream fstream = null;
			DataInputStream in = null;
			BufferedReader br = null;
			try {
				fstream = new FileInputStream(file);
				in = new DataInputStream(fstream);
				br = new BufferedReader(new InputStreamReader(in));

				String str;
				while ((str = br.readLine()) != null) {
					output = output + "\n" + str;
				}
				in.close();
			} catch (FileNotFoundException e) {
				log.error("FileNotFoundException : ", e);
			} catch (IOException e) {
				log.error("FileNotFoundException : ", e);
			} catch (Exception e) {
				log.error("Exception : ", e);
			} finally {
				try {
					if (fstream != null) {
						fstream.close();
					}
				} catch (Exception e) {
				}

				try {
					if (in != null) {
						in.close();
					}
				} catch (Exception e) {
				}

				try {
					if (br != null) {
						br.close();
					}
				} catch (Exception e) {
				}
			}
			return output;
		} else {
			return file.getName() + " not exists ! ";
		}
	}

	/**
	 * Saves content of given XML file name Checks if XML file name exists in
	 * database
	 * 
	 * @param fileName
	 *            name of given XML file
	 * @param content
	 *            the content of given XML File
	 * @param override
	 *            the boolen value for permission to update
	 * @return XMLSaveResponse response of save
	 */
	public XMLSaveResponse saveXMLFile(String fileName, String content,
			boolean override) {
		XMLSaveResponse xmlSaveResponse = new XMLSaveResponse();
		String str = fileName + " not saved !";
		if (!fileName.endsWith(".xml")) {
			fileName = fileName + ".xml";
		}
		String fileNameWithfullClassPath = destDir + "/" + fileName;
		File file = new File(fileNameWithfullClassPath);
		if (file.exists() && (!override)) {
			xmlSaveResponse.setErrorCode(1);
			xmlSaveResponse.setErrorMessage("XML File Name Exists");
			xmlSaveResponse.setMessage("do you want to override ?");
			return xmlSaveResponse;
		} else {
			try {
				/** Create file */
				FileWriter fstream = new FileWriter(file);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(content);
				/** Close the output stream */
				out.close();
				str = fileName + " saved !";
				xmlSaveResponse.setErrorCode(0);
				xmlSaveResponse.setErrorMessage("saved succesfully");
				xmlSaveResponse.setMessage(str);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				str = fileName + " not saved ! \n" + e.getMessage();
				xmlSaveResponse.setErrorCode(2);
				xmlSaveResponse
						.setErrorMessage("Exception During XML File save");
				xmlSaveResponse.setMessage(str);
			}
		}
		return xmlSaveResponse;
	}

	
	


}
