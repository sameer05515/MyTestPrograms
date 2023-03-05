package com.ist.iagent.designer.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ist.iagent.admin.db.ServiceManagementRPC;
import com.ist.iagent.core.service.ServiceLoaderRPC;
import com.ist.iagent.core.service.util.ServiceConfigInfo;
import com.ist.iagent.designer.rpc.DocumentationRpc;
import com.ist.iagent.designer.rpc.SwfRpc;
import com.ist.iagent.designer.rpc.UploadImageRpc;
import com.ist.iagent.designer.rpc.XmlRpc;
import com.ist.iagent.designer.util.FileUtil;
import com.ist.iagent.designer.util.FolderZiper;
import com.ist.iagent.designer.util.PropertyUtil;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;

public class ExportModuleHandler {

	private static final Logger log = Logger
			.getLogger(ExportModuleHandler.class);
	
	public static final String IAGENT_SERVICE_FILE="iagent-service-config.xml";  
	protected static ServiceManagementRPC serviceMgmtRpc;
	protected static String EXPORT_FOLDER = "export-projects";
	protected static File exportProjectPath;
	protected XmlRpc xmlRpc;
	protected static UploadImageRpc imageUploadRpc;
	protected static SwfRpc swfRpc;
	protected static DocumentationRpc documentationRpc;
	protected static String serverLibPath = PropertyUtil.getInstance()
			.getValueForKey("serverLibPath");

	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();

	private static ServiceLoaderRPC serviceRpc;

	public ExportModuleHandler() {

		exportProjectPath=getProjectStoreFolder();
		if(exportProjectPath==null){
			log.error("project export url or folder not found.");
			return;
		}
		
		if (imageUploadRpc == null) {
			imageUploadRpc = new UploadImageRpc();
		}

		if (swfRpc == null) {
			swfRpc = new SwfRpc();
		}
		if (documentationRpc == null) {
			documentationRpc = new DocumentationRpc();
		}
		if (serviceRpc == null) {
			serviceRpc = new ServiceLoaderRPC();
		}
		if(serviceMgmtRpc==null){
			serviceMgmtRpc=new ServiceManagementRPC();
		}
	}

	public static File getProjectStoreFolder() {
		
		File exportProjectFolder=null;
		
		String exportFileSaveUrl = PropertyUtil.getInstance().getValueForKey(
				"exportFileSaveUrl");
		String exportFileSaveFolderName = PropertyUtil.getInstance()
				.getValueForKey("exportFileSaveFolderName");

		if (exportFileSaveUrl == null) {
			log.error("export file save url not found");
			return null;
		}
		if (exportFileSaveFolderName == null) {
			log.error("export file save folder path not found");
			return null;
		}

		if (exportFileSaveFolderName != null
				&& exportFileSaveFolderName.trim().length() > 2) {
			EXPORT_FOLDER = exportFileSaveFolderName;
		}
		log.debug("export Path found :" + exportFileSaveUrl);
		log.debug("export folder name found:" + exportFileSaveFolderName);

		File f = FileUtil.createFolderAtUrl(exportFileSaveUrl);

		if (f != null) {
			exportProjectFolder = FileUtil.createFolderAtUrl(f.getAbsolutePath()
					+ "/" + EXPORT_FOLDER);
		} else {
			log.debug("unable to create export folder." + exportFileSaveUrl);
		}
		return exportProjectFolder;
	}

	public XmlRpc getXmlRpc() {
		return xmlRpc;
	}

	public void setXmlRpc(XmlRpc xmlRpc) {
		this.xmlRpc = xmlRpc;
	}

	public String exportModule(ExportPojo exportPojo) throws Exception {
		if (exportPojo == null) {
			return null;
		}
		File f = null;
		List<String> swfFiles = new ArrayList<String>();
		List<String> imagesFilesList = new ArrayList<String>();
		List<Document> localModuleXmlList;
		List<String> jarsToExport = new ArrayList<String>();
		List<String> methodNames=null;
		try {

			localModuleXmlList = getAllLocaleModuleXmlList(
					exportPojo.getModuleName(), exportPojo.isExportRefXml());

			if (localModuleXmlList == null) {
				throw new Exception("Unable to export module.");
			}

			for (Document doc : localModuleXmlList) {

				if (exportPojo.isExportSwf()) {
					log.debug("getting swf list to export for document name :"
							+ doc.getDocumentURI());
					swfFiles.addAll(getComponentXmlValues(doc, "swfLoader",
							new String[] { "swfFileName" }));
				}
				// getting components images name list
				if (exportPojo.isExportImages()) {

					log.debug("getting images list to export for document name :"
							+ doc.getDocumentURI());

					imagesFilesList.addAll(getValuesByTagName(doc, "tabPanel",
							new String[] { "tabBackgroundDownFillSource",
									"tabBackgroundOverFillSource",
									"tabBackgroundSelectedFillSource",
									"tabBackgroundUpFillSource" }));

					imagesFilesList.addAll(getValuesByTagName(doc,
							"tabContainer", new String[] {
									"buttonBackgroundUpFillSource",
									"buttonBackgroundDownFillSource",
									"buttonBackgroundSelectedFillSource",
									"buttonBackgroundOverFillSource" }));

					imagesFilesList.addAll(getComponentXmlValues(doc, "image",
							new String[] { "source" }));
					imagesFilesList.addAll(getComponentXmlValues(doc, "button",
							new String[] { "Icon", "mouseOverIcon",
									"mouseDownIcon" }));
					imagesFilesList.addAll(getComponentXmlValues(doc,
							"fileUpload", new String[] { "Icon",
									"mouseOverIcon", "mouseDownIcon" }));
					imagesFilesList
							.addAll(getComponentXmlValues(doc, "tree",
									new String[] { "leafNodeIconSource",
											"nodeCloseIconSource",
											"nodeOpenIconSource" }));
					imagesFilesList.addAll(getComponentXmlValues(doc,
							"searchDataGrid", new String[] {
									"nextBtnMouseOverIcon",
									"nextBtnMouseDownIcon", "nextBtnIcon",
									"previousBtnMouseOverIcon",
									"previousBtnMouseDownIcon",
									"previousBtnIcon",
									"buttonBackgroundUpFillSource",
									"buttonBackgroundDownFillSource",
									"buttonBackgroundSelectedFillSource",
									"buttonBackgroundOverFillSource" }));

					imagesFilesList.addAll(getImagesFromActionTag(doc,imageUploadRpc.getImageUploadFolderName()));

				}

				if (exportPojo.isExportJars()) {
					log.debug("getting jars list to export for document name :"
							+ doc.getDocumentURI());

					methodNames = getValuesByTagName(doc,
							"rpcService", new String[] { "serviceName" });
					if (methodNames != null) {
						//jarsToExport=serviceRpc.getDBBackUP(methodNames);
						ServiceConfigInfo obj=serviceRpc.getDBBackUP(methodNames);
						jarsToExport=obj.getJarNames();
						/*for (String methodName : methodNames) {
							String jarName = getJarName(methodName);
							if (jarName != null) {
								jarsToExport.add(jarName);
							}
						}*/
					}
				}

			}

			f = new File(exportProjectPath.getAbsolutePath() + "/"
					+ exportPojo.getModuleName());
			FileUtil.removeFolder(f);// removing old folder

			f.mkdir();

			File xmlFolder = new File(f.getAbsolutePath() + "/xml");
			xmlFolder.mkdir();
			
			exportModuleFiles(xmlFolder, localModuleXmlList);

			if (exportPojo.isExportPublishXml()) {
				exportPublishModuleFiles(xmlFolder, localModuleXmlList);
			}

			if (exportPojo.isExportlocaleXml()) {
				exportLocaleXmlFiles(xmlFolder, localModuleXmlList);
			}

			if (exportPojo.isExportDocumentationXml()
					|| exportPojo.isExportDocumentationPdf()) {
				exportDocumentationXml(xmlFolder, localModuleXmlList,
						exportPojo.isExportDocumentationXml(),
						exportPojo.isExportDocumentationPdf());
			}

			if (exportPojo.isExportImages()) {
				exportModuleImagesFiles(f, imagesFilesList);
			}

			if (exportPojo.isExportSwf()) {
				exportModuleSwfFiles(f, swfRpc.getFileUploadUrl(), swfFiles);
			}
			
			if (exportPojo.isExportJars()) {
				// creating folder for jar files-----------------------
				File jarFolder = new File(f.getAbsolutePath() + "/lib");
				jarFolder.mkdir();
				// creating folder jar files-----------------------

				for (String jarName : jarsToExport) {
					// saving swf files--------------
					serverLibPath = serverLibPath.endsWith("/") ? serverLibPath
							: serverLibPath + "/";
					FileUtil.copyFile(new File(serverLibPath + jarName),
							new File(jarFolder.getAbsolutePath() + "/"
									+ jarName));
				}
				
				//------------ creating iagent-service-config.xml--------------
				File tempFile=new File(f.getAbsolutePath()+"/"+IAGENT_SERVICE_FILE);
				tempFile.createNewFile();
				//List<String> methodsNamesList=new ArrayList<String>();
				//methodsNamesList.addAll(jarsToExport);
				if(methodNames!=null){
					printToFile(tempFile, serviceMgmtRpc.exportDataToXML(methodNames));
				}
				//serviceMgmtRpc.exportDataToXML(tempFile,jarsNameList);
				//serviceMgmtRpc.exportDataToXML(tempFile);
				//------------ creating iagent-service-config.xml--------------
				
			}

			exportPojo.saveReleaseNotes(f);
			exportPojo.saveObjectProperties(f);
			// zip module------------------------
			FolderZiper.zipFolder(f.getAbsolutePath(), f.getAbsolutePath()+ ".bap");
			// ----- parent folder name ( xml folder )+/+export-project
			// folder/filename
			/*return f.getParentFile().getParentFile().getName() + "/"
					+ f.getParentFile().getName() + "/" + f.getName() + ".bap";*/
			return f.getName() + ".bap";

		} catch (Exception e) {
			log.error("Error in exporting module. ", e);
			throw e;
		} finally {
			// remove temp folder------------
			FileUtil.removeFolder(f);
		}
		
	}
	
	private void printToFile(File xmlFile,Document dom) throws IOException {

		  try {
			  if(dom==null || xmlFile==null){
				  return;
			  }
		   OutputFormat format = new OutputFormat(dom);
		   // dom.toXM;
		   format.setIndenting(true);
		   OutputStream outputStream = new FileOutputStream(xmlFile);
		   XMLSerializer serializer = new XMLSerializer(outputStream, format);
		   serializer.serialize(dom);
		   outputStream.close();
		  } catch (FileNotFoundException e) {
		   System.out.println("FileNotFoundException : " + e);
		   log.debug("FileNotFoundException : ", e);
		   throw e;
		  } catch (IOException e) {
		   System.out.println("IOException : " + e);
		   log.debug("IOException : ", e);
		   throw e;
		  }

		 }

	private void exportModuleFiles(File destinationDir,
			List<Document> localModuleXmlList) {
		
		File designerFilesFolder = new File(destinationDir.getAbsolutePath()
				+ "/" + xmlRpc.getDesignerXmlFolderName());
		designerFilesFolder.mkdir();// creating folder

		File localModuleFolder = new File(designerFilesFolder.getAbsolutePath()+"/"+xmlRpc.getLocalModulesFolder().getName());
		localModuleFolder.mkdir();
		
		
		// copying local module xml files---------------------------
		for (Document doc : localModuleXmlList) {
			String fileName = FileUtil.getFileName(doc.getDocumentURI());
			FileUtil.copyFile(getModuleFile(fileName), new File(
					localModuleFolder.getAbsolutePath() + "/" + fileName));
		}
		// copying local module xml files ends---------------------------
	}

	private void exportLocaleXmlFiles(File destinationDir,
			List<Document> localModuleXmlList) {
		log.debug("--------- copying locale file starts ------------");
		File dataFileFolder = new File(destinationDir.getAbsolutePath() + "/"
				+ xmlRpc.getLocaleRpc().getLocaleSavePath().getName());
		dataFileFolder.mkdir();// creating folder

		for (Document doc : localModuleXmlList) {
			String fileName = FileUtil.getFileName(doc.getDocumentURI());
			fileName = fileName.substring(0, fileName.lastIndexOf("."))
					+ "_locale.xml";
			log.debug("copying locale file : " + fileName);
			FileUtil.copyFile(new File(xmlRpc.getLocaleRpc()
					.getLocaleSavePath().getAbsolutePath()
					+ "/" + fileName),
					new File(dataFileFolder.getAbsolutePath() + "/" + fileName));
		}
		log.debug("--------- copying locale file ends ------------");
	}

	private void exportDocumentationXml(File destinationDir,
			List<Document> localModuleXmlList, boolean isExportXml,
			boolean isExportPdf) {
		log.debug("--------- copying documentaion file starts ------------");
		File dataFileFolder = new File(destinationDir.getAbsolutePath() + "/"
				+ documentationRpc.getDocumentationSavePath().getName());
		dataFileFolder.mkdir();// creating folder

		for (Document doc : localModuleXmlList) {
			String fileName = FileUtil.getFileName(doc.getDocumentURI());

			if (isExportXml) {
				log.debug("copying documentation xml file : " + fileName);
				FileUtil.copyFile(new File(documentationRpc
						.getDocumentationSavePath().getAbsolutePath()
						+ "/"
						+ fileName), new File(dataFileFolder.getAbsolutePath()
						+ "/" + fileName));
			}

			if (isExportPdf) {
				fileName = fileName.substring(0, fileName.lastIndexOf("."))
						+ ".pdf";
				log.debug("copying documentation pdf file : " + fileName);
				FileUtil.copyFile(new File(documentationRpc
						.getDocumentationSavePath().getAbsolutePath()
						+ "/"
						+ fileName), new File(dataFileFolder.getAbsolutePath()
						+ "/" + fileName));
			}

		}
		log.debug("--------- copying documentaion file ends ------------");
	}

	private void exportPublishModuleFiles(File destinationDir,
			List<Document> localModuleXmlList) {
		File dataFileFolder = new File(destinationDir.getAbsolutePath() + "/"
				+ xmlRpc.getIagentXML().getPublishXmlSaveFolder().getName());
		dataFileFolder.mkdir();// creating folder

		for (Document doc : localModuleXmlList) {
			String fileName = FileUtil.getFileName(doc.getDocumentURI());
			FileUtil.copyFile(new File(xmlRpc.getIagentXML()
					.getPublishXmlSaveFolder().getAbsolutePath()
					+ "/" + fileName),
					new File(dataFileFolder.getAbsolutePath() + "/" + fileName));
		}
	}

	private void exportModuleSwfFiles(File destinationDir, String sourcePath,
			List<String> swfFiles) {
		// creating folder for swf files
		log.debug("------------  copying swf files ----------------------");
		File swfFolder = new File(destinationDir.getAbsolutePath() + "/"
				+ swfRpc.getModuleUploadFolder());

		// creating folder swf files
		swfFolder.mkdir();

		for (String swfFileName : swfFiles) {
			// saving swf files--------------
			log.debug("copying swf files :" + swfFileName);
			FileUtil.copyFile(new File(sourcePath + "/" + swfFileName),
					new File(swfFolder.getAbsolutePath() + "/" + swfFileName));
		}
		log.debug("------------  copying swf files saved----------------------");
	}

	private void exportModuleImagesFiles(File destinationDir,
			List<String> imagesFilesList) {
		// creating folder for image-----------------------
		log.debug("------------  copying module images starts ----------------------");
		File imageFolder = new File(destinationDir.getAbsolutePath() + "/"
				+ imageUploadRpc.getImageUploadFolderName());
		imageFolder.mkdir();
		// creating folder for image-----------------------

		for (String imageName : imagesFilesList) {
			// saving image--------------
			FileUtil.copyFile(new File(imageUploadRpc.getImageUploadFolder()
					.getAbsolutePath() + "/" + imageName),
					new File(imageFolder.getAbsolutePath() + "/" + imageName));
		}
		log.debug("------------  copying module images done ----------------------");
	}

	private List<String> getImagesFromActionTag(Document doc,String imageUploadFolderName) {
		List<String> list = new ArrayList<String>();

		if (doc != null) {
			NodeList nodes = doc.getElementsByTagName("action");
			log.debug("Finding images in action tag ");
			for (int i = 0; i < nodes.getLength(); i++) {

				Node node = nodes.item(i);
				if (node.getAttributes() == null) {
					continue;
				}
				// log.debug("getImagesFromActionTag -------"+node.getTextContent());
				if (node.getAttributes().getNamedItem("data") == null) {
					continue;
				}
				String value = node.getAttributes().getNamedItem("data")
						.getNodeValue();
				// log.debug("getImagesFromActionTag -------"+value);
				if (value != null
						&& !value.isEmpty()
						&& value.contains(imageUploadFolderName)
						&& value.contains(".")) {
					value = value.substring(value.indexOf("/") + 1);
					list.add(value);

				}

			}
		}
		return list;
	}

//	public String getJarName(String methodName) {
//		return serviceRpc.getJarNameForService(methodName);
//	}

	private List<Document> getAllLocaleModuleXmlList(String mainModule,
			boolean recursive) throws SAXException, IOException,
			ParserConfigurationException {

		List<Document> list = new ArrayList<Document>();

		mainModule = mainModule.endsWith(".xml") ? mainModule
				: (mainModule + ".xml");
		Document doc = docFactory.newDocumentBuilder().parse(
				getModuleFile(mainModule));
		list.add(doc);
		if (recursive) {
			List<String> xmlRefFileNameList = getComponentXmlValues(doc,
					"xmlReference", new String[] { "source" });
			for (String xmlRefFileName : xmlRefFileNameList) {
				list.addAll(getAllLocaleModuleXmlList(xmlRefFileName, recursive));
			}
		}

		return list;
	}

	private List<String> getValuesByTagName(Document doc, String containerType,
			String[] attribToFindValue) {
		List<String> list = new ArrayList<String>();

		if (doc != null) {
			NodeList nodes = doc.getElementsByTagName(containerType);
			log.debug("Finding xml tag " + containerType);
			for (int i = 0; i < nodes.getLength(); i++) {

				Node node = nodes.item(i);
				if (node.getAttributes() == null) {
					continue;
				}

				for (String item : attribToFindValue) {
					// log.debug("Finding xml tag values "+containerType+"  "+item);

					if (nodes.item(i).getAttributes().getNamedItem(item) == null) {
						continue;
					}
					list.add(nodes.item(i).getAttributes().getNamedItem(item)
							.getNodeValue());
				}
			}
		}
		return list;
	}

	private List<String> getComponentXmlValues(Document doc,
			String componentType, String[] attribToFindValue) {
		List<String> list = new ArrayList<String>();

		if (doc != null) {
			NodeList nodes = doc.getElementsByTagName("component");

			for (int i = 0; i < nodes.getLength(); i++) {

				Node node = nodes.item(i);

				if (node.getAttributes() == null
						|| node.getAttributes().getNamedItem("componentType") == null) {

					continue;
				}

				if (node.getAttributes().getNamedItem("componentType")
						.getNodeValue().equals(componentType)) {

					for (String item : attribToFindValue) {
						Node nodeItem=nodes.item(i).getAttributes().getNamedItem(item);
						if(nodeItem!=null){
						list.add(nodeItem.getNodeValue());
						}
					}
				}

			}
		}
		return list;
	}

	private File getModuleFile(String moduleName) {
		File localModule = xmlRpc.getLocalModulesFolder();
		File file = new File(localModule.getAbsolutePath() + "/" + moduleName);
		log.debug("finding module file. " + localModule.getAbsolutePath() + "/"
				+ moduleName);

		if (file.exists() && file.isFile()) {
			log.debug("module file found. " + localModule.getAbsolutePath()
					+ "/" + moduleName);
			return file;
		} else {
			log.error("module file not found. " + localModule.getAbsolutePath()
					+ "/" + moduleName);
			return null;
		}

	}

	// public static void main(String[] args) {
	//
	// }

}
