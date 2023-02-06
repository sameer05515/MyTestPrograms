package com.ist.iagent.designer.export;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.ServiceManagementRPC;
import com.ist.iagent.designer.util.FileUtil;
import com.ist.iagent.designer.util.FolderUnzipper;

public class ImportModuleHandler extends ExportModuleHandler{

	private static final Logger log=Logger.getLogger(ImportModuleHandler.class);
	
	public ImportModuleHandler(){
		super();
		
	}
	

	
	public ImportPojo uploadModule(String zipFileName) throws Exception{
		
		log.debug("Extracting module : "+zipFileName);	
		try{
		boolean extracted=FolderUnzipper.unzipFiles(exportProjectPath.getAbsolutePath(), zipFileName, exportProjectPath.getAbsolutePath());
		if(!extracted){
			throw new Exception("Error in extracting module "+zipFileName);
		}
		ImportPojo pojo=new ImportPojo();
		File f=new File(exportProjectPath.getAbsolutePath()+"/"+zipFileName.substring(0,zipFileName.indexOf(".")));
		pojo.setObjectProperties(f);
		pojo.readReleaseNotes(f);
		findConflict(pojo,f);
		
		return pojo;
		}catch(Exception e){
			log.error("Error in importing module. ",e);
			throw e;
		}finally{
			//deleting zip file---------------------------------------------
			File f=new File(exportProjectPath.getAbsolutePath()+"/"+zipFileName);
			if(f.exists() && f.isFile()){
				f.delete();
			}
			//deleting zip file---------------------------------------------
		}
		
	}
	
	private void findConflict(ImportPojo pojo, File folderToMerge){
		if(pojo==null || folderToMerge==null || !folderToMerge.exists() || !folderToMerge.isDirectory()){
			return;
		}
		//checking local module file for conflict
		Path destination=null;
		Path source=null;
		Path f=exportProjectPath.toPath().resolve(pojo.getModuleName());
		
		
		/*pojo.checkConflict(pojo.getModuleName()+".xml",ConflictFile.LOCAL_MODULE_FILE,
				xmlRpc.getLocalModulesFolder());*/
		
		destination=xmlRpc.getLocalModulesFolder().toPath();
		source=f.resolve(destination.getName(destination.getNameCount()-3).getFileName());
		source=source.resolve(destination.getName(destination.getNameCount()-2).getFileName());
		source=source.resolve(destination.getName(destination.getNameCount()-1).getFileName());
		
		checkConflict(source.toFile(),destination.toFile(),pojo,ConflictFile.LOCAL_MODULE_FILE);
		
		//if(pojo.isExportDocumentationPdf()){
			/*pojo.checkConflict(pojo.getModuleName()+".pdf",ConflictFile.DOCUMENTATION_FILE,
					documentationRpc.getDocumentationSavePath());*/
		//}
		if(pojo.isExportDocumentationXml() || pojo.isExportDocumentationPdf()){
			/*pojo.checkConflict(pojo.getModuleName()+".xml",ConflictFile.DOCUMENTATION_FILE,
					documentationRpc.getDocumentationSavePath());*/
			destination=documentationRpc.getDocumentationSavePath().toPath();
			source=f.resolve(destination.getName(destination.getNameCount()-2).getFileName()).resolve(destination.getName(destination.getNameCount()-1).getFileName());
			checkConflict(source.toFile(),destination.toFile(),pojo,ConflictFile.DOCUMENTATION_FILE);
		}
		
		if(pojo.isExportlocaleXml()){
			/*pojo.checkConflict(pojo.getModuleName()+"_locale.xml",ConflictFile.LOCALE_FILE,
					xmlRpc.getLocaleRpc().getLocaleSavePath());*/
			destination=xmlRpc.getLocaleRpc().getLocaleSavePath().toPath();
			source=f.resolve(destination.getName(destination.getNameCount()-2).getFileName()).resolve(destination.getName(destination.getNameCount()-1).getFileName());
			checkConflict(source.toFile(),destination.toFile(),pojo,ConflictFile.LOCALE_FILE);
		}
		
		if(pojo.isExportSwf()){
			File swfFolder=new File(folderToMerge.getAbsolutePath()+"/"+swfRpc.getModuleUploadFolder());
			checkConflict(swfFolder,new File(swfRpc.getFileUploadUrl()),pojo,ConflictFile.SWF_FILE);
		}
		
		if(pojo.isExportJars()){
			File libFolder=new File(folderToMerge.getAbsolutePath()+"/lib");
			checkConflict(libFolder,new File(serverLibPath),pojo,ConflictFile.JAR_FILE);
		}
	}
	
	private void checkConflict(File source,File dest,ImportPojo pojo,String conflictType){
	
		if(!source.exists() || !source.isDirectory()){
			log.error("error in checking file conflict, source path is invalid");
			return;
		}
		
		if(!dest.exists() || !dest.isDirectory()){
			log.error("error in checking file conflict, destination path is invalid");
			return;
		}
			File[] files=source.listFiles();
			for(File f:files){
				pojo.checkConflict(f.getName(),conflictType,dest);
			}
		
	}
	
	
	public boolean importModule(ImportPojo importPojo) throws Exception{
		
		try{
		
		if(importPojo==null){
			throw new Exception("Unable to import module.");
		}
		log.debug("importing module : "+importPojo.getModuleName());
		
		
		Path destination=null;
		Path source=null;
		Path f=exportProjectPath.toPath().resolve(importPojo.getModuleName());
		
		for(String ftype:ConflictFile.fileTypes()){
			
			List<ConflictFile> files=importPojo.getFilesArray(ftype);	
			
			if(files.size()==0){
				continue;
			}
			
			
			if(ftype==ConflictFile.SWF_FILE){
				
				destination=new File(swfRpc.getFileUploadUrl()).toPath();
				source=f.resolve(destination.getFileName());
				
			}else if(ftype==ConflictFile.JAR_FILE){
				
				destination=new File(serverLibPath).toPath();
				source=f.resolve(destination.getFileName());
				
			}else if(ftype==ConflictFile.LOCALE_FILE){
				
				destination=xmlRpc.getLocaleRpc().getLocaleSavePath().toPath();
				source=f.resolve(destination.getName(destination.getNameCount()-2).getFileName()).resolve(destination.getName(destination.getNameCount()-1).getFileName());
				
			}else if(ftype==ConflictFile.LOCAL_MODULE_FILE){
				
				destination=xmlRpc.getLocalModulesFolder().toPath();
				source=f.resolve(destination.getName(destination.getNameCount()-3).getFileName());
				source=source.resolve(destination.getName(destination.getNameCount()-2).getFileName());
				source=source.resolve(destination.getName(destination.getNameCount()-1).getFileName());
				
			}else if(ftype==ConflictFile.DOCUMENTATION_FILE){
				
				destination=documentationRpc.getDocumentationSavePath().toPath();
				source=f.resolve(destination.getName(destination.getNameCount()-2).getFileName()).resolve(destination.getName(destination.getNameCount()-1).getFileName());
				
			}
			
			copyFiles(files, source,destination);
			
		}
		
		//------------------- importing publish xml -------------------------------
		destination=xmlRpc.getIagentXML().getPublishXmlSaveFolder().toPath();
		source=f.resolve(destination.getName(destination.getNameCount()-2).getFileName()).resolve(destination.getName(destination.getNameCount()-1).getFileName());
		FileUtil.copyFilesAllFiles(source.toFile(),destination.toFile(),true);
		//------------------- importing publish xml end-------------------------------
		
		
		//------------------- importing iagent-service-config xml -------------------------------
		
		File temp=new File(f.toFile().getAbsolutePath()+"/"+IAGENT_SERVICE_FILE);
		if(temp.exists() && temp.isFile()){
			log.debug("importing iagent service config xml");
			try {
				//serviceMgmtRpc.importDataFromXML(temp);
				serviceMgmtRpc.importDataFromXML(temp,new ArrayList<String>());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("error in importing iagent service config xml",e);
				throw e;
			}
		}
		//------------------- importing iagent-service-config xml end-------------------------------
		
		
		//------------------- importing images -------------------------------
		destination=imageUploadRpc.getImageUploadFolder().toPath();
		source=f.resolve(destination.getFileName());
		FileUtil.copyFilesAllFiles(source.toFile(),destination.toFile(),true);
		//------------------- importing images end-------------------------------
	
		FileUtil.removeFolder(f.toFile());
		
		}catch(Exception e){
			log.error("Error in importing module.", e);
			throw e;
		}
		
		return false;
	}
	
	
	
	private void copyFiles(List<ConflictFile> files, Path source,Path destination){
		if(files==null || source==null || destination==null){
			return;
		}
		
		
		if(!source.toFile().exists() || !source.toFile().isDirectory()){
			log.error("unable to copy files, source path is invalid "+source.toFile().getAbsolutePath());
			return;
		}
		if(!destination.toFile().exists() || !destination.toFile().isDirectory()){
			log.error("unable to copy files, destination path is invalid "+destination.toFile().getAbsolutePath());
			return;
		}
		for(ConflictFile file:files){
			if(file.isSave()){
				log.debug("Copying file "+file.getFilename());
				FileUtil.copyFile(source.resolve(file.getFilename()).toFile(), new File(destination.toFile().getAbsolutePath()+"/"+file.getFilename()));
			}
		}
	}
	
	
}
