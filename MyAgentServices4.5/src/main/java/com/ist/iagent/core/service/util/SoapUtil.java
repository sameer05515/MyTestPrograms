package com.ist.iagent.core.service.util;

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import com.sun.org.apache.xerces.internal.dom.ElementNSImpl;
import com.sun.org.apache.xerces.internal.dom.NodeImpl;

public class SoapUtil {

	private static final Logger log = Logger.getLogger(SoapUtil.class);

	public static String PREFIX = "web";
	/**
	 * This variable is should have same value as web service parameter which
	 * brings request to execute client's web service.
	 */
	public static String ClientWsArguement = "clientWsArguement";

	/**
	 * returns value of first available node, it should not be used if value is
	 * of type object that has further child elements.
	 */
	public static void getValueFirstNode(Object object,
			SOAPElement bodyElement, String key, boolean usePrefix)
			throws SOAPException {

		ElementNSImpl mp = (ElementNSImpl) object;

		if (mp.getLength() < 2) {
			log.trace("--------Going to process non-complex element-------");
			log.trace(mp.getLocalName() + "-" + mp.getTextContent());

			// addElement(bodyElement, usePrefix, key).addTextNode(
			// mp.getTextContent());
			return;
		}
		log.trace("--------Going to process complex element-------");

		for (int i = 0; i < (mp.getLength() - 1); i++) {
			// log.trace("--------Parsing " + i + " Node-------");
			log.trace("<" + key + ">");
			NodeImpl nod = (NodeImpl) mp.item(i);
			iterateNod(nod, bodyElement, key, usePrefix);
			log.trace("</" + key + ">");
		}
	}

	//
	// private static SOAPElement addElement(SOAPElement bodyElement,
	// boolean usePrefix, String key) throws SOAPException {
	// SOAPElement returnBodyElement = null;
	// if (usePrefix) {
	// returnBodyElement = bodyElement.addChildElement(key, PREFIX);
	// } else {
	// returnBodyElement = bodyElement.addChildElement(key);
	// }
	// return returnBodyElement;
	// }

	private static void iterateNod(NodeImpl nod, SOAPElement bodyElement,
			String key, boolean usePrefix) throws SOAPException {

		SOAPElement bodyElement2 = null;
		if (nod.getNodeType() == Node.TEXT_NODE) {
			if (!"parameters".equalsIgnoreCase(nod.getParentNode()
					.getLocalName())) {
				log.trace("<" + nod.getParentNode().getLocalName() + ">"
						+ nod.getTextContent() + "</"
						+ nod.getParentNode().getLocalName() + ">");
			}
		} else if (nod.getNodeType() == Node.ELEMENT_NODE) {
			// log.trace("element node "+nod.getLocalName());
		}

		for (int i = 0; i < nod.getChildNodes().getLength(); i++) {
			NodeImpl nodImpl = (NodeImpl) nod.item(i);
			iterateNod(nodImpl, bodyElement2, key, usePrefix);
		}
	}

}
