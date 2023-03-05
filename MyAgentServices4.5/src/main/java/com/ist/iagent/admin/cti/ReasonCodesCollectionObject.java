package com.ist.iagent.admin.cti;

import java.util.ArrayList;

public class ReasonCodesCollectionObject {

	private ArrayList<PropertyKeyValuePair> notReadyCollection;
	private ArrayList<PropertyKeyValuePair> logoutCollection;
	private ArrayList<PropertyKeyValuePair> wrapupCollection;
	public ArrayList<PropertyKeyValuePair> getNotReadyCollection() {
		return notReadyCollection;
	}
	public void setNotReadyCollection(
			ArrayList<PropertyKeyValuePair> notReadyCollection) {
		this.notReadyCollection = notReadyCollection;
	}
	public ArrayList<PropertyKeyValuePair> getLogoutCollection() {
		return logoutCollection;
	}
	public void setLogoutCollection(ArrayList<PropertyKeyValuePair> logoutCollection) {
		this.logoutCollection = logoutCollection;
	}
	public ArrayList<PropertyKeyValuePair> getWrapupCollection() {
		return wrapupCollection;
	}
	public void setWrapupCollection(ArrayList<PropertyKeyValuePair> wrapupCollection) {
		this.wrapupCollection = wrapupCollection;
	}
	
}
