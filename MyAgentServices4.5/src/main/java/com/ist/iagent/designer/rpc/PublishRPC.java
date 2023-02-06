package com.ist.iagent.designer.rpc;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.log4j.Logger;
import com.ist.iagent.designer.util.PropertyUtil;

public class PublishRPC {
	
	
	private String IAGENT_XML_FOLDER="/iagentxml";
	private static final Logger log=Logger.getLogger(PublishRPC.class);
	private File folderPath;
	
	public PublishRPC(String xmlFolderPath){
	//	String xmlFolderPath=PropertyUtil.getInstance().getValueForKey("xmlSaveURL");
		String publishSaveFolderName=PropertyUtil.getInstance().getValueForKey("publishSaveFolderName");
		log.debug("iagent xml(publishSaveFolderName) key found :"+publishSaveFolderName);
		log.debug("xmlFolderPath key found :"+xmlFolderPath);
		if(publishSaveFolderName!=null && publishSaveFolderName.trim().length()>2){
			IAGENT_XML_FOLDER="/"+publishSaveFolderName;
		}
		
		log.debug("xml Path found :"+xmlFolderPath);
		File f=new File(xmlFolderPath);
		if(!f.exists() || !f.isDirectory()){
			log.debug("folder not found creating folder "+xmlFolderPath);
			f.mkdir();
		}
		
		folderPath=new File(f.getAbsolutePath()+IAGENT_XML_FOLDER);
		log.debug("checking iagentxml folder :"+folderPath.getAbsolutePath());
		if(!folderPath.exists() || !folderPath.isDirectory()){
		log.debug("folder not found creating folder "+folderPath.getAbsolutePath());
		folderPath.mkdir();
		}

		f=null;
		
	}
	
	public File getPublishXmlSaveFolder(){
		return folderPath;
	}
	
	
	public Boolean deleteIAgentXML(String fileName){
		log.debug("deleting iagent xml file");
		File file=null;
		file=new File(folderPath.getAbsolutePath()+"/"+fileName);	
		
		if(file.exists() && file.isFile()){
			log.debug("deleting iagent xml file :"+file.getAbsolutePath());
			return file.delete();
		}
		return false;
	}
	
	public Boolean saveIAgentXML(String fileName,String fileData){
		log.debug("saving file iagent xml file");
		
		Writer fr =null;
		
		try{
		
			log.debug("deleting old iagent xml file "+fileName);
			deleteIAgentXML(fileName);
			
			log.trace("saving new iagent xml file "+fileData);
			fr= new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(folderPath.getAbsolutePath()+"/"+fileName), "UTF-8"));
		
			
			
		
		fr.write(fileData);
		fr.flush();
		fr.close();
		return true;
		}catch(Exception e){
			log.error("Error Writing XML :"+e);
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

}
