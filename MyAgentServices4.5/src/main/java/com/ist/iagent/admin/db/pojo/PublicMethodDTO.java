package com.ist.iagent.admin.db.pojo;

import java.util.ArrayList;
import java.util.List;

public class PublicMethodDTO {

	private int linked_jar_id;	
	private String className;
	private String methodName;
	private List<ParameterDescDTO> inputType=new ArrayList<ParameterDescDTO>();
	private List<ParameterDescDTO> returnType=new ArrayList<ParameterDescDTO>();
	public int getLinked_jar_id() {
		return linked_jar_id;
	}
	public void setLinked_jar_id(int linked_jar_id) {
		this.linked_jar_id = linked_jar_id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	///////////////
	public List<ParameterDescDTO> getInputType() {
		return inputType;
	}
	public void setInputType(List<ParameterDescDTO> inputType) {
		this.inputType = inputType;
	}
	public List<ParameterDescDTO> getReturnType() {
		return returnType;
	}
	public void setReturnType(List<ParameterDescDTO> returnType) {
		this.returnType = returnType;
	}
}
