package com.ist.iagent.core.service;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import com.ibm.wsdl.factory.WSDLFactoryImpl;
import com.ist.iagent.admin.datasource.DBQueryServiceRPC;
import com.ist.iagent.admin.db.dao.WSdlDAO;
import com.ist.iagent.admin.db.pojo.IAgentService;
import com.ist.iagent.admin.db.pojo.IAgentServiceParameter;
import com.ist.iagent.admin.db.pojo.JavaService;
import com.ist.iagent.admin.db.pojo.ParameterDescDTO;
import com.ist.iagent.admin.db.pojo.QueryParameter;
import com.ist.iagent.admin.db.pojo.QueryService;
import com.ist.iagent.admin.db.pojo.WSService;
import com.ist.iagent.admin.db.pojo.WSdlDTO;
import com.ist.iagent.admin.ws.WebServiceInvoker;
import com.ist.iagent.admin.ws.param.split.Parameter;
import com.ist.iagent.admin.ws.param.split.ParameterAssembler;
import com.ist.iagent.admin.ws.param.split.ParameterSchemaReader;
import com.ist.iagent.core.service.db.dao.ServiceLogDAO;
import com.ist.iagent.core.service.db.pojo.ServiceLog;
import com.ist.iagent.core.service.exception.IAgentServiceException;
import com.ist.iagent.core.service.message.IAgentServiceRequest;
import com.ist.iagent.core.service.message.IAgentSessionObject;
import com.ist.iagent.core.service.property.util.PropertyUtil;
import com.ist.iagent.core.service.util.SoapUtil;

import flex.messaging.io.amf.ASObject;

//import com.ist.iagent.admin.util.PropertyUtil;

public class ServiceExecutor {

	private static final Logger log = Logger.getLogger(ServiceExecutor.class);

	public Object execute(IAgentServiceRequest agentServiceRequest)
			throws Exception {
		long timeInterval = System.currentTimeMillis();
		String status = "";
		Object retObj = null;

		try {

			String serviceName = agentServiceRequest.getServiceName();
			log.debug("Got service request for : " + serviceName);
			IAgentService objIAgentService = ServiceLoader.getInstance()
					.getService(agentServiceRequest.getServiceName());

			if (objIAgentService == null) {
				log.error("ServiceName not found : " + serviceName);
				throw new IAgentServiceException("ServiceName not found :- "
						+ serviceName);
			}

			if (objIAgentService instanceof JavaService) {
				log.debug("JavaService request");
				JavaService objJavaService = (JavaService) objIAgentService;

				retObj = executeJavaService(objJavaService,
						agentServiceRequest.getParameters());
				status = "success";
			} else if (objIAgentService instanceof WSService) {
				log.debug("WSService request");
				WSService objWSService = (WSService) objIAgentService;

				retObj = executeWSService(objWSService,
						agentServiceRequest.getParameters());
				status = "success";
				// retObj = executeWEBService(objWSService,
				// agentServiceRequest.getParameters());

			} else if (objIAgentService instanceof QueryService) {
				log.debug("QueryService request");
				QueryService objQueryService = (QueryService) objIAgentService;

				retObj = executeQueryService(objQueryService,
						agentServiceRequest.getParameters());
				status = "success";
			}
		} catch (Throwable ex) {
			status = ex.getMessage();
			log.error("Unable to execute a service : " + ex, ex);
			throw new IAgentServiceException("Unable to execute a service : "
					+ ex, ex);
		} finally {
			String loggingEnableStr = PropertyUtil.getInstance().getProperty(
					"serviceLoggingEnable");
			boolean serviceLoggingEnable = false;
			if (loggingEnableStr != null
					&& !(loggingEnableStr.trim().equalsIgnoreCase(""))) {
				serviceLoggingEnable = ("true"
						.equalsIgnoreCase(loggingEnableStr.trim())
						|| "yes".equalsIgnoreCase(loggingEnableStr.trim()) || "ok"
						.equalsIgnoreCase(loggingEnableStr.trim()));
			}
			if (serviceLoggingEnable) {
				doLogging(agentServiceRequest, retObj, timeInterval, status);
			}
		}
		return retObj;
	}

	private void doLogging(final IAgentServiceRequest agentServiceRequest,
			final Object retObj, final long timeInterval, final String status) {
		log.trace("About to log service request.");
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					ServiceLog objServiceLog = new ServiceLog();
					IAgentSessionObject so = agentServiceRequest
							.getSessionObject();
					String agentID = so == null ? "NoSessionObject"
							: agentServiceRequest.getSessionObject()
									.getAgentId();
					String callID = so == null ? "NoSessionObject"
							: agentServiceRequest.getSessionObject()
									.getCallId();
					String customerID = so == null ? "NoSessionObject"
							: agentServiceRequest.getSessionObject()
									.getCustomerUniqueId();

					objServiceLog.setAgentID(agentID);
					objServiceLog.setCallID(callID);
					objServiceLog.setCustomerID(customerID);
					objServiceLog.setServiceName(agentServiceRequest
							.getServiceName());

					String request = "service request parameters";
					for (Object param : agentServiceRequest.getParameters()) {
						if (param != null) {
							request += "   " + param.toString();
						}

					}
					objServiceLog.setRequest(request);

					if (retObj != null) {
						objServiceLog.setResponse(retObj.toString() + "");
					} else {
						objServiceLog.setResponse("  ");
					}

					objServiceLog.setStatus(status);

					int diff = (int) (System.currentTimeMillis() - timeInterval);
					objServiceLog.setTimeInterval(diff);

					ServiceLogDAO.saveServiceLog(objServiceLog);
				} catch (Throwable th) {
					log.error(
							"Unable to log a service "
									+ agentServiceRequest.getServiceName(), th);
				}
			}
		});
	}

	/**
	 * member functions related to invoke a java service
	 */

	/**
	 * execute a given javaService with given arguments
	 * 
	 * @param objJavaService
	 *            the object of javaService
	 * @param arguments
	 *            the Object array of arguments
	 * @return Object of result
	 */
	private Object executeJavaService(JavaService objJavaService,
			Object[] arguments) throws IAgentServiceException {
		String msg = "";
		Object retobj = null;
		try {
			Class<?> cls = Class.forName(objJavaService.getClassName());
			log.trace("Preparing Java class for execution "
					+ objJavaService.getClassName());
			Object obj = cls.newInstance();

			List<IAgentServiceParameter> parameterList = objJavaService
					.getInputType();
			int inputParamCount = parameterList.size();
			Class<?> partypes[] = new Class[inputParamCount];

			for (int count = 0; count < partypes.length; count++) {
				String parameterType = parameterList.get(count)
						.getParameterType();
				if (isPrimitive(parameterType)) {
					partypes[count] = getWrapperClass(parameterType);
				} else {
					partypes[count] = Class.forName(parameterType);
				}
			}

			Method meth = cls.getMethod(objJavaService.getMethodName(),
					partypes);
			msg = "Successfully created Method object for method :- "
					+ meth.getName() + " in class :-"
					+ objJavaService.getClassName();
			log.debug(msg);
			retobj = meth.invoke(obj, arguments);
			if (retobj != null) {
				log.trace("Successfully got a result from Method object for method :- "
						+ retobj.toString());
				msg += "\n" + retobj.toString();
			}
			return retobj;
		} catch (ClassNotFoundException e) {
			log.error("Class not found :- ", e);
			throw new IAgentServiceException("Unable to execute a service:"
					+ e.getMessage());
		} catch (InstantiationException e) {
			log.error("Class not instantiated :- ", e);
			throw new IAgentServiceException("Unable to execute a service:"
					+ e.getMessage());
		} catch (IllegalAccessException e) {
			log.error("Illegal Access to Class :- ", e);
			throw new IAgentServiceException("Unable to execute a service:"
					+ e.getMessage());
		} catch (SecurityException e) {
			log.error("Security Exception during invoking method :- ", e);
			throw new IAgentServiceException("Unable to execute a service:"
					+ e.getMessage());
		} catch (NoSuchMethodException e) {
			log.error("not found such method :- ", e);
			throw new IAgentServiceException("Unable to execute a service:"
					+ e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("illegal arguments passed to  method :- ", e);
			throw new IAgentServiceException("Unable to execute a service:"
					+ e.getMessage());
		} catch (InvocationTargetException e) {
			log.error("problem in invoking method :- ", e);
			throw new IAgentServiceException("Unable to execute a service:"
					+ e.getMessage());
		} catch (Throwable e) {
			log.error("Error occured :- ", e);
			throw new IAgentServiceException("Unable to execute a service:"
					+ e.getMessage());
		}

	}

	/**
	 * checks if the parameter is of java primitive type
	 * 
	 * @param parameterType
	 *            String name of parameterType
	 * @return true if given parameterType is a java primitive
	 * */
	private boolean isPrimitive(String parameterType) {
		boolean isPrimitive = false;
		if (parameterType.equals("boolean")) {
			isPrimitive = true;
		} else if (parameterType.equals("byte")) {
			isPrimitive = true;
		} else if (parameterType.equals("char")) {
			isPrimitive = true;
		} else if (parameterType.equals("double")) {
			isPrimitive = true;
		} else if (parameterType.equals("float")) {
			isPrimitive = true;
		} else if (parameterType.equals("int")) {
			isPrimitive = true;
		} else if (parameterType.equals("long")) {
			isPrimitive = true;
		} else if (parameterType.equals("short")) {
			isPrimitive = true;
		} else if (parameterType.equals("void")) {
			isPrimitive = true;
		} else {
			isPrimitive = false;
		}
		return isPrimitive;
	}

	/**
	 * gives Class object for a java primitive
	 * 
	 * @param parameterType
	 *            String name of parameterType
	 * @return Class object for a java primitive
	 * */
	private Class<?> getWrapperClass(String parameterType) {
		if (parameterType.equals("boolean")) {
			return Boolean.TYPE;
		} else if (parameterType.equals("byte")) {
			return Byte.TYPE;
		} else if (parameterType.equals("char")) {
			return Character.TYPE;
		} else if (parameterType.equals("double")) {
			return Double.TYPE;
		} else if (parameterType.equals("float")) {
			return Float.TYPE;
		} else if (parameterType.equals("int")) {
			return Integer.TYPE;
		} else if (parameterType.equals("long")) {
			return Long.TYPE;
		} else if (parameterType.equals("short")) {
			return Short.TYPE;
		} else if (parameterType.equals("void")) {
			return Void.TYPE;
		}
		return null;
	}

	/**
	 * #######################################
	 * 
	 * MEMBER FUNCTIONS RELATED TO INVOKE A WEB SERVICE
	 * 
	 * #######################################
	 */

	/**
	 * execute a given WSService with given arguments
	 * 
	 * @param objJavaService
	 *            the object of WSService
	 * @param arguments
	 *            the Object array of arguments
	 * @return Object of result
	 */
	private Object executeWSService(WSService objWSService, Object[] arguments)
			throws Exception {
		log.debug("executeWEBService for (" + objWSService + ") with ("
				+ arguments + ")");
		if (objWSService == null) {
			String msg = "Unable to execute Web Service due to null service details";
			log.error(msg);
			throw new IAgentServiceException(msg);
		}
		String sName = objWSService.getServiceName();
		String oName = objWSService.getOperationName();

		log.trace("Service:" + sName + ",oName:" + oName);

		/**
		 * verify same number of arguments are received that are needed for this
		 * service.
		 */
		int paramsRequired = objWSService.getInputType() == null ? 0
				: objWSService.getInputType().size();
		log.trace("Parameters required to run " + objWSService.getServiceName()
				+ " " + paramsRequired);
		int paramsReceived = arguments == null ? 0 : arguments.length;

		if (paramsReceived != paramsRequired) {
			String msg = "System error parameters recieved and required to run a service are not matching."
					+ paramsReceived + "," + paramsRequired;
			log.warn(msg);
			throw new IAgentServiceException(msg);
		}
		WSdlDTO wSdlDTO = new WSdlDAO().fetchWSdlDTOForWSDLid(objWSService
				.getLinkedWSId());
		String webServiceClassFullName = wSdlDTO.getWsPackageName() + "."
				+ wSdlDTO.getServiceClass();
		String getServicePortServiceName = "get"
				+ wSdlDTO.getSoapBindingInterface();
		List<ParameterDescDTO> inpType = new ArrayList<ParameterDescDTO>();
		for (IAgentServiceParameter isp : objWSService.getInputType()) {
			inpType.add((ParameterDescDTO) isp);
		}

		// ////////////////////////////////////////////////////
		ParameterSchemaReader objPsReaderFromString = new ParameterSchemaReader();
		List<Parameter> parameterList = objPsReaderFromString
				.getParameters(objWSService.getParameterSchema());
		Class<?> partypes[] = new Class[parameterList.size()];

		for (int count = 0; count < partypes.length; count++) {
			String parameterType = parameterList.get(count).getType();
			if (isPrimitive(parameterType)) {
				partypes[count] = getWrapperClass(parameterType);
			} else {
				partypes[count] = Class.forName(parameterType);
			}
		}
		Object[] wrappedArguments = new ParameterAssembler().wrapArguments(
				parameterList, arguments);

		Object responseMesg = new WebServiceInvoker().invoke(
				webServiceClassFullName, getServicePortServiceName, oName,
				partypes, wrappedArguments);

		return responseMesg;
		// /////////////////////////////////////////////////////

		// //////////////////////////////////////////
		// Class<?> partypes[] = new Class[paramsRequired];
		//
		// for (int count = 0; count < partypes.length; count++) {
		// String parameterType = inpType.get(count).getParameterType();
		// if (isPrimitive(parameterType)) {
		// partypes[count] = getWrapperClass(parameterType);
		// } else {
		// partypes[count] = Class.forName(parameterType);
		// }
		// }
		// Object responseMesg = new WebServiceInvoker().invoke(
		// webServiceClassFullName, getServicePortServiceName, oName,
		// partypes, arguments);
		//
		// return responseMesg;
		// ///////////////////////////////////////////

		// ///////////////////////////////////
		// Object responseMesg = callWebService(wsdl, oName, tNSpace, inpType,
		// arguments, usePrefix);
		// /////////////////////////////////////
	}

	/**
	 * execute a given WSService with given arguments
	 * 
	 * @param objJavaService
	 *            the object of WSService
	 * @param arguments
	 *            the Object array of arguments
	 * @return Object of result
	 */
	@Deprecated
	private Object executeWEBService(WSService objWSService, Object[] arguments)
			throws Exception {
		log.debug("executeWEBService for (" + objWSService + ") with ("
				+ arguments + ")");
		if (objWSService == null) {
			String msg = "Unable to execute Web Service due to null service details";
			log.error(msg);
			throw new IAgentServiceException(msg);
		}
		String sName = objWSService.getServiceName();
		String oName = objWSService.getOperationName();

		log.trace("Service:" + sName + ",oName:" + oName);

		// verify same number of arguments are received that are needed for
		// this service.
		int paramsRequired = objWSService.getInputType() == null ? 0
				: objWSService.getInputType().size();
		log.trace("Parameters required to run " + objWSService.getServiceName()
				+ " " + paramsRequired);
		int paramsReceived = arguments == null ? 0 : arguments.length;

		if (paramsReceived != paramsRequired) {
			String msg = "System error parameters recived and required to run a sercice are not matching."
					+ paramsReceived + "," + paramsRequired;
			log.warn(msg);
			throw new IAgentServiceException(msg);
		}
		WSdlDTO wSdlDTO = new WSdlDAO().fetchWSdlDTOForWSDLid(objWSService
				.getLinkedWSId());
		String wsdl = wSdlDTO.getWsURL();
		boolean usePrefix = "y".equalsIgnoreCase(wSdlDTO.getUsePrefix());
		log.debug("\nusePrefix == " + usePrefix);
		String tNSpace = wSdlDTO.getNameSpace();
		if (tNSpace == null || tNSpace.trim().length() == 0) {
			log.debug("NameSpace for WSDL("
					+ wsdl
					+ ") is not defined, trying to retreive now. This should be populated in DB.");
			tNSpace = getNameSpace(wsdl);
		}
		log.debug("Going to use namespace " + tNSpace);

		List<ParameterDescDTO> inpType = new ArrayList<ParameterDescDTO>();
		for (IAgentServiceParameter isp : objWSService.getInputType()) {
			inpType.add((ParameterDescDTO) isp);
		}
		Object responseMesg = callWebService(wsdl, oName, tNSpace, inpType,
				arguments, usePrefix);
		return responseMesg;
	}

	private String getNameSpace(String wsdl) throws IAgentServiceException {
		Definition implDef = null;

		/** first get the definition object got the WSDL impl */
		try {
			WSDLFactory factory = new WSDLFactoryImpl();
			WSDLReader reader = factory.newWSDLReader();
			implDef = reader.readWSDL(wsdl);

		} catch (WSDLException e) {
			log.error("WSDLException occured : " + e);
			e.printStackTrace();
		} catch (Exception e) {
			log.error("Exception occured : " + e);
			e.printStackTrace();
		}

		if (implDef == null) {
			throw new IAgentServiceException("No WSDL impl definition found.");
		}

		return implDef.getTargetNamespace();

	}

	private void updateParams(SOAPElement bodyElement,
			List<ParameterDescDTO> paramNames, Object[] paramValues,
			boolean usePrefix) throws SOAPException {
		// Add parameters
		int index = 0;
		for (ParameterDescDTO descDTO : paramNames) {
			String key = descDTO.getParameterName();
			SoapUtil.getValueFirstNode(paramValues[index++], bodyElement, key,
					usePrefix);
		}
	}

	private Object callWebService(String destination, String operation,
			String targetNamespace, List<ParameterDescDTO> paramNames,
			Object[] paramValues, boolean usePrefix) throws Exception {
		SOAPConnection connection = null;
		try {
			// First create the connection
			SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory
					.newInstance();
			connection = soapConnFactory.createConnection();

			// Next, create the actual message
			MessageFactory messageFactory = MessageFactory.newInstance();

			SOAPMessage message = messageFactory.createMessage();

			MimeHeaders mimeHeader = message.getMimeHeaders();

			// change header's attribute
			String soapAction = targetNamespace;
			if (!(soapAction.endsWith("\\") || soapAction.endsWith("/"))) {
				soapAction = soapAction + "/";
			}

			mimeHeader.setHeader("SOAPAction", "\"" + soapAction + operation
					+ "\"");
			log.debug("SoapAction " + (targetNamespace + operation));

			// if you want to add new header's attribute use:
			// mimeHeader.addHeader(action, value)

			SOAPPart soapPart = message.getSOAPPart();

			log.trace("got the soap part" + soapPart);
			SOAPEnvelope envelope = soapPart.getEnvelope();
			// This method demonstrates how to set HTTP and SOAP headers.
			// setOptionalHeaders(message, envelope);

			log.trace("got  soap envelope" + envelope);
			// Create and populate the body
			SOAPBody body = envelope.getBody();

			log.trace("got  soap boady" + body);
			// Create the main element and namespace
			SOAPElement bodyElement = body.addChildElement(envelope.createName(
					operation, SoapUtil.PREFIX, targetNamespace));

			log.trace("got  soap boadyElemnt" + bodyElement
					+ "  bodyElement.getPrefix() == " + bodyElement.getPrefix());

			/********************************/
			// Add parameters
			int index = 0;
			for (ParameterDescDTO descDTO : paramNames) {
				String key = descDTO.getParameterName();
				SOAPElement soapBodyElem1 = null;
				if (usePrefix) {
					soapBodyElem1 = bodyElement.addChildElement(key,
							bodyElement.getPrefix());
				} else {
					soapBodyElem1 = bodyElement.addChildElement(key);
				}
				soapBodyElem1.addTextNode(paramValues[index++] + "");
			}

			// /
			// updateParams(bodyElement, paramNames, paramValues, usePrefix);
			/********************************/

			log.debug("parameters updated");
			// Save the message
			message.saveChanges();

			log.trace("message.saveChanges()");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			message.writeTo(out);
			log.debug("Request-:-" + out.toString());

			// Send the message and get the reply
			SOAPMessage reply = connection.call(message, destination);

			soapPart = reply.getSOAPPart();
			envelope = soapPart.getEnvelope();
			body = envelope.getBody();

			Document doc = body.extractContentAsDocument();

			Source source = new DOMSource(doc);
			StringWriter stringWriter = new StringWriter();
			Result result = new StreamResult(stringWriter);
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			transformer.transform(source, result);
			String strBody = stringWriter.getBuffer().toString();

			// ByteArrayOutputStream out = new ByteArrayOutputStream();
			// reply.writeTo(out);
			// String responseMessage = out.toString();
			// log.debug("Response:" + out.toString());
			String response = strBody.substring(strBody.indexOf(">") + 1);
			log.debug("Response:" + response);
			return "<![CDATA[" + response + "]]>";
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SOAPException e) {

				}
			}
		}
	}

	/**
	 * member functions related to invoke a query service
	 * 
	 */

	/**
	 * execute a given QueryService with given arguments
	 * 
	 * @param objQueryService
	 *            the object of QueryService
	 * @param arguments
	 *            the Object array of arguments
	 * @return Object of result
	 */
	private Object executeQueryService(QueryService objQueryService,
			Object[] arguments) throws Exception {
		log.debug("executeQueryService for (" + objQueryService + ") with ("
				+ arguments + ")");
		if (objQueryService == null) {
			String msg = "Unable to execute Query Service due to null service details";
			log.error(msg);
			throw new IAgentServiceException(msg);
		}

		/**
		 * verify same number of arguments are received that are needed for this
		 * service.
		 */
		int paramsRequired = objQueryService.getInputType() == null ? 0
				: objQueryService.getInputType().size();
		log.trace("Parameters required to run "
				+ objQueryService.getServiceName() + " " + paramsRequired);
		int paramsReceived = arguments == null ? 0 : arguments.length;

		if (paramsReceived != paramsRequired) {
			String msg = "System error parameters recived and required to run a service are not matching."
					+ paramsReceived + "," + paramsRequired;
			log.warn(msg);
			throw new IAgentServiceException(msg);
		}

		/**
		 * putting the parameter value in query Parameter Object
		 * */
		int count = 0;
		for (IAgentServiceParameter qp : objQueryService.getInputType()) {
			String paramValue = (String) arguments[count];
			((QueryParameter) qp).setParamValue(paramValue);
			count++;
		}
		/**
		 * Executing the query service
		 * */
		DBQueryServiceRPC dbQ = new DBQueryServiceRPC();
		String queryResultType = objQueryService.getQueryResultType();
		if (queryResultType != null
				&& queryResultType.trim().equalsIgnoreCase(
						DBQueryServiceRPC.QUERY_RESULT_TYPE_VALUE_OBJECT)) {
			ASObject value = dbQ.executeQueryToGetSingleData(objQueryService);
			return value;
		} else if (queryResultType != null
				&& queryResultType.trim().equalsIgnoreCase(
						DBQueryServiceRPC.QUERY_RESULT_TYPE_TABULAR_DATA)) {
			List<ASObject> list = dbQ
					.executeQueryToGetTabularData(objQueryService);
			return list;
		} else {
			throw new IAgentServiceException("Invalid Query Result Type : "
					+ (queryResultType != null ? (queryResultType.trim()
							.equalsIgnoreCase("") ? "empty string"
							: queryResultType) : "null"));
		}
		// String resultString = dbQ.executeQuery(objQueryService);
		//
		// return resultString;
	}

	/**
	 * 
	 * Execute Service via web service channel
	 * 
	 * **/

	// public Object executeWithWsChannel(IAgentServiceRequest
	// agentServiceRequest)
	// throws Exception {
	//
	// // ///////////
	// System.out.println("Starting Execution using web service channel ...");
	// System.out.println("IAgentServiceRequest :: " + agentServiceRequest);
	// if (agentServiceRequest != null)
	// System.out.println("Service to be invoked :: "
	// + agentServiceRequest.getServiceName());
	//
	// // ################
	// String status = "";
	// Object retObj = null;
	// try {
	// String serviceName = agentServiceRequest.getServiceName();
	// log.debug("Got service request for : " + serviceName);
	// IAgentService objIAgentService = ServiceLoader.getInstance()
	// .getService(agentServiceRequest.getServiceName());
	// if (objIAgentService == null) {
	// log.error("ServiceName not found : " + serviceName);
	// throw new IAgentServiceException("ServiceName not found :- "
	// + serviceName);
	// }
	// printParameters(agentServiceRequest.getParameters());
	//
	// if (objIAgentService instanceof JavaService) {
	// log.debug("JavaService request");
	// JavaService objJavaService = (JavaService) objIAgentService;
	//
	// // ---------------------------------
	// List<IAgentServiceParameter> parameterList = objJavaService
	// .getInputType();
	// int inputParamCount = parameterList.size();
	// Class<?> partypes[] = new Class[inputParamCount];
	//
	// for (int count = 0; count < partypes.length; count++) {
	// String parameterType = parameterList.get(count)
	// .getParameterType();
	// if (isPrimitive(parameterType)) {
	// partypes[count] = getWrapperClass(parameterType);
	// } else {
	// partypes[count] = Class.forName(parameterType);
	// }
	// }
	// // ---------------------------------
	//
	// // $$$$$$$$$$$$$$$$$$$$$$$$
	// /**
	// * verify same number of arguments are received that are needed
	// * for this service.
	// */
	// int paramsRequired = objJavaService.getInputType() == null ? 0
	// : objJavaService.getInputType().size();
	// log.trace("Parameters required to run "
	// + objJavaService.getServiceName() + " "
	// + paramsRequired);
	// int paramsReceived = agentServiceRequest.getParameters() == null ? 0
	// : agentServiceRequest.getParameters().length;
	//
	// if (paramsReceived != paramsRequired) {
	// String msg =
	// "System error parameters recieved and required to run a service are not matching."
	// + paramsReceived + "," + paramsRequired;
	// log.warn(msg);
	// throw new IAgentServiceException(msg);
	// }
	// // $$$$$$$$$$$$$$$$$$$$$$$$
	//
	// // /####
	// Object[] parameters = agentServiceRequest.getParameters();
	// List<Object> list = null;
	// ElementNSImpl obj = null;
	// if (parameters != null) {
	// log.debug(" parameters.length == " + parameters.length);
	// if (parameters.length > 0) {
	// if (list == null) {
	// list = new ArrayList<Object>();
	// }
	// for (int argCount = 0; argCount < parameters.length; argCount++) {
	// if (parameters[argCount] instanceof ElementNSImpl) {
	// obj = (ElementNSImpl) parameters[argCount];
	// System.out.println("parameters[" + argCount
	// + "] == " + obj);
	// if (obj != null) {
	// System.out
	// .println("\n\n=======================================");
	// //setGreeting(obj);
	// System.out
	// .println("\n\n=======================================");
	// Class<?> argClass = partypes[argCount];
	// if (argClass == Boolean.TYPE) {
	// list.add(Boolean.getBoolean(obj
	// .getTextContent()));
	// } else if (argClass == Byte.TYPE) {
	// list.add(Byte.valueOf(obj
	// .getTextContent()));
	// } else if (argClass == Character.TYPE) {
	// list.add(obj.getTextContent());
	// } else if (argClass == Double.TYPE) {
	// list.add(Double.parseDouble(obj
	// .getTextContent()));
	// } else if (argClass == Float.TYPE) {
	// list.add(Float.parseFloat(obj
	// .getTextContent()));
	// } else if (argClass == Integer.TYPE) {
	// list.add(Integer.parseInt(obj
	// .getTextContent()));
	// } else if (argClass == Long.TYPE) {
	// list.add(Long.parseLong(obj
	// .getTextContent()));
	// } else if (argClass == Short.TYPE) {
	// list.add(Short.parseShort(obj
	// .getTextContent()));
	// } else if (argClass == String.class) {
	// list.add(obj.getTextContent());
	// } else if (java.util.Map.class
	// .isAssignableFrom(argClass)) {
	// list.add(obj.getTextContent());
	// } else {
	// list.add(obj.getTextContent());
	// }
	// // list.add(obj.getTextContent());
	// }
	// }
	// }
	//
	// }
	// agentServiceRequest.setParameters(list.toArray());
	// }
	// // /@@@@
	//
	// retObj = executeJavaService(objJavaService,
	// agentServiceRequest.getParameters());
	// status = "success";
	// } else if (objIAgentService instanceof WSService) {
	// log.debug("WSService request");
	// WSService objWSService = (WSService) objIAgentService;
	// retObj = executeWSService(objWSService,
	// agentServiceRequest.getParameters());
	// status = "success";
	// } else if (objIAgentService instanceof QueryService) {
	// log.debug("QueryService request");
	// QueryService objQueryService = (QueryService) objIAgentService;
	// retObj = executeQueryService(objQueryService,
	// agentServiceRequest.getParameters());
	// status = "success";
	// }
	// } catch (Throwable ex) {
	// status = ex.getMessage();
	// log.error("Unable to execute a service : " + ex, ex);
	// throw new IAgentServiceException("Unable to execute a service : "
	// + ex, ex);
	// }
	// return retObj;
	// }

	// private void printParameters(Object[] parameter) {
	//
	// StringBuffer sb = new StringBuffer();
	// sb.append("<Request-Parameters>");
	// ElementNSImpl obj = null;
	// if (parameter != null) {
	// log.debug(" parameters.length == " + parameter.length);
	// if (parameter.length > 0) {
	// for (int argCount = 0; argCount < parameter.length; argCount++) {
	// if (parameter[argCount] instanceof ElementNSImpl) {
	// obj = (ElementNSImpl) parameter[argCount];
	// System.out
	// .println("\n=========================\nparameter["
	// + argCount + "] \n");
	// if (obj != null) {
	// sb.append("<parameter>" + printObj(obj)
	// + "</parameter>");
	// } else {
	// System.out.println("\n value is null>>>>>>>>>>>>");
	// sb.append("<parameter>" + "null" + "</parameter>");
	// }
	// }
	// }
	// }
	// }
	// sb.append("</Request-Parameters>");
	// //print(sb.toString(),
	// "C:/Documents and Settings/Admin/Desktop/test.xml");
	// }

	// private String printObj(NodeImpl object) {
	//
	// ElementNSImpl mp = (ElementNSImpl) object;
	// StringBuffer sb = new StringBuffer();
	// sb.append("<Node ").append("localName=\"" + mp.getLocalName() + "\" ")
	// .append("NodeName=\"" + mp.getNodeName() + "\" ")
	// .append("TextContent=\"" + mp.getTextContent() + "\" ")
	// // .append("BaseURI=\"" + mp.getBaseURI() + "\" ")
	// // .append("NamespaceURI=\"" + mp.getNamespaceURI() + "\" ")
	// // .append("NodeType=\"" + mp.getNodeType() + "\" ")
	// // .append("Prefix=\"" + mp.getPrefix() + "\" ")
	// // .append("TagName=\"" + mp.getTagName() + "\" ")
	// // .append("TypeName=\"" + mp.getTypeName() + "\" ")
	// // .append("TypeNamespace=\"" + mp.getTypeNamespace() + "\" ")
	// // .append("UserData=\"" + mp.getUserData() + "\" ")
	// .append(">");
	//
	// System.out.println("--------@@ children start @@-------------------");
	// for (int i = 0; i < mp.getLength(); i++) {
	//
	// System.out.println("---------------------------");
	// NodeImpl nod = (NodeImpl) mp.item(i);
	// if (nod instanceof ElementNSImpl) {
	// System.out.println(" nod " + nod.getLocalName()
	// + " is a ElementNSImpl . ");
	// sb.append(printObj(nod));
	// } else {
	// System.out.println(" nod " + nod.getLocalName()
	// + " is not a ElementNSImpl . ");
	// }
	// System.out.println("nod.getLocalName():" + nod.getLocalName());
	// System.out.println("nod.getNodeName():" + nod.getNodeName());
	// System.out.println("nod.getNodeValue():" + nod.getNodeValue());
	// System.out.println("nod.getTextContent():" + nod.getTextContent());
	// System.out.println("nod.hasChildNodes():" + nod.hasChildNodes());
	// System.out.println("nod.getUserData():" + nod.getUserData());
	// }
	// System.out.println("--------@@ children End @@-------------------");
	// sb.append("</Node>");
	// return sb.toString();
	// }

	// private void print(String text, String filename) {
	// try {
	// File file = new File(filename);
	// if (!file.exists()) {
	// if (!file.getParentFile().exists()) {
	// file.getParentFile().mkdirs();
	// }
	// file.createNewFile();
	// }
	// PrintStream printStream = new PrintStream(
	// new FileOutputStream(file));
	// printStream.print(text);
	// printStream.close();
	// System.out.println("file has been written");
	// } catch (Throwable e) {
	// e.printStackTrace();
	// }
	// }

}
