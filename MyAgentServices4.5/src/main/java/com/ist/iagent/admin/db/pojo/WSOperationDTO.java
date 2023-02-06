package com.ist.iagent.admin.db.pojo;

import java.util.ArrayList;
import java.util.List;

public class WSOperationDTO {

	// private var _wsdlOpration:WSDLOperation=null;

	// private var _operationName:String="";

	// private var _linkedWSId:int=0;

	// private var _portName:String="";

	// private var _inputType:ArrayCollection=new ArrayCollection();

	// private var _returnType:ArrayCollection=new ArrayCollection();

	// private var _wsdlOpration:WSDLOperation=null;
	private String operationName;
	private int linkedWSId;
	private String portName;
	private String parameterSchema;
	private List<ParameterDescDTO> inputType = new ArrayList<ParameterDescDTO>();
	private List<ParameterDescDTO> returnType = new ArrayList<ParameterDescDTO>();
	/**
	 * @return the operationName
	 */
	public String getOperationName() {
		return operationName;
	}
	/**
	 * @param operationName the operationName to set
	 */
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	/**
	 * @return the linkedWSId
	 */
	public int getLinkedWSId() {
		return linkedWSId;
	}
	/**
	 * @param linkedWSId the linkedWSId to set
	 */
	public void setLinkedWSId(int linkedWSId) {
		this.linkedWSId = linkedWSId;
	}
	/**
	 * @return the portName
	 */
	public String getPortName() {
		return portName;
	}
	/**
	 * @param portName the portName to set
	 */
	public void setPortName(String portName) {
		this.portName = portName;
	}
	/**
	 * @return the inputType
	 */
	public List<ParameterDescDTO> getInputType() {
		return inputType;
	}
	/**
	 * @param inputType the inputType to set
	 */
	public void setInputType(List<ParameterDescDTO> inputType) {
		this.inputType = inputType;
	}
	/**
	 * @return the returnType
	 */
	public List<ParameterDescDTO> getReturnType() {
		return returnType;
	}
	/**
	 * @param returnType the returnType to set
	 */
	public void setReturnType(List<ParameterDescDTO> returnType) {
		this.returnType = returnType;
	}
	/**
	 * @return the parameterSchema
	 */
	public String getParameterSchema() {
		return parameterSchema;
	}
	/**
	 * @param parameterSchema the parameterSchema to set
	 */
	public void setParameterSchema(String parameterSchema) {
		this.parameterSchema = parameterSchema;
	}
}
