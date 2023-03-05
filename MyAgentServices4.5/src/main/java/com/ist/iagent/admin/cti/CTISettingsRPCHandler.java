package com.ist.iagent.admin.cti;

import java.util.ArrayList;

public class CTISettingsRPCHandler {

	public String getCTIType() {

		CTIPropertyManager propManager = CTIPropertyManager.getInstance();
		return propManager.getCTIType();

	}

	public ArrayList<PropertyKeyValuePair> getCTISettings() {

		CTIPropertyManager propManager = CTIPropertyManager.getInstance();

		return propManager.readPropertiesFromHtmlTemplet();

	}

	public ReasonCodesCollectionObject getDefinedReasonCodes() {

		CTIPropertyManager propManager = CTIPropertyManager.getInstance();

		ArrayList<PropertyKeyValuePair> notReadyCollection = propManager
				.readReasonCodesFromHtmlTemplet("not_ready_reason");
		ArrayList<PropertyKeyValuePair> logoutCollection = propManager
				.readReasonCodesFromHtmlTemplet("logout_reason");
		ArrayList<PropertyKeyValuePair> wrapupCollection = propManager
				.readReasonCodesFromHtmlTemplet("wrapup");
		ReasonCodesCollectionObject returnObject = new ReasonCodesCollectionObject();

		returnObject.setLogoutCollection(logoutCollection);
		returnObject.setNotReadyCollection(notReadyCollection);
		returnObject.setWrapupCollection(wrapupCollection);

		return returnObject;

	}

	public String saveCTISettings(ArrayList<PropertyKeyValuePair> propertyList,
			ArrayList<PropertyKeyValuePair> notReadyCodes,
			ArrayList<PropertyKeyValuePair> logoutCodes,
			ArrayList<PropertyKeyValuePair> wrapupCodes) {

		CTIPropertyManager propManager = CTIPropertyManager.getInstance();
		propManager.writePropertiesInHTMLTemplet(propertyList,notReadyCodes,logoutCodes,wrapupCodes);
		return "Settings Saved";
	}
}
