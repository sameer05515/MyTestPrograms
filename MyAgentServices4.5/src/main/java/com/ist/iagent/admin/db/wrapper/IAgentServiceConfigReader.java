package com.ist.iagent.admin.db.wrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.ist.iagent.admin.db.pojo.DataSource;
import com.ist.iagent.admin.db.pojo.IAgentServiceParameter;
import com.ist.iagent.admin.db.pojo.JarDTO;
import com.ist.iagent.admin.db.pojo.JavaService;
import com.ist.iagent.admin.db.pojo.ParameterDescDTO;
import com.ist.iagent.admin.db.pojo.QueryParameter;
import com.ist.iagent.admin.db.pojo.QueryService;
import com.ist.iagent.admin.db.pojo.WSService;
import com.ist.iagent.admin.db.pojo.WSdlDTO;

public class IAgentServiceConfigReader {

	private static Logger log = Logger
			.getLogger(IAgentServiceConfigReader.class);

	/**
	 * returns List<JavaService> from data saved in xmlFile
	 * 
	 * @param File
	 *            xmlFile file object of given xml
	 * 
	 * @return List<JavaService>
	 * 
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public List<JavaService> getJavaservicesListFromXML(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException {

		Element rootElemNode = getRootNodeElement(xmlFile);
		return getJavaservicesListFromXML(rootElemNode);
	}

	/**
	 * returns List<JarDTO> from data saved in xmlFile
	 * 
	 * @param File
	 *            xmlFile file object of given xml
	 * 
	 * @return List<JarDTO>
	 * 
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public List<JarDTO> getJarListFromXML(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException {

		Element rootElemNode = getRootNodeElement(xmlFile);
		return getJarListFromXML(rootElemNode);
	}

	/**
	 * returns List<WSdlDTO> from data saved in xmlFile
	 * 
	 * @param File
	 *            xmlFile file object of given xml
	 * 
	 * @return List<WSdlDTO>
	 * 
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public List<WSdlDTO> getWSdlDTOListFromXML(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException {

		Element rootElemNode = getRootNodeElement(xmlFile);
		return getWSdlDTOListFromXML(rootElemNode);
	}

	/**
	 * returns List<WSService> from data saved in xmlFile
	 * 
	 * @param File
	 *            xmlFile file object of given xml
	 * 
	 * @return List<WSService>
	 * 
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public List<WSService> getWSServiceListFromXML(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException {

		Element rootElemNode = getRootNodeElement(xmlFile);
		return getWSServiceListFromXML(rootElemNode);

	}

	/**
	 * returns List<DataSource> from data saved in xmlFile
	 * 
	 * @param File
	 *            xmlFile file object of given xml
	 * 
	 * @return List<DataSource>
	 * 
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public List<DataSource> getDataSourceListFromXML(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException {

		Element rootElemNode = getRootNodeElement(xmlFile);
		return getDataSourceListFromXML(rootElemNode);
	}

	/**
	 * returns List<QueryService> from data saved in xmlFile
	 * 
	 * @param File
	 *            xmlFile file object of given xml
	 * 
	 * @return List<QueryService>
	 * 
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public List<QueryService> getQueryServiceListFromXML(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException {

		Element rootElemNode = getRootNodeElement(xmlFile);
		return getQueryServiceListFromXML(rootElemNode);
	}

	/**
	 * __________________________
	 * 
	 * 
	 * Helper methods
	 * 
	 * __________________________
	 * 
	 * */
	private int getIntValue(String givenString) {

		return (givenString != null && !givenString.trim().equalsIgnoreCase("")) ? (Integer
				.parseInt(givenString)) : 0;
	}

	private String getCharacterDataFromElement(Element e) {

		Node child = e.getFirstChild();
		if ((child != null) && (child instanceof CharacterData)) {
			CharacterData cd = (CharacterData) child;
			log.debug(cd.getData());
			return cd.getData();
		}
		return "";
	}

	// private String getTextValue(Element ele, String tagName) {
	//
	// String textVal = null;
	// NodeList nodeList = ele.getElementsByTagName(tagName);
	// if (nodeList != null && nodeList.getLength() > 0) {
	// Element element = (Element) nodeList.item(0);
	// textVal = element.getFirstChild().getNodeValue();
	// }
	// return textVal;
	// }

	/**
	 * _______
	 * 
	 * First Essential method
	 * 
	 * ____________
	 * 
	 * */

	private Element getRootNodeElement(File xmlFile)
			throws ParserConfigurationException, SAXException, IOException {

		try {
			// SAXParserFactory factory = SAXParserFactory.newInstance();
			InputStream inputData = new FileInputStream(xmlFile);
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(inputData);
			doc.getDocumentElement().normalize();
			NodeList rootNodeList1 = doc
					.getElementsByTagName(IAgentServiceConfigConstants.ROOT
							.getNodeName());
			Node rootNode = rootNodeList1.item(0);
			if (rootNode.getNodeType() == Node.ELEMENT_NODE) {
				Element rootElemNode = (Element) rootNode;
				return rootElemNode;
			}
		} catch (FileNotFoundException e) {
			log.debug("FileNotFoundException : ", e);
			throw e;
		} catch (ParserConfigurationException e) {
			log.debug("ParserConfigurationException : ", e);
			throw e;
		} catch (SAXException e) {
			log.debug("SAXException : ", e);
			throw e;
		} catch (IOException e) {
			log.debug("IOException : ", e);
			throw e;
		}
		return null;
	}

	/**
	 * ______________________________
	 * 
	 * get java services from xml
	 * 
	 * ______________________________
	 * 
	 * */
	private List<JavaService> getJavaservicesListFromXML(Element rootElemNode) {

		List<JavaService> listJavaServices = new ArrayList<JavaService>();
		if (rootElemNode == null) {
			return listJavaServices;
		}
		/** ____ get java service list node ____ */
		NodeList javaServicelistNodeList = ((NodeList) (rootElemNode)
				.getElementsByTagName(IAgentServiceConfigConstants.JAVA_SERVICE_LIST
						.getNodeName()));

		if (javaServicelistNodeList != null
				&& javaServicelistNodeList.getLength() > 0) {
			Node javaServicelistNode = javaServicelistNodeList.item(0);
			if (javaServicelistNode.getNodeType() == Node.ELEMENT_NODE) {
				Element javaServicelistElemNode = (Element) javaServicelistNode;

				/** ____ get java services nodes ____ */
				NodeList javaServicelist = ((NodeList) (javaServicelistElemNode)
						.getElementsByTagName(IAgentServiceConfigConstants.JavaService.node
								.getNodeName()));
				if (javaServicelist != null) {
					for (int count = 0; count < javaServicelist.getLength(); count++) {

						Node javaServiceNode = javaServicelist.item(count);
						if (javaServiceNode.getNodeType() == Node.ELEMENT_NODE) {
							Element javaServiceElemNode = (Element) javaServiceNode;
							JavaService objJavaService = getJavaService(javaServiceElemNode);
							listJavaServices.add(objJavaService);
						}

					}
				}
			}
		}

		return listJavaServices;
	}

	private JavaService getJavaService(Element javaServiceElemNode) {

		JavaService objJavaService = null;
		if (javaServiceElemNode != null) {
			objJavaService = new JavaService();

			/** ____ JavaService.serviceId ____ * */
			objJavaService
					.setServiceId(getIntValue(javaServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.JavaService.serviceId
									.getNodeName())));

			/** ____ JavaService.linkedJarId ____ * */
			String linkedJarIdStr = javaServiceElemNode
					.getAttribute(IAgentServiceConfigConstants.JavaService.linkedJarId
							.getNodeName());
			int linkedJarId = (linkedJarIdStr != null) ? (Integer
					.parseInt(linkedJarIdStr)) : 0;
			objJavaService.setLinkedJarId(linkedJarId);

			/** ____ JavaService.serviceName ____ * */
			objJavaService
					.setServiceName(javaServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.JavaService.serviceName
									.getNodeName()));

			/** ____ JavaService.className ____ * */
			objJavaService
					.setClassName(javaServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.JavaService.className
									.getNodeName()));

			/** ____ JavaService.methodName ____ * */
			objJavaService
					.setMethodName(javaServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.JavaService.methodName
									.getNodeName()));

			/** ____ JavaService.saveLastRecord ____ * */
			String saveLastRecordStr = javaServiceElemNode
					.getAttribute(IAgentServiceConfigConstants.JavaService.saveLastRecord
							.getNodeName());
			boolean saveLastRecord = "true".equalsIgnoreCase(saveLastRecordStr);
			objJavaService.setSaveLastRecord(saveLastRecord);

			/** ____ JavaService.loggingAllowed ____ * */
			String loggingAllowedStr = javaServiceElemNode
					.getAttribute(IAgentServiceConfigConstants.JavaService.loggingAllowed
							.getNodeName());
			boolean loggingAllowed = "true".equalsIgnoreCase(loggingAllowedStr);

			objJavaService.setLoggingAllowed(loggingAllowed);

			/** ____ JavaService.inputType ____ * */
			NodeList inputTypelist = ((NodeList) (javaServiceElemNode)
					.getElementsByTagName(IAgentServiceConfigConstants.JavaService.inputType
							.getNodeName()));
			Node inputTypelistNode = inputTypelist.item(0);
			if (inputTypelistNode.getNodeType() == Node.ELEMENT_NODE) {
				Element inputTypelistElemNode = (Element) inputTypelistNode;
				/** ____ get java services nodes ____ */
				NodeList javaServiceParameterlist = ((NodeList) (inputTypelistElemNode)
						.getElementsByTagName(IAgentServiceConfigConstants.ParameterDescDTO.node
								.getNodeName()));
				if (javaServiceParameterlist != null) {
					List<ParameterDescDTO> inputTypeParameterList = getParameterDescDTOs(javaServiceParameterlist);
					List<IAgentServiceParameter> listISP = new ArrayList<IAgentServiceParameter>();
					for (ParameterDescDTO qp : inputTypeParameterList) {
						listISP.add(qp);
					}
					objJavaService.setInputType(listISP);
				}
			}

			/** ____ JavaService.returnType ____ * */
			NodeList returnTypelist = ((NodeList) (javaServiceElemNode)
					.getElementsByTagName(IAgentServiceConfigConstants.JavaService.returnType
							.getNodeName()));
			Node returnTypelistNode = returnTypelist.item(0);
			if (inputTypelistNode.getNodeType() == Node.ELEMENT_NODE) {
				Element returnTypelistElemNode = (Element) returnTypelistNode;
				/** ____ get java services nodes ____ */
				NodeList javaServiceParameterlist = ((NodeList) (returnTypelistElemNode)
						.getElementsByTagName(IAgentServiceConfigConstants.ParameterDescDTO.node
								.getNodeName()));
				if (javaServiceParameterlist != null) {
					List<ParameterDescDTO> inputTypeParameterList = getParameterDescDTOs(javaServiceParameterlist);
					List<IAgentServiceParameter> listISP = new ArrayList<IAgentServiceParameter>();
					for (ParameterDescDTO qp : inputTypeParameterList) {
						listISP.add(qp);
					}
					objJavaService.setReturnType(listISP);
				}
			}
		}
		return objJavaService;
	}

	private List<ParameterDescDTO> getParameterDescDTOs(
			NodeList javaServiceParameterlist) {

		List<ParameterDescDTO> serviceParamList = new ArrayList<ParameterDescDTO>();
		if (javaServiceParameterlist != null
				&& javaServiceParameterlist.getLength() > 0) {
			for (int count = 0; count < javaServiceParameterlist.getLength(); count++) {
				Node javaServiceParameterNode = javaServiceParameterlist
						.item(count);
				if (javaServiceParameterNode.getNodeType() == Node.ELEMENT_NODE) {
					Element javaServiceElemNode = (Element) javaServiceParameterNode;
					ParameterDescDTO objDescDTO = new ParameterDescDTO();

					/** ____ ParameterDescDTO.serviceId ____ * */
					String serviceIdStr = javaServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.ParameterDescDTO.serviceId
									.getNodeName());
					int serviceId = (serviceIdStr != null && !serviceIdStr
							.trim().equalsIgnoreCase("")) ? (Integer
							.parseInt(serviceIdStr)) : 0;
					objDescDTO.setServiceId(serviceId);

					/** ____ ParameterDescDTO.linkedJarId ____ * */
					String paramIdStr = javaServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.ParameterDescDTO.paramId
									.getNodeName());
					int paramId = (paramIdStr != null && !paramIdStr.trim()
							.equalsIgnoreCase("")) ? (Integer
							.parseInt(paramIdStr)) : 0;
					objDescDTO.setParamId(paramId);

					/** ____ ParameterDescDTO.parameterName ____ * */
					objDescDTO
							.setParameterName(javaServiceElemNode
									.getAttribute(IAgentServiceConfigConstants.ParameterDescDTO.parameterName
											.getNodeName()));

					/** ____ ParameterDescDTO.parameterType ____ * */
					objDescDTO
							.setParameterType(javaServiceElemNode
									.getAttribute(IAgentServiceConfigConstants.ParameterDescDTO.parameterType
											.getNodeName()));

					/** ____ ParameterDescDTO.parameterDescr ____ * */
					objDescDTO
							.setParameterDescr(javaServiceElemNode
									.getAttribute(IAgentServiceConfigConstants.ParameterDescDTO.parameterDescr
											.getNodeName()));

					serviceParamList.add(objDescDTO);
				}
			}
		}
		return serviceParamList;
	}

	/**
	 * ______________________________
	 * 
	 * get jar list from xml
	 * 
	 * ______________________________
	 * */

	private List<JarDTO> getJarListFromXML(Element rootElemNode) {

		List<JarDTO> listJarDTOs = new ArrayList<JarDTO>();
		if (rootElemNode == null) {
			return listJarDTOs;
		}
		/** ____ get jar list node ____ */
		NodeList jarlistNodeList = ((NodeList) (rootElemNode)
				.getElementsByTagName(IAgentServiceConfigConstants.JARLIST
						.getNodeName()));

		if (jarlistNodeList != null && jarlistNodeList.getLength() > 0) {
			Node jarlistNode = jarlistNodeList.item(0);
			if (jarlistNode.getNodeType() == Node.ELEMENT_NODE) {
				Element jarlistElemNode = (Element) jarlistNode;
				/** ____ get jar nodes ____ */
				NodeList jarlist = ((NodeList) (jarlistElemNode)
						.getElementsByTagName(IAgentServiceConfigConstants.JarDTO.node
								.getNodeName()));
				if (jarlist != null) {
					for (int count = 0; count < jarlist.getLength(); count++) {
						Node jarNode = jarlist.item(count);
						if (jarNode.getNodeType() == Node.ELEMENT_NODE) {
							Element jarElemNode = (Element) jarNode;
							JarDTO objJarDTO = new JarDTO();

							/** ____ JarDTO.jarId ____ * */
							String jarID = jarElemNode
									.getAttribute(IAgentServiceConfigConstants.JarDTO.jarId
											.getNodeName());
							int complex = (jarID != null) ? (Integer
									.parseInt(jarID)) : 0;
							objJarDTO.setJar_id(complex);

							/** ____ JarDTO.jarName ____ * */
							objJarDTO
									.setJar_name(jarElemNode
											.getAttribute(IAgentServiceConfigConstants.JarDTO.jarName
													.getNodeName()));

							listJarDTOs.add(objJarDTO);
						}
					}
				}

			}
		}

		return listJarDTOs;
	}

	/**
	 * ______________________________
	 * 
	 * get WSdlDTO list from xml
	 * 
	 * ______________________________
	 * */

	private List<WSdlDTO> getWSdlDTOListFromXML(Element rootElemNode) {

		List<WSdlDTO> listWSdlDTOs = new ArrayList<WSdlDTO>();
		if (rootElemNode == null) {
			return listWSdlDTOs;
		}
		/** ____ get wsdl list node ____ */
		NodeList wsdlListNodeList = ((NodeList) (rootElemNode)
				.getElementsByTagName(IAgentServiceConfigConstants.WSDL_LIST
						.getNodeName()));

		if (wsdlListNodeList != null && wsdlListNodeList.getLength() > 0) {
			Node wsdlListNode = wsdlListNodeList.item(0);
			if (wsdlListNode.getNodeType() == Node.ELEMENT_NODE) {
				Element wsdlListElemNode = (Element) wsdlListNode;
				/** ____ get wsdl nodes ____ */
				NodeList wsdlList = ((NodeList) (wsdlListElemNode)
						.getElementsByTagName(IAgentServiceConfigConstants.WSdlDTO.node
								.getNodeName()));
				if (wsdlList != null) {
					for (int count = 0; count < wsdlList.getLength(); count++) {
						Node wsdlNode = wsdlList.item(count);
						if (wsdlNode.getNodeType() == Node.ELEMENT_NODE) {
							Element wsdlElemNode = (Element) wsdlNode;
							WSdlDTO objWSdlDTO = new WSdlDTO();

							/** ____ WSdlDTO.wsId ____ * */
							String jarID = wsdlElemNode
									.getAttribute(IAgentServiceConfigConstants.WSdlDTO.wsId
											.getNodeName());
							int complex = (jarID != null) ? (Integer
									.parseInt(jarID)) : 0;
							objWSdlDTO.setWsId(complex);

							/** ____ WSdlDTO.wsName ____ * */
							objWSdlDTO
									.setWsName(wsdlElemNode
											.getAttribute(IAgentServiceConfigConstants.WSdlDTO.wsName
													.getNodeName()));

							/** ____ WSdlDTO.wsURL ____ * */
							objWSdlDTO
									.setWsURL(wsdlElemNode
											.getAttribute(IAgentServiceConfigConstants.WSdlDTO.wsURL
													.getNodeName()));

							/** ____ WSdlDTO.nameSpace ____ * */
							objWSdlDTO
									.setNameSpace(wsdlElemNode
											.getAttribute(IAgentServiceConfigConstants.WSdlDTO.nameSpace
													.getNodeName()));

							/** ____ WSdlDTO.usePrefix ____ * */
							objWSdlDTO
									.setUsePrefix(wsdlElemNode
											.getAttribute(IAgentServiceConfigConstants.WSdlDTO.usePrefix
													.getNodeName()));

							/** ____ WSdlDTO.soapBindingInterface ____ * */
							objWSdlDTO
									.setSoapBindingInterface(wsdlElemNode
											.getAttribute(IAgentServiceConfigConstants.WSdlDTO.soapBindingInterface
													.getNodeName()));

							/** ____ WSdlDTO.serviceClass ____ * */
							objWSdlDTO
									.setServiceClass(wsdlElemNode
											.getAttribute(IAgentServiceConfigConstants.WSdlDTO.serviceClass
													.getNodeName()));

							/** ____ WSdlDTO.wsPackageName ____ * */
							objWSdlDTO
									.setWsPackageName(wsdlElemNode
											.getAttribute(IAgentServiceConfigConstants.WSdlDTO.wsPackageName
													.getNodeName()));

							listWSdlDTOs.add(objWSdlDTO);
						}
					}
				}

			}
		}

		return listWSdlDTOs;
	}

	/**
	 * ______________________________
	 * 
	 * get WSService list from xml
	 * 
	 * ______________________________
	 * */

	private List<WSService> getWSServiceListFromXML(Element rootElemNode) {

		List<WSService> listWSServices = new ArrayList<WSService>();
		if (rootElemNode == null) {
			return listWSServices;
		}
		/** ____ get java service list node ____ */
		NodeList wsServicelistNodeList = ((NodeList) (rootElemNode)
				.getElementsByTagName(IAgentServiceConfigConstants.WS_SERVICE_LIST
						.getNodeName()));

		if (wsServicelistNodeList != null
				&& wsServicelistNodeList.getLength() > 0) {
			Node wsServicelistNode = wsServicelistNodeList.item(0);
			if (wsServicelistNode.getNodeType() == Node.ELEMENT_NODE) {
				Element wsServicelistElemNode = (Element) wsServicelistNode;
				/** ____ get java services nodes ____ */
				NodeList wsServicelist = ((NodeList) (wsServicelistElemNode)
						.getElementsByTagName(IAgentServiceConfigConstants.WSService.node
								.getNodeName()));
				if (wsServicelist != null) {
					for (int count = 0; count < wsServicelist.getLength(); count++) {

						Node wsServiceNode = wsServicelist.item(count);
						if (wsServiceNode.getNodeType() == Node.ELEMENT_NODE) {
							Element wsServiceElemNode = (Element) wsServiceNode;
							WSService objWSService = getWSService(wsServiceElemNode);
							listWSServices.add(objWSService);
						}

					}
				}
			}
		}

		return listWSServices;
	}

	private WSService getWSService(Element wsServiceElemNode) {

		WSService objWSService = null;
		if (wsServiceElemNode != null) {
			objWSService = new WSService();

			/** ____ WSService.serviceId ____ * */
			objWSService
					.setServiceId(getIntValue(wsServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.WSService.serviceId
									.getNodeName())));

			/** ____ WSService.serviceName ____ * */
			objWSService
					.setServiceName(wsServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.WSService.serviceName
									.getNodeName()));

			/** ____ WSService.linkedJarId ____ * */
			String linkedWSIdStr = wsServiceElemNode
					.getAttribute(IAgentServiceConfigConstants.WSService.linkedWSId
							.getNodeName());
			int linkedWSId = (linkedWSIdStr != null) ? (Integer
					.parseInt(linkedWSIdStr)) : 0;
			objWSService.setLinkedWSId(linkedWSId);

			/** ____ WSService.operationName ____ * */
			objWSService
					.setOperationName(wsServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.WSService.operationName
									.getNodeName()));

			/** ____ WSService.portName ____ * */
			objWSService
					.setPortName(wsServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.WSService.portName
									.getNodeName()));

			/** ____ WSService.saveLastRecord ____ * */
			String saveLastRecordStr = wsServiceElemNode
					.getAttribute(IAgentServiceConfigConstants.WSService.saveLastRecord
							.getNodeName());
			boolean saveLastRecord = "true".equalsIgnoreCase(saveLastRecordStr);
			objWSService.setSaveLastRecord(saveLastRecord);

			/** ____ WSService.loggingAllowed ____ * */
			String loggingAllowedStr = wsServiceElemNode
					.getAttribute(IAgentServiceConfigConstants.WSService.loggingAllowed
							.getNodeName());
			boolean loggingAllowed = "true".equalsIgnoreCase(loggingAllowedStr);

			objWSService.setLoggingAllowed(loggingAllowed);

			/** ___ WSService.parameterSchema ____ */
			NodeList parameterSchemalist = ((NodeList) (wsServiceElemNode)
					.getElementsByTagName(IAgentServiceConfigConstants.WSService.parameterSchema
							.getNodeName()));
			Node parameterSchemalistNode = parameterSchemalist.item(0);
			if (parameterSchemalistNode.getNodeType() == Node.ELEMENT_NODE) {
				Element parameterSchemaListElemNode = (Element) parameterSchemalistNode;
				String paramSchema = getCharacterDataFromElement(parameterSchemaListElemNode);
				objWSService.setParameterSchema(paramSchema);
				// log.debug("paramSchema == " + paramSchema);
			}

			/** ____ WSService.inputType ____ * */
			NodeList inputTypelist = ((NodeList) (wsServiceElemNode)
					.getElementsByTagName(IAgentServiceConfigConstants.WSService.inputType
							.getNodeName()));
			Node inputTypelistNode = inputTypelist.item(0);
			if (inputTypelistNode.getNodeType() == Node.ELEMENT_NODE) {
				Element inputTypelistElemNode = (Element) inputTypelistNode;
				/** ____ get java services nodes ____ */
				NodeList wsServiceParameterlist = ((NodeList) (inputTypelistElemNode)
						.getElementsByTagName(IAgentServiceConfigConstants.ParameterDescDTO.node
								.getNodeName()));
				if (wsServiceParameterlist != null) {
					List<ParameterDescDTO> inputTypeParameterList = getParameterDescDTOs(wsServiceParameterlist);
					List<IAgentServiceParameter> listISP = new ArrayList<IAgentServiceParameter>();
					for (ParameterDescDTO qp : inputTypeParameterList) {
						listISP.add(qp);
					}
					objWSService.setInputType(listISP);
				}
			}

			/** ____ WSService.returnType ____ * */
			NodeList returnTypelist = ((NodeList) (wsServiceElemNode)
					.getElementsByTagName(IAgentServiceConfigConstants.WSService.returnType
							.getNodeName()));
			Node returnTypelistNode = returnTypelist.item(0);
			if (inputTypelistNode.getNodeType() == Node.ELEMENT_NODE) {
				Element returnTypelistElemNode = (Element) returnTypelistNode;
				/** ____ get java services nodes ____ */
				NodeList wsServiceParameterlist = ((NodeList) (returnTypelistElemNode)
						.getElementsByTagName(IAgentServiceConfigConstants.ParameterDescDTO.node
								.getNodeName()));
				if (wsServiceParameterlist != null) {
					List<ParameterDescDTO> inputTypeParameterList = getParameterDescDTOs(wsServiceParameterlist);
					List<IAgentServiceParameter> listISP = new ArrayList<IAgentServiceParameter>();
					for (ParameterDescDTO qp : inputTypeParameterList) {
						listISP.add(qp);
					}
					objWSService.setReturnType(listISP);
				}
			}
		}

		return objWSService;
	}

	/**
	 * ______________________________
	 * 
	 * get DataSource list from xml
	 * 
	 * ______________________________
	 * */

	private List<DataSource> getDataSourceListFromXML(Element rootElemNode) {

		List<DataSource> listDataSources = new ArrayList<DataSource>();
		if (rootElemNode == null) {
			return listDataSources;
		}
		/** ____ get wsdl list node ____ */
		NodeList dataSourceListNodeList = ((NodeList) (rootElemNode)
				.getElementsByTagName(IAgentServiceConfigConstants.DATA_SOURCE_LIST
						.getNodeName()));

		if (dataSourceListNodeList != null
				&& dataSourceListNodeList.getLength() > 0) {
			Node dataSourceListNode = dataSourceListNodeList.item(0);
			if (dataSourceListNode.getNodeType() == Node.ELEMENT_NODE) {
				Element dataSourceListElemNode = (Element) dataSourceListNode;
				/** ____ get datasource nodes ____ */
				NodeList dataSourceList = ((NodeList) (dataSourceListElemNode)
						.getElementsByTagName(IAgentServiceConfigConstants.DataSource.node
								.getNodeName()));
				if (dataSourceList != null) {
					for (int count = 0; count < dataSourceList.getLength(); count++) {
						Node dataSourceNode = dataSourceList.item(count);
						if (dataSourceNode.getNodeType() == Node.ELEMENT_NODE) {
							Element dataSourceElemNode = (Element) dataSourceNode;
							DataSource objDataSource = new DataSource();

							/** ____ DataSource.dbID ____ * */
							String dbID = dataSourceElemNode
									.getAttribute(IAgentServiceConfigConstants.DataSource.dbID
											.getNodeName());
							int complex = (dbID != null) ? (Integer
									.parseInt(dbID)) : 0;
							objDataSource.setDbID(complex);

							/** ____ DataSource.dbAlias ____ * */
							objDataSource
									.setDbAlias(dataSourceElemNode
											.getAttribute(IAgentServiceConfigConstants.DataSource.dbAlias
													.getNodeName()));

							/** ____ DataSource.dbUsername ____ * */
							objDataSource
									.setDbUsername(dataSourceElemNode
											.getAttribute(IAgentServiceConfigConstants.DataSource.dbUsername
													.getNodeName()));

							/** ____ DataSource.dbPassword ____ * */
							objDataSource
									.setDbPassword(dataSourceElemNode
											.getAttribute(IAgentServiceConfigConstants.DataSource.dbPassword
													.getNodeName()));

							/** ____ DataSource.dbDriverClassName ____ * */
							objDataSource
									.setDbDriverClassName(dataSourceElemNode
											.getAttribute(IAgentServiceConfigConstants.DataSource.dbDriverClassName
													.getNodeName()));

							/** ____ DataSource.dbURL ____ * */
							objDataSource
									.setDbURL(dataSourceElemNode
											.getAttribute(IAgentServiceConfigConstants.DataSource.dbURL
													.getNodeName()));

							listDataSources.add(objDataSource);
						}
					}
				}

			}
		}

		return listDataSources;
	}

	/**
	 * ______________________________
	 * 
	 * get QueryService list from xml
	 * 
	 * ______________________________
	 * */

	private List<QueryService> getQueryServiceListFromXML(Element rootElemNode) {

		List<QueryService> listQueryServices = new ArrayList<QueryService>();
		if (rootElemNode == null) {
			return listQueryServices;
		}
		/** ____ get query service list node ____ */
		NodeList queryServicelistNodeList = ((NodeList) (rootElemNode)
				.getElementsByTagName(IAgentServiceConfigConstants.QUERY_SERVICE_LIST
						.getNodeName()));

		if (queryServicelistNodeList != null
				&& queryServicelistNodeList.getLength() > 0) {
			Node queryServicelistNode = queryServicelistNodeList.item(0);
			if (queryServicelistNode.getNodeType() == Node.ELEMENT_NODE) {
				Element queryServicelistElemNode = (Element) queryServicelistNode;
				/** ____ get java services nodes ____ */
				NodeList queryServicelist = ((NodeList) (queryServicelistElemNode)
						.getElementsByTagName(IAgentServiceConfigConstants.QueryService.node
								.getNodeName()));
				if (queryServicelist != null) {
					for (int count = 0; count < queryServicelist.getLength(); count++) {

						Node queryServiceNode = queryServicelist.item(count);
						if (queryServiceNode.getNodeType() == Node.ELEMENT_NODE) {
							Element queryServiceElemNode = (Element) queryServiceNode;
							QueryService objQueryService = getQueryService(queryServiceElemNode);
							listQueryServices.add(objQueryService);
						}

					}
				}
			}
		}

		return listQueryServices;
	}

	private QueryService getQueryService(Element queryServiceElemNode) {

		QueryService objQueryService = null;
		if (queryServiceElemNode != null) {
			objQueryService = new QueryService();

			/** ____ QueryService.serviceId ____ * */
			objQueryService
					.setServiceId(getIntValue(queryServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.QueryService.serviceId
									.getNodeName())));

			/** ____ QueryService.serviceName ____ * */
			objQueryService
					.setServiceName(queryServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.QueryService.serviceName
									.getNodeName()));

			/** ____ QueryService.linkedDbID ____ * */
			String linkedDbIDStr = queryServiceElemNode
					.getAttribute(IAgentServiceConfigConstants.QueryService.linkedDbID
							.getNodeName());
			int linkedDbID = (linkedDbIDStr != null) ? (Integer
					.parseInt(linkedDbIDStr)) : 0;
			objQueryService.setLinkedDbID(linkedDbID);

			/** ____ QueryService.storedProc ____ * */
			String storedProcStr = queryServiceElemNode
					.getAttribute(IAgentServiceConfigConstants.QueryService.storedProc
							.getNodeName());
			boolean storedProc = "true".equalsIgnoreCase(storedProcStr);
			objQueryService.setStoredProc(storedProc);

			/** ____ QueryService.queryResultType ____ * */
			objQueryService
					.setQueryResultType(queryServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.QueryService.queryResultType
									.getNodeName()));

			/** ____ QueryService.queryType ____ * */
			objQueryService
					.setQueryType(queryServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.QueryService.queryType
									.getNodeName()));

			/** ____ QueryService.loggingAllowed ____ * */
			String loggingAllowedStr = queryServiceElemNode
					.getAttribute(IAgentServiceConfigConstants.QueryService.loggingAllowed
							.getNodeName());
			boolean loggingAllowed = "true".equalsIgnoreCase(loggingAllowedStr);

			objQueryService.setLoggingAllowed(loggingAllowed);

			/** ___ QueryService.parameterSchema ____ */
			NodeList parameterSchemalist = ((NodeList) (queryServiceElemNode)
					.getElementsByTagName(IAgentServiceConfigConstants.QueryService.queryText
							.getNodeName()));
			Node parameterSchemalistNode = parameterSchemalist.item(0);
			if (parameterSchemalistNode.getNodeType() == Node.ELEMENT_NODE) {
				Element parameterSchemaListElemNode = (Element) parameterSchemalistNode;
				String paramSchema = getCharacterDataFromElement(parameterSchemaListElemNode);
				objQueryService.setQueryText(paramSchema);
			}

			/** ____ QueryService.inputType ____ * */
			NodeList inputTypelist = ((NodeList) (queryServiceElemNode)
					.getElementsByTagName(IAgentServiceConfigConstants.QueryService.inputType
							.getNodeName()));
			Node inputTypelistNode = inputTypelist.item(0);
			if (inputTypelistNode.getNodeType() == Node.ELEMENT_NODE) {
				Element inputTypelistElemNode = (Element) inputTypelistNode;
				/** ____ get java services nodes ____ */
				NodeList wsServiceParameterlist = ((NodeList) (inputTypelistElemNode)
						.getElementsByTagName(IAgentServiceConfigConstants.QueryParameter.node
								.getNodeName()));
				if (wsServiceParameterlist != null) {
					List<QueryParameter> inputTypeParameterList = getQueryParameters(wsServiceParameterlist);
					List<IAgentServiceParameter> listISP = new ArrayList<IAgentServiceParameter>();
					for (QueryParameter qp : inputTypeParameterList) {
						listISP.add(qp);
					}
					objQueryService.setInputType(listISP);
				}
			}

			/** ____ QueryService.returnType ____ * */
			NodeList returnTypelist = ((NodeList) (queryServiceElemNode)
					.getElementsByTagName(IAgentServiceConfigConstants.QueryService.returnType
							.getNodeName()));
			Node returnTypelistNode = returnTypelist.item(0);
			if (inputTypelistNode.getNodeType() == Node.ELEMENT_NODE) {
				Element returnTypelistElemNode = (Element) returnTypelistNode;
				/** ____ get java services nodes ____ */
				NodeList queryServiceParameterlist = ((NodeList) (returnTypelistElemNode)
						.getElementsByTagName(IAgentServiceConfigConstants.QueryParameter.node
								.getNodeName()));
				if (queryServiceParameterlist != null) {
					List<QueryParameter> inputTypeParameterList = getQueryParameters(queryServiceParameterlist);
					List<IAgentServiceParameter> listISP = new ArrayList<IAgentServiceParameter>();
					for (QueryParameter qp : inputTypeParameterList) {
						listISP.add(qp);
					}
					objQueryService.setReturnType(listISP);
				}
			}
		}

		return objQueryService;
	}

	private List<QueryParameter> getQueryParameters(
			NodeList queryServiceParameterlist) {

		List<QueryParameter> queryParameterList = new ArrayList<QueryParameter>();
		if (queryServiceParameterlist != null
				&& queryServiceParameterlist.getLength() > 0) {
			for (int count = 0; count < queryServiceParameterlist.getLength(); count++) {
				Node queryServiceParameterNode = queryServiceParameterlist
						.item(count);
				if (queryServiceParameterNode.getNodeType() == Node.ELEMENT_NODE) {
					Element queryServiceElemNode = (Element) queryServiceParameterNode;
					QueryParameter objQueryParameter = new QueryParameter();

					/** ____ QueryParameter.serviceId ____ * */
					String serviceIdStr = queryServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.QueryParameter.serviceId
									.getNodeName());
					int serviceId = (serviceIdStr != null && !serviceIdStr
							.trim().equalsIgnoreCase("")) ? (Integer
							.parseInt(serviceIdStr)) : 0;
					objQueryParameter.setServiceId(serviceId);

					/** ____ QueryParameter.linkedJarId ____ * */
					String paramIdStr = queryServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.QueryParameter.paramId
									.getNodeName());
					int paramId = (paramIdStr != null && !paramIdStr.trim()
							.equalsIgnoreCase("")) ? (Integer
							.parseInt(paramIdStr)) : 0;
					objQueryParameter.setParamId(paramId);

					/** ____ QueryParameter.parameterName ____ * */
					objQueryParameter
							.setParameterName(queryServiceElemNode
									.getAttribute(IAgentServiceConfigConstants.QueryParameter.parameterName
											.getNodeName()));

					/** ____ QueryParameter.parameterType ____ * */
					objQueryParameter
							.setParameterType(queryServiceElemNode
									.getAttribute(IAgentServiceConfigConstants.QueryParameter.parameterType
											.getNodeName()));

					/** ____ QueryParameter.parameterDescr ____ * */
					objQueryParameter
							.setParameterDescr(queryServiceElemNode
									.getAttribute(IAgentServiceConfigConstants.QueryParameter.parameterDescr
											.getNodeName()));

					/** ____ QueryParameter.parameterResultType ____ * */
					objQueryParameter
							.setParameterName(queryServiceElemNode
									.getAttribute(IAgentServiceConfigConstants.QueryParameter.parameterResultType
											.getNodeName()));

					/** ____ QueryParameter.blankAllowed ____ * */

					String blankAllowed = queryServiceElemNode
							.getAttribute(IAgentServiceConfigConstants.QueryParameter.blankAllowed
									.getNodeName());
					objQueryParameter.setBlankAllowed("y"
							.equalsIgnoreCase(blankAllowed));

					/** ____ QueryParameter.paramValue ____ * */
					objQueryParameter
							.setParamValue(queryServiceElemNode
									.getAttribute(IAgentServiceConfigConstants.QueryParameter.paramValue
											.getNodeName()));

					queryParameterList.add(objQueryParameter);
				}
			}
		}
		return queryParameterList;
	}

}
