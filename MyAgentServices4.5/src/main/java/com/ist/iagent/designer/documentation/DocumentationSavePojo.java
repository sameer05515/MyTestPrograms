package com.ist.iagent.designer.documentation;




import java.util.Map;

public class DocumentationSavePojo {

private	String fileName;
	private String fileData;
	private Map<String,byte[]> imgs;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileData() {
		return fileData;
	}
	public void setFileData(String fileData) {
		this.fileData = fileData;
	}
	public Map<String,byte[]> getImgs() {
		return imgs;
	}
	public void setImgs(Map<String,byte[]> imgs) {
		this.imgs = imgs;
	}
	
}
