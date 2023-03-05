package com.ist.iagent.designer.rpc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.apache.log4j.Logger;

import com.ist.iagent.designer.documentation.DocumentationSavePojo;
import com.ist.iagent.designer.documentation.PdfPublishPojo;
import com.ist.iagent.designer.documentation.PdfPublisher;
import com.ist.iagent.designer.util.PropertyUtil;


public class DocumentationRpc {

	private String DOCUMENTATION_FOLDER="/documentation";
	private static final Logger log=Logger.getLogger(DocumentationRpc.class);
	private File xmlFolder;
	
	public DocumentationRpc(){
		String documentationSaveURL=PropertyUtil.getInstance().getValueForKey("documentationSaveURL");
		String documentationSaveFolderName=PropertyUtil.getInstance().getValueForKey("documentationSaveFolderName");
		
		if(documentationSaveURL==null){
			log.error("documentation save url not found");
			return;
		}
		if(documentationSaveFolderName==null){
			log.error("documentation save folder path not found");
			return;
		}
		
		if(documentationSaveFolderName!=null && documentationSaveFolderName.trim().length()>2){
			DOCUMENTATION_FOLDER="/"+documentationSaveFolderName;
		}
		log.debug("documentation Path found :"+documentationSaveURL);
		
		File f=new File(documentationSaveURL);
		if(!f.exists() || !f.isDirectory()){
			log.debug("folder not found creating folder "+documentationSaveURL);
			f.mkdir();
		}
	
		
		xmlFolder=new File(documentationSaveURL+DOCUMENTATION_FOLDER);
		log.debug("checking documentation folder :"+xmlFolder.getAbsolutePath());
		if(!xmlFolder.exists() || !xmlFolder.isDirectory()){
		log.debug("folder not found creating folder "+xmlFolder.getAbsolutePath());
		xmlFolder.mkdir();
		}
			
	}
	
	public File getDocumentationSavePath(){
		return xmlFolder;
	}
	
	
	public String readDocumentationFile(String fileName){
		
		if(xmlFolder==null){
			log.error("unable to read documentation file. Documentaton save folder path not found");
			return null;
		}
		
		StringBuilder fileData;
		
		log.debug("documentation file folder :"+xmlFolder.getAbsolutePath());
		
		if(!xmlFolder.exists() || !xmlFolder.isDirectory()){
			
			log.debug("file documentation folder not found:"+xmlFolder.getAbsolutePath());
			return null;
		}
		
		File file=null; 
		
			file=new File(xmlFolder.getAbsolutePath()+"/"+fileName+".xml");
		
		log.debug("reading documentation file :"+file.getAbsolutePath());
		FileReader freader=null;
		BufferedReader breader=null;
		try{
		if(file.exists() && file.isFile()){
		log.debug("documentation file found :"+file.getAbsolutePath());
		freader=new FileReader(file);
		breader=new BufferedReader(freader);
		String data=null;
		fileData=new StringBuilder();
		while((data = breader.readLine()) != null){
			log.trace(data);
			fileData.append(data);
		}
		
		return fileData.toString();
		}
		}catch(Exception e){
			log.error("Error in reading documentation file :"+file.getAbsolutePath(),e);
		}finally{
			try{
				if(breader!=null){
						breader.close();
				}
				if(freader!=null){
					freader.close();
				}
			}catch(Exception e){
				log.error("Error in closing documentation file handler :",e);
			}
		}
		return null;
		}
	
	
	public void saveDocumentationFile(DocumentationSavePojo documentationData){
		
		if(xmlFolder==null){
			log.error("unable to save documentation file. Documentaton folder save path not found");
			return;
		}
		
		FileWriter fwriter=null;
		try{
		log.debug("documentation file to save: "+documentationData.getFileName());
		
		File fileToSave=new File(xmlFolder.getAbsolutePath()+"/"+documentationData.getFileName()+".xml");
		
		log.debug("saving documentation file: "+fileToSave.getAbsolutePath());
		fwriter=new FileWriter(fileToSave);
		fwriter.write(documentationData.getFileData());
		fwriter.flush();
		
		log.debug("documentation file saved");
		
		}catch(Exception e){
		log.error("Error in saving documentation data", e);	
		}finally{
			try{
				if(fwriter!=null){
				fwriter.close();
				}
			}catch(Exception e){
				log.error("Error in closing documentation file save handler", e);	
			}
		}
		
	}
	
	/**
	 * 
	 * @param publishPojo
	 * @return String path of saved pdf file,else null 
	 */
	public String publishDocument(PdfPublishPojo publishPojo){
		PdfPublisher publish;
		try{
			log.debug("publishing pdf document "+publishPojo.getFileSaveName());
			
			if(publishPojo.getDocumentation()!=null){
			log.debug("saving xml data file "+publishPojo.getDocumentation().getFileName());
			saveDocumentationFile(publishPojo.getDocumentation());
			
			publish=new PdfPublisher(xmlFolder);
			publish.setPublishPojo(publishPojo);
			publish.generatePdf();
			
			log.debug("pdf published successfully");
			log.debug("pdf published path "+xmlFolder.getParentFile().getName()+"/"+publish.getSavedPdfPath());
			return xmlFolder.getParentFile().getName()+"/"+publish.getSavedPdfPath();
			}
		}catch(Exception e){
			log.error("Error in publishing document "+publishPojo.getFileSaveName(),e);
			return null;
		}
		return null;
	}
	
	public boolean deleteDocumentationFile(String fileName){
		try{
			if(xmlFolder==null){
				log.error("unable to delete documentation file. Documentaton folder save path not found");
				return false;
			}
			
		File xmlFile=new File(xmlFolder.getAbsolutePath()+"/"+fileName+".xml");
		File pdfFile=new File(xmlFolder.getAbsolutePath()+"/"+fileName+".pdf");
		
		if(xmlFile.exists() && xmlFile.isFile()){
			log.debug("Deleting documentation xml file "+xmlFile.getName());
			xmlFile.delete();
		}
		
		if(pdfFile.exists() && pdfFile.isFile()){
			log.debug("Deleting documentation pdf file "+pdfFile.getName());
			pdfFile.delete();
		}
			
		}catch(Exception e){
			log.error("Error in deleting documentation file :"+e);
		}
		
		
		return false;
	}
	
	/*	public void saveDocumentationFile(DocumentationSavePojo documentationData){
		
			if(xmlFolder==null){
				log.error("unable to save documentation file. Documentaton folder save path not found");
				return;
			}
			
			File folder=null;
			FileWriter fwriter=null;
			try{
			folder=new File(xmlFolder.getAbsolutePath()+"/"+documentationData.fileName);
			log.debug("documentation save file folder : "+folder.getAbsolutePath());
			
			if(!folder.exists() || !folder.isDirectory()){
				
				log.debug("file documentation folder not found: "+folder.getAbsolutePath());
				log.debug("creation file documentation folder: "+folder.getAbsolutePath());
				folder.mkdir();
				
			}
			//remove old images----------------------->>
			if(documentationData.imgs!=null && documentationData.imgs.size()>0){
				log.debug("file documentation folder found: "+folder.getAbsolutePath());
				log.debug("removing oldData from documentation folder: "+folder.getAbsolutePath());
				deleteDocumentationData(folder.getAbsolutePath());
			}
			//remove old images ends----------------------->>
			
			File fileToSave=new File(folder.getAbsolutePath()+"/"+documentationData.fileName+".xml");
			log.debug("saving documentation file: "+fileToSave.getAbsolutePath());
			fwriter=new FileWriter(fileToSave);
			fwriter.write(documentationData.fileData);
			fwriter.flush();
			
			log.debug("documentation file saved");
			
			if(documentationData.imgs!=null){
			log.debug("saving documentation images");
			
			for(DocumentationImageSave obj : documentationData.imgs){
				saveImg(folder.getAbsolutePath()+"/"+obj.imgName,obj.imgData);
			}
			}
			
			}catch(Exception e){
			log.error("Error in saving documentation data", e);	
			}finally{
				try{
					fwriter.close();
				}catch(Exception e){
					log.error("Error in closing documentation file save handler", e);	
				}
			}
			
		}*/
	/*	private void saveImg(String file,byte[] imgData){
			
			FileOutputStream imgWriter=null;
			File filePath=new File(file);
			
			try{
			if(imgData!=null){
				log.debug("saving image"+filePath.getAbsolutePath());
			imgWriter=new FileOutputStream(filePath);
			imgWriter.write(imgData);
			imgWriter.flush();
			}
			}catch(Exception e){
				log.error("error in saving image"+filePath.getAbsolutePath());
			}finally{
				try{
					imgWriter.close();
				}catch(Exception e){
					log.error("error in closing image writer"+e);
				}
			}
			
		}
	
		public void deleteDocumentationData(String fileName){
			try{
			File f=new File(fileName);
			if(f.exists() && f.isDirectory()){
			String files[]=f.list();
			log.debug("Deleting documentation folder data : "+f.getAbsolutePath());
			
			for(int i=0;i<files.length;i++){
				
				File fileToDelete=new File(f.getAbsolutePath()+"/"+files[i]);
				
				if(fileToDelete.isFile()){
					log.debug("Deleting documentation file :"+fileToDelete.getName());
					fileToDelete.delete();
				}else{
					deleteDocumentationFolder(fileToDelete.getAbsolutePath());
				}
			}
			
			}
			}catch(Exception es){
				log.error("error in deleting documentation file data:"+es);	
			}
		}
		
		
		public void deleteDocumentationFolder(String fileName){
			
			
			File f=new File(fileName);
			try{
				
		
			log.debug("Deleting documentation folder :"+f.getAbsolutePath());
			if(f.exists() && f.isDirectory()){
				
				String files[]=f.list();
				
				for(int i=0;i<files.length;i++){
					
					File fileToDelete=new File(f.getAbsolutePath()+"/"+files[i]);	
					
					if(fileToDelete.isFile()){
						log.debug("Deleting documentation file :"+fileToDelete.getName());
						fileToDelete.delete();
					}else{
						deleteDocumentationFolder(fileToDelete.getAbsolutePath());
					}
				}
				
			}
			if(f.list().length==0){
				log.debug("Documentation folder deleted:"+f.getAbsolutePath());
				f.delete();
			}else{
				deleteDocumentationFolder(f.getAbsolutePath());
			}
			}catch(Exception e){
				log.debug("error in deleting documentation folder"+e);
			}
		}*/
/*	public static void main(String ar[]){
		File f=new File("D:/IST/iagent4-server/webapps/ist/xml/documentation");
		if(f.exists()){
			System.out.println(f.getAbsolutePath());
			System.out.println(f.getParentFile().getName());
		}
	}*/
	
}
