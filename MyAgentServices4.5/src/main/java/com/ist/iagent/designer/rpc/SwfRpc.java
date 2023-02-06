package com.ist.iagent.designer.rpc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.designer.util.PropertyUtil;



public class SwfRpc {
private static final Logger log=Logger.getLogger(SwfRpc.class);
private File moduleSavePath;
	public SwfRpc(){
	String xmlFolderPath=PropertyUtil.getInstance().getValueForKey("swfSaveUrl");
	moduleSavePath=new File(xmlFolderPath);
	log.debug("swf module save path found :"+xmlFolderPath);
	if(!moduleSavePath.exists() || !moduleSavePath.isDirectory()){
		log.debug("creating swf module folder:"+xmlFolderPath);
		moduleSavePath.mkdir();
	}
}
	
	public String getModuleUploadFolder(){
		
		return moduleSavePath.getName();
	}
	
	public String getFileUploadUrl(){
		return moduleSavePath.getAbsolutePath();
	}
	
	public boolean saveSwf(String fileName,byte[] data){
		log.debug("saving swf module");
		FileOutputStream fout=null;
		try{
		if(data!=null){
			File f=new File(moduleSavePath.getAbsolutePath()+"/"+fileName);
			log.debug("Saving swf file "+f.getAbsolutePath());
			fout=new FileOutputStream(f);
			fout.write(data);
			fout.flush();
			return true;
		}
		log.debug("saving swf module done");
		return false;
		}catch(Exception e){
			log.error("Error in saving SWF File", e);
			return false;
		}finally{
			try{
			if(fout!=null){
				fout.close();
			}
			}catch(Exception e){
				log.error("Error in closing swf file handler in saveSwf", e);
			}
		}
		
	}
	public List<String> getSwfList(){
		log.debug("getting swf modules list");
		List<String> list=new ArrayList<String>();
		File[] f=moduleSavePath.listFiles();
		for(int i=0;i<f.length;i++){
			log.debug("swf module :"+f[i].getName());
			list.add(f[i].getName());
		}
		
		return list;
	}
	
	public byte[] getSwf(String fileName){
		log.debug("getting swf module :"+fileName);
		FileInputStream fin=null;
		byte arr[]=null;
		try{
		
			File f=new File(moduleSavePath.getAbsolutePath()+"/"+fileName);
			if(f.exists() && f.isFile()){
				log.debug("reading swf file "+f.getAbsolutePath());
				fin=new FileInputStream(f);
				arr=new byte[(int)f.length()];
				fin.read(arr);
			}
		
		
		}catch(Exception e){
			log.error("Error in reading SWF File", e);
			
		}finally{
			try{
			if(fin!=null){
				fin.close();
			}
			}catch(Exception e){
				log.error("Error in closing swf file handler in getSwf", e);
			}
		}
		return arr;
	}
	
	
	public Boolean isFileExists(String fileName){
		File file=null;
		file=new File(moduleSavePath.getAbsoluteFile()+"/"+fileName);
		log.debug("checking swf module file to exists or not :"+file.getAbsolutePath());
		if(file.exists() && file.isFile()){
			log.debug("swf module file already exists :"+file.getAbsolutePath());
			return true;
		}else{
			log.debug("swf module file not exists :"+file.getAbsolutePath());
			return false;
		}
	}
	
}
