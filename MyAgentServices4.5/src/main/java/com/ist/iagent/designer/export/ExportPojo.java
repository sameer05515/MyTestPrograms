package com.ist.iagent.designer.export;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ExportPojo {

	private static transient final Logger log=Logger.getLogger(ExportPojo.class);
	private String moduleName;
	private boolean exportPublishXml;
	private boolean exportlocaleXml;
	private boolean exportSwf;
	private boolean exportRefXml;
	private boolean exportImages;
	private boolean exportJars;
	private boolean exportDocumentationXml;
	private boolean exportDocumentationPdf;
	private transient String releaseNotes;

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public boolean isExportPublishXml() {
		return exportPublishXml;
	}

	public void setExportPublishXml(boolean exportPublishXml) {
		this.exportPublishXml = exportPublishXml;
	}

	public boolean isExportlocaleXml() {
		return exportlocaleXml;
	}

	public void setExportlocaleXml(boolean exportlocaleXml) {
		this.exportlocaleXml = exportlocaleXml;
	}

	public boolean isExportSwf() {
		return exportSwf;
	}

	public void setExportSwf(boolean exportSwf) {
		this.exportSwf = exportSwf;
	}

	public boolean isExportRefXml() {
		return exportRefXml;
	}

	public void setExportRefXml(boolean exportRefXml) {
		this.exportRefXml = exportRefXml;
	}

	public boolean isExportImages() {
		return exportImages;
	}

	public void setExportImages(boolean exportImages) {
		this.exportImages = exportImages;
	}

	public boolean isExportJars() {
		return exportJars;
	}

	public void setExportJars(boolean exportJars) {
		this.exportJars = exportJars;
	}

	public boolean isExportDocumentationXml() {
		return exportDocumentationXml;
	}

	public void setExportDocumentationXml(boolean exportDocumentationXml) {
		this.exportDocumentationXml = exportDocumentationXml;
	}

	public boolean isExportDocumentationPdf() {
		return exportDocumentationPdf;
	}

	public void setExportDocumentationPdf(boolean exportDocumentationPdf) {
		this.exportDocumentationPdf = exportDocumentationPdf;
	}

	public String getReleaseNotes() {
		return releaseNotes;
	}

	public void setReleaseNotes(String releaseNotes) {
		this.releaseNotes = releaseNotes;
	}
	/**
	 * create a release not file in specified folder
	 * @param destinationFolder valid folder path
	 * @throws Exception
	 */
	public void saveReleaseNotes(File destinationFolder) throws Exception{
		if(destinationFolder==null){
			return;
		}
		if(!destinationFolder.exists()){
			destinationFolder.mkdir();
		}
		
		BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destinationFolder.getAbsolutePath()+"/Readme.txt"),"UTF-8"));
		fw.write("==============================================");
		fw.newLine();
		fw.write("================= IAGENT 4.5 =================");
		fw.newLine();
		fw.write("Module Name   : "+getModuleName());
		fw.newLine();
		fw.write("Creation Date : "+new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date()));
		fw.newLine();
		fw.write("==============================================");
		fw.newLine();
		if(releaseNotes!=null){
			fw.write(releaseNotes);
		}
		fw.flush();
		fw.close();
	}

	/**
	 * serialize this object in specified folder, file named data.data
	 * 
	 * @param destinationFolder
	 * @throws Exception
	 */
	public void saveObjectProperties(File destinationFolder) throws Exception {
		if (destinationFolder == null) {
			return;
		}
		if (!destinationFolder.exists()) {
			destinationFolder.mkdir();
		}
		
		FileOutputStream fout=new FileOutputStream(destinationFolder.getAbsolutePath() + "/data.dat");
		
		Properties p=new Properties();
		p.put("moduleName", moduleName);
		p.put("exportPublishXml", String.valueOf(exportPublishXml));
		p.put("exportlocaleXml", String.valueOf(exportlocaleXml));
		p.put("exportSwf", String.valueOf(exportSwf));
		p.put("exportRefXml", String.valueOf(exportRefXml));
		p.put("exportImages", String.valueOf(exportImages));
		p.put("exportJars", String.valueOf(exportJars));
		p.put("exportDocumentationXml", String.valueOf(exportDocumentationXml));
		p.put("exportDocumentationPdf", String.valueOf(exportDocumentationPdf));
		p.store(fout, "");
		fout.flush();
		fout.close();
//		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
//				destinationFolder.getAbsolutePath() + "/data.dat"));
//		out.writeObject(this);
//		out.flush();
//		out.close();
	}
	
	public void readReleaseNotes(File destinationFolder) throws Exception {
		if (destinationFolder == null) {
			return;
		}
		if (!destinationFolder.exists() || !destinationFolder.isDirectory()) {
			return;
		}

		File f=new File(destinationFolder.getAbsolutePath() + "/Readme.txt");
		if(!f.exists() || !f.isFile()){
			return;
		}
		
		
		BufferedReader fw = new BufferedReader(new InputStreamReader(
				new FileInputStream(f), "UTF-8"));
		String data;
		releaseNotes="";
	
		while((data=fw.readLine())!=null){
			releaseNotes+=data+"\n";
		}
		fw.close();
	}
	
	/**
	 * de-serialize this object from specified folder, file named data.data
	 * 
	 * @param destinationFolder
	 * @throws Exception
	 */
	public void setObjectProperties(File destinationFolder) throws Exception {
		if (destinationFolder == null) {
			return;
		}
		log.debug("reading object properties : "+destinationFolder.getAbsolutePath());
		if (!destinationFolder.exists() || !destinationFolder.isDirectory()) {
			return;
		}
		
		File f=new File(destinationFolder.getAbsolutePath() + "/data.dat");
		if(!f.exists() || !f.isFile()){
			return;
		}
		log.debug("object property file found : ");
		
		FileInputStream fin=new FileInputStream(f);
		Properties p=new Properties();
		p.load(fin);
		
		moduleName=p.getProperty("moduleName");
		exportPublishXml=Boolean.parseBoolean(p.getProperty("exportPublishXml"));
		exportlocaleXml=Boolean.parseBoolean(p.getProperty("exportlocaleXml"));
		exportSwf=Boolean.parseBoolean(p.getProperty("exportSwf"));
		exportRefXml=Boolean.parseBoolean(p.getProperty("exportRefXml"));
		exportImages=Boolean.parseBoolean(p.getProperty("exportImages"));
		exportJars=Boolean.parseBoolean(p.getProperty("exportJars"));
		exportDocumentationXml=Boolean.parseBoolean(p.getProperty("exportDocumentationXml"));
		exportDocumentationPdf=Boolean.parseBoolean(p.getProperty("exportDocumentationPdf"));
		
		fin.close();
	}
}
