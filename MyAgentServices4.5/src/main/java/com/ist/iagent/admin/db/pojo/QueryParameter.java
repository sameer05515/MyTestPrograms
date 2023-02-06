package com.ist.iagent.admin.db.pojo;

public class QueryParameter extends ParameterDescDTO {

	private String parameterResultType;
	private boolean blankAllowed;
	private String paramValue;

	/**
	 * @return the parameterResultType
	 */
	public String getParameterResultType() {
		return parameterResultType;
	}

	/**
	 * @param parameterResultType
	 *            the parameterResultType to set
	 */
	public void setParameterResultType(String parameterResultType) {
		this.parameterResultType = parameterResultType;
	}

	/**
	 * @return the blankAllowed
	 */
	public boolean getBlankAllowed() {
		return blankAllowed;
	}

	/**
	 * @param blankAllowed
	 *            the blankAllowed to set
	 */
	public void setBlankAllowed(boolean blankAllowed) {
		this.blankAllowed = blankAllowed;
	}

	/**
	 * @return the paramValue
	 */
	public String getParamValue() {
		return paramValue;
	}

	/**
	 * @param paramValue
	 *            the paramValue to set
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
}
