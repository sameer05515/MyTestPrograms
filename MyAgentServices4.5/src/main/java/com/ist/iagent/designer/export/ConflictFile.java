package com.ist.iagent.designer.export;

public class ConflictFile{
	
	
	private String filename;
	private boolean conflict;
	private boolean save=true;
	private String filetype;
	
	public transient static final String LOCAL_MODULE_FILE="LOCAL_MODULE_FILE";
	public transient static final String PUBLISH_FILE="PUBLISH_FILE";
	public transient static final String SWF_FILE="SWF_FILE";
	public transient static final String DOCUMENTATION_FILE="DOCUMENTATION_FILE";
	public transient static final String LOCALE_FILE="LOCALE_FILE";
	public transient static final String JAR_FILE="JAR_FILE";
	
	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public boolean isConflict() {
		return conflict;
	}


	public void setConflict(boolean conflict) {
		this.conflict = conflict;
	}


	public boolean isSave() {
		return save;
	}


	public void setSave(boolean save) {
		this.save = save;
	}


	public String getFiletype() {
		return filetype;
	}


	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}


	
	
	
	
	public static final String[] fileTypes(){
		return new String[]{LOCAL_MODULE_FILE,PUBLISH_FILE,SWF_FILE,DOCUMENTATION_FILE,LOCALE_FILE,JAR_FILE};
	}
	
	public ConflictFile(){
		
	}
	
	public ConflictFile(String filename,String fileType){
		this.filename=filename;
		this.filetype=fileType;
	}

}
