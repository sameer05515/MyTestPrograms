package com.ist.iagent.designer.documentation;



public class PdfPublishPojo {
	private String fileSaveName;
	private DocumentationSavePojo documentation;
	private DocumentationInfoPojo docInfo;
	public String getFileSaveName() {
		return fileSaveName;
	}
	public void setFileSaveName(String fileSaveName) {
		this.fileSaveName = fileSaveName;
	}
	
	
	public DocumentationSavePojo getDocumentation() {
		return documentation;
	}
	public void setDocumentation(DocumentationSavePojo documentation) {
		this.documentation = documentation;
	}
	public DocumentationInfoPojo getDocInfo() {
		return docInfo;
	}
	public void setDocInfo(DocumentationInfoPojo docInfo) {
		this.docInfo = docInfo;
	}
	
}
