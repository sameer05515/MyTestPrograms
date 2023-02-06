package com.ist.iagent.designer.export;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import com.ist.iagent.designer.util.FileUtil;

public class ImportPojo extends ExportPojo{

	private List<ConflictFile> list=new ArrayList<ConflictFile>();
	private static final Logger log=Logger.getLogger(ImportPojo.class);
	
	public List<ConflictFile> getList() {
		return list;
	}

	public void setList(List<ConflictFile> list) {
		this.list = list;
	}

	
	
	public void checkConflict(String fileName,String fileType,File folder){
		ConflictFile f=new ConflictFile(fileName,fileType);
		f.setConflict(FileUtil.isFileExists(fileName, folder));
		if(f.isConflict()){
			log.debug("file conflict found : "+f.getFilename());
		}
		this.list.add(f);
	}
	
	public List<ConflictFile> getFilesArray(String fileType){
		List<ConflictFile> filesList=new ArrayList<ConflictFile>();
		
		if(list==null){
			return filesList;
		}
		
		for(ConflictFile file:list){
			if(file.getFiletype().equals(fileType)){
				filesList.add(file);
			}
		}
		
		
		return filesList;
	}
	
	
}
