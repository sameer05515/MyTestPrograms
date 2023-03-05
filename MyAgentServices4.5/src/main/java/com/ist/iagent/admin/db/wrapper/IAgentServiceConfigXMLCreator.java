package com.ist.iagent.admin.db.wrapper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ist.iagent.admin.db.ServiceManagementRPC;
import com.ist.iagent.admin.db.pojo.DataSource;
import com.ist.iagent.admin.db.pojo.IAgentService;
import com.ist.iagent.admin.db.pojo.IAgentServiceParameter;
import com.ist.iagent.admin.db.pojo.JarDTO;
import com.ist.iagent.admin.db.pojo.JavaService;
import com.ist.iagent.admin.db.pojo.ParameterDescDTO;
import com.ist.iagent.admin.db.pojo.QueryParameter;
import com.ist.iagent.admin.db.pojo.QueryService;
import com.ist.iagent.admin.db.pojo.WSService;
import com.ist.iagent.admin.db.pojo.WSdlDTO;
import com.ist.iagent.core.service.ServiceLoaderRPC;

public class IAgentServiceConfigXMLCreator {

	private static Logger log = Logger
			.getLogger(IAgentServiceConfigXMLCreator.class);

	private Document dom;
	private Element rootEle = null;
	// private boolean commentingAllowed = false;
	// private List<String> serviceNamesList = new ArrayList<String>();
	private Set<JarDTO> jarDTOs = new HashSet<JarDTO>();
	private Set<DataSource> dataSources = new HashSet<DataSource>();
	private Set<WSdlDTO> wsdlDTOs = new HashSet<WSdlDTO>();

	public IAgentServiceConfigXMLCreator() {

	}

	/**
	 * exports all data related to services to a document object
	 * 
	 * @param List
	 *            <String> names of all services
	 * 
	 * @return Document object containing xml data
	 * 
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SQLException
	 */
	public Document exportDataToXML(List<String> serviceNamesList)
			throws IOException, ParserConfigurationException, SQLException {

		if (!(serviceNamesList != null && serviceNamesList.size() > 0)) {
			serviceNamesList = new ArrayList<String>();
		}
		List<IAgentService> allservices = new ServiceLoaderRPC()
				.getAllServices();
		List<JarDTO> listJarDTOs = new ServiceManagementRPC().getJarList();
		List<DataSource> listDataSources = new ServiceManagementRPC()
				.getDataSourceList();
		List<WSdlDTO> listWSdlDTOs = new ServiceManagementRPC().getWSDLList();
		for (String serviceNameee : serviceNamesList) {
			for (IAgentService objIAgentService : allservices) {
				if (objIAgentService.getServiceName().equals(serviceNameee)) {
					if (objIAgentService instanceof JavaService) {
						JavaService service = (JavaService) objIAgentService;
						for (JarDTO objJ : listJarDTOs) {
							if (service.getLinkedJarId() == objJ.getJar_id()) {
								jarDTOs.add(objJ);
							}
						}
					} else if (objIAgentService instanceof QueryService) {
						QueryService service = (QueryService) objIAgentService;
						for (DataSource objD : listDataSources) {
							if (service.getLinkedDbID() == objD.getDbID()) {
								dataSources.add(objD);
							}
						}
					} else if (objIAgentService instanceof WSService) {
						WSService service = (WSService) objIAgentService;
						for (WSdlDTO objW : listWSdlDTOs) {
							if (service.getLinkedWSId() == objW.getWsId()) {
								wsdlDTOs.add(objW);
							}
						}
					}
				}
			}
		}
		log.debug("Document creation started .. ");
		// this.serviceNamesList = serviceNamesList;
		createDocument();
		Document dom = createDOMTree();
		/**
		 * As per instructions now only document will be created here File
		 * creation work is now not done from here.
		 * */
		// printToFile(xmlFile);
		log.debug("Generated file successfully.");
		return dom;
	}

	// private void printToFile(File xmlFile) throws IOException {
	//
	// try {
	// OutputFormat format = new OutputFormat(dom);
	// format.setIndenting(true);
	// OutputStream outputStream = new FileOutputStream(xmlFile);
	// XMLSerializer serializer = new XMLSerializer(outputStream, format);
	// serializer.serialize(dom);
	// outputStream.close();
	// } catch (FileNotFoundException e) {
	// log.debug("FileNotFoundException : ", e);
	// throw e;
	// } catch (IOException e) {
	// log.debug("IOException : ", e);
	// throw e;
	// }
	//
	// }

	private Document createDOMTree() throws SQLException {

		rootEle = dom.createElement(IAgentServiceConfigConstants.ROOT
				.getNodeName());
		dom.appendChild(rootEle);
		if (jarDTOs != null && jarDTOs.size() > 0) {
			appendJarsList();
			appendJavaServiceList();
		}
		if (wsdlDTOs != null && wsdlDTOs.size() > 0) {
			appendWsdlList();
			appendWSServiceList();
		}
		if (dataSources != null && dataSources.size() > 0) {
			appendDataSourceList();
			appendQueryServiceList();
		}
		return dom;
	}

	/**
	 * 
	 * _______________________________
	 * 
	 * Append QueryService List
	 * 
	 * _______________________________
	 * 
	 * 
	 * */
	private void appendQueryServiceList() throws SQLException {

		if (!(dataSources != null && dataSources.size() > 0)) {
			return;
		}
		List<QueryService> listQueryServices = new ArrayList<QueryService>();
		List<QueryService> listTotalQueryServices = new ServiceManagementRPC()
				.getQueryServiceList();
		for (QueryService objQservDto : listTotalQueryServices) {
			for (DataSource objDSource : dataSources) {
				if (objQservDto.getLinkedDbID() == objDSource.getDbID()) {
					listQueryServices.add(objQservDto);
				}
			}
		}

		/** ___ IAgentServiceConfigConstants.QUERY_SERVICE_LIST ___ */
		Element wsServiceListEle = dom
				.createElement(IAgentServiceConfigConstants.QUERY_SERVICE_LIST
						.getNodeName());
		// wsServiceListEle.appendChild(dom
		// .createComment("Here query services will be added"));
		if (listQueryServices != null && listQueryServices.size() > 0) {
			Iterator<QueryService> it = listQueryServices.iterator();
			while (it.hasNext()) {
				QueryService b = it.next();
				Element wsServiceEle = createQueryServiceElement(b);
				wsServiceListEle.appendChild(wsServiceEle);
			}
		}

		rootEle.appendChild(wsServiceListEle);
	}

	private Element createQueryServiceElement(QueryService objQueryService) {

		/** ___ IAgentServiceConfigConstants.QueryService.node ___ */
		Element queryServiceEle = dom
				.createElement(IAgentServiceConfigConstants.QueryService.node
						.getNodeName());

		/** ___ QueryService.serviceId ___ */
		queryServiceEle.setAttribute(
				IAgentServiceConfigConstants.QueryService.serviceId
						.getNodeName(), objQueryService.getServiceId() + "");

		/** ___ QueryService.serviceName ___ */
		queryServiceEle.setAttribute(
				IAgentServiceConfigConstants.QueryService.serviceName
						.getNodeName(), objQueryService.getServiceName());

		/** ___ QueryService.linkedDbID ___ */
		queryServiceEle.setAttribute(
				IAgentServiceConfigConstants.QueryService.linkedDbID
						.getNodeName(), objQueryService.getLinkedDbID() + "");

		/** ___ QueryService.storedProc ___ */
		queryServiceEle.setAttribute(
				IAgentServiceConfigConstants.QueryService.storedProc
						.getNodeName(), objQueryService.getStoredProc() + "");

		/** ___ QueryService.queryText ___ */
		Element queryTextEle = dom
				.createElement(IAgentServiceConfigConstants.QueryService.queryText
						.getNodeName());
		String queryText = objQueryService.getQueryText();
		queryTextEle.appendChild(dom
				.createCDATASection((queryText != null) ? queryText : ""));
		queryServiceEle.appendChild(queryTextEle);

		/** ___ QueryService.queryResultType ___ */
		queryServiceEle.setAttribute(
				IAgentServiceConfigConstants.QueryService.queryResultType
						.getNodeName(), objQueryService.getQueryResultType());

		/** ___ QueryService.loggingAllowed ___ */
		queryServiceEle.setAttribute(
				IAgentServiceConfigConstants.QueryService.loggingAllowed
						.getNodeName(), objQueryService.loggingAllowed() + "");

		/** ___ QueryService.queryType ___ */
		queryServiceEle.setAttribute(
				IAgentServiceConfigConstants.QueryService.queryType
						.getNodeName(), objQueryService.getQueryType());

		/**
		 * ___ QueryService.inputType
		 * 
		 * Input parameters ___
		 **/
		Element inputTypeEle = dom
				.createElement(IAgentServiceConfigConstants.QueryService.inputType
						.getNodeName());
		// if (commentingAllowed) {
		// inputTypeEle
		// .appendChild(dom
		// .createComment("Here ws services input parameters will be added"));
		// }
		List<IAgentServiceParameter> inputTypeParameters = objQueryService
				.getInputType();
		if (inputTypeParameters != null && inputTypeParameters.size() > 0) {
			for (IAgentServiceParameter param : inputTypeParameters) {
				QueryParameter objParameterDescDTO = ((QueryParameter) param);
				Element wsServiceParameterEle = createQueryServiceParameterElement(objParameterDescDTO);
				inputTypeEle.appendChild(wsServiceParameterEle);
			}
		}
		queryServiceEle.appendChild(inputTypeEle);

		/**
		 * ___ QueryService.returnType
		 * 
		 ** Output parameters ___
		 */
		Element returnTypeEle = dom
				.createElement(IAgentServiceConfigConstants.QueryService.returnType
						.getNodeName());
		// if (commentingAllowed) {
		// returnTypeEle
		// .appendChild(dom
		// .createComment("Here ws services return parameters will be added"));
		// }
		List<IAgentServiceParameter> returnTypeParameters = objQueryService
				.getReturnType();
		if (returnTypeParameters != null && returnTypeParameters.size() > 0) {
			for (IAgentServiceParameter param : inputTypeParameters) {
				QueryParameter objParameterDescDTO = ((QueryParameter) param);
				Element wsServiceParameterEle = createQueryServiceParameterElement(objParameterDescDTO);
				returnTypeEle.appendChild(wsServiceParameterEle);
			}
		}
		queryServiceEle.appendChild(returnTypeEle);

		return queryServiceEle;
	}

	private Element createQueryServiceParameterElement(
			QueryParameter objParameterDescDTO) {

		/** ___ QueryParameter.node ___ */
		Element paramDescDtoEle = dom
				.createElement(IAgentServiceConfigConstants.QueryParameter.node
						.getNodeName());

		/** ___ QueryParameter.paramId ___ */
		paramDescDtoEle.setAttribute(
				IAgentServiceConfigConstants.QueryParameter.paramId
						.getNodeName(), objParameterDescDTO.getParamId() + "");

		/** ___ QueryParameter.serviceId ___ */
		paramDescDtoEle
				.setAttribute(
						IAgentServiceConfigConstants.QueryParameter.serviceId
								.getNodeName(),
						objParameterDescDTO.getServiceId() + "");

		/** ___ QueryParameter.parameterName ___ */
		paramDescDtoEle.setAttribute(
				IAgentServiceConfigConstants.QueryParameter.parameterName
						.getNodeName(), objParameterDescDTO.getParameterName());

		/** ___ QueryParameter.parameterType ___ */
		paramDescDtoEle.setAttribute(
				IAgentServiceConfigConstants.QueryParameter.parameterType
						.getNodeName(), objParameterDescDTO.getParameterType());

		/** ___ QueryParameter.parameterDescr ___ */
		paramDescDtoEle
				.setAttribute(
						IAgentServiceConfigConstants.QueryParameter.parameterDescr
								.getNodeName(), objParameterDescDTO
								.getParameterDescr());

		/** ___ QueryParameter.parameterResultType ___ */
		paramDescDtoEle.setAttribute(
				IAgentServiceConfigConstants.QueryParameter.parameterResultType
						.getNodeName(), objParameterDescDTO
						.getParameterResultType());

		/** ___ QueryParameter.blankAllowed ___ */
		paramDescDtoEle.setAttribute(
				IAgentServiceConfigConstants.QueryParameter.blankAllowed
						.getNodeName(), objParameterDescDTO.getBlankAllowed()
						+ "");

		/** ___ QueryParameter.paramValue ___ */
		paramDescDtoEle.setAttribute(
				IAgentServiceConfigConstants.QueryParameter.paramValue
						.getNodeName(), objParameterDescDTO.getParamValue());

		return paramDescDtoEle;
	}

	/**
	 * 
	 * _______________________________
	 * 
	 * Append Data Source List
	 * 
	 * _______________________________
	 * 
	 * */
	private void appendDataSourceList() {

		if (dataSources == null) {
			return;
		}

		/** ___ IAgentServiceConfigConstants.DATA_SOURCE_LIST ___ */
		Element dataSourceListEle = dom
				.createElement(IAgentServiceConfigConstants.DATA_SOURCE_LIST
						.getNodeName());
		// dataSourceListEle.appendChild(dom
		// .createComment("Here datasources will be added"));
		Iterator<DataSource> it = dataSources.iterator();
		while (it.hasNext()) {
			DataSource b = it.next();
			Element dataSourceEle = createDataSourceElement(b);
			dataSourceListEle.appendChild(dataSourceEle);
		}

		rootEle.appendChild(dataSourceListEle);
	}

	private Element createDataSourceElement(DataSource objDataSource) {

		/** ___ DataSource.node ___ */
		Element dataSourceEle = dom
				.createElement(IAgentServiceConfigConstants.DataSource.node
						.getNodeName());

		/** ___ DataSource.dbID ___ */
		dataSourceEle.setAttribute(
				IAgentServiceConfigConstants.DataSource.dbID.getNodeName(),
				objDataSource.getDbID() + "");

		/** ___ DataSource.dbAlias ___ */
		dataSourceEle.setAttribute(
				IAgentServiceConfigConstants.DataSource.dbAlias.getNodeName(),
				objDataSource.getDbAlias());

		/** ___ DataSource.dbUsername ___ */
		dataSourceEle.setAttribute(
				IAgentServiceConfigConstants.DataSource.dbUsername
						.getNodeName(), objDataSource.getDbUsername() + "");

		/** ___ DataSource.dbPassword ___ */
		dataSourceEle.setAttribute(
				IAgentServiceConfigConstants.DataSource.dbPassword
						.getNodeName(), objDataSource.getDbPassword());

		/** ___ DataSource.dbDriverClassName ___ */
		dataSourceEle.setAttribute(
				IAgentServiceConfigConstants.DataSource.dbDriverClassName
						.getNodeName(), objDataSource.getDbDriverClassName()
						+ "");

		/** ___ DataSource.dbURL ___ */
		dataSourceEle.setAttribute(
				IAgentServiceConfigConstants.DataSource.dbURL.getNodeName(),
				objDataSource.getDbURL());

		return dataSourceEle;
	}

	/**
	 * 
	 * _______________________________
	 * 
	 * Append WSService List
	 * 
	 * _______________________________
	 * 
	 * 
	 * */
	private void appendWSServiceList() throws SQLException {

		if (!(wsdlDTOs != null && wsdlDTOs.size() > 0)) {
			return;
		}
		List<WSService> listWSServices = new ArrayList<WSService>();
		List<WSService> listTotalWSServices = new ServiceManagementRPC()
				.getWSServiceList();
		for (WSService objJservDto : listTotalWSServices) {
			for (WSdlDTO objJDto : wsdlDTOs) {
				if (objJservDto.getLinkedWSId() == objJDto.getWsId()) {
					listWSServices.add(objJservDto);
				}
			}
		}

		/** ___ IAgentServiceConfigConstants.WS_SERVICE_LIST ___ */
		Element wsServiceListEle = dom
				.createElement(IAgentServiceConfigConstants.WS_SERVICE_LIST
						.getNodeName());
		// wsServiceListEle.appendChild(dom
		// .createComment("Here ws services will be added"));
		if (listWSServices != null && listWSServices.size() > 0) {
			Iterator<WSService> it = listWSServices.iterator();
			while (it.hasNext()) {
				WSService b = it.next();
				Element wsServiceEle = createWSServiceElement(b);
				wsServiceListEle.appendChild(wsServiceEle);
			}
		}

		rootEle.appendChild(wsServiceListEle);
	}

	private Element createWSServiceElement(WSService objWSService) {

		/** ___ WSService.node ___ */
		Element wsServiceEle = dom
				.createElement(IAgentServiceConfigConstants.WSService.node
						.getNodeName());

		/** ___ WSService.serviceId ___ */
		wsServiceEle.setAttribute(
				IAgentServiceConfigConstants.WSService.serviceId.getNodeName(),
				objWSService.getServiceId() + "");

		/** ___ WSService.serviceName ___ */
		wsServiceEle.setAttribute(
				IAgentServiceConfigConstants.WSService.serviceName
						.getNodeName(), objWSService.getServiceName());

		/** ___ WSService.operationName ___ */
		wsServiceEle.setAttribute(
				IAgentServiceConfigConstants.WSService.operationName
						.getNodeName(), objWSService.getOperationName() + "");

		/** ___ WSService.saveLastRecord ___ */
		wsServiceEle.setAttribute(
				IAgentServiceConfigConstants.WSService.saveLastRecord
						.getNodeName(), objWSService.isSaveLastRecord() + "");

		/** ___ WSService.linkedWSId ___ */
		wsServiceEle
				.setAttribute(IAgentServiceConfigConstants.WSService.linkedWSId
						.getNodeName(), objWSService.getLinkedWSId() + "");

		/** ___ WSService.portName ___ */
		wsServiceEle.setAttribute(
				IAgentServiceConfigConstants.WSService.portName.getNodeName(),
				objWSService.getPortName());

		/** ___ WSService.loggingAllowed ___ */
		wsServiceEle.setAttribute(
				IAgentServiceConfigConstants.WSService.loggingAllowed
						.getNodeName(), objWSService.loggingAllowed() + "");

		/** ___ WSService.parameterSchema ___ */
		Element parameterSchemaEle = dom
				.createElement(IAgentServiceConfigConstants.WSService.parameterSchema
						.getNodeName());
		String parameterSchema = objWSService.getParameterSchema();
		parameterSchemaEle.appendChild(dom
				.createCDATASection((parameterSchema != null) ? parameterSchema
						: ""));
		wsServiceEle.appendChild(parameterSchemaEle);

		/**
		 * ___ WSService.inputType ___
		 * 
		 * Input parameters ___
		 **/
		Element inputTypeEle = dom
				.createElement(IAgentServiceConfigConstants.WSService.inputType
						.getNodeName());
		// if (commentingAllowed) {
		// inputTypeEle
		// .appendChild(dom
		// .createComment("Here ws services input parameters will be added"));
		// }
		List<IAgentServiceParameter> inputTypeParameters = objWSService
				.getInputType();
		if (inputTypeParameters != null && inputTypeParameters.size() > 0) {
			for (IAgentServiceParameter param : inputTypeParameters) {
				ParameterDescDTO objParameterDescDTO = ((ParameterDescDTO) param);
				Element wsServiceParameterEle = createWSServiceParameterElement(objParameterDescDTO);
				inputTypeEle.appendChild(wsServiceParameterEle);
			}
		}
		wsServiceEle.appendChild(inputTypeEle);

		/**
		 * ___ WSService.returnType ___
		 * 
		 * Output parameters ___
		 **/
		Element returnTypeEle = dom
				.createElement(IAgentServiceConfigConstants.WSService.returnType
						.getNodeName());
		// if (commentingAllowed) {
		// returnTypeEle
		// .appendChild(dom
		// .createComment("Here ws services return parameters will be added"));
		// }
		List<IAgentServiceParameter> returnTypeParameters = objWSService
				.getReturnType();
		if (returnTypeParameters != null && returnTypeParameters.size() > 0) {
			for (IAgentServiceParameter param : inputTypeParameters) {
				ParameterDescDTO objParameterDescDTO = ((ParameterDescDTO) param);
				Element wsServiceParameterEle = createWSServiceParameterElement(objParameterDescDTO);
				returnTypeEle.appendChild(wsServiceParameterEle);
			}
		}
		wsServiceEle.appendChild(returnTypeEle);

		return wsServiceEle;
	}

	private Element createWSServiceParameterElement(
			ParameterDescDTO objParameterDescDTO) {

		/** ___ ParameterDescDTO.node ___ */
		Element paramDescDtoEle = dom
				.createElement(IAgentServiceConfigConstants.ParameterDescDTO.node
						.getNodeName());

		/** ___ ParameterDescDTO.paramId ___ */
		paramDescDtoEle.setAttribute(
				IAgentServiceConfigConstants.ParameterDescDTO.paramId
						.getNodeName(), objParameterDescDTO.getParamId() + "");

		/** ___ ParameterDescDTO.serviceId ___ */
		paramDescDtoEle
				.setAttribute(
						IAgentServiceConfigConstants.ParameterDescDTO.serviceId
								.getNodeName(),
						objParameterDescDTO.getServiceId() + "");

		/** ___ ParameterDescDTO.parameterName ___ */
		paramDescDtoEle.setAttribute(
				IAgentServiceConfigConstants.ParameterDescDTO.parameterName
						.getNodeName(), objParameterDescDTO.getParameterName());

		/** ___ ParameterDescDTO.parameterType ___ */
		paramDescDtoEle.setAttribute(
				IAgentServiceConfigConstants.ParameterDescDTO.parameterType
						.getNodeName(), objParameterDescDTO.getParameterType());

		/** ___ ParameterDescDTO.parameterDescr ___ */
		paramDescDtoEle
				.setAttribute(
						IAgentServiceConfigConstants.ParameterDescDTO.parameterDescr
								.getNodeName(), objParameterDescDTO
								.getParameterDescr());
		return paramDescDtoEle;
	}

	/**
	 * 
	 * _______________________________
	 * 
	 * Append WSDL List
	 * 
	 * _______________________________
	 * 
	 * */
	private void appendWsdlList() {

		if (wsdlDTOs == null) {
			return;
		}

		/** ___ IAgentServiceConfigConstants.WSDL_LIST ___ */
		Element wsdlListEle = dom
				.createElement(IAgentServiceConfigConstants.WSDL_LIST
						.getNodeName());
		// wsdlListEle.appendChild(dom.createComment("Here wsdls will be added"));
		Iterator<WSdlDTO> it = wsdlDTOs.iterator();
		while (it.hasNext()) {
			WSdlDTO b = it.next();
			Element bookEle = createWSdlDTOElement(b);
			wsdlListEle.appendChild(bookEle);
		}

		rootEle.appendChild(wsdlListEle);
	}

	private Element createWSdlDTOElement(WSdlDTO objJarDTO) {

		/** ___ WSdlDTO.node ___ */
		Element wsdlDTOEle = dom
				.createElement(IAgentServiceConfigConstants.WSdlDTO.node
						.getNodeName());

		/** ___ WSdlDTO.wsId ___ */
		wsdlDTOEle.setAttribute(
				IAgentServiceConfigConstants.WSdlDTO.wsId.getNodeName(),
				objJarDTO.getWsId() + "");

		/** ___ WSdlDTO.wsName ___ */
		wsdlDTOEle.setAttribute(
				IAgentServiceConfigConstants.WSdlDTO.wsName.getNodeName(),
				objJarDTO.getWsName());

		/** ___ WSdlDTO.wsURL ___ */
		wsdlDTOEle.setAttribute(
				IAgentServiceConfigConstants.WSdlDTO.wsURL.getNodeName(),
				objJarDTO.getWsURL() + "");

		/** ___ WSdlDTO.nameSpace ___ */
		wsdlDTOEle.setAttribute(
				IAgentServiceConfigConstants.WSdlDTO.nameSpace.getNodeName(),
				objJarDTO.getNameSpace());

		/** ___ WSdlDTO.usePrefix ___ */
		wsdlDTOEle.setAttribute(
				IAgentServiceConfigConstants.WSdlDTO.usePrefix.getNodeName(),
				objJarDTO.getUsePrefix() + "");

		/** ___ WSdlDTO.soapBindingInterface ___ */
		wsdlDTOEle.setAttribute(
				IAgentServiceConfigConstants.WSdlDTO.soapBindingInterface
						.getNodeName(), objJarDTO.getSoapBindingInterface());

		/** ___ WSdlDTO.serviceClass ___ */
		wsdlDTOEle
				.setAttribute(IAgentServiceConfigConstants.WSdlDTO.serviceClass
						.getNodeName(), objJarDTO.getServiceClass() + "");

		/** ___ WSdlDTO.wsPackageName ___ */
		wsdlDTOEle.setAttribute(
				IAgentServiceConfigConstants.WSdlDTO.wsPackageName
						.getNodeName(), objJarDTO.getWsPackageName());

		return wsdlDTOEle;
	}

	/**
	 * 
	 * _______________________________
	 * 
	 * Append JavaService List
	 * 
	 * _______________________________
	 * 
	 * */

	private void appendJavaServiceList() throws SQLException {

		if (!(jarDTOs != null && jarDTOs.size() > 0)) {
			return;
		}
		List<JavaService> listJavaServices = new ArrayList<JavaService>();
		List<JavaService> listTotalJavaServices = new ServiceManagementRPC()
				.getServiceList();
		for (JavaService objJservDto : listTotalJavaServices) {
			for (JarDTO objJDto : jarDTOs) {
				if (objJservDto.getLinkedJarId() == objJDto.getJar_id()) {
					listJavaServices.add(objJservDto);
				}
			}
		}
		log.debug(listJavaServices);

		/** ___ IAgentServiceConfigConstants.JAVA_SERVICE_LIST ___ */
		Element javaServiceListEle = dom
				.createElement(IAgentServiceConfigConstants.JAVA_SERVICE_LIST
						.getNodeName());
		// javaServiceListEle.appendChild(dom
		// .createComment("Here java services will be added"));
		if (listJavaServices != null && listJavaServices.size() > 0) {
			Iterator<JavaService> it = listJavaServices.iterator();
			while (it.hasNext()) {
				JavaService b = it.next();
				Element bookEle = createJavaServiceElement(b);
				javaServiceListEle.appendChild(bookEle);
			}
		}

		rootEle.appendChild(javaServiceListEle);
	}

	private Element createJavaServiceElement(JavaService objJavaService) {

		/** ___ JavaService.node ___ */
		Element javaServiceEle = dom
				.createElement(IAgentServiceConfigConstants.JavaService.node
						.getNodeName());

		/** ___ JavaService.serviceId ___ */
		javaServiceEle.setAttribute(
				IAgentServiceConfigConstants.JavaService.serviceId
						.getNodeName(), objJavaService.getServiceId() + "");

		/** ___ JavaService.serviceName ___ */
		javaServiceEle.setAttribute(
				IAgentServiceConfigConstants.JavaService.serviceName
						.getNodeName(), objJavaService.getServiceName());

		/** ___ JavaService.className ___ */
		javaServiceEle.setAttribute(
				IAgentServiceConfigConstants.JavaService.className
						.getNodeName(), objJavaService.getClassName() + "");

		/** ___ JavaService.methodName ___ */
		javaServiceEle.setAttribute(
				IAgentServiceConfigConstants.JavaService.methodName
						.getNodeName(), objJavaService.getMethodName());

		/** ___ JavaService.saveLastRecord ___ */
		javaServiceEle
				.setAttribute(
						IAgentServiceConfigConstants.JavaService.saveLastRecord
								.getNodeName(),
						objJavaService.getSaveLastRecord() + "");

		/** ___ JavaService.linkedJarId ___ */
		javaServiceEle.setAttribute(
				IAgentServiceConfigConstants.JavaService.linkedJarId
						.getNodeName(), objJavaService.getLinkedJarId() + "");

		/** ___ JavaService.loggingAllowed ___ */
		javaServiceEle.setAttribute(
				IAgentServiceConfigConstants.JavaService.loggingAllowed
						.getNodeName(), objJavaService.loggingAllowed() + "");

		/**
		 * ___ JavaService.inputType ___
		 * 
		 * Input parameters ___
		 */
		Element inputTypeEle = dom
				.createElement(IAgentServiceConfigConstants.JavaService.inputType
						.getNodeName());
		// if (commentingAllowed) {
		// inputTypeEle
		// .appendChild(dom
		// .createComment("Here java services input parameters will be added"));
		// }
		List<IAgentServiceParameter> inputTypeParameters = objJavaService
				.getInputType();
		if (inputTypeParameters != null && inputTypeParameters.size() > 0) {
			for (IAgentServiceParameter param : inputTypeParameters) {
				ParameterDescDTO objParameterDescDTO = ((ParameterDescDTO) param);
				Element javaServiceParameterEle = createJavaServiceParameterElement(objParameterDescDTO);
				inputTypeEle.appendChild(javaServiceParameterEle);
			}
		}
		javaServiceEle.appendChild(inputTypeEle);

		/**
		 * ___ JavaService.returnType ___
		 * 
		 * Output parameters ___
		 */
		Element returnTypeEle = dom
				.createElement(IAgentServiceConfigConstants.JavaService.returnType
						.getNodeName());
		// if (commentingAllowed) {
		// returnTypeEle
		// .appendChild(dom
		// .createComment("Here java services return parameters will be added"));
		// }
		List<IAgentServiceParameter> returnTypeParameters = objJavaService
				.getReturnType();
		if (returnTypeParameters != null && returnTypeParameters.size() > 0) {
			for (IAgentServiceParameter param : inputTypeParameters) {
				ParameterDescDTO objParameterDescDTO = ((ParameterDescDTO) param);
				Element javaServiceParameterEle = createJavaServiceParameterElement(objParameterDescDTO);
				returnTypeEle.appendChild(javaServiceParameterEle);
			}
		}
		javaServiceEle.appendChild(returnTypeEle);

		return javaServiceEle;
	}

	private Element createJavaServiceParameterElement(
			ParameterDescDTO objParameterDescDTO) {

		/** ___ ParameterDescDTO.node ___ */
		Element paramDescDtoEle = dom
				.createElement(IAgentServiceConfigConstants.ParameterDescDTO.node
						.getNodeName());

		/** ___ ParameterDescDTO.paramId ___ */
		paramDescDtoEle.setAttribute(
				IAgentServiceConfigConstants.ParameterDescDTO.paramId
						.getNodeName(), objParameterDescDTO.getParamId() + "");

		/** ___ ParameterDescDTO.serviceId ___ */
		paramDescDtoEle
				.setAttribute(
						IAgentServiceConfigConstants.ParameterDescDTO.serviceId
								.getNodeName(),
						objParameterDescDTO.getServiceId() + "");

		/** ___ ParameterDescDTO.parameterName ___ */
		paramDescDtoEle.setAttribute(
				IAgentServiceConfigConstants.ParameterDescDTO.parameterName
						.getNodeName(), objParameterDescDTO.getParameterName());

		/** ___ ParameterDescDTO.parameterType ___ */
		paramDescDtoEle.setAttribute(
				IAgentServiceConfigConstants.ParameterDescDTO.parameterType
						.getNodeName(), objParameterDescDTO.getParameterType());

		/** ___ ParameterDescDTO.parameterDescr ___ */
		paramDescDtoEle
				.setAttribute(
						IAgentServiceConfigConstants.ParameterDescDTO.parameterDescr
								.getNodeName(), objParameterDescDTO
								.getParameterDescr());

		return paramDescDtoEle;
	}

	/**
	 * 
	 * _______________________________
	 * 
	 * Append Jar List
	 * 
	 * _______________________________
	 * 
	 * */

	private void appendJarsList() throws SQLException {

		if (jarDTOs == null) {
			return;
		}

		/** ___ IAgentServiceConfigConstants.JARLIST ___ */
		Element jarlistEle = dom
				.createElement(IAgentServiceConfigConstants.JARLIST
						.getNodeName());
		// jarlistEle.appendChild(dom.createComment("Here jars will be added"));
		Iterator<JarDTO> it = jarDTOs.iterator();
		while (it.hasNext()) {
			JarDTO b = it.next();
			Element bookEle = createJarDTOElement(b);
			jarlistEle.appendChild(bookEle);
		}
		rootEle.appendChild(jarlistEle);
	}

	private Element createJarDTOElement(JarDTO objJarDTO) {

		/** ___ JarDTO.node ___ */
		Element jarDtoEle = dom
				.createElement(IAgentServiceConfigConstants.JarDTO.node
						.getNodeName());

		/** ___ JarDTO.jarId ___ */
		jarDtoEle.setAttribute(
				IAgentServiceConfigConstants.JarDTO.jarId.getNodeName(),
				objJarDTO.getJar_id() + "");

		/** ___ JarDTO.jarName ___ */
		jarDtoEle.setAttribute(
				IAgentServiceConfigConstants.JarDTO.jarName.getNodeName(),
				objJarDTO.getJar_name());

		return jarDtoEle;
	}

	/**
	 * Using JAXP in implementation independent manner create a document object
	 * using which we create a xml tree in memory
	 * 
	 * @throws ParserConfigurationException
	 */
	private void createDocument() throws ParserConfigurationException {

		/** __ get an instance of factory __ */
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			/** __ get an instance of builder __ */
			DocumentBuilder db = dbf.newDocumentBuilder();

			/** __ create an instance of DOM __ */
			dom = db.newDocument();

		} catch (ParserConfigurationException pce) {
			log.debug("Error while trying to instantiate DocumentBuilder "
					+ pce);
			throw pce;
		}
	}

}
