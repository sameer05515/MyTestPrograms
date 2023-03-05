package com.ist.iagent.admin.datasource;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.ServiceManagementRPC;
import com.ist.iagent.admin.db.dao.DataSourceDAO;
import com.ist.iagent.admin.db.dao.JarDAO;
import com.ist.iagent.admin.db.dao.QueryServiceDAO;
import com.ist.iagent.admin.db.dao.ServiceDAO;
import com.ist.iagent.admin.db.dao.ServiceDetailDAO;
import com.ist.iagent.admin.db.dao.WSServiceDAO;
import com.ist.iagent.admin.db.dao.WSdlDAO;
import com.ist.iagent.admin.db.pojo.DataSource;
import com.ist.iagent.admin.db.pojo.IAgentService;
import com.ist.iagent.admin.db.pojo.IAgentServiceParameter;
import com.ist.iagent.admin.db.pojo.JarDTO;
import com.ist.iagent.admin.db.pojo.JavaService;
import com.ist.iagent.admin.db.pojo.ParameterDescDTO;
import com.ist.iagent.admin.db.pojo.QueryParameter;
import com.ist.iagent.admin.db.pojo.QueryService;
import com.ist.iagent.admin.db.pojo.ServiceDetail;
import com.ist.iagent.admin.db.pojo.WSService;
import com.ist.iagent.admin.db.pojo.WSdlDTO;
import com.ist.iagent.admin.db.response.ServiceSaveResponse;
import com.ist.iagent.admin.db.wrapper.IAgentServiceConfigReader;

public class ServiceImporter {

	private static final Logger log = Logger.getLogger(ServiceImporter.class);
	private String dbURL;
	private String dbUserName;
	private String dbPassword;
	private String dbDriverClassName;

	/** ___ Get External Database Connection _____ */

	private Connection getConnection(String dbURL, String dbUserName,
			String dbPassword, String dbDriverClassName) throws SQLException {
		Connection conn = null;
		try {
			Class.forName(dbDriverClassName).newInstance();
			conn = DriverManager.getConnection(dbURL, dbUserName, dbPassword);
		} catch (Exception e) {
			log.error("Error in connection to database url == " + dbURL
					+ " username == " + dbUserName + " password == "
					+ dbPassword + " driver == " + dbDriverClassName, e);
			throw new SQLException("Error in connection to database url == "
					+ dbURL + " username == " + dbUserName + " password == "
					+ dbPassword + " driver == " + dbDriverClassName, e);

		}
		return conn;
	}

	/** ______ External Java Services List ______ */

	public ImportServiceResponse importJavaServices(String dbURL,
			String dbUserName, String dbPassword, String dbDriverClassName,
			boolean leaveConflictedJarName) {
		this.dbURL = dbURL;
		this.dbUserName = dbUserName;
		this.dbPassword = dbPassword;
		this.dbDriverClassName = dbDriverClassName;

		ImportServiceResponse objImportServiceResponse = new ImportServiceResponse();

		List<JavaService> successfullySavedJavaService = new ArrayList<JavaService>();
		List<JavaService> nameConflictedJavaService = new ArrayList<JavaService>();

		Connection con = null;
		try {
			con = getConnection(dbURL, dbUserName, dbPassword,
					dbDriverClassName);
		} catch (Exception e1) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			log.error("Error in getting Connection:-\n", e1);
			objImportServiceResponse
					.setMessage("Error in getting Connection:-\n" + e1);
			return objImportServiceResponse;
		}

		if (con == null) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse
					.setMessage("Error in getting Connection : got null Connection object");
			log.error("Error in getting Connection : got null Connection object");
			return objImportServiceResponse;
		}
		/** jar name import */
		List<JarDTO> listExternalJars = new JarDAO().getJarList(con);
		List<JarDTO> listExistingJars = new JarDAO().getJarList();
		List<JavaService> listExternalJavaServices;
		List<JavaService> listExistingJavaServices;
		try {
			listExternalJavaServices = getJavaServiceList();
			listExistingJavaServices = new ServiceManagementRPC()
					.getServiceList();
		} catch (SQLException sqlException) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse
					.setMessage("Error in Java Service list from specified database:-\n"
							+ sqlException);
			log.error("Error in Java Service list from specified database:-\n",
					sqlException);
			return objImportServiceResponse;
		}
		if (listExternalJavaServices == null
				|| listExistingJavaServices == null) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse.setMessage("Error in getting Java Service"
					+ " list from specified database:-\nNull list found");
			log.error("Error in getting Java Service"
					+ " list from specified database:-\nNull list found");
			return objImportServiceResponse;
		}

		log.debug("External Jar List :- ");
		/** check jar name conflict */
		List<JarDTO> conflictedJars = getConflictedJarList(listExternalJars,
				listExistingJars);
		if (conflictedJars.size() > 0 && !leaveConflictedJarName) {
			objImportServiceResponse
					.setCode(ImportServiceResponse.JAR_NAME_CONFLICT);
			objImportServiceResponse.setConflictedJar(conflictedJars);
			return objImportServiceResponse;
		}
		for (JarDTO externJAR : listExternalJars) {
			boolean jarNameExists = jarNameExists(externJAR, listExistingJars);
			if (!jarNameExists) {
				/** save jar name */
				try {
					int oldJarId = externJAR.getJar_id();
					new ServiceManagementRPC().saveJarName(externJAR
							.getJar_name());
					listExistingJars = new JarDAO().getJarList();
					/** get new jarDTO object */
					JarDTO savedJarObject = listExistingJars
							.get(listExistingJars.size() - 1);
					for (JavaService externalJavaService : listExternalJavaServices) {
						/** update linked jar id with new one */
						if (externalJavaService.getLinkedJarId() == oldJarId) {
							externalJavaService.setLinkedJarId(savedJarObject
									.getJar_id());
							ServiceSaveResponse objResponse;
							try {
								objResponse = new ServiceManagementRPC()
										.saveService(externalJavaService);
								if (objResponse.getErrorCode() == 0) {
									successfullySavedJavaService
											.add(externalJavaService);
								}
							} catch (Exception e) {
								log.error("Error in save service : "
										+ externalJavaService.getServiceName(),
										e);
							}

						}

					}
				} catch (Exception e) {
					objImportServiceResponse
							.setCode(ImportServiceResponse.FAIL);
					objImportServiceResponse
							.setMessage("Error in saving jar name :-\n"
									+ externJAR.getJar_name() + e);
					log.error(
							"Error in saving jar name :-\n"
									+ externJAR.getJar_name() + "\n", e);
					return objImportServiceResponse;
				}
			}
		}

		objImportServiceResponse
				.setSuccessfullySavedServices(new ArrayList<IAgentService>(
						successfullySavedJavaService));
		nameConflictedJavaService = getConflictedJavaServices(
				listExternalJavaServices, listExistingJavaServices);
		objImportServiceResponse
				.setNameConflictedServices(new ArrayList<IAgentService>(
						nameConflictedJavaService));
		return objImportServiceResponse;
	}

	/** ______ External Web Services List ______ */

	public ImportServiceResponse importWSServices(String dbURL,
			String dbUserName, String dbPassword, String dbDriverClassName,
			boolean leaveConflictedWSdlName) {
		this.dbURL = dbURL;
		this.dbUserName = dbUserName;
		this.dbPassword = dbPassword;
		this.dbDriverClassName = dbDriverClassName;

		ImportServiceResponse objImportServiceResponse = new ImportServiceResponse();

		List<WSService> successfullySavedWSService = new ArrayList<WSService>();
		List<WSService> nameConflictedWSService = new ArrayList<WSService>();

		Connection con = null;
		try {
			con = getConnection(dbURL, dbUserName, dbPassword,
					dbDriverClassName);
		} catch (Exception e1) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			log.error("Error in getting Connection:-\n", e1);
			objImportServiceResponse
					.setMessage("Error in getting Connection:-\n" + e1);
			return objImportServiceResponse;
		}

		if (con == null) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse
					.setMessage("Error in getting Connection : got null Connection object");
			log.error("Error in getting Connection : got null Connection object");
			return objImportServiceResponse;
		}
		/** Wsdl name import */
		List<WSdlDTO> listExternalWSdlDTOs = new WSdlDAO().getWSDLList(con);
		List<WSdlDTO> listExistingWSdlDTOs = new WSdlDAO().getWSDLList();
		List<WSService> listExternalWSServices;
		List<WSService> listExistingWSServices;
		try {
			listExternalWSServices = getWSServiceList();
			listExistingWSServices = new ServiceManagementRPC()
					.getWSServiceList();
		} catch (SQLException sqlException) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse
					.setMessage("Error in Web Service list from specified database:-\n"
							+ sqlException);
			log.error("Error in Web Service list from specified database:-\n",
					sqlException);
			return objImportServiceResponse;
		}
		if (listExternalWSServices == null || listExistingWSServices == null) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse.setMessage("Error in getting Web Service"
					+ " list from specified database:-\nNull list found");
			log.error("Error in getting Web Service"
					+ " list from specified database:-\nNull list found");
			return objImportServiceResponse;
		}

		log.debug("External Web List :- ");
		/** check wsdlDTO name conflict */
		List<WSdlDTO> conflictedWSdlDTOs = getConflictedWsdlList(
				listExternalWSdlDTOs, listExistingWSdlDTOs);
		if (conflictedWSdlDTOs.size() > 0 && !leaveConflictedWSdlName) {
			objImportServiceResponse
					.setCode(ImportServiceResponse.WSDL_NAME_CONFLICT);
			objImportServiceResponse.setConflictedWsdlName(conflictedWSdlDTOs);
			return objImportServiceResponse;
		}
		for (WSdlDTO externWSdlDTO : listExternalWSdlDTOs) {
			boolean wsdlNameExists = wsdlNameExists(externWSdlDTO,
					listExistingWSdlDTOs);
			if (!wsdlNameExists) {
				/** save wsdlDTO name */
				try {
					int oldWsId = externWSdlDTO.getWsId();
					externWSdlDTO.setWsId(0);
					new ServiceManagementRPC().saveWSDL(externWSdlDTO);
					listExistingWSdlDTOs = new WSdlDAO().getWSDLList();
					/** get new WSdlDTO object */
					WSdlDTO savedWSdlDtoObject = listExistingWSdlDTOs
							.get(listExistingWSdlDTOs.size() - 1);
					for (WSService externalWSService : listExternalWSServices) {
						/** update linked ws id with new one */
						if (externalWSService.getLinkedWSId() == oldWsId) {
							externalWSService.setLinkedWSId(savedWSdlDtoObject
									.getWsId());
							ServiceSaveResponse objResponse;
							try {
								objResponse = new ServiceManagementRPC()
										.saveWSService(externalWSService);
								if (objResponse.getErrorCode() == 0) {
									successfullySavedWSService
											.add(externalWSService);
								}
							} catch (Exception e) {
								log.error("Error in save service : "
										+ externalWSService.getServiceName(), e);
							}

						}

					}
				} catch (Exception e) {
					objImportServiceResponse
							.setCode(ImportServiceResponse.FAIL);
					objImportServiceResponse
							.setMessage("Error in saving wsdl name :-\n"
									+ externWSdlDTO.getWsName());
					log.error(
							"Error in saving wsdl name :-\n"
									+ externWSdlDTO.getWsName() + "\n", e);
					return objImportServiceResponse;
				}
			}
		}

		objImportServiceResponse
				.setSuccessfullySavedServices(new ArrayList<IAgentService>(
						successfullySavedWSService));
		nameConflictedWSService = getConflictedWSServices(
				listExternalWSServices, listExistingWSServices);
		objImportServiceResponse
				.setNameConflictedServices(new ArrayList<IAgentService>(
						nameConflictedWSService));
		return objImportServiceResponse;
	}

	/** ______ External Query Services List ______ */

	public ImportServiceResponse importQueryServices(String dbURL,
			String dbUserName, String dbPassword, String dbDriverClassName,
			boolean leaveConflictedDSName) {
		this.dbURL = dbURL;
		this.dbUserName = dbUserName;
		this.dbPassword = dbPassword;
		this.dbDriverClassName = dbDriverClassName;

		ImportServiceResponse objImportServiceResponse = new ImportServiceResponse();

		List<QueryService> successfullySavedQueryService = new ArrayList<QueryService>();
		List<QueryService> nameConflictedQueryService = new ArrayList<QueryService>();

		Connection con = null;
		try {
			con = getConnection(dbURL, dbUserName, dbPassword,
					dbDriverClassName);
		} catch (Exception e1) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			log.debug("Error in getting Connection:-\n" + e1);
			objImportServiceResponse
					.setMessage("Error in getting Connection:-\n" + e1);
			return objImportServiceResponse;
		}

		if (con == null) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse
					.setMessage("Error in getting Connection : got null Connection object");
			log.debug("Error in getting Connection : got null Connection object");
			return objImportServiceResponse;
		}
		/** Wsdl name import */
		List<DataSource> listExternalDataSources = new DataSourceDAO()
				.getDataSourceList(con);
		List<DataSource> listExistingDataSources = new DataSourceDAO()
				.getDataSourceList();
		List<QueryService> listExternalQueryServices;
		List<QueryService> listExistingQueryServices;
		try {
			listExternalQueryServices = getQueryServiceList();
			listExistingQueryServices = new ServiceManagementRPC()
					.getQueryServiceList();
		} catch (SQLException sqlException) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse
					.setMessage("Error in Query Service list from specified database:-\n"
							+ sqlException);
			log.debug("Error in Query Service list from specified database:-\n"
					+ sqlException);
			return objImportServiceResponse;
		}
		if (listExternalQueryServices == null) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse
					.setMessage("Error in getting Query Service"
							+ " list from specified database:-\nNull list found");
			log.debug("Error in getting Query Service"
					+ " list from specified database:-\nNull list found");
			return objImportServiceResponse;
		}

		log.debug("External DataSource List :- ");
		/** check DataSource name conflict */
		List<DataSource> conflictedQueryServices = getConflictedDataSourceList(
				listExternalDataSources, listExistingDataSources);
		if (conflictedQueryServices.size() > 0 && !leaveConflictedDSName) {
			objImportServiceResponse
					.setCode(ImportServiceResponse.DATA_SOURCE_NAME_CONFLICT);
			objImportServiceResponse
					.setConflictedDataSourceName(conflictedQueryServices);
			return objImportServiceResponse;
		}
		for (DataSource externDataSource : listExternalDataSources) {
			boolean dataSourceNameExists = dataSourceNameExists(
					externDataSource, listExistingDataSources);
			if (!dataSourceNameExists) {
				/** save wsdlDTO name */
				try {
					int oldDbId = externDataSource.getDbID();
					externDataSource.setDbID(0);
					boolean checkDBConnection = false;
					new ServiceManagementRPC().saveDataSource(externDataSource,
							checkDBConnection);
					listExistingDataSources = new DataSourceDAO()
							.getDataSourceList();
					/** get new DataSource object */
					DataSource savedDataSourceObject = listExistingDataSources
							.get(listExistingDataSources.size() - 1);
					for (QueryService externalWSService : listExternalQueryServices) {
						/** update linked db id with new one */
						if (externalWSService.getLinkedDbID() == oldDbId) {
							externalWSService
									.setLinkedDbID(savedDataSourceObject
											.getDbID());
							ServiceSaveResponse objResponse;
							try {
								objResponse = new ServiceManagementRPC()
										.saveQueryService(externalWSService);
								if (objResponse.getErrorCode() == 0) {
									successfullySavedQueryService
											.add(externalWSService);
								}
							} catch (Exception e) {
								log.error("Error in save service : "
										+ externalWSService.getServiceName(), e);
							}

						}

					}
				} catch (Exception e) {
					objImportServiceResponse
							.setCode(ImportServiceResponse.FAIL);
					objImportServiceResponse
							.setMessage("Error in saving DataSource name :-\n"
									+ externDataSource.getDbUsername() + e);
					log.debug("Error in saving DataSource name :-\n"
							+ externDataSource.getDbUsername() + "\n" + e);
					return objImportServiceResponse;
				}
			}
		}

		objImportServiceResponse
				.setSuccessfullySavedServices(new ArrayList<IAgentService>(
						successfullySavedQueryService));
		nameConflictedQueryService = getConflictedQueryServices(
				listExternalQueryServices, listExistingQueryServices);
		objImportServiceResponse
				.setNameConflictedServices(new ArrayList<IAgentService>(
						nameConflictedQueryService));
		return objImportServiceResponse;
	}

	/** ______ External Java Services List Helper methods______ */

	private List<JarDTO> getConflictedJarList(List<JarDTO> listExternalJars,
			List<JarDTO> listExistingJars) {
		List<JarDTO> returnList = new ArrayList<JarDTO>();
		if (listExistingJars != null && listExistingJars.size() > 0) {
			for (JarDTO exterJarDTO : listExternalJars) {
				boolean exists = jarNameExists(exterJarDTO, listExistingJars);
				if (exists) {
					returnList.add(exterJarDTO);
				}
			}
		}

		return returnList;
	}

	private List<JavaService> getConflictedJavaServices(
			List<JavaService> listExternalJavaServices,
			List<JavaService> listExistingservices) {
		List<JavaService> resultList = new ArrayList<JavaService>();
		for (JavaService external : listExternalJavaServices) {
			for (JavaService existing : listExistingservices) {
				if (external.getServiceName().equalsIgnoreCase(
						existing.getServiceName())) {
					resultList.add(external);
				}
			}
		}
		return resultList;
	}

	/**
	 * gives all javaService objects from external Database as list
	 * 
	 * @return List of all javaService from external Database
	 * @throws SQLException
	 */
	private List<JavaService> getJavaServiceList() throws SQLException {

		List<JavaService> finallist = new ArrayList<JavaService>();
		Connection con = getConnection(dbURL, dbUserName, dbPassword,
				dbDriverClassName);
		if (con != null) {
			ServiceDetailDAO objServiceDetailDAO = new ServiceDetailDAO();
			List<ServiceDetail> serviceDetailList = objServiceDetailDAO
					.getServiceDetailsForType(
							ServiceManagementRPC.JAVA_SERVICE, con);
			ServiceDAO objServiceDAO = new ServiceDAO();
			List<JavaService> list = getExternalJavaServiceList(
					serviceDetailList, objServiceDAO);

			for (JavaService w : list) {
				con = getConnection(dbURL, dbUserName, dbPassword,
						dbDriverClassName);
				List<ParameterDescDTO> inpType = objServiceDAO
						.getServiceParameters(w.getServiceId(), con);
				List<IAgentServiceParameter> listISP = new ArrayList<IAgentServiceParameter>();
				for (ParameterDescDTO qp : inpType) {
					listISP.add(qp);
				}
				w.setInputType(listISP);
				finallist.add(w);
			}
		}
		return finallist;
	}

	private List<JavaService> getExternalJavaServiceList(
			List<ServiceDetail> serviceDetailList, ServiceDAO objServiceDAO)
			throws SQLException {
		List<JavaService> serviceList = new ArrayList<JavaService>();
		for (ServiceDetail serDetail : serviceDetailList) {
			Connection con = getConnection(dbURL, dbUserName, dbPassword,
					dbDriverClassName);
			if (con != null) {
				JavaService objJS = objServiceDAO.fetchJavaService(serDetail,
						con);
				if (objJS != null) {
					serviceList.add(objJS);
				}
			}
		}
		return serviceList;
	}

	private boolean jarNameExists(JarDTO externalJarDTO,
			List<JarDTO> listExistingJars) {
		boolean exists = false;
		if (listExistingJars != null && listExistingJars.size() > 0) {
			for (JarDTO presentJarDTO : listExistingJars) {
				if (presentJarDTO.getJar_name().equalsIgnoreCase(
						externalJarDTO.getJar_name())) {
					exists = true;
					break;
				}
			}
		}

		return exists;
	}

	/** ______ External Web Services List Helper methods______ */

	private List<WSService> getConflictedWSServices(
			List<WSService> listExternalJavaServices,
			List<WSService> listExistingservices) {
		List<WSService> resultList = new ArrayList<WSService>();
		for (WSService external : listExternalJavaServices) {
			for (WSService existing : listExistingservices) {
				if (external.getServiceName().equalsIgnoreCase(
						existing.getServiceName())) {
					resultList.add(external);
				}
			}
		}
		return resultList;
	}

	private boolean wsdlNameExists(WSdlDTO externWSdlDTO,
			List<WSdlDTO> listExistingWSdlDTOs) {
		boolean exists = false;
		if (listExistingWSdlDTOs != null && listExistingWSdlDTOs.size() > 0) {
			for (WSdlDTO presentJarDTO : listExistingWSdlDTOs) {
				if (presentJarDTO.getWsName().equalsIgnoreCase(
						externWSdlDTO.getWsName())) {
					exists = true;
					break;
				}
			}
		}
		return exists;
	}

	private List<WSdlDTO> getConflictedWsdlList(
			List<WSdlDTO> listExternalWSdlDTOs,
			List<WSdlDTO> listExistingWSdlDTOs) {
		List<WSdlDTO> returnList = new ArrayList<WSdlDTO>();
		if (listExistingWSdlDTOs != null && listExistingWSdlDTOs.size() > 0) {
			for (WSdlDTO externWSdlDTO : listExternalWSdlDTOs) {
				boolean exists = wsdlNameExists(externWSdlDTO,
						listExistingWSdlDTOs);
				if (exists) {
					returnList.add(externWSdlDTO);
				}
			}
		}
		return returnList;
	}

	/**
	 * gives all WSService objects from external Database as list
	 * 
	 * @return List of all WSService from external Database
	 * @throws SQLException
	 */
	private List<WSService> getWSServiceList() throws SQLException {

		List<WSService> finallist = new ArrayList<WSService>();
		Connection con = getConnection(dbURL, dbUserName, dbPassword,
				dbDriverClassName);
		if (con != null) {
			ServiceDetailDAO objServiceDetailDAO = new ServiceDetailDAO();
			List<ServiceDetail> serviceDetailList = objServiceDetailDAO
					.getServiceDetailsForType(ServiceManagementRPC.WEB_SERVICE,
							con);
			WSServiceDAO objServiceDAO = new WSServiceDAO();
			List<WSService> list = getExternalWSServiceList(serviceDetailList,
					objServiceDAO);

			for (WSService w : list) {
				con = getConnection(dbURL, dbUserName, dbPassword,
						dbDriverClassName);
				List<ParameterDescDTO> inpType = objServiceDAO
						.getWSServiceParameters(w.getServiceId(), con);
				List<IAgentServiceParameter> listISP = new ArrayList<IAgentServiceParameter>();
				for (ParameterDescDTO qp : inpType) {
					listISP.add(qp);
				}
				w.setInputType(listISP);
				finallist.add(w);
			}
		}
		return finallist;
	}

	private List<WSService> getExternalWSServiceList(
			List<ServiceDetail> serviceDetailList, WSServiceDAO objServiceDAO)
			throws SQLException {
		List<WSService> serviceList = new ArrayList<WSService>();
		for (ServiceDetail serDetail : serviceDetailList) {
			Connection con = getConnection(dbURL, dbUserName, dbPassword,
					dbDriverClassName);
			if (con != null) {
				WSService objWS = objServiceDAO.fetchWebService(serDetail, con);
				if (objWS != null) {
					serviceList.add(objWS);
				}
			} else {
				break;
			}
		}
		return serviceList;
	}

	/** ______ External Query Services List Helper methods______ */

	private boolean dataSourceNameExists(DataSource exterDataSource,
			List<DataSource> listExistingDataSources) {
		boolean exists = false;
		if (listExistingDataSources != null
				&& listExistingDataSources.size() > 0) {
			for (DataSource presentDataSource : listExistingDataSources) {
				if (presentDataSource.getDbAlias().equalsIgnoreCase(
						exterDataSource.getDbAlias())) {
					exists = true;
					break;
				}
			}
		}
		return exists;
	}

	/**
	 * gives all QueryService objects from external Database as list
	 * 
	 * @return List of all QueryService from external Database
	 * @throws SQLException
	 */
	private List<QueryService> getQueryServiceList() throws SQLException {

		List<QueryService> finallist = new ArrayList<QueryService>();
		Connection con = getConnection(dbURL, dbUserName, dbPassword,
				dbDriverClassName);
		if (con != null) {
			ServiceDetailDAO objServiceDetailDAO = new ServiceDetailDAO();
			List<ServiceDetail> serviceDetailList = objServiceDetailDAO
					.getServiceDetailsForType(
							ServiceManagementRPC.QUERY_SERVICE, con);
			QueryServiceDAO objServiceDAO = new QueryServiceDAO();
			List<QueryService> list = getExternalQueryServiceList(
					serviceDetailList, objServiceDAO);

			for (QueryService w : list) {
				con = getConnection(dbURL, dbUserName, dbPassword,
						dbDriverClassName);
				if (con != null) {
					List<QueryParameter> inpType = objServiceDAO
							.getQueryServiceParameters(w.getServiceId(), con);
					List<IAgentServiceParameter> listISP = new ArrayList<IAgentServiceParameter>();
					for (QueryParameter qp : inpType) {
						listISP.add(qp);
					}
					w.setInputType(listISP);
					finallist.add(w);
				}
			}
		}
		return finallist;
	}

	private List<QueryService> getExternalQueryServiceList(
			List<ServiceDetail> serviceDetailList, QueryServiceDAO objServiceDAO)
			throws SQLException {

		List<QueryService> serviceList = new ArrayList<QueryService>();
		for (ServiceDetail serDetail : serviceDetailList) {
			Connection con = getConnection(dbURL, dbUserName, dbPassword,
					dbDriverClassName);
			if (con != null) {
				QueryService objQS = objServiceDAO.fetchQueryService(serDetail,
						con);
				if (objQS != null) {
					serviceList.add(objQS);
				}
			}
		}
		return serviceList;
	}

	private List<QueryService> getConflictedQueryServices(
			List<QueryService> listExternalQueryServices,
			List<QueryService> listExistingQueryServices) {

		List<QueryService> resultList = new ArrayList<QueryService>();
		for (QueryService external : listExternalQueryServices) {
			for (QueryService existing : listExistingQueryServices) {
				if (external.getServiceName().equalsIgnoreCase(
						existing.getServiceName())) {
					resultList.add(external);
				}
			}
		}
		return resultList;
	}

	private List<DataSource> getConflictedDataSourceList(
			List<DataSource> listExternalDataSources,
			List<DataSource> listExistingDataSources) {

		List<DataSource> returnList = new ArrayList<DataSource>();
		if (listExistingDataSources != null
				&& listExistingDataSources.size() > 0) {
			for (DataSource exterDataSource : listExternalDataSources) {
				boolean exists = dataSourceNameExists(exterDataSource,
						listExistingDataSources);
				if (exists) {
					returnList.add(exterDataSource);
				}

			}
		}
		return returnList;
	}

	/**
	 * 
	 * ___
	 * 
	 * Import db data from xml
	 * 
	 * _____
	 */
	public void importDataFromXML(File xmlFile, List<String> discardJarNames) {

		importJavaServices(xmlFile, true, discardJarNames);
		importQueryServices(xmlFile, true);
		importWSServices(xmlFile, true);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					new ServiceManagementRPC().refreshServiceXML();
				} catch (Throwable th) {
					log.error("Unable to refresh services.xml ", th);
				}
			}
		});
	}

	/** ______ External WSServices List from xml ______ */

	private ImportServiceResponse importWSServices(File xmlFile,
			boolean leaveConflictedWSdlName) {

		IAgentServiceConfigReader objServiceConfigReader = new IAgentServiceConfigReader();
		ImportServiceResponse objImportServiceResponse = new ImportServiceResponse();

		List<WSService> successfullySavedWSService = new ArrayList<WSService>();
		List<WSService> nameConflictedWSService = new ArrayList<WSService>();

		/** Wsdl name import */
		log.debug("getting external wsdldto list from xml");
		List<WSdlDTO> listExternalWSdlDTOs = new ArrayList<WSdlDTO>();
		try {
			listExternalWSdlDTOs = objServiceConfigReader
					.getWSdlDTOListFromXML(xmlFile);
			// if (listExternalWSdlDTOs != null) {
			// log.debug("got following WSdlDTO names");
			// for (WSdlDTO objjj : listExternalWSdlDTOs) {
			// log.debug(objjj.getWsName() + " >>> " + objjj.getWsId());
			// }
			// }
		} catch (Exception sqlException) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse
					.setMessage("Error in WSService list from xml:-\n"
							+ sqlException);
			log.error("Error in WSService list from xml:-\n", sqlException);
			return objImportServiceResponse;
		}

		List<WSdlDTO> listExistingWSdlDTOs = new WSdlDAO().getWSDLList();
		List<WSService> listExternalWSServices;
		List<WSService> listExistingWSServices;
		try {
			log.debug("getting external ws services list from xml");
			listExternalWSServices = objServiceConfigReader
					.getWSServiceListFromXML(xmlFile);
			listExistingWSServices = new ServiceManagementRPC()
					.getWSServiceList();
		} catch (Exception sqlException) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse
					.setMessage("Error in Web Service list from xml:-\n"
							+ sqlException);
			log.error("Error in Web Service list from xml:-\n", sqlException);
			return objImportServiceResponse;
		}
		if (listExternalWSServices == null || listExistingWSServices == null) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse.setMessage("Error in getting Web Service"
					+ " list from xml:-\nNull list found");
			log.error("Error in getting Web Service"
					+ " list from xml:-\nNull list found");
			return objImportServiceResponse;
		}

		log.debug("External Web List :- ");
		/** check wsdlDTO name conflict */
		List<WSdlDTO> conflictedWSdlDTOs = getConflictedWsdlList(
				listExternalWSdlDTOs, listExistingWSdlDTOs);
		if (conflictedWSdlDTOs.size() > 0 && !leaveConflictedWSdlName) {
			objImportServiceResponse
					.setCode(ImportServiceResponse.WSDL_NAME_CONFLICT);
			objImportServiceResponse.setConflictedWsdlName(conflictedWSdlDTOs);
			return objImportServiceResponse;
		}
		for (WSdlDTO externWSdlDTO : listExternalWSdlDTOs) {
			boolean wsdlNameExists = wsdlNameExists(externWSdlDTO,
					listExistingWSdlDTOs);
			if (!wsdlNameExists) {
				/** save wsdlDTO name */
				try {
					int oldWsId = externWSdlDTO.getWsId();
					externWSdlDTO.setWsId(0);
					// new ServiceManagementRPC().saveWSDL(externWSdlDTO);
					new WSdlDAO().saveWSDL(externWSdlDTO);
					listExistingWSdlDTOs = new WSdlDAO().getWSDLList();
					/** get new WSdlDTO object */
					WSdlDTO savedWSdlDtoObject = listExistingWSdlDTOs
							.get(listExistingWSdlDTOs.size() - 1);
					for (WSService externalWSService : listExternalWSServices) {
						/** update linked ws id with new one */
						if (externalWSService.getLinkedWSId() == oldWsId) {
							externalWSService.setLinkedWSId(savedWSdlDtoObject
									.getWsId());
							ServiceSaveResponse objResponse;
							try {
								objResponse = new ServiceManagementRPC()
										.saveWSService(externalWSService);
								if (objResponse.getErrorCode() == 0) {
									successfullySavedWSService
											.add(externalWSService);
								}
							} catch (Exception e) {
								log.error("Error in save service : "
										+ externalWSService.getServiceName(), e);
							}

						}

					}
				} catch (Exception e) {
					objImportServiceResponse
							.setCode(ImportServiceResponse.FAIL);
					objImportServiceResponse
							.setMessage("Error in saving wsdl name :-\n"
									+ externWSdlDTO.getWsName());
					log.error(
							"Error in saving wsdl name :-\n"
									+ externWSdlDTO.getWsName() + "\n", e);
					return objImportServiceResponse;
				}
			}
		}

		log.debug("\n\nSuccessfully saved WS services : ");
		for (WSService jj : successfullySavedWSService) {
			log.debug("jj.getServiceName() == " + jj.getServiceName()
					+ " jj.getLinkedWSId() == " + jj.getLinkedWSId());
		}
		objImportServiceResponse
				.setSuccessfullySavedServices(new ArrayList<IAgentService>(
						successfullySavedWSService));
		nameConflictedWSService = getConflictedWSServices(
				listExternalWSServices, listExistingWSServices);

		log.debug("\n\nname Conflicted WS services : ");
		for (WSService jj : nameConflictedWSService) {
			log.debug("jj.getServiceName() == " + jj.getServiceName()
					+ " jj.getLinkedWSId() == " + jj.getLinkedWSId());
		}
		objImportServiceResponse
				.setNameConflictedServices(new ArrayList<IAgentService>(
						nameConflictedWSService));
		return objImportServiceResponse;
	}

	/** ______ External Query Services List from xml ______ */

	private ImportServiceResponse importQueryServices(File xmlFile,
			boolean leaveConflictedDSName) {

		IAgentServiceConfigReader objServiceConfigReader = new IAgentServiceConfigReader();
		ImportServiceResponse objImportServiceResponse = new ImportServiceResponse();

		List<QueryService> successfullySavedQueryService = new ArrayList<QueryService>();
		List<QueryService> nameConflictedQueryService = new ArrayList<QueryService>();

		/** Wsdl name import */
		log.debug("getting external wsdldto list from xml");
		List<DataSource> listExternalDataSources = new ArrayList<DataSource>();
		try {
			listExternalDataSources = objServiceConfigReader
					.getDataSourceListFromXML(xmlFile);
			// if (listExternalDataSources != null) {
			// log.debug("got following jar names");
			// for (DataSource objjj : listExternalDataSources) {
			// log.debug(objjj.getDbAlias() + " >>> " + objjj.getDbID());
			// }
			// }
		} catch (Exception sqlException) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse
					.setMessage("Error in QueryService list from xml:-\n"
							+ sqlException);
			log.error("Error in QueryService list from xml:-\n", sqlException);
			return objImportServiceResponse;
		}
		List<DataSource> listExistingDataSources = new DataSourceDAO()
				.getDataSourceList();
		List<QueryService> listExternalQueryServices;
		List<QueryService> listExistingQueryServices;
		try {
			log.debug("getting external java services list from xml");
			listExternalQueryServices = objServiceConfigReader
					.getQueryServiceListFromXML(xmlFile);
			listExistingQueryServices = new ServiceManagementRPC()
					.getQueryServiceList();
		} catch (Exception sqlException) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse
					.setMessage("Error in Query Service list from xml:-\n"
							+ sqlException);
			log.error("Error in Query Service list from xml:-\n", sqlException);
			return objImportServiceResponse;
		}
		if (listExternalQueryServices == null) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse
					.setMessage("Error in getting Query Service"
							+ " list from xml:-\nNull list found");
			log.debug("Error in getting Query Service"
					+ " list from xml:-\nNull list found");
			return objImportServiceResponse;
		}

		log.debug("External DataSource List :- ");
		/** check DataSource name conflict */
		List<DataSource> conflictedQueryServices = getConflictedDataSourceList(
				listExternalDataSources, listExistingDataSources);
		if (conflictedQueryServices.size() > 0 && !leaveConflictedDSName) {
			objImportServiceResponse
					.setCode(ImportServiceResponse.DATA_SOURCE_NAME_CONFLICT);
			objImportServiceResponse
					.setConflictedDataSourceName(conflictedQueryServices);
			return objImportServiceResponse;
		}
		for (DataSource externDataSource : listExternalDataSources) {
			boolean dataSourceNameExists = dataSourceNameExists(
					externDataSource, listExistingDataSources);
			if (!dataSourceNameExists) {
				/** save DataSource name */
				try {
					int oldDbId = externDataSource.getDbID();
					externDataSource.setDbID(0);
					boolean checkDBConnection = false;
					new ServiceManagementRPC().saveDataSource(externDataSource,
							checkDBConnection);
					listExistingDataSources = new DataSourceDAO()
							.getDataSourceList();
					/** get new DataSource object */
					DataSource savedDataSourceObject = listExistingDataSources
							.get(listExistingDataSources.size() - 1);
					for (QueryService externalWSService : listExternalQueryServices) {
						/** update linked db id with new one */
						if (externalWSService.getLinkedDbID() == oldDbId) {
							externalWSService
									.setLinkedDbID(savedDataSourceObject
											.getDbID());
							ServiceSaveResponse objResponse;
							try {
								objResponse = new ServiceManagementRPC()
										.saveQueryService(externalWSService);
								if (objResponse.getErrorCode() == 0) {
									successfullySavedQueryService
											.add(externalWSService);
								}
							} catch (Exception e) {
								log.error("Error in save service : "
										+ externalWSService.getServiceName(), e);
							}

						}

					}
				} catch (Exception e) {
					objImportServiceResponse
							.setCode(ImportServiceResponse.FAIL);
					objImportServiceResponse
							.setMessage("Error in saving DataSource name :-\n"
									+ externDataSource.getDbUsername() + e);
					log.error("Error in saving DataSource name :-\n"
							+ externDataSource.getDbUsername() + "\n", e);
					return objImportServiceResponse;
				}
			}
		}

		log.debug("\n\nSuccessfully saved Query services : ");
		for (QueryService jj : successfullySavedQueryService) {
			log.debug("jj.getServiceName() == " + jj.getServiceName()
					+ " jj.getLinkedDbID() == " + jj.getLinkedDbID());
		}
		objImportServiceResponse
				.setSuccessfullySavedServices(new ArrayList<IAgentService>(
						successfullySavedQueryService));
		nameConflictedQueryService = getConflictedQueryServices(
				listExternalQueryServices, listExistingQueryServices);

		log.debug("\n\nname Conflicted Query services : ");
		for (QueryService jj : nameConflictedQueryService) {
			log.debug("jj.getServiceName() == " + jj.getServiceName()
					+ " jj.getLinkedDbID() == " + jj.getLinkedDbID());
		}
		objImportServiceResponse
				.setNameConflictedServices(new ArrayList<IAgentService>(
						nameConflictedQueryService));
		return objImportServiceResponse;
	}

	/** ______ External Java Services List from xml ______ */

	public ImportServiceResponse importJavaServices(File xmlFile,
			boolean leaveConflictedJarName, List<String> discardJarNames) {

		IAgentServiceConfigReader objServiceConfigReader = new IAgentServiceConfigReader();
		ImportServiceResponse objImportServiceResponse = new ImportServiceResponse();

		List<JavaService> successfullySavedJavaService = new ArrayList<JavaService>();
		List<JavaService> nameConflictedJavaService = new ArrayList<JavaService>();

		/** jar name import */
		// log.debug("getting external jars list from xml");
		List<JarDTO> listExternalJars = new ArrayList<JarDTO>();
		try {
			listExternalJars = objServiceConfigReader
					.getJarListFromXML(xmlFile);
			// if (listExternalJars != null) {
			// log.debug("got following jar names");
			// for (JarDTO objJarD : listExternalJars) {
			// log.debug(objJarD.getJar_name() + " >>> ");
			// }
			// }
		} catch (Exception sqlException) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse
					.setMessage("Error in Java Service list from xml:-\n"
							+ sqlException);
			log.error("Error in Java Service list from xml:-\n", sqlException);
			return objImportServiceResponse;
		}
		List<JarDTO> listExistingJars = new JarDAO().getJarList();

		/** External java services import */
		List<JavaService> listExternalJavaServices;
		List<JavaService> listExistingJavaServices;
		try {
			// log.debug("getting external java services list from xml");
			listExternalJavaServices = objServiceConfigReader
					.getJavaservicesListFromXML(xmlFile);
			// if (listExternalJavaServices != null) {
			// log.debug("got following java services from xml : --");
			// for (JavaService objJavaSer : listExternalJavaServices) {
			// log.debug(objJavaSer.getServiceName() + " >>> "
			// + objJavaSer.getLinkedJarId() + " >>> "
			// + objJavaSer.getClassName());
			// }
			// }
			listExistingJavaServices = new ServiceManagementRPC()
					.getServiceList();
		} catch (Exception sqlException) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse
					.setMessage("Error in Java Service list from xml:-\n"
							+ sqlException);
			log.error("Error in Java Service list from xml:-\n", sqlException);
			return objImportServiceResponse;
		}
		if (listExternalJavaServices == null
				|| listExistingJavaServices == null) {
			objImportServiceResponse.setCode(ImportServiceResponse.FAIL);
			objImportServiceResponse.setMessage("Error in getting Java Service"
					+ " list from specified database:-\nNull list found");
			log.error("Error in getting Java Service"
					+ " list from specified database:-\nNull list found");
			return objImportServiceResponse;
		}

		log.debug("External Jar List :- ");
		/** check jar name conflict */
		List<JarDTO> conflictedJars = getConflictedJarList(listExternalJars,
				listExistingJars);

		// ///////////////////////////

		/**
		 * Added code to remove jar and its related services and parameters
		 * entries from database
		 * 
		 * and commented code which is used to send response information that
		 * services related to conflicted jars will not be added
		 * */

		// if (conflictedJars.size() > 0 && !leaveConflictedJarName) {
		// objImportServiceResponse
		// .setCode(ImportServiceResponse.JAR_NAME_CONFLICT);
		// objImportServiceResponse.setConflictedJar(conflictedJars);
		// return objImportServiceResponse;
		// }

		/** If there is any jar name confliction with database */
		if (conflictedJars.size() > 0) {
			for (JarDTO conflicJJar : conflictedJars) {
				for (JarDTO exstngJJar : listExistingJars) {
					if (conflicJJar.getJar_name().equalsIgnoreCase(
							exstngJJar.getJar_name())) {
						try {
							if ((discardJarNames == null)
									|| (discardJarNames != null && !discardJarNames
											.contains(exstngJJar.getJar_name()))) {
								/**
								 * If conflicting jar name is not commanded to
								 * discard
								 */
								new ServiceManagementRPC()
										.deleteJarName(exstngJJar.getJar_id());
								// listExistingJars.remove(exstngJJar);
							} else {
								if (listExternalJars.contains(conflicJJar)) {
									listExternalJars.remove(conflicJJar);
								}
							}
						} catch (Throwable e) {
							log.error("Error in deleting jar with name : "
									+ exstngJJar.getJar_name(), e);
							if (listExternalJars.contains(conflicJJar)) {
								listExternalJars.remove(conflicJJar);
							}
						}

					}
				}
			}
		}
		listExistingJars = new JarDAO().getJarList();
		// ////////////////////////////

		for (JarDTO externJAR : listExternalJars) {
			boolean jarNameExists = jarNameExists(externJAR, listExistingJars);
			if (!jarNameExists) {
				/** save jar name */
				try {
					// log.debug("Saving External Jar : "
					// + externJAR.getJar_name());
					int oldJarId = externJAR.getJar_id();
					new ServiceManagementRPC().saveJarName(externJAR
							.getJar_name());
					log.debug("Successfully Saved External Jar : "
							+ externJAR.getJar_name());
					listExistingJars = new JarDAO().getJarList();
					/** get new jarDTO object */
					JarDTO savedJarObject = listExistingJars
							.get(listExistingJars.size() - 1);
					// log.debug("New jar id of Saved External Jar : "
					// + externJAR.getJar_name() + " is : === "
					// + savedJarObject.getJar_id());
					for (JavaService externalJavaService : listExternalJavaServices) {
						/** update linked jar id with new one */
						if (externalJavaService.getLinkedJarId() == oldJarId) {
							externalJavaService.setLinkedJarId(savedJarObject
									.getJar_id());
							ServiceSaveResponse objResponse;
							try {
								objResponse = new ServiceManagementRPC()
										.saveService(externalJavaService);
								if (objResponse.getErrorCode() == 0) {
									successfullySavedJavaService
											.add(externalJavaService);
								}
							} catch (Exception e) {
								log.error("Error in save service : "
										+ externalJavaService.getServiceName(),
										e);
							}

						}

					}
				} catch (Exception e) {
					objImportServiceResponse
							.setCode(ImportServiceResponse.FAIL);
					objImportServiceResponse
							.setMessage("Error in saving jar name :-\n"
									+ externJAR.getJar_name() + e);
					log.error(
							"Error in saving jar name :-\n"
									+ externJAR.getJar_name() + "\n", e);
					return objImportServiceResponse;
				}
			}
		}

		log.debug("\n\nSuccessfully saved java services : ");
		for (JavaService objJavaService : successfullySavedJavaService) {
			log.debug("ServiceName == " + objJavaService.getServiceName()
					+ " LinkedJarId == " + objJavaService.getLinkedJarId());
		}
		objImportServiceResponse
				.setSuccessfullySavedServices(new ArrayList<IAgentService>(
						successfullySavedJavaService));
		nameConflictedJavaService = getConflictedJavaServices(
				listExternalJavaServices, listExistingJavaServices);
		log.debug("\n\nname Conflicted java services : ");
		for (JavaService objJavaService : nameConflictedJavaService) {
			log.debug("ServiceName == " + objJavaService.getServiceName()
					+ " LinkedJarId == " + objJavaService.getLinkedJarId());
		}
		objImportServiceResponse
				.setNameConflictedServices(new ArrayList<IAgentService>(
						nameConflictedJavaService));
		return objImportServiceResponse;
	}
}
