package com.ist.iagent.admin.ws.param.split;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ParameterSchemaReader {

	public static final String PARAMETER_SCHEMA = "ParameterSchema";

	public static final String PARAMETER = "parameter";

	public static final String PARAMETER_TYPE = "type";

	public static final String PARAMETER_COMPLEX = "complex";

	public static final String FIELDS = "fields";

	public static final String FIELD_NAME = "name";

	public static final String FIELD_TYPE = "type";

	public static final String FIELD_COMPLEX = "complex";

	private static Logger log = Logger.getLogger(ParameterAssembler.class);

	public List<Parameter> getParameters(String contentOfXMLFile) {

		List<Parameter> tParameterList = new ArrayList<Parameter>();
		if (contentOfXMLFile == null) {
			log.debug("content of given XML is null. Nothing to process.");
			return tParameterList;
		}
		if (contentOfXMLFile.trim().equalsIgnoreCase("")) {
			log.debug("content of given XML is empty String. Nothing to process.");
			return tParameterList;
		}
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(contentOfXMLFile));
			Document doc = docBuilder.parse(is);
			/**
			 * Document doc = docBuilder.parse(new File(pathOfXMLFile));
			 * log.debug(contentOfXMLFile);
			 */
			/**
			 * ######
			 * 
			 * NORMALISE TEXT REPRESENTATION
			 * 
			 * #######
			 */
			doc.getDocumentElement().normalize();
			NodeList rootNodeList1 = doc.getElementsByTagName(PARAMETER_SCHEMA);
			Node parameterSchemaNode = rootNodeList1.item(0);
			if (parameterSchemaNode.getNodeType() == Node.ELEMENT_NODE) {
				Element parameterSchemaElemNode = (Element) parameterSchemaNode;
				NodeList parameterNodeList = ((NodeList) (parameterSchemaElemNode)
						.getElementsByTagName(PARAMETER));
				tParameterList = getParameterList(parameterNodeList);
			}
		} catch (SAXParseException err) {
			log.error("** Parsing error" + ", line " + err.getLineNumber()
					+ ", uri " + err.getSystemId());
			log.error("SAXParseException : ", err);

		} catch (SAXException e) {
			log.error("SAXException : ", e);

		} catch (Throwable t) {
			log.error("Exception : ", t);
		}
		return tParameterList;
	}

	private List<Parameter> getParameterList(NodeList parameterNodeList) {

		List<Parameter> tParameterList = new ArrayList<Parameter>();
		for (int count = 0; count < parameterNodeList.getLength(); count++) {
			Node parameterNode = parameterNodeList.item(count);
			if (parameterNode.getNodeType() == Node.ELEMENT_NODE) {
				if (tParameterList == null) {
					tParameterList = new ArrayList<Parameter>();
				}
				Element parameterElemNode = (Element) parameterNode;
				Parameter objParameter = new Parameter();

				String complexVal = getTextValue(parameterElemNode,
						PARAMETER_COMPLEX);
				boolean complex = (complexVal != null) ? ("true"
						.equalsIgnoreCase(complexVal)) : false;
				objParameter.setComplex(complex);
				objParameter.setType(getTextValue(parameterElemNode,
						PARAMETER_TYPE));
				NodeList fieldNodeList = parameterElemNode
						.getElementsByTagName(FIELDS);
				List<Field> fieldList = getFields(fieldNodeList);
				objParameter.setFields(fieldList);
				tParameterList.add(objParameter);
			}
		}
		return tParameterList;
	}

	private List<Field> getFields(NodeList fieldNodeList) {

		List<Field> tFieldList = null;
		for (int count = 0; count < fieldNodeList.getLength(); count++) {
			Node fieldNode = fieldNodeList.item(count);
			if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
				if (tFieldList == null) {
					tFieldList = new ArrayList<Field>();
				}
				Element fieldElemNode = (Element) fieldNode;
				Field objField = new Field();
				String complexVal = getTextValue(fieldElemNode, FIELD_COMPLEX);
				boolean complex = (complexVal != null) ? ("true"
						.equalsIgnoreCase(complexVal)) : false;
				objField.setComplex(complex);
				objField.setType(getTextValue(fieldElemNode, FIELD_TYPE));
				objField.setName(getTextValue(fieldElemNode, FIELD_NAME));
				NodeList flchild = fieldElemNode.getElementsByTagName(FIELDS);
				List<Field> fieldList = getFields(flchild);
				objField.setField(fieldList);
				tFieldList.add(objField);
			}
		}
		return tFieldList;
	}

	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nodeList = ele.getElementsByTagName(tagName);
		if (nodeList != null && nodeList.getLength() > 0) {
			Element element = (Element) nodeList.item(0);
			textVal = element.getFirstChild().getNodeValue();
		}
		return textVal;
	}

	// public static void main(String[] args) {
	//
	// String contentOfXMLFile = "<ParameterSchema>" + "<parameter>"
	// + "<type>com.test.servicetest.TestPojo2</type>"
	// + "<complex>true</complex>" + "<fields>"
	// + "<name>address</name>"
	// + "<type>class java.lang.String</type>"
	// + "<complex>false</complex>" + "</fields>" + "<fields>"
	// + "<name>name</name>" + "<type>class java.lang.String</type>"
	// + "<complex>false</complex>" + "</fields>" + "</parameter>"
	// + "</ParameterSchema>";
	//
	// String uu = "<ParameterSchema>"
	// + "\n"
	// + "		<parameter>"
	// + "\n"
	// +
	// "			<type>com.ist.iagent.core.service.message.IAgentServiceRequest</type>"
	// + "\n"
	// + "			<complex>true</complex>"
	// + "\n"
	// + "				<fields>"
	// + "\n"
	// + "					<name>serialVersionUID</name>"
	// + "\n"
	// + "					<type>long</type>"
	// + "\n"
	// + "					<complex>false</complex>"
	// + "\n"
	// + "				</fields>"
	// + "\n"
	// + "				<fields>"
	// + "\n"
	// + "					<name>sessionObject</name>"
	// + "\n"
	// +
	// "					<type>com.ist.iagent.core.service.message.IAgentSessionObject</type>"
	// + "\n" + "					<complex>true</complex>" + "\n"
	// + "					<fields>" + "\n"
	// + "						<name>serialVersionUID</name>" + "\n"
	// + "						<type>long</type>" + "\n"
	// + "						<complex>false</complex>" + "\n" + "					</fields>"
	// + "\n" + "					<fields>" + "\n" + "						<name>agentId</name>"
	// + "\n" + "						<type>java.lang.String</type>" + "\n"
	// + "						<complex>false</complex>" + "\n" + "					</fields>"
	// + "\n" + "					<fields>" + "\n" + "						<name>callId</name>"
	// + "\n" + "						<type>java.lang.String</type>" + "\n"
	// + "						<complex>false</complex>" + "\n" + "					</fields>"
	// + "\n" + "					<fields>" + "\n"
	// + "						<name>customerUniqueId</name>" + "\n"
	// + "						<type>java.lang.String</type>" + "\n"
	// + "						<complex>false</complex>" + "\n" + "					</fields>"
	// + "\n" + "					<fields>" + "\n"
	// + "						<name>callVariables</name>" + "\n"
	// + "						<type>java.util.List</type>" + "\n"
	// + "						<complex>false</complex>" + "\n" + "					</fields>"
	// + "\n" + "					<fields>" + "\n"
	// + "						<name>extension</name>" + "\n"
	// + "						<type>java.lang.String</type>" + "\n"
	// + "						<complex>false</complex>" + "\n" + "					</fields>"
	// + "\n" + "				</fields>" + "\n" + "				<fields>" + "\n"
	// + "					<name>serviceName</name>" + "\n"
	// + "					<type>java.lang.String</type>" + "\n"
	// + "					<complex>false</complex>" + "\n" + "				</fields>"
	// + "\n" + "				<fields>" + "\n" + "					<name>parameters</name>"
	// + "\n" + "					<type>[Ljava.lang.Object;</type>" + "\n"
	// + "					<complex>false</complex>" + "\n" + "				</fields>"
	// + "\n" + "		</parameter>" + "\n" + "	</ParameterSchema>";
	//
	// // String contentOfXMLFile = "<parameter-schema>" + "<parameter>"
	// // + "<type>java.lang.String</type>" + "<complex>false</complex>"
	// // + "</parameter><parameter>" + "<type>java.lang.String</type>"
	// // + "<complex>false</complex>" + "</parameter>"
	// // + "</parameter-schema>";
	//
	// // String contentOfXMLFile = null;
	// List<Parameter> tParameterList = new ParameterSchemaReader()
	// .getParameters(uu);
	//
	// log.debug("Total parameters == "
	// + ((tParameterList != null) ? tParameterList.size() : 0)
	// + "\nparameters details == \n"
	// + ((tParameterList != null) ? tParameterList : "null"));
	// }

}
