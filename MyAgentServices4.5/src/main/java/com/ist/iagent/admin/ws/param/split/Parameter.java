package com.ist.iagent.admin.ws.param.split;

import java.util.List;

public class Parameter {

	private String type;
	private boolean complex;
	private List<Field> fields;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the complex
	 */
	public boolean isComplex() {
		return complex;
	}

	/**
	 * @param complex
	 *            the complex to set
	 */
	public void setComplex(boolean complex) {
		this.complex = complex;
	}

	/**
	 * @return the fields
	 */
	public List<Field> getFields() {
		return fields;
	}

	/**
	 * @param fields
	 *            the fields to set
	 */
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String toString() {
		return new StringBuffer().append("\n{").append(" type == " + (type == null ?"null":type))
				.append(" , complex == " + complex)
				.append(" , fields == " + fields).append(
						" , fields.size() == "
								+ (fields == null ? "0" : fields.size()))
				.append("}").toString();
	}

}
