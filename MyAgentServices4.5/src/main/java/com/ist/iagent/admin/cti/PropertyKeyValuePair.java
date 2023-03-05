package com.ist.iagent.admin.cti;


public class PropertyKeyValuePair {

	private String key;
	private String value;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public String toString(){
		
		return this.key+"="+this.value;
		
	}
}
