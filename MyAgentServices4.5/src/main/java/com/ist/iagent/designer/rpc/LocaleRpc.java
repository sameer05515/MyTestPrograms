package com.ist.iagent.designer.rpc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

import com.ist.iagent.designer.util.PropertyUtil;

public class LocaleRpc {

	private String LOCALE_SAVE_FOLDER="locale";
	private static final Logger log=Logger.getLogger(LocaleRpc.class);
	private File localeSavePath;
	public File getLocaleSavePath() {
		return localeSavePath;
	}

	public void setLocaleSavePath(File localeSavePath) {
		this.localeSavePath = localeSavePath;
	}

	public LocaleRpc(String xmlFolderPath){
		
		//String xmlFolderPath = PropertyUtil.getInstance().getValueForKey("xmlSaveURL");
		
		if(xmlFolderPath==null || xmlFolderPath.trim().length()<1){
			log.error("key : xmlSaveURL not found");
			return;
		}
		log.debug("xml save url found :" + xmlFolderPath);
		
		String xmlPath=PropertyUtil.getInstance().getValueForKey("localeSaveFolderName");
		
		if(xmlPath==null || xmlPath.length()<=0){
			log.debug("key : localeSaveFolderName not found ");
			log.debug("setting default locale save folder name to 'locale'");
		}else{
			LOCALE_SAVE_FOLDER=xmlPath;
		}
		
		
		File f = new File(xmlFolderPath);
		if (!f.exists() || !f.isDirectory()) {
			log.debug("folder not found creating folder " + xmlFolderPath);
			f.mkdir();
		}
		
		localeSavePath = new File(xmlFolderPath +"/"+LOCALE_SAVE_FOLDER);
		if (!localeSavePath.exists() || !localeSavePath.isDirectory()) {
			log.debug("folder not found creating folder "+ localeSavePath.getAbsolutePath());
			localeSavePath.mkdir();
		}
		
	}
	
	public String readLocaleFile(String fileName){
		BufferedReader br=null;
		StringBuilder fileData=null;
		try{
			if(localeSavePath==null){
				log.error("Error in reading locale file. localeSaveFolderName not found or xml save path not found.");
				return null;
			}
			
			
		br=new BufferedReader(new InputStreamReader(new FileInputStream(localeSavePath.getAbsolutePath()+"/"+fileName),"UTF-8"));
		
		log.debug("Reading locale file "+fileName);
		
		String data = null;
		fileData = new StringBuilder();
		while ((data = br.readLine()) != null) {
			log.trace(data);
			fileData.append(data);
		}

		return fileData.toString();
			
		}catch(Exception e){
			log.error("Error in reading locale file "+fileName+" : "+e);	
		}finally{
			try{
			if(br!=null){
				br.close();
			}
			}catch(Exception e){
				log.error("Error in closing locale file reader :"+e);
			}
		}
		return null;
	}
	
	public boolean deleteLocaleFile(String fileName){
		
		try{
			if(localeSavePath==null){
				log.error("Error in deleting locale file. localeSaveFolderName not found or xml save path not found.");
				return false;
			}
		File f=new File(localeSavePath+"/"+fileName);	
		
		if(f.exists() && f.isFile()){
			log.debug("Deleting locale file :"+fileName);
			f.delete();
		}
		
		}catch(Exception e){
		log.error("Error in deleting locale file "+fileName+":"+e);	
		}
		
		return false;
	}
	
	public boolean saveLocaleFile(String fileName,String fileData){
		Writer fr=null;
		try{
			if(localeSavePath==null){
				log.error("Error in saving locale file. localeSaveFolderName not found or xml save path not found.");
				return false;
			}
			log.debug("Saving locale file");
			fr=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(localeSavePath.getAbsolutePath()+"/"+fileName),"UTF-8"));
			fr.write(fileData);
			fr.flush();
			log.debug("locale file saved");
			return true;
		}catch(Exception e){
			log.error("Error in saving locale file :",e);
		}finally{
			try{
			if(fr!=null){
				fr.close();
			}
			}catch(Exception e){
				log.error("Error in closing file writer :",e);
			}
		}
		return false;
	}
	
}
