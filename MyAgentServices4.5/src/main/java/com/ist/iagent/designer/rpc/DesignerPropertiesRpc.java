package com.ist.iagent.designer.rpc;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


import org.apache.log4j.Logger;

import com.ist.iagent.designer.util.PropertyUtil;

public class DesignerPropertiesRpc {
	private File f;
	private static final Logger log=Logger.getLogger(DesignerPropertiesRpc.class);
	private static final String FILE_NAME="DesignerProp.prop";
	public DesignerPropertiesRpc(){
		String xmlPath=PropertyUtil.getInstance().getValueForKey("designerPropUrl");
		
		log.debug("designer properties save path found "+xmlPath);
		
		f=new File(xmlPath);
		if(!f.exists() || !f.isDirectory()){
			log.debug("creating designer properties folder "+xmlPath);
			f.mkdir();
		}
		log.debug("Designer Properties folder "+f.getAbsolutePath());
	}
	
	public void saveDesignProperties(Map<String,Integer> properties){
		log.debug("saving designer properties ");
		FileOutputStream fout=null;
		try{
		HashMap<String,Integer> data=(HashMap<String,Integer>)properties;
		if(data!=null){
		Properties prop=new Properties();
		for(String key:data.keySet()){
			//log.debug("Saving--->"+key+"  "+data.get(key));
			prop.put(key, ((Integer)data.get(key)).toString());
		}
		if(!prop.isEmpty()){
		fout=new FileOutputStream(f.getAbsolutePath()+"/"+FILE_NAME);
		log.debug("Saving properties :"+f.getAbsolutePath()+"/"+FILE_NAME);
		prop.store(fout,null);
		}
		log.debug("Designer Properties saved");
		}
		}catch(Exception e){
			log.error("Error in saving designer properties ",e);
		}finally{
			try{
			if(fout!=null){
				fout.close();
			}
			}catch(Exception e){
				log.error("Error in saving designer properties ",e);
			}
		}
	}
	public Map<String,Integer> getDesignProperties(){
		log.debug("getting designer properties");
		HashMap<String,Integer> data=null;
		FileInputStream fin=null;
		try{
			File file=new File(f.getAbsoluteFile()+"/"+FILE_NAME);
			log.debug("designer properties file path"+file.getAbsolutePath());
			if(file.exists() && file.isFile()){
				log.debug("designer properties file found ");
			fin=new FileInputStream(file);
			Properties p=new Properties();
			p.load(fin);
			log.debug("designer properties loaded");
		if(!p.isEmpty()){
			log.debug("getting Designer Properties");
		data=new HashMap<String, Integer>();
		for(Object key:p.keySet()){
			data.put((String)key, Integer.valueOf((String)p.get(key)));
		}
		
		}
			}
		}catch(Exception e){
			log.error("Error in getting designer properties ",e);
		}finally{
			try{
			if(fin!=null){
				fin.close();
			}
			}catch(Exception e){
				log.error("Error in getting designer properties ",e);
			}
		}
		return data;
	}
}
