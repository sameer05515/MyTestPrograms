package com.ist.iagent.admin.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.wsdl.WSDLException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.ist.iagent.admin.datasource.DBModel;
import com.ist.iagent.admin.datasource.DBQueryServiceRPC;
import com.ist.iagent.admin.datasource.ImportServiceResponse;
import com.ist.iagent.admin.datasource.ServiceImporter;
import com.ist.iagent.admin.db.dao.ConfigServiceDAO;
import com.ist.iagent.admin.db.dao.DataSourceDAO;
import com.ist.iagent.admin.db.dao.IAgentServiceChannelDAO;
import com.ist.iagent.admin.db.dao.JarDAO;
import com.ist.iagent.admin.db.dao.QueryServiceDAO;
import com.ist.iagent.admin.db.dao.ServiceDAO;
import com.ist.iagent.admin.db.dao.ServiceDetailDAO;
import com.ist.iagent.admin.db.dao.WSServiceDAO;
import com.ist.iagent.admin.db.dao.WSdlDAO;
import com.ist.iagent.admin.db.pojo.ConfigService;
import com.ist.iagent.admin.db.pojo.ConfigServiceType;
import com.ist.iagent.admin.db.pojo.DataSource;
import com.ist.iagent.admin.db.pojo.IAgentService;
import com.ist.iagent.admin.db.pojo.IAgentServiceChannel;
import com.ist.iagent.admin.db.pojo.IAgentServiceChannelType;
import com.ist.iagent.admin.db.pojo.IAgentServiceParameter;
import com.ist.iagent.admin.db.pojo.JarDTO;
import com.ist.iagent.admin.db.pojo.JavaService;
import com.ist.iagent.admin.db.pojo.ParameterDescDTO;
import com.ist.iagent.admin.db.pojo.QueryParameter;
import com.ist.iagent.admin.db.pojo.QueryService;
import com.ist.iagent.admin.db.pojo.ServiceDetail;
import com.ist.iagent.admin.db.pojo.WSOperationDTO;
import com.ist.iagent.admin.db.pojo.WSService;
import com.ist.iagent.admin.db.pojo.WSdlDTO;
import com.ist.iagent.admin.db.response.ServiceSaveResponse;
import com.ist.iagent.admin.db.wrapper.IAgentServiceConfigXMLCreator;
import com.ist.iagent.admin.util.PropertyUtil;
import com.ist.iagent.admin.ws.WSStubBuilder;
import com.ist.iagent.admin.ws.WebServiceInvoker;
import com.ist.iagent.admin.xml.response.XMLSaveResponse;
import com.isuite.iagent.commons.servelet.ISuiteContextListener;

public class ServiceManagementRPC {

	private static final Logger log = Logger
			.getLogger(ServiceManagementRPC.class);

	public static final String JAVA_SERVICE = PropertyUtil.getInstance()
			.getValueForKey("java_service_type");
	public static final String WEB_SERVICE = PropertyUtil.getInstance()
			.getValueForKey("web_service_type");
	public static final String QUERY_SERVICE = PropertyUtil.getInstance()
			.getValueForKey("query_service_type");

	public static final String JAVA_SERV_TABLE = PropertyUtil.getInstance()
			.getValueForKey("java_service_table");
	public static final String WEB_SERV_TABLE = PropertyUtil.getInstance()
			.getValueForKey("web_service_table");
	public static final String QUERY_SERV_TABLE = PropertyUtil.getInstance()
			.getValueForKey("query_service_table");

	private static final int TYPE_LOGGING_CONFIG_SERVICE_ID = 1;
	private static final int TYPE_AUTHENTICATION_CONFIG_SERVICE_ID = 2;
	private static final int TYPE_APPLICATION_CONFIG_SERVICE_ID = 3;

	private static final String DEFAULT_WS_PACKAGE_INITIAL = "com.isuite.ws.service.";

	/**
	 * ##############################
	 * 
	 * INITIAL SERVICE
	 * 
	 * ##############################
	 */

	public String getUploadServletURL() {
		String str = "";
		str = PropertyUtil.getInstance().getValueForKey("uploadServletURL");
		return str;
	}

	/**
	 * ##############################
	 * 
	 * JAR RELATED RPC
	 * 
	 * ##############################
	 */

	/**
	 * Saves name of given jar file
	 * 
	 * @param jarName
	 *            name of jar file
	 * @return String response of saving
	 * @throws SQLException
	 */
	public String saveJarName(String jarName) throws SQLException {
		return new JarDAO().saveJarName(jarName);
	}

	/**
	 * Returns names of all jar files saved in database
	 * 
	 * @return String response of saving
	 * @throws SQLException
	 */
	public List<JarDTO> getJarList() throws SQLException {
		return new JarDAO().getJarList();
	}

	/**
	 * deletes name of given jar file and all related services
	 * 
	 * @param int jarId id of jar file
	 * @return String response of deletion
	 * @throws SQLException
	 */
	public String deleteJarName(int jarId) throws SQLException {
		JarDAO objJarDAO = new JarDAO();
		String str = "";
		str = deleteServiceWithJarID(jarId);
		str = str + "\n" + objJarDAO.deleteJarName(jarId);
		return str;
	}

	/**
	 * deletes name of given jar file and all related services
	 * 
	 * @param int jarId id of jar file
	 * @return String response of deletion
	 * @throws SQLException
	 */
	public String fetchJarName(int jarId) throws SQLException {

		String str = new JarDAO().fetchJarName(jarId);
		if (str != null && !str.trim().equalsIgnoreCase("")) {
			return str;
		}
		return null;
	}

	/**
	 * ##############################
	 * 
	 * JAVA SERVICE RELATED RPC
	 * 
	 * ##############################
	 */

	/**
	 * Saves a java service
	 * 
	 * @param objServiceDTO
	 *            object of javaService
	 * @return ServiceSaveResponse response of saving
	 * @throws SQLException
	 */
	public ServiceSaveResponse saveService(JavaService objServiceDTO)
			throws SQLException {
		ServiceSaveResponse objResponse = new ServiceSaveResponse();
		if (serviceNameExists(objServiceDTO.getServiceName())) {
			objResponse.setErrorCode(1);
			objResponse.setErrorMessage("service name exists : - "
					+ objServiceDTO.getServiceName());
			objResponse.setMessage("Enter Different Name");
			return objResponse;
		} else {
			String message = "";
			ServiceDetail objServiceDetail = saveServiceDetail(objServiceDTO);
			objServiceDTO.setServiceId(objServiceDetail.getServiceId());
			ServiceDAO objServiceDAO = new ServiceDAO();
			message += objServiceDAO.saveService(objServiceDTO);
			log.debug(objServiceDTO.getServiceId());
			message += objServiceDAO.saveAllParameters(objServiceDTO);
			/** ____ Added code to add config service */
			ConfigService objConfigService = new ConfigService();
			objConfigService.setLinkedServiceId(objServiceDTO.getServiceId());
			objConfigService
					.setConfigServiceTypeId(TYPE_APPLICATION_CONFIG_SERVICE_ID);
			objConfigService.setServiceChannel(null);
			message += saveConfigService(objConfigService);
			//
			objResponse.setErrorCode(0);
			objResponse.setErrorMessage("service saved successfully : - "
					+ objServiceDTO.getServiceName());
			objResponse.setMessage(message);
			return objResponse;
		}
	}

	private ServiceDetail saveServiceDetail(IAgentService objAgentService)
			throws SQLException {
		ServiceDetail retObj = null;
		if (objAgentService instanceof JavaService) {
			JavaService js = (JavaService) objAgentService;
			ServiceDetail sd = new ServiceDetail();
			sd.setServiceName(js.getServiceName());
			sd.setServiceType(JAVA_SERVICE);
			sd.setServiceTableName(JAVA_SERV_TABLE);
			sd.setServiceStatus(true);
			sd = new ServiceDetailDAO().saveServiceDetail(sd);
			retObj = sd;
		} else if (objAgentService instanceof WSService) {
			WSService js = (WSService) objAgentService;
			ServiceDetail sd = new ServiceDetail();
			sd.setServiceName(js.getServiceName());
			sd.setServiceType(WEB_SERVICE);
			sd.setServiceTableName(WEB_SERV_TABLE);
			sd.setServiceStatus(true);
			sd = new ServiceDetailDAO().saveServiceDetail(sd);
			retObj = sd;
		} else if (objAgentService instanceof QueryService) {
			QueryService js = (QueryService) objAgentService;
			ServiceDetail sd = new ServiceDetail();
			sd.setServiceName(js.getServiceName());
			sd.setServiceType(QUERY_SERVICE);
			sd.setServiceTableName(QUERY_SERV_TABLE);
			sd.setServiceStatus(true);
			sd = new ServiceDetailDAO().saveServiceDetail(sd);
			retObj = sd;
		}
		return retObj;
	}

	/**
	 * Checks if given Service name exists in database
	 * 
	 * @param serviceName
	 *            name of service
	 * @return boolean true for exists
	 * @throws SQLException
	 */
	public boolean serviceNameExists(String serviceName) throws SQLException {
		boolean serNameExists = false;
		List<ServiceDetail> serDetailList = getServiceDetailList();
		// List<JavaService> serList=getServiceList();
		// List<WSService> wsSerList=getWSServiceList();
		// List<QueryService> querySerList=getQueryServiceList();
		// for(JavaService serDTO : serList){
		// if(serDTO.getServiceName().equalsIgnoreCase(serviceName)){
		// serNameExists=true;
		// break;
		// }
		// }
		// for(WSService serDTO : wsSerList){
		// if(serDTO.getServiceName().equalsIgnoreCase(serviceName)){
		// serNameExists=true;
		// break;
		// }
		// }
		// for(QueryService serDTO : querySerList){
		// if(serDTO.getServiceName().equalsIgnoreCase(serviceName)){
		// serNameExists=true;
		// break;
		// }
		// }
		for (ServiceDetail serDetail : serDetailList) {
			if (serDetail.getServiceName().equalsIgnoreCase(serviceName)) {
				serNameExists = true;
				break;
			}
		}
		return serNameExists;
	}

	/**
	 * gives all ServiceDetail objects as list from "service_detail" table
	 * 
	 * @return List of all ServiceDetail
	 * @throws SQLException
	 */
	public List<ServiceDetail> getServiceDetailList() throws SQLException {
		ServiceDetailDAO serDetailDAO = new ServiceDetailDAO();
		List<ServiceDetail> serDetailList = serDetailDAO.getServiceDetailList();
		return serDetailList;
	}

	/**
	 * gives all javaService objects as list
	 * 
	 * @return List of all javaService
	 * @throws SQLException
	 */
	public List<JavaService> getServiceList() throws SQLException {
		ServiceDetailDAO objServiceDetailDAO = new ServiceDetailDAO();
		List<ServiceDetail> serviceDetailList = objServiceDetailDAO
				.getServiceDetailsForType(JAVA_SERVICE);

		ServiceDAO objServiceDAO = new ServiceDAO();
		// List<JavaService> list= objServiceDAO.getServiceList();
		List<JavaService> list = objServiceDAO
				.getServiceList(serviceDetailList);
		List<JavaService> finallist = new ArrayList<JavaService>();
		for (JavaService w : list) {
			List<ParameterDescDTO> inpType = objServiceDAO
					.getServiceParameters(w.getServiceId());
			List<IAgentServiceParameter> listISP = new ArrayList<IAgentServiceParameter>();
			for (ParameterDescDTO qp : inpType) {
				listISP.add(qp);
			}
			w.setInputType(listISP);
			finallist.add(w);
		}
		return finallist;
	}

	/**
	 * deletes javaService and its all parameters of given serviceId from
	 * database
	 * 
	 * @param serviceId
	 *            serviceId of given javaService
	 * @return String response of delete
	 * 
	 * @throws SQLException
	 */
	public String deleteService(int serviceId) throws SQLException {
		ServiceDAO objServiceDAO = new ServiceDAO();
		String msg = objServiceDAO.deleteAllParameters(serviceId);
		msg += objServiceDAO.deleteService(serviceId);
		msg += deleteServiceDetail(serviceId);
		return msg;
	}

	private String deleteServiceDetail(int serviceId) throws SQLException {
		ServiceDetailDAO objServiceDetailDAO = new ServiceDetailDAO();
		String msg = objServiceDetailDAO.deleteServiceDetail(serviceId);
		return msg;
	}

	/**
	 * Updates a given javaService and all its parameters also checks if new
	 * service name exists in database
	 * 
	 * @param objServiceDTO
	 *            object of java service
	 * @param sameServiceName
	 *            if service name is not changed
	 * @return ServiceSaveResponse response of update
	 * @throws SQLException
	 */
	public ServiceSaveResponse updateService(JavaService objServiceDTO,
			boolean sameServiceName) throws SQLException {
		ServiceSaveResponse objResponse = new ServiceSaveResponse();
		if (serviceNameExists(objServiceDTO.getServiceName())
				&& (sameServiceName == false)) {
			objResponse.setErrorCode(1);
			objResponse.setErrorMessage("service name exists : - "
					+ objServiceDTO.getServiceName());
			objResponse.setMessage("Enter Different Name");
			return objResponse;
		} else {
			String message = "";
			ServiceDetail objServiceDetail = updateServiceDetail(objServiceDTO);
			objServiceDTO.setServiceId(objServiceDetail.getServiceId());
			ServiceDAO objServiceDAO = new ServiceDAO();
			message += objServiceDAO.updateService(objServiceDTO);
			log.debug(objServiceDTO.getServiceId());
			message += objServiceDAO.updateAllParameters(objServiceDTO);
			objResponse.setErrorCode(0);
			objResponse.setErrorMessage("service updated successfully : - "
					+ objServiceDTO.getServiceName());
			objResponse.setMessage(message);
			return objResponse;
		}
	}

	/**
	 * Updates a given IAgentService
	 * 
	 * @param objAgentService
	 *            object of IAgentService
	 * 
	 * @return ServiceDetail response of updated serviceDetail
	 * @throws SQLException
	 */

	private ServiceDetail updateServiceDetail(IAgentService objAgentService)
			throws SQLException {
		ServiceDetail retObj = null;
		if (objAgentService instanceof JavaService) {
			JavaService js = (JavaService) objAgentService;

			ServiceDetail sd = new ServiceDetail();

			sd.setServiceId(js.getServiceId());
			sd.setServiceName(js.getServiceName());
			sd.setServiceType(JAVA_SERVICE);
			sd.setServiceTableName(JAVA_SERV_TABLE);
			sd.setServiceStatus(true);

			sd = new ServiceDetailDAO().updateServiceDetail(sd);
			retObj = sd;
		} else if (objAgentService instanceof WSService) {
			WSService ws = (WSService) objAgentService;

			ServiceDetail sd = new ServiceDetail();

			sd.setServiceId(ws.getServiceId());
			sd.setServiceName(ws.getServiceName());
			sd.setServiceType(WEB_SERVICE);
			sd.setServiceTableName(WEB_SERV_TABLE);
			sd.setServiceStatus(true);

			sd = new ServiceDetailDAO().updateServiceDetail(sd);
			retObj = sd;
		} else if (objAgentService instanceof QueryService) {
			QueryService qs = (QueryService) objAgentService;

			ServiceDetail sd = new ServiceDetail();

			sd.setServiceId(qs.getServiceId());
			sd.setServiceName(qs.getServiceName());
			sd.setServiceType(QUERY_SERVICE);
			sd.setServiceTableName(QUERY_SERV_TABLE);
			sd.setServiceStatus(true);

			sd = new ServiceDetailDAO().updateServiceDetail(sd);
			retObj = sd;
		}
		return retObj;
	}

	/**
	 * deletes all services related to a given jarId of given jar file
	 * 
	 * @param jarID
	 *            id of jar file
	 * @return String response of delete
	 * @throws SQLException
	 */
	public String deleteServiceWithJarID(int jarID) throws SQLException {
		List<Integer> serviceIDs = new ServiceDAO()
				.fetchAllServiceIDsForJarID(jarID);
		int count = 0;
		for (int serID : serviceIDs) {
			deleteService(serID);
			count++;
		}
		return ("" + count + " Service(s) deleted from database");
	}

	/**
	 * ##############################
	 * 
	 * WS SERVICE RELATED RPC
	 * 
	 * ##############################
	 */

	/**
	 * Saves a WSDL object in database
	 * 
	 * @param objWSdlDTO
	 *            object of given WSdlDTO
	 * @return String response of saving
	 * @throws SQLException
	 */
	public List<WSOperationDTO> getWSOperations(WSdlDTO objWSdlDTO) {

		List<WSOperationDTO> listWSOperationDTOs = new ArrayList<WSOperationDTO>();
		try {
			listWSOperationDTOs = new WebServiceInvoker()
					.getWSOperations(objWSdlDTO);
		} catch (Throwable e) {
			log.error(
					"Error while getting operations for web service : name == "
							+ objWSdlDTO.getWsName() + " url == "
							+ objWSdlDTO.getWsURL(), e);
		}
		return listWSOperationDTOs;
	}

	public String getStubJarOfWSdlDTOForID(int wsId) {

		WSdlDTO objWsdlDto = new WSdlDAO().fetchWSdlDTOForWSDLid(wsId);
		if (objWsdlDto != null) {
			String wsdlName = objWsdlDto.getWsName();
			if (wsdlName != null && !wsdlName.trim().equalsIgnoreCase("")) {
				return wsdlName + ".jar";
			}
		}
		return null;
	}

	/**
	 * Saves a WSDL object in database
	 * 
	 * @param objWSdlDTO
	 *            object of given WSdlDTO
	 * @return String response of saving
	 * @throws SQLException
	 */
	public ServiceSaveResponse saveWSDL(WSdlDTO objWSdlDTO) throws SQLException {

		ServiceSaveResponse objResponse = new ServiceSaveResponse();
		String message = "";
		try {
			WSStubBuilder objWSStubBuilder = new WSStubBuilder();
			log.debug("\n\n>>> setServiceClassAndBindingInterface");
			objWSdlDTO = objWSStubBuilder
					.setServiceClassAndBindingInterface(objWSdlDTO);

			log.debug("objWSdlDTO.getServiceClass() == "
					+ objWSdlDTO.getServiceClass());
			log.debug("objWSdlDTO.getSoapBindingInterface() == "
					+ objWSdlDTO.getSoapBindingInterface());
			objWSdlDTO
					.setWsPackageName((DEFAULT_WS_PACKAGE_INITIAL + objWSdlDTO
							.getWsName()).toLowerCase());

			objWSStubBuilder.createStub("src", objWSdlDTO.getWsPackageName(),
					"bin", objWSdlDTO.getWsName() + ".jar",
					objWSdlDTO.getWsURL());

			if (objWSdlDTO.getWsId() == 0) {
				message = new WSdlDAO().saveWSDL(objWSdlDTO);
			} else {
				message = new WSdlDAO().updateWSDL(objWSdlDTO);
			}
			objResponse.setErrorCode(0);
			objResponse
					.setErrorMessage("Successfully created stub and "
							+ ((objWSdlDTO.getWsId() == 0) ? "saved"
									: "updated")
							+ " web service :"
							+ " \n Please re-start the server to changes may take effect"
							+ message);
			objResponse
					.setMessage("Successfully created stub and "
							+ ((objWSdlDTO.getWsId() == 0) ? "saved"
									: "updated")
							+ " web service :"
							+ " \n Please re-start the server to changes may take effect");
			return objResponse;
		} catch (WSDLException e) {
			objResponse.setErrorCode(1);
			objResponse.setErrorMessage("Unable to retreive Details for WSDL "
					+ ((objWSdlDTO.getWsURL() != null && !objWSdlDTO.getWsURL()
							.trim().equalsIgnoreCase("")) ? objWSdlDTO
							.getWsURL() : "empty string"));
			objResponse.setMessage("Unable to retreive Details for WSDL : \n"
					+ ((objWSdlDTO.getWsURL() != null && !objWSdlDTO.getWsURL()
							.trim().equalsIgnoreCase("")) ? objWSdlDTO
							.getWsURL() : "empty string"));
			log.error(
					"Unable to retreive Details for WSDL "
							+ ((objWSdlDTO.getWsURL() != null && !objWSdlDTO
									.getWsURL().trim().equalsIgnoreCase("")) ? objWSdlDTO
									.getWsURL() : "empty string"), e);
			return objResponse;
		} catch (Throwable e) {
			objResponse.setErrorCode(1);
			objResponse.setErrorMessage("Error occured in process : " + e);
			objResponse.setMessage("Error occured in process : " + e);
			log.error("Error occured in process", e);
			return objResponse;
		}
	}

	/**
	 * gives all WSdlDTO objects saved in database
	 * 
	 * @return List of all WSdlDTO objects
	 * @throws SQLException
	 */
	public List<WSdlDTO> getWSDLList() throws SQLException {
		return new WSdlDAO().getWSDLList();
	}

	/**
	 * Deletes a given WSdlDTO from database also deletes all related services
	 * 
	 * @param wsId
	 *            id of given WSdlDTO object
	 * @return String response of delete
	 * @throws SQLException
	 */
	public String deleteWSDL(int wsId) throws SQLException {

		WSdlDAO objWSdlDAO = new WSdlDAO();
		String msg = deleteWSServiceForWSid(wsId);
		msg += objWSdlDAO.deleteWSDL(wsId);
		return msg;
	}

	/**
	 * deletes all WSServices related to given wsId form database
	 * 
	 * @param wsId
	 *            id of given WSdlDTO
	 * @return String response of delete
	 * @throws SQLException
	 */
	public String deleteWSServiceForWSid(int wsId) throws SQLException {
		WSServiceDAO objServiceDAO = new WSServiceDAO();
		List<Integer> serviceIDs = objServiceDAO
				.fetchAllServiceIDsForWSid(wsId);
		int count = 0;
		for (int serID : serviceIDs) {
			deleteWSService(serID);
			count++;
		}
		return "" + count + " WS Service(s) deleted from database";
	}

	/**
	 * Saves a WSService
	 * 
	 * @param objServiceDTO
	 *            object of WSService
	 * @return ServiceSaveResponse response of saving
	 * @throws SQLException
	 */
	public ServiceSaveResponse saveWSService(WSService objServiceDTO)
			throws SQLException {
		ServiceSaveResponse objResponse = new ServiceSaveResponse();
		if (serviceNameExists(objServiceDTO.getServiceName())) {
			objResponse.setErrorCode(1);
			objResponse.setErrorMessage("service name exists : - "
					+ objServiceDTO.getServiceName());
			objResponse.setMessage("Enter Different Name");
			return objResponse;
		} else {
			String message = "";
			ServiceDetail objServiceDetail = saveServiceDetail(objServiceDTO);
			objServiceDTO.setServiceId(objServiceDetail.getServiceId());
			WSServiceDAO objServiceDAO = new WSServiceDAO();
			message += objServiceDAO.saveWSService(objServiceDTO);
			log.debug(objServiceDTO.getServiceId());
			message += objServiceDAO.saveAllWSParameters(objServiceDTO);
			/** ____ Added code to add config service */
			ConfigService objConfigService = new ConfigService();
			objConfigService.setLinkedServiceId(objServiceDTO.getServiceId());
			objConfigService
					.setConfigServiceTypeId(TYPE_APPLICATION_CONFIG_SERVICE_ID);
			objConfigService.setServiceChannel(null);
			message += saveConfigService(objConfigService);
			//
			objResponse.setErrorCode(0);
			objResponse.setErrorMessage("service saved successfully : - "
					+ objServiceDTO.getServiceName());
			objResponse.setMessage(message);
			return objResponse;
		}
	}

	/**
	 * gives all WSService objects saved in database
	 * 
	 * @return List of all WSService objects
	 * @throws SQLException
	 */
	public List<WSService> getWSServiceList() throws SQLException {
		ServiceDetailDAO objServiceDetailDAO = new ServiceDetailDAO();
		List<ServiceDetail> serviceDetailList = objServiceDetailDAO
				.getServiceDetailsForType(WEB_SERVICE);
		WSServiceDAO objWSServiceDAO = new WSServiceDAO();
		List<WSService> list = objWSServiceDAO
				.getWSServiceList(serviceDetailList);
		List<WSService> finallist = new ArrayList<WSService>();
		for (WSService w : list) {
			List<ParameterDescDTO> inpType = objWSServiceDAO
					.getWSServiceParameters(w.getServiceId());
			List<IAgentServiceParameter> listISP = new ArrayList<IAgentServiceParameter>();
			for (ParameterDescDTO qp : inpType) {
				listISP.add(qp);
			}
			w.setInputType(listISP);
			finallist.add(w);
		}
		return finallist;
	}

	/**
	 * Updates a given WSService and all its parameters also checks if new
	 * service name exists in database
	 * 
	 * @param objWSServiceDTO
	 *            object of WSService
	 * @param sameServiceName
	 *            if service name is not changed
	 * @return ServiceSaveResponse response of update
	 * @throws SQLException
	 */
	public ServiceSaveResponse updateWSService(WSService objWSServiceDTO,
			boolean sameServiceName) throws SQLException {
		ServiceSaveResponse objResponse = new ServiceSaveResponse();
		if (serviceNameExists(objWSServiceDTO.getServiceName())
				&& (sameServiceName == false)) {
			objResponse.setErrorCode(1);
			objResponse.setErrorMessage("service name exists : - "
					+ objWSServiceDTO.getServiceName());
			objResponse.setMessage("Enter Different Name");
			return objResponse;
		} else {
			String message = "";
			ServiceDetail objServiceDetail = updateServiceDetail(objWSServiceDTO);
			objWSServiceDTO.setServiceId(objServiceDetail.getServiceId());
			WSServiceDAO objWSServiceDAO = new WSServiceDAO();
			message += objWSServiceDAO.updateWSService(objWSServiceDTO);
			log.debug(objWSServiceDTO.getServiceId());
			message += objWSServiceDAO.updateAllWSParameters(objWSServiceDTO);
			objResponse.setErrorCode(0);
			objResponse.setErrorMessage("service updated successfully : - "
					+ objWSServiceDTO.getServiceName());
			objResponse.setMessage(message);
			return objResponse;
		}
	}

	/**
	 * deletes WSSservice related to a given serviceId
	 * 
	 * @param serviceId
	 *            id of given WSService
	 * @return String response of delete
	 * @throws SQLException
	 */
	public String deleteWSService(int serviceId) throws SQLException {
		WSServiceDAO objWSServiceDAO = new WSServiceDAO();
		String msg = objWSServiceDAO.deleteAllWSParameters(serviceId);
		msg += objWSServiceDAO.deleteWSService(serviceId);
		msg += deleteServiceDetail(serviceId);
		return msg;
	}

	/**
	 * #######################
	 * 
	 * DATASOURCE QUERY SERVICE RELATED RPC
	 * 
	 * #######################
	 */

	/**
	 * Saves a DataSource object in database
	 * 
	 * @param objDataSource
	 *            object of given DataSource
	 * @return String response of saving
	 * @throws SQLException
	 */
	public String saveDataSource(DataSource objDataSource) throws SQLException {
		if (!dbConnEstablished(objDataSource)) {
			throw new SQLException("Error in establishing connection "
					+ "to databaseAlias : - " + objDataSource.getDbAlias());
		}
		if (objDataSource.getDbID() == 0) {
			return new DataSourceDAO().saveDataSource(objDataSource);
		} else {
			return new DataSourceDAO().updateDataSource(objDataSource);
		}
	}

	public String saveDataSource(DataSource objDataSource,
			boolean checkDBConnection) throws SQLException {
		if (!dbConnEstablished(objDataSource) && checkDBConnection) {
			throw new SQLException("Error in establishing connection "
					+ "to databaseAlias : - " + objDataSource.getDbAlias());
		}
		if (objDataSource.getDbID() == 0) {
			return new DataSourceDAO().saveDataSource(objDataSource);
		} else {
			return new DataSourceDAO().updateDataSource(objDataSource);
		}
	}

	/**
	 * Checks a DataSource parameters for connection
	 * 
	 * @param objDataSource
	 *            object of given DataSource
	 * @return boolean response of data source connection established
	 * 
	 */
	public boolean dbConnEstablished(DataSource objDataSource) {
		return new DBQueryServiceRPC().dbConnEstablished(objDataSource);
	}

	/**
	 * gives all DataSource objects saved in database
	 * 
	 * @return List of all DataSource objects
	 * @throws SQLException
	 */
	public List<DataSource> getDataSourceList() throws SQLException {
		return new DataSourceDAO().getDataSourceList();
	}

	/**
	 * Deletes a given DataSource from database also deletes all related
	 * services
	 * 
	 * @param dbID
	 *            id of given DataSource object
	 * @return String response of delete
	 * @throws SQLException
	 */
	public String deleteDataSource(int dbID) throws SQLException {

		DataSourceDAO objSourceDAO = new DataSourceDAO();
		String msg = deleteQueryServiceFordbID(dbID);
		msg += objSourceDAO.deleteDataSource(dbID);
		return msg;
	}

	/**
	 * deletes all WSServices related to given wsId form databse
	 * 
	 * @param dbID
	 *            id of given WSdlDTO
	 * @return String response of delete
	 * @throws SQLException
	 */
	public String deleteQueryServiceFordbID(int linkedDbId) throws SQLException {
		QueryServiceDAO objServiceDAO = new QueryServiceDAO();
		List<Integer> serviceIDs = objServiceDAO
				.fetchAllServiceIDsForDsID(linkedDbId);
		int count = 0;
		for (int serID : serviceIDs) {
			deleteQueryService(serID);
			count++;
		}
		return "" + count + " Query Service(s) deleted from database";
	}

	/**
	 * ##############################
	 * 
	 * QUERY SERVICE RELATED RPC
	 * 
	 * ##############################
	 */

	/**
	 * Saves a Service
	 * 
	 * @param objQueryService
	 *            object of QueryService
	 * @return ServiceSaveResponse response of saving
	 * @throws SQLException
	 */
	public ServiceSaveResponse saveQueryService(QueryService objQueryService)
			throws SQLException {
		ServiceSaveResponse objResponse = new ServiceSaveResponse();
		if (serviceNameExists(objQueryService.getServiceName())) {
			objResponse.setErrorCode(1);
			objResponse.setErrorMessage("service name exists : - "
					+ objQueryService.getServiceName());
			objResponse.setMessage("Enter Different Name");
			return objResponse;
		} else {
			String message = "";
			ServiceDetail objServiceDetail = saveServiceDetail(objQueryService);
			objQueryService.setServiceId(objServiceDetail.getServiceId());
			QueryServiceDAO objServiceDAO = new QueryServiceDAO();
			message += objServiceDAO.saveQueryService(objQueryService);
			log.debug(objQueryService.getServiceId());
			message += objServiceDAO.saveAllQueryParameters(objQueryService);
			/** ____ Added code to add config service */
			ConfigService objConfigService = new ConfigService();
			objConfigService.setLinkedServiceId(objQueryService.getServiceId());
			objConfigService
					.setConfigServiceTypeId(TYPE_APPLICATION_CONFIG_SERVICE_ID);
			objConfigService.setServiceChannel(null);
			message += saveConfigService(objConfigService);
			//
			objResponse.setErrorCode(0);
			objResponse.setErrorMessage("service saved successfully : - "
					+ objQueryService.getServiceName());
			objResponse.setMessage(message);
			return objResponse;
		}
	}

	/**
	 * gives all QueryService objects saved in database
	 * 
	 * @return List of all QueryService objects
	 * @throws SQLException
	 */
	public List<QueryService> getQueryServiceList() throws SQLException {
		ServiceDetailDAO objServiceDetailDAO = new ServiceDetailDAO();
		List<ServiceDetail> serviceDetailList = objServiceDetailDAO
				.getServiceDetailsForType(QUERY_SERVICE);
		QueryServiceDAO objServiceDAO = new QueryServiceDAO();
		List<QueryService> list = objServiceDAO
				.getQueryServiceList(serviceDetailList);
		List<QueryService> finallist = new ArrayList<QueryService>();
		for (QueryService w : list) {
			List<QueryParameter> inpType = objServiceDAO
					.getQueryServiceParameters(w.getServiceId());
			List<IAgentServiceParameter> listISP = new ArrayList<IAgentServiceParameter>();
			for (QueryParameter qp : inpType) {
				listISP.add(qp);
			}
			w.setInputType(listISP);
			finallist.add(w);
		}
		return finallist;
	}

	/**
	 * Updates a given QueryService and all its parameters also checks if new
	 * service name exists in database
	 * 
	 * @param objQueryService
	 *            object of QueryService
	 * @param sameServiceName
	 *            if service name is not changed
	 * @return ServiceSaveResponse response of update
	 * @throws SQLException
	 */
	public ServiceSaveResponse updateQueryService(QueryService objQueryService,
			boolean sameServiceName) throws SQLException {
		ServiceSaveResponse objResponse = new ServiceSaveResponse();
		if (serviceNameExists(objQueryService.getServiceName())
				&& (sameServiceName == false)) {
			objResponse.setErrorCode(1);
			objResponse.setErrorMessage("service name exists : - "
					+ objQueryService.getServiceName());
			objResponse.setMessage("Enter Different Name");
			return objResponse;
		} else {
			String message = "";
			ServiceDetail objServiceDetail = updateServiceDetail(objQueryService);
			objQueryService.setServiceId(objServiceDetail.getServiceId());
			QueryServiceDAO objServiceDAO = new QueryServiceDAO();
			message += objServiceDAO.updateQueryService(objQueryService);
			log.debug(objQueryService.getServiceId());
			objServiceDAO.deleteAllQueryParameters(objQueryService
					.getServiceId());
			message += objServiceDAO.saveAllQueryParameters(objQueryService);
			objResponse.setErrorCode(0);
			objResponse.setErrorMessage("service updated successfully : - "
					+ objQueryService.getServiceName());
			objResponse.setMessage(message);
			return objResponse;
		}
	}

	/**
	 * deletes service related to a given serviceId
	 * 
	 * @param serviceId
	 *            id of given QueryService
	 * @return String response of delete
	 * @throws SQLException
	 */
	public String deleteQueryService(int serviceId) throws SQLException {
		QueryServiceDAO objServiceDAO = new QueryServiceDAO();
		String msg = objServiceDAO.deleteAllQueryParameters(serviceId);
		msg += objServiceDAO.deleteQueryService(serviceId);
		msg += deleteServiceDetail(serviceId);
		return msg;
	}

	/**
	 * executes a given query service with given parameter values , if any
	 * 
	 * @param objQueryService
	 *            object of given QueryService
	 * @return String "XML String Response" of execution of query
	 * @throws Exception
	 */
	public String executeQuery(QueryService objQueryService) throws Exception {
		DBQueryServiceRPC dbQ = new DBQueryServiceRPC();
		String result = dbQ.executeQuery(objQueryService);
		return result;
	}

	/**
	 * ##############################
	 * 
	 * CONFIGURED SERVICE RELATED RPC
	 * 
	 * ##############################
	 */

	/**
	 * Saves selected Services
	 * 
	 * @param selectedConfigServices
	 *            list of objects of ConfigService
	 * @return ServiceSaveResponse response of saving
	 * @throws SQLException
	 */
	public ServiceSaveResponse saveSelectedConfigServices(
			List<ConfigService> selectedConfigServices) throws SQLException {

		ServiceSaveResponse objResponse = new ServiceSaveResponse();
		log.debug("selectedConfigServices : " + selectedConfigServices);
		if (selectedConfigServices != null && selectedConfigServices.size() > 0) {

			int noOfConflicts = 0;
			int noOfSavedServices = 0;
			String savedServicesName = "";
			String conflictedServicesName = "";
			ServiceDetailDAO objServiceDetailDAO = new ServiceDetailDAO();
			for (ConfigService objConfigService : selectedConfigServices) {

				ServiceDetail objServiceDetail = objServiceDetailDAO
						.getServiceDetailForID(objConfigService
								.getLinkedServiceId());
				if (configServiceAddedForGivenType(objConfigService)) {
					noOfConflicts = noOfConflicts + 1;
					conflictedServicesName = conflictedServicesName + "\n  "
							+ objServiceDetail.getServiceName();
				} else {

					noOfSavedServices = noOfSavedServices + 1;
					String message = "";
					ConfigServiceDAO objServiceDAO = new ConfigServiceDAO();
					message += objServiceDAO
							.saveConfigService(objConfigService);
					log.debug(objConfigService.getLinkedServiceId());

					savedServicesName = savedServicesName + "\n  "
							+ objServiceDetail.getServiceName();
				}
			}
			objResponse.setErrorCode(0);
			String msg = "";
			if (noOfSavedServices > 0) {
				// msg = msg + noOfSavedServices
				// + " services saved successfully for given type";
				// msg = msg
				// +
				// "Following services are successfully added for given type . Below is the list :-"
				// + savedServicesName + "\n\n";
			} else {
				// msg = msg + " zero services saved";
			}

			if (noOfConflicts > 0) {
				msg = msg
						+ "Following services are ignored as they have already added . Below is the list :-"
						+ conflictedServicesName;
			}

			objResponse.setErrorMessage(msg);
			objResponse.setMessage(msg);

			return objResponse;
		} else {
			objResponse.setErrorCode(1);
			objResponse
					.setErrorMessage("select services to be added for given type ");
			objResponse
					.setMessage("select services to be added for given type  ");
			return objResponse;
		}

		// //////////////
		// if (configServiceAddedForGivenType(objConfigService)) {
		// objResponse.setErrorCode(1);
		// objResponse
		// .setErrorMessage("service already added for given type ");
		// objResponse.setMessage("service already added for given type ");
		// return objResponse;
		// } else {
		// String message = "";
		// ConfigServiceDAO objServiceDAO = new ConfigServiceDAO();
		// message += objServiceDAO.saveConfigService(objConfigService);
		// log.debug(objConfigService.getLinkedServiceId());
		//
		// objResponse.setErrorCode(0);
		// objResponse.setErrorMessage("service saved successfully : - "
		// + objConfigService.getLinkedServiceId());
		// objResponse.setMessage(message);
		// return objResponse;
		// }
	}

	/**
	 * Saves a Service
	 * 
	 * @param objConfigService
	 *            object of ConfigService
	 * @return ServiceSaveResponse response of saving
	 * @throws SQLException
	 */
	public ServiceSaveResponse saveConfigService(ConfigService objConfigService)
			throws SQLException {
		ServiceSaveResponse objResponse = new ServiceSaveResponse();
		if (configServiceAddedForGivenType(objConfigService)) {
			objResponse.setErrorCode(1);
			objResponse
					.setErrorMessage("service already added for given type ");
			objResponse.setMessage("service already added for given type ");
			return objResponse;
		} else {
			String message = "";
			ConfigServiceDAO objServiceDAO = new ConfigServiceDAO();
			message += objServiceDAO.saveConfigService(objConfigService);
			log.debug(objConfigService.getLinkedServiceId());

			objResponse.setErrorCode(0);
			objResponse.setErrorMessage("service saved successfully : - "
					+ objConfigService.getLinkedServiceId());
			objResponse.setMessage(message);
			return objResponse;
		}
	}

	/**
	 * Checks if given ConfigService already added for given configServiceType
	 * in database
	 * 
	 * @param objConfigService
	 *            object of ConfigService
	 * @return boolean true for exists
	 * @throws SQLException
	 */
	public boolean configServiceAddedForGivenType(ConfigService objConfigService)
			throws SQLException {

		List<ConfigService> objConfList = getAllConfigServiceList();
		for (ConfigService cs : objConfList) {
			if ((cs.getConfigServiceTypeId() == objConfigService
					.getConfigServiceTypeId())
					&& (cs.getLinkedServiceId() == objConfigService
							.getLinkedServiceId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * gives all ConfigService objects of given confServiceTypeId saved in
	 * database
	 * 
	 * @param confServiceTypeId
	 *            id of ConfigService
	 * @return List of all QueryService objects
	 * @throws SQLException
	 */
	public List<ConfigService> getAllConfigServiceList() throws SQLException {
		ConfigServiceDAO objServiceDAO = new ConfigServiceDAO();
		List<ConfigService> list = objServiceDAO.getAllConfigServiceList();
		// Filter Deleted Config Services
		List<ConfigService> finallist = new ArrayList<ConfigService>();
		for (ConfigService w : list) {
			IAgentService objIAgentService = getIAgentServiceForSerId(w
					.getLinkedServiceId());
			if (objIAgentService != null) {
				w.setService(objIAgentService);
				finallist.add(w);
			} else {
				deleteConfigService(w.getConfigServiceSrNo());
			}
		}

		for (ConfigService confser : finallist) {
			if (confser.getServiceChannel() != null
					&& confser.getServiceChannel().getServiceChannelID() > 0) {
				int serviceChannelID = confser.getServiceChannel()
						.getServiceChannelID();
				IAgentServiceChannel objChannel = getServiceChannelForID(serviceChannelID);
				if (objChannel != null) {
					confser.setServiceChannel(objChannel);
				}
			}
		}

		return finallist;
	}

	/**
	 * updates ConfigService related to a given configServiceSrNo
	 * 
	 * @param configServiceSrNo
	 *            id of given ConfigService
	 * @return String response of update
	 * @throws SQLException
	 */
	public String updateConfService(ConfigService objConfigService) {
		String msg = "";
		ConfigServiceDAO objServiceDAO = new ConfigServiceDAO();
		msg += objServiceDAO.updateConfigService(objConfigService);
		return msg;
	}

	/**
	 * deletes ConfigService related to a given configServiceSrNo
	 * 
	 * @param configServiceSrNo
	 *            id of given ConfigService
	 * @return String response of delete
	 * @throws SQLException
	 */
	public String deleteConfigService(int configServiceSrNo)
			throws SQLException {
		ConfigServiceDAO objServiceDAO = new ConfigServiceDAO();
		return objServiceDAO.deleteConfigService(configServiceSrNo);
	}

	/** update Config Service Type */
	public String updateConfigServiceType(
			ConfigServiceType objServiceChannelType) {
		ConfigServiceDAO objServiceDAO = new ConfigServiceDAO();
		String msg = objServiceDAO
				.updateConfigServiceType(objServiceChannelType);
		// System.out.println("updated config service type");
		// makeServiceXML();

		return msg;
	}

	public List<ConfigServiceType> fetchAllConfigServiceTypes() {
		ConfigServiceDAO objServiceDAO = new ConfigServiceDAO();
		List<ConfigServiceType> configServiceTypes = objServiceDAO
				.fetchAllConfigServiceTypes();
		for (ConfigServiceType cType : configServiceTypes) {
			int defaultChannelID = cType.getDefaultChannel()
					.getServiceChannelID();
			if (defaultChannelID > 0) {
				IAgentServiceChannel objChannel = getServiceChannelForID(defaultChannelID);
				if (objChannel != null) {
					cType.setDefaultChannel(objChannel);
				}
			}
		}

		return configServiceTypes;
	}

	/**
	 * returns IAgentService related to a given serviceId
	 * 
	 * @param serviceId
	 *            id of given IAgentService
	 * @return IAgentService object response
	 * @throws SQLException
	 */
	public IAgentService getIAgentServiceForSerId(int serviceId)
			throws SQLException {
		IAgentService objIAgentService = null;
		ServiceDetailDAO objDetailDAO = new ServiceDetailDAO();
		ServiceDetail objServiceDetail = objDetailDAO
				.getServiceDetailForID(serviceId);
		if (objServiceDetail == null) {
			return objIAgentService;
		}
		String serType = objServiceDetail.getServiceType();
		if (serType == null) {
			return objIAgentService;
		}
		if (serType.equalsIgnoreCase(JAVA_SERVICE)) {
			ServiceDAO objServiceDAO = new ServiceDAO();
			objIAgentService = objServiceDAO.fetchJavaService(objServiceDetail);
		} else if (serType.equalsIgnoreCase(WEB_SERVICE)) {
			WSServiceDAO objWsServiceDAO = new WSServiceDAO();
			objIAgentService = objWsServiceDAO
					.fetchWebService(objServiceDetail);
		} else if (serType.equalsIgnoreCase(QUERY_SERVICE)) {
			QueryServiceDAO objWsServiceDAO = new QueryServiceDAO();
			objIAgentService = objWsServiceDAO
					.fetchQueryService(objServiceDetail);
		}
		return objIAgentService;
	}

	/**
	 * ###############################
	 * 
	 * SERVICE CHANNEL RELATED RPC
	 * 
	 * ###############################
	 */

	/**
	 * gives all IAgentServiceChannelType objects saved in database
	 * 
	 * @return List of all IAgentServiceChannelType objects
	 * @throws SQLException
	 */

	public List<IAgentServiceChannelType> getAllServiceChannelTypeList()
			throws SQLException {
		List<IAgentServiceChannelType> channnelTypeList = new ArrayList<IAgentServiceChannelType>();
		IAgentServiceChannelDAO objChannelDAO = new IAgentServiceChannelDAO();
		channnelTypeList = objChannelDAO.getAllServiceChannelTypes();
		return channnelTypeList;
	}

	/**
	 * Saves a IAgentServiceChannel
	 * 
	 * @param objServiceChannel
	 *            object of IAgentServiceChannel
	 * @return String response of saving
	 * @throws SQLException
	 */
	public ServiceSaveResponse saveServiceChannel(
			IAgentServiceChannel objServiceChannel) throws SQLException {
		ServiceSaveResponse objResponse = new ServiceSaveResponse();
		if (serviceChannelNameExists(objServiceChannel.getServiceChannelName())) {
			objResponse.setErrorCode(1);
			objResponse
					.setErrorMessage("Channel name already added to database");
			objResponse.setMessage("Enter different Channel Name");
			return objResponse;
		} else {
			String message = "";
			IAgentServiceChannelDAO objServiceDAO = new IAgentServiceChannelDAO();
			message += objServiceDAO.saveServiceChannel(objServiceChannel);

			objResponse.setErrorCode(0);
			objResponse.setErrorMessage("Channel saved successfully : - "
					+ objServiceChannel.getServiceChannelName());
			objResponse.setMessage(message);

			// makeServiceXML();

			return objResponse;
		}
	}

	/**
	 * Checks if given ServiceChannel name exists in database
	 * 
	 * @param serviceChannelName
	 *            name of service
	 * @return boolean true for exists
	 * @throws SQLException
	 */
	public boolean serviceChannelNameExists(String serviceChannelName)
			throws SQLException {
		List<IAgentServiceChannel> objServiceChannels = getAllServiceChannelList();
		for (IAgentServiceChannel objChannel : objServiceChannels) {
			if (objChannel.getServiceChannelName().equalsIgnoreCase(
					serviceChannelName)) {
				return true;
			}
		}
		return false;
	}

	public IAgentServiceChannel getServiceChannelForID(int serviceChannelID) {
		IAgentServiceChannelDAO objChannelDAO = new IAgentServiceChannelDAO();
		return objChannelDAO.getServiceChannelForID(serviceChannelID);
	}

	/**
	 * gives all IAgentServiceChannel objects saved in database
	 * 
	 * @return List of all IAgentServiceChannel objects
	 * @throws SQLException
	 */
	public List<IAgentServiceChannel> getAllServiceChannelList()
			throws SQLException {

		List<IAgentServiceChannel> serviceChannelList = new ArrayList<IAgentServiceChannel>();
		IAgentServiceChannelDAO objChannelDAO = new IAgentServiceChannelDAO();
		serviceChannelList = objChannelDAO.getAllServiceChannelList();
		List<IAgentServiceChannelType> objChannelTypes = getAllServiceChannelTypeList();

		for (IAgentServiceChannel objChannel : serviceChannelList) {
			for (IAgentServiceChannelType objType : objChannelTypes) {
				if (objType.getId() == objChannel.getChannelType().getId()) {
					objChannel.getChannelType().setName(objType.getName());
				}
			}
		}
		return serviceChannelList;
	}

	/**
	 * Updates a given IAgentServiceChannel and also checks if new channel name
	 * exists in database
	 * 
	 * @param objServiceChannel
	 *            object of IAgentServiceChannel
	 * @param sameServiceChannelName
	 *            if service name is not changed
	 * @return ServiceSaveResponse response of update
	 * @throws SQLException
	 */
	public ServiceSaveResponse updateServiceChannel(
			IAgentServiceChannel objServiceChannel,
			boolean sameServiceChannelName) throws SQLException {

		ServiceSaveResponse objResponse = new ServiceSaveResponse();
		if (serviceChannelNameExists(objServiceChannel.getServiceChannelName())
				&& (sameServiceChannelName == false)) {
			objResponse.setErrorCode(1);
			objResponse.setErrorMessage("Channel name exists : - "
					+ objServiceChannel.getServiceChannelName());
			objResponse.setMessage("Enter Different Channel Name");
			return objResponse;

		} else {
			String message = "";
			IAgentServiceChannelDAO objServiceDAO = new IAgentServiceChannelDAO();
			message += objServiceDAO.updateServiceChannel(objServiceChannel);

			objResponse.setErrorCode(0);
			objResponse.setErrorMessage("channel updated successfully : - "
					+ objServiceChannel.getServiceChannelName());
			objResponse.setMessage(message);

			// makeServiceXML();

			return objResponse;
		}
	}

	/**
	 * Deletes IAgentServiceChannel object for given serviceChannelID
	 * 
	 * @param serviceChannelID
	 * 
	 * @return String response of delete
	 * @throws SQLException
	 */
	public String deleteServiceChannel(int serviceChannelID) {
		String msg = "";
		if (serviceChannelID <= 0) {
			msg = "no Service Channel Defined For serviceChannelID == "
					+ serviceChannelID;
			log.info(msg);
			return msg;
		}
		try {
			IAgentServiceChannelDAO objChannelDAO = new IAgentServiceChannelDAO();

			List<ConfigService> configServicesList = getAllConfigServiceList();
			List<ConfigServiceType> serviceChannelTypes = fetchAllConfigServiceTypes();

			boolean channelBeingUsed = false;
			List<String> namesToDisplay = new ArrayList<String>();

			for (ConfigServiceType confSer : serviceChannelTypes) {

				if (confSer.getDefaultChannel() != null
						&& confSer.getDefaultChannel().getServiceChannelID() == serviceChannelID) {

					channelBeingUsed = true;

					String channelName = confSer.getConfigSerTypeName();
					if (channelName != null
							&& !channelName.trim().equalsIgnoreCase("")) {

						namesToDisplay.add(channelName + " as Default Channel");

					}

				}
			}

			for (ConfigService confSer : configServicesList) {
				if (confSer.getServiceChannel() != null
						&& confSer.getServiceChannel().getServiceChannelID() == serviceChannelID) {

					channelBeingUsed = true;
					String channelName = confSer.getService().getServiceName();
					if (channelName != null
							&& !channelName.trim().equalsIgnoreCase("")) {

						namesToDisplay.add(channelName + " as Service Channel");

					}

				}
			}

			if (!channelBeingUsed) {
				msg = objChannelDAO.deleteServiceChannel(serviceChannelID);
			} else {
				msg = " channel being used by Following : ";
				for (String name : namesToDisplay) {
					msg += "\n " + name;
				}
			}

			// makeServiceXML();
		} catch (Exception ex) {
			msg += "Error in delete of service channel with id == "
					+ serviceChannelID;
			log.error(msg, ex);

		}

		return msg;
	}

	/**
	 * #######################
	 * 
	 * GENERATES SERVICES.XML
	 * 
	 * #######################
	 */

	/**
	 * generates Service.XML file using String result from
	 * makeServiceChannelXML()
	 * 
	 * @return String response
	 * @throws SQLException
	 * 
	 */
	public String refreshServiceXML() {
		String msg = "problem in creating services.xml";
		String content = "";
		try {
			content = makeServiceChannelXML();

			log.trace("Successfully created xml : ");
			log.trace(content);
		} catch (SQLException e1) {
			log.error("Problem in making xml due to : ", e1);
			content = "";
			// e1.printStackTrace();
		}

		String destDir = PropertyUtil.getInstance().getValueForKey(
				"serviceXMLFolderPath");

		if (destDir == null) {
			msg = "problem in creating services.xml. serviceXMLFolderPath not found";
			log.debug(msg);
			return msg;
		}
		if (content != null && !content.trim().equalsIgnoreCase("")) {
			try {

				File serviceXmlFile = new File(destDir + "/" + "services.xml");
				if (!serviceXmlFile.exists()) {
					serviceXmlFile.createNewFile();
				}

				/** Create file */
				FileWriter fstream = new FileWriter(serviceXmlFile);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(content);
				/** Close the output stream */
				out.close();
				msg = "Successfully created services.xml ";
			} catch (IOException e) {
				log.error("problem in creating xml.", e);
				// e.printStackTrace();
			}
		} else {
			log.debug("problem in creating xml. content is null or empty");
		}

		return msg;

	}

	/**
	 * generates Service Channel XML using list IAgentServiceChannel and
	 * ConfigService objects from database
	 * 
	 * @return String XML
	 * @throws SQLException
	 * 
	 */
	private String makeServiceChannelXML() throws SQLException {

		String resultXML = "";
		try {
			List<IAgentServiceChannel> channelList = getAllServiceChannelList();

			resultXML += "<services>\n";

			/** make service channels node */

			resultXML += "\t<serviceChannel>\n";
			for (IAgentServiceChannel objChannel : channelList) {
				resultXML += "\t\t<channel ";

				resultXML += " id = \"" + objChannel.getServiceChannelName()
						+ "\"";
				resultXML += "\n\t\t\t\t destination = \""
						+ objChannel.getDestination() + "\"";
				resultXML += "\n\t\t\t\t requestName = \""
						+ objChannel.getRequestName() + "\"";
				resultXML += "\n\t\t\t\t type = \""
						+ objChannel.getChannelType().getName() + "\""
						+ " />\n";

			}
			resultXML += "\t</serviceChannel>\n";

			List<ConfigService> configServicesList = getAllConfigServiceList();

			List<ConfigServiceType> serviceChannelTypes = fetchAllConfigServiceTypes();

			/** _____ applicationServices ___________ */
			resultXML += getConfigServiceTypesAsXML(
					TYPE_APPLICATION_CONFIG_SERVICE_ID, "applicationServices",
					configServicesList, serviceChannelTypes);

			/** _____ loggingServices ___________ */
			resultXML += getConfigServiceTypesAsXML(
					TYPE_LOGGING_CONFIG_SERVICE_ID, "loggingServices",
					configServicesList, serviceChannelTypes);

			/** _____ authenticationServices ___________ */
			resultXML += getConfigServiceTypesAsXML(
					TYPE_AUTHENTICATION_CONFIG_SERVICE_ID,
					"authenticationServices", configServicesList,
					serviceChannelTypes);

			resultXML += "</services>\n";
		} catch (SQLException th) {
			log.error("Error in generating XML due to : ", th);
			throw new SQLException(th);
		}
		return resultXML;
	}

	private String getConfigServiceTypesAsXML(int configServiceTypeID,
			String displayName, List<ConfigService> configServicesList,
			List<ConfigServiceType> serviceChannelTypes) {
		String resultXML = "";

		String defaultChannelName = "";

		/** _______ applicationServices ___________ */
		for (ConfigServiceType sChType : serviceChannelTypes) {
			if (sChType.getConfigSerTypeID() == configServiceTypeID) {
				if (sChType.getDefaultChannel() != null
						&& sChType.getDefaultChannel().getServiceChannelName() != null) {
					defaultChannelName = sChType.getDefaultChannel()
							.getServiceChannelName();
				} else {
					defaultChannelName = "";
				}
			}
		}

		resultXML += "\n\t<" + displayName + " \t defaultChannel = \""
				+ defaultChannelName + "\">\n";
		for (ConfigService objConfigService : configServicesList) {
			if (objConfigService.getConfigServiceTypeId() == configServiceTypeID) {

				resultXML += getConfigServiceAsXML(objConfigService) + "";

			}
		}
		resultXML += "\t</" + displayName + ">\n";

		return resultXML;
	}

	private String getConfigServiceAsXML(ConfigService objConfigService) {
		String resultXML = "";

		resultXML += "\t\t<service ";

		resultXML += " name = \""
				+ objConfigService.getService().getServiceName() + "\"";
		if (objConfigService.getServiceChannel() != null
				&& objConfigService.getServiceChannel().getServiceChannelName() != null
				&& !objConfigService.getServiceChannel()
						.getServiceChannelName().trim().equalsIgnoreCase("")) {
			resultXML += "\n\t\t\t\t serviceChannel = \""
					+ objConfigService.getServiceChannel()
							.getServiceChannelName() + "\" />\n";
		} else {
			resultXML += "\n\t\t\t\t serviceChannel =  \"\" " + " />\n";
		}

		return resultXML;
	}

	/**
	 * #######################
	 * 
	 * IMPORT EXTERNAL SERVICES RELATED RPC
	 * 
	 * #######################
	 * */

	/**
	 * ___ Import Java Services_________
	 * 
	 * 
	 */
	public ImportServiceResponse importJavaServices(String dbURL,
			String dbUserName, String dbPassword, String dbDriverClassName,
			boolean leaveConflictedJarName) {
		ServiceImporter objServiceImporter = new ServiceImporter();

		return objServiceImporter.importJavaServices(dbURL, dbUserName,
				dbPassword, dbDriverClassName, leaveConflictedJarName);
	}

	/**
	 * ___ Import Web Services_________
	 * 
	 * 
	 */
	public ImportServiceResponse importWSServices(String dbURL,
			String dbUserName, String dbPassword, String dbDriverClassName,
			boolean leaveConflictedWSdlName) {
		ServiceImporter objServiceImporter = new ServiceImporter();

		return objServiceImporter.importWSServices(dbURL, dbUserName,
				dbPassword, dbDriverClassName, leaveConflictedWSdlName);
	}

	/**
	 * ___ Import Query Services_________
	 * 
	 * 
	 */
	public ImportServiceResponse importQueryServices(String dbURL,
			String dbUserName, String dbPassword, String dbDriverClassName,
			boolean leaveConflictedDSName) {
		ServiceImporter objServiceImporter = new ServiceImporter();

		return objServiceImporter.importQueryServices(dbURL, dbUserName,
				dbPassword, dbDriverClassName, leaveConflictedDSName);
	}

	/**
	 * ____ Export db data to xml ____
	 * 
	 * */
	public Document exportDataToXML(List<String> serviceNamesList)
			throws IOException, ParserConfigurationException, SQLException {
		return new IAgentServiceConfigXMLCreator()
				.exportDataToXML(serviceNamesList);
	}

	/**
	 * ____ Import db data from xml ____
	 * 
	 * */
	public void importDataFromXML(File xmlFile, List<String> discardJarNames)
			throws IOException, ParserConfigurationException, SQLException {

		new ServiceImporter().importDataFromXML(xmlFile, discardJarNames);
	}

	public List<DBModel> getAllDBModels() {
		List<DBModel> listDBModel = new ArrayList<DBModel>();
		listDBModel.add(new DBModel("SQLite", "jdbc:sqlite://[path]/[dbName]",
				"org.sqlite.JDBC", false, false));
		listDBModel.add(new DBModel("Oracle",
				"jdbc:oracle:[driverType]:[ip]:[port]:[dbName]",
				"oracle.jdbc.driver.OracleDriver", true, true));
		listDBModel.add(new DBModel("SQL SERVER 2005",
				"jdbc:jtds:sqlserver://[ip]:[port];DatabaseName=[dbName]",
				"net.sourceforge.jtds.jdbc.Driver", true, true));
		listDBModel.add(new DBModel("MySQL",
				"jdbc:mysql://[ip]:[port]/[dbName]", "com.mysql.jdbc.Driver",
				true, true));
		listDBModel.add(new DBModel("Others", "", "", false, false));

		return listDBModel;
	}

	public XMLSaveResponse saveReferencedModuleXML(String content) {
		XMLSaveResponse xmlSaveResponse = new XMLSaveResponse();

		String fileNameWithfullClassPath = PropertyUtil.getInstance()
				.getValueForKey("referencedModuleXMLFile");

		if (fileNameWithfullClassPath == null
				|| "".equals(fileNameWithfullClassPath)
				|| "null".equals(fileNameWithfullClassPath)) {

			fileNameWithfullClassPath = ISuiteContextListener.getContextPath()
					+ "/xml/admin/admin-module.xml";
		}

		File file = new File(fileNameWithfullClassPath);

		try {
			/** Create file */
			FileWriter fstream = new FileWriter(file);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(content);
			/** Close the output stream */
			out.close();

			xmlSaveResponse.setErrorCode(0);
			xmlSaveResponse.setErrorMessage("");
			xmlSaveResponse.setMessage("saved succesfully");
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			xmlSaveResponse.setErrorCode(2);
			xmlSaveResponse.setErrorMessage("Exception During XML File save:"
					+ e);
			xmlSaveResponse.setMessage("");

		}
		return xmlSaveResponse;
	}

}
