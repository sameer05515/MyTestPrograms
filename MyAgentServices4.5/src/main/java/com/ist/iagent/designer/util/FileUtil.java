package com.ist.iagent.designer.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.log4j.Logger;

public class FileUtil {

	private static final Logger log=Logger.getLogger(FileUtil.class);
	
	/**
	 * create a folder at specified url
	 * @param fileSaveUrl
	 * @return
	 */
	public static File createFolderAtUrl(String fileSaveUrl) {
		try {

			File f = new File(fileSaveUrl);
			if (!f.exists() || !f.isDirectory()) {
				log.debug("folder not found creating folder " + fileSaveUrl);
				f.mkdir();
			}
			return f;
		} catch (Exception e) {
			log.error("Error in creating folder " + fileSaveUrl + ". "
					+ e.getMessage());
		}
		return null;
	}
	
	
	public static void copyFilesAllFiles(File sourceFolder,File destinationFolder, boolean replaceIfExists){
		if(sourceFolder==null || destinationFolder==null){
			return;
		}
		if(!sourceFolder.exists() || !sourceFolder.isDirectory()){
			log.error("unable to copy files, source path is invalid "+sourceFolder.getAbsolutePath());
			return;
		}
		if(!destinationFolder.exists() || !destinationFolder.isDirectory()){
			log.error("unable to copy files, destination path is invalid "+destinationFolder.getAbsolutePath());
			return;
		}
		
		File[] files=sourceFolder.listFiles();
		
		for(File f:files){
			if(f.isDirectory()){
				
			File f2=new File(destinationFolder.getAbsolutePath()+"/"+f.getName());	
			
			if(!f2.exists() || !f2.isDirectory()){
				f2.mkdir();
			}
			copyFilesAllFiles(f,f2,replaceIfExists);
			
			}else{
				
				if(replaceIfExists){
					copyFile(f, new File(destinationFolder.getAbsolutePath()+"/"+f.getName()));	
				}else{
					if(!containsFile(destinationFolder,f.getName())){
						copyFile(f, new File(destinationFolder.getAbsolutePath()+"/"+f.getName()));
					}
				}
				
			}
		}
		
	}
	
	public static boolean containsFile(File folder,String fileName){
		if(folder==null || !folder.exists() || !folder.isDirectory() || fileName==null){
			return false;
		}
		File[] files=folder.listFiles();
		
		for(File f:files){
			if(f.getName().equalsIgnoreCase(fileName)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * copy file from source to destination
	 * @param sourceFile must be valid file
	 * @param destFile must be a valid file path
	 */
	public static void copyFile(File sourceFile, File destFile) {
		if (sourceFile != null && destFile != null) {
			
			if (!sourceFile.exists() || !sourceFile.isFile()) {
				return;
			}
			FileOutputStream fout=null;
			
			try {
				log.debug("copying file : source "
						+ sourceFile.getAbsolutePath() + " dest : "
						+ destFile.getAbsolutePath());
				
				fout = new FileOutputStream(destFile);
				Files.copy(sourceFile.toPath(), fout);
				
			} catch (Exception e) {
				log.error("error in copying file : source "
						+ sourceFile.getAbsolutePath() + " dest : "
						+ destFile.getAbsolutePath());
			}finally{
				try {
					if(fout!=null){
					fout.flush();
					fout.close();
					}
				} catch (IOException e) {
					log.error("Error in closing file stream ",e);
				}
			}
		}
	}
	
	public static void removeFolder(File f) {
		if (f == null || !f.exists()) {
			return;
		}
	
		if(f.isDirectory()){
		File[] files = f.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				
				try {
					Files.delete(files[i].toPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} else if (files[i].isDirectory()) {
				removeFolder(files[i]);
			}
		}
	}
		f.delete();
	}
	/**
	 * abstract file name from path
	 * @param path
	 * @return
	 */
	public static String getFileName(String path) {
		return path.substring(path.lastIndexOf("/") + 1);
	}
	
	public static boolean isFileExists(String fileName, File path){
		
		if(path==null || !path.exists() || !path.isDirectory() || fileName==null){
			return false;
		}
		log.debug("checking file is exists or not : directory :"+path.getAbsolutePath()+"  file to find "+fileName);
		
		File[] files=path.listFiles();
		for(File f:files){
			if(f.isFile() && f.getName().toLowerCase().equals(fileName.toLowerCase())){
				log.debug("file already exists"+fileName);
				return true;
			}
		}
		return false;
	}
	
//	public static void main(String...strings){
//		copyFilesAllFiles(new File("d:/5a93cb0368e424c9598c9d"), new File("e:/5a93cb0368e424c9598c9d"), false);
//	}
	
	
}
