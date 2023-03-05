package com.ist.iagent.designer.rpc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.Logger;

public class CtiBarSave {

	private static final Logger log=Logger.getLogger(CtiBarSave.class);
	private String ctiBarFolderName="ctibar";
	private File ctiBarFolder;
	
	public CtiBarSave(String xmlSavePath){
		
		if(xmlSavePath==null){
			log.error("xml save folder path not found.");
			return;
		}
		log.error("xml save folder path not found."+xmlSavePath);
		
		ctiBarFolder=new File(xmlSavePath+"/"+ctiBarFolderName);
		
		if(!ctiBarFolder.exists() || !ctiBarFolder.isDirectory()){
			log.debug("creating ctibar xml save folder "+ctiBarFolder.getAbsolutePath());
			ctiBarFolder.mkdir();
		}
	}
	
	public boolean saveCtibarFile(String fileName,String fileData){
	
		
		if(ctiBarFolder==null || fileName==null || fileData==null){
			log.debug("error in saving ctibar xml file.");
			return false;
		}
		
		log.debug("saving file ctibar xml file");
		
		Writer fr =null;
		
		try{
			
			fr= new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(ctiBarFolder.getAbsolutePath()+"/"+fileName), "UTF-8"));
		
			
			
		
		fr.write(fileData);
		fr.flush();
		return true;
		}catch(Exception e){
			log.error("Error Writing ctibar XML :"+e);
		}finally{
			try{
			if(fr!=null){
			fr.close();
			}
			}catch(Exception e){
				log.error("Error in closing file handler :",e);	
			}
		}
		return false;
		}
	
	
	public Boolean deleteCtibarXML(String fileName){
		
		if(ctiBarFolder==null || fileName==null){
			log.debug("error in saving ctibar xml file.");
			return false;
		}
		
		log.debug("deleting ctibar xml file");
		File file=null;
		file=new File(ctiBarFolder.getAbsolutePath()+"/"+fileName);	
		
		if(file.exists() && file.isFile()){
			log.debug("deleting ctibar xml file :"+file.getAbsolutePath());
			return file.delete();
		}
		return false;
	}
	
	
	
}
