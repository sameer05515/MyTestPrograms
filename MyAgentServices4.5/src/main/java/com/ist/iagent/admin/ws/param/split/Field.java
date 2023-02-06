package com.ist.iagent.admin.ws.param.split;

import java.util.List;

public class Field {

	private String name;
	private String type;
	private boolean complex;
	private List<Field> fields;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

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
	 * @return the field
	 */
	public List<Field> getField() {
		return fields;
	}

	/**
	 * @param field
	 *            the field to set
	 */
	public void setField(List<Field> field) {
		this.fields = field;
	}

	public String toString() {
		return new StringBuffer().append("\n\t\t\t{").append(
				" name == " + (name == null ? "null" : name)).append(
				" , type == " + (type == null ? "null" : type)).append(
				" , complex == " + complex).append(" , fields == " + fields)
				.append("}").toString();
	}

}
