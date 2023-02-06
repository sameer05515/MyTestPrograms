package com.ist.iagent.designer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

public class FolderZiper {

	private static Logger log = Logger.getLogger(FolderZiper.class);

	static public void zipFolder(String srcFolder, String destZipFile)
			throws Exception {
		log.debug("zip process started");
		log.debug("source Folder : "+srcFolder);
		log.debug("destination Folder : "+destZipFile);
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;

		fileWriter = new FileOutputStream(destZipFile);
		zip = new ZipOutputStream(fileWriter);
		
		addFolderToZip("", srcFolder, zip);
		zip.flush();
		zip.close();
		fileWriter.flush();
		fileWriter.close();
		log.debug("zip process complete");
	}

	static private void addFileToZip(String path, String srcFile,
			ZipOutputStream zip) throws Exception {
		FileInputStream in=null;
		try{
		
		File folder = new File(srcFile);
		if (folder.isDirectory()) {
			addFolderToZip(path, srcFile, zip);
		} else {
			byte[] buf = new byte[1024];
			int len;
			in = new FileInputStream(srcFile);
			zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
			while ((len = in.read(buf)) > 0) {
				zip.write(buf, 0, len);
			}
		}
		}finally{
			if(in!=null){
			in.close();
			}
		}
	}

	static private void addFolderToZip(String path, String srcFolder,
			ZipOutputStream zip) throws Exception {
		File folder = new File(srcFolder);

		for (String fileName : folder.list()) {
			if (path.equals("")) {
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			} else {
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/"
						+ fileName, zip);
			}
		}
	}

//	 public static void main(String[] a) throws Exception {
//	 String srcFolder =
//	 "D:/SychTest/Admin/apache-tomcat-6.0.16/webapps/ROOT/data";
//	 String destZipFile =
//	 "D:/SychTest/Admin/apache-tomcat-6.0.16/webapps/ROOT/data.zip";
//	 // String srcFolder="D:/iagent/conf";
//	 // String destZipFile="D:/iagent/conf.zip";
//	 System.out.println("zip process started");
//	 zipFolder(srcFolder, destZipFile);
//	 System.out.println("zip process complete");
//	 }
}