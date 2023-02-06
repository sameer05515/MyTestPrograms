package com.ist.iagent.designer.rpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.ist.iagent.designer.export.ExportModuleHandler;
import com.ist.iagent.designer.export.ExportPojo;
import com.ist.iagent.designer.export.ImportModuleHandler;
import com.ist.iagent.designer.export.ImportPojo;
import com.ist.iagent.designer.util.PropertyUtil;


public class XmlRpc {

	// private String xmlFolderPath;
	// private File xmlFolder;
	private String DESIGNER_XML_FOLDER = "/designerData";
	private static final Logger log = Logger.getLogger(XmlRpc.class);
	// private static final String MODULE="MODULE";
	private static final String GLOBEL_MODULE = "GLOBEL_MODULE";
	private File localModulesFolder;
	private File globelModulesFolder;
	private PublishRPC iagentXML;
	private LocaleRpc localeRpc;
	private CtiBarSave ctiBarRpc;
	private ExportModuleHandler projectExport;
	private ImportModuleHandler projectImport;

	public XmlRpc() {
		String xmlFolderPath = PropertyUtil.getInstance().getValueForKey(
				"xmlSaveURL");
		String designerSaveFolderName = PropertyUtil.getInstance()
				.getValueForKey("desigerSaveFolderName");
		if (designerSaveFolderName != null
				&& designerSaveFolderName.trim().length() > 2) {
			DESIGNER_XML_FOLDER = "/" + designerSaveFolderName;
		}

		if (xmlFolderPath == null || xmlFolderPath.trim().length() < 1) {
			log.error("xml save path not found. xmlSaveURL property not exists.");
			return;
		} else {
			log.debug("xml Path found :" + xmlFolderPath);
		}

		File f = new File(xmlFolderPath);
		if (!f.exists() || !f.isDirectory()) {
			log.debug("folder not found creating folder " + xmlFolderPath);
			f.mkdir();
		}

		File xmlFolder = new File(xmlFolderPath + DESIGNER_XML_FOLDER);
		log.debug("checking design folder :" + xmlFolder.getAbsolutePath());
		if (!xmlFolder.exists() || !xmlFolder.isDirectory()) {
			log.debug("folder not found creating folder "
					+ xmlFolder.getAbsolutePath());
			xmlFolder.mkdir();
		}
		localModulesFolder = new File(xmlFolder.getAbsolutePath()
				+ "/localModules");
		if (!localModulesFolder.exists() || !localModulesFolder.isDirectory()) {
			localModulesFolder.mkdir();
		}
		globelModulesFolder = new File(xmlFolder.getAbsolutePath()
				+ "/globelModules");
		if (!globelModulesFolder.exists() || !globelModulesFolder.isDirectory()) {
			globelModulesFolder.mkdir();
		}
		f = null;
		xmlFolder = null;

		iagentXML = new PublishRPC(xmlFolderPath);
		localeRpc = new LocaleRpc(xmlFolderPath);
		ctiBarRpc = new CtiBarSave(xmlFolderPath);
		projectExport=new ExportModuleHandler();
		projectExport.setXmlRpc(this);
		
		projectImport=new ImportModuleHandler();
		projectImport.setXmlRpc(this);
	}
	
	public PublishRPC getIagentXML() {
		return iagentXML;
	}

	public void setIagentXML(PublishRPC iagentXML) {
		this.iagentXML = iagentXML;
	}

	public LocaleRpc getLocaleRpc() {
		return localeRpc;
	}

	public void setLocaleRpc(LocaleRpc localeRpc) {
		this.localeRpc = localeRpc;
	}
	
	public String getDesignerXmlFolderName(){
		return DESIGNER_XML_FOLDER;
	}

	public File getLocalModulesFolder() {
		return localModulesFolder;
	}

	public void setLocalModulesFolder(File localModulesFolder) {
		this.localModulesFolder = localModulesFolder;
	}
	
	/*
	 * public List<XmlFileData> getDesignerFilesNamesList(String moduleType){
	 * List<String> list=new ArrayList<String>(); File[] files;
	 * if(moduleType!=null && moduleType.equals(GLOBEL_MODULE)){
	 * files=globelModulesFolder.listFiles(); }else{
	 * files=localModulesFolder.listFiles(); } if(files.length>0){ for(int
	 * i=0;i<files.length;i++){ if(files[i].isFile() &&
	 * files[i].getName().endsWith(".img")){ list.add(files[i].getName());
	 * }else{ list.add(files[i].getName()); } } } return list; }
	 */

	public byte[] getImageData(String moduleType, String fileName) {
		byte[] imgData = new byte[10];
		File imgFile;
		FileInputStream fin = null;
		String imgFileName = fileName.substring(0, fileName.indexOf('.'))
				+ ".img";
		if (moduleType != null && moduleType.equals(GLOBEL_MODULE)) {
			imgFile = new File(globelModulesFolder.getAbsolutePath() + "/"
					+ imgFileName);
		} else {
			imgFile = new File(localModulesFolder.getAbsolutePath() + "/"
					+ imgFileName);
		}
		log.debug("getting image data:" + imgFile.getAbsolutePath());
		try {
			if (imgFile.exists() && imgFile.isFile()) {
				log.debug("image file found ");
				imgData = new byte[(int) imgFile.length()];
				fin = new FileInputStream(imgFile);
				fin.read(imgData);
			}
		} catch (Exception e) {
			log.error("Error in reading file :" + imgFile.getAbsolutePath(), e);
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
			} catch (Exception e) {
				log.error(
						"Error in reading file :" + imgFile.getAbsolutePath(),
						e);
			}
		}
		return imgData;
	}

	public List<String> getDesignerFilesNamesList(String moduleType) {
		List<String> list = new ArrayList<String>();
		File[] files;
		log.debug("getting designer files List module Type :" + moduleType);
		if (moduleType != null && moduleType.equals(GLOBEL_MODULE)) {
			files = globelModulesFolder.listFiles();
		} else {
			files = localModulesFolder.listFiles();
		}
		log.debug("Module Type :" + moduleType + " total files found : "
				+ files.length);
		if (files.length > 0) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].getName().endsWith(".xml")
						|| files[i].getName().endsWith(".bak")) {
					list.add(files[i].getName());
					log.debug("add file in list :" + files[i].getName());
				}
			}
		}
		return list;
	}

	public String readDesignerFile(String moduleType, String fileName) {
		StringBuilder fileData;
		File file = null;
		if (moduleType != null && moduleType.equals(GLOBEL_MODULE)) {
			file = new File(globelModulesFolder.getAbsolutePath() + "/"
					+ fileName);
		} else {
			file = new File(localModulesFolder.getAbsolutePath() + "/"
					+ fileName);
		}
		log.debug("reading file :" + file.getAbsolutePath());
		// FileReader freader = null;

		BufferedReader breader = null;
		try {

			if (file.exists() && file.isFile()) {
				log.debug("file found :" + file.getAbsolutePath());

				breader = new BufferedReader(new InputStreamReader(
						new FileInputStream(file.getAbsolutePath()), "UTF-8"));
				String data = null;
				fileData = new StringBuilder();
				while ((data = breader.readLine()) != null) {
					log.trace(data);
					fileData.append(data);
				}

				return fileData.toString();
			} else {
				throw new FileNotFoundException("File not found : " + fileName);
			}
		} catch (Exception e) {
			log.error("Error in reading file :" + file.getAbsolutePath(), e);
		} finally {
			try {
				if (breader != null) {
					breader.close();
				}

			} catch (Exception e) {
				log.error("Error in closing file handler :", e);
			}
		}
		return null;
	}

	public Boolean isFileExists(String moduleType, String fileName) {
		File file = null;
		if (moduleType != null && moduleType.equals(GLOBEL_MODULE)) {
			file = new File(globelModulesFolder.getAbsolutePath() + "/"
					+ fileName);
		} else {
			file = new File(localModulesFolder.getAbsolutePath() + "/"
					+ fileName);
		}
		log.debug("checking file to exists or not :" + file.getAbsolutePath());
		if (file.exists() && file.isFile()) {
			log.debug("file exists : " + fileName);
			return true;
		} else {
			log.debug("file not exists" + fileName);
			return false;
		}
	}

	public Boolean deleteModule(String moduleType, String fileName) {

		try {

			log.debug("deleting file :" + fileName);
			File file = null;
			File fileImage = null;
			String imgFileName = fileName.substring(0, fileName.indexOf('.'))
					+ ".img";
			if (moduleType != null && moduleType.equals(GLOBEL_MODULE)) {
				file = new File(globelModulesFolder.getAbsolutePath() + "/"
						+ fileName);
				fileImage = new File(globelModulesFolder.getAbsolutePath()
						+ "/" + imgFileName);
			} else {
				file = new File(localModulesFolder.getAbsolutePath() + "/"
						+ fileName);
				fileImage = new File(localModulesFolder.getAbsolutePath() + "/"
						+ imgFileName);
			}

			if (fileImage.exists() && file.isFile()) {
				fileImage.delete();
			}
			if (file.exists() && file.isFile()) {
				log.debug("file deleted :" + file.getAbsolutePath());
				deleteIAgentXML(fileName);
				return file.delete();
			}
		} catch (Exception e) {
			log.error("Error in deleting file " + fileName, e);
		}

		return false;
	}

	/*
	 * private void deleteBackUpFile(String absoluteFileNamePath) throws
	 * Exception{ if(absoluteFileNamePath!=null){ File f=new
	 * File(absoluteFileNamePath+".bak"); if(f.exists() && f.isFile()){
	 * log.debug(''); } } }
	 */

	public Boolean writeDesignerFile(String moduleType, String fileName,
			String fileData, byte[] imgData, boolean makeBackup) {
		log.debug("saving file");
		String fileSave = null;
		File imgSave = null;
		Writer fwriter = null;
		FileOutputStream imgWriter = null;

		try {

			String imgName = fileName.substring(0, fileName.indexOf('.'))
					+ ".img";
			if (moduleType != null && moduleType.equals(GLOBEL_MODULE)) {
				fileSave = globelModulesFolder.getAbsolutePath() + "/"
						+ fileName;
				imgSave = new File(globelModulesFolder.getAbsolutePath() + "/"
						+ imgName);
			} else {
				fileSave = localModulesFolder.getAbsolutePath() + "/"
						+ fileName;
				imgSave = new File(localModulesFolder.getAbsolutePath() + "/"
						+ imgName);
			}

			if (isFileExists(moduleType, fileName) && makeBackup) {
				createBackUpFile(fileSave);
			}
			log.debug("removing old desigener xml and image file");
			deleteModule(moduleType, fileName);

			log.debug("writing to file" + fileSave);

			fwriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(fileSave), "UTF-8"));

			fwriter.write(fileData);
			fwriter.flush();
			fwriter.close();

			if (imgSave != null) {
				log.debug("saving image" + imgSave.getAbsolutePath());
				imgWriter = new FileOutputStream(imgSave);
				imgWriter.write(imgData);
				imgWriter.flush();
			}
			return true;
		} catch (Exception e) {
			log.error("Error in reading file :" + fileSave, e);
		} finally {
			try {
				if (fwriter != null) {
					fwriter.close();
				}
				if (imgWriter != null) {
					imgWriter.close();
				}
			} catch (Exception e) {
				log.error("Error in closing file handler :", e);
			}
		}
		return false;
	}

	/**
	 * create backup (rename file as .bak) of the specified by absoluteFile if
	 * exists
	 * 
	 * @param absoluteFile
	 *            file path and name
	 */
	private void createBackUpFile(String absoluteFile) throws Exception {

		File f = new File(absoluteFile);
		if (f.exists() && f.isFile()) {
			log.debug("File found for backup " + absoluteFile
					+ ". Creating backup ");
			File backupFile = new File(absoluteFile + ".bak");

			if (backupFile.exists() && backupFile.isFile()) {
				backupFile.delete();
			}
			f.renameTo(backupFile);
		}

	}

	public boolean saveLocaleFile(String fileName, String fileData) {
		return localeRpc.saveLocaleFile(fileName, fileData);
	}

	public String readLocaleFile(String fileName) {
		return localeRpc.readLocaleFile(fileName);
	}

	public boolean deleteLocaleFile(String fileName) {
		return localeRpc.deleteLocaleFile(fileName);
	}

	public boolean saveIAgentXML(String fileName, String fileData) {
		return iagentXML.saveIAgentXML(fileName, fileData);
	}

	public boolean deleteIAgentXML(String fileName) {
		return iagentXML.deleteIAgentXML(fileName);
	}

	public boolean deleteCtiBarXML(String fileName) {
		return ctiBarRpc.deleteCtibarXML(fileName);
	}

	public boolean saveCtibarXML(String fileName, String fileData) {
		return ctiBarRpc.saveCtibarFile(fileName, fileData);
	}

	public String getFinessesUrl(){
		return PropertyUtil.getInstance().getValueForKey("finesseServletUrl");
	}
	public String exportModule(ExportPojo exportPojo) throws Exception{
		return projectExport.exportModule(exportPojo);
	}
	
	public ImportPojo uploadModule(String moduleName) throws Exception{
		return projectImport.uploadModule(moduleName);
	}
	public boolean importModule(ImportPojo importPojo) throws Exception{
		return projectImport.importModule(importPojo);
	}
	
}
