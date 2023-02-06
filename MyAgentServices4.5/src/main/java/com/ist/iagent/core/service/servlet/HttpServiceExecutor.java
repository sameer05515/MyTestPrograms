package com.ist.iagent.core.service.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.pojo.IAgentService;
import com.ist.iagent.admin.db.pojo.IAgentServiceParameter;
import com.ist.iagent.admin.db.pojo.JavaService;
import com.ist.iagent.admin.db.pojo.QueryService;
import com.ist.iagent.admin.db.pojo.WSService;
import com.ist.iagent.core.service.ServiceLoader;
import com.ist.iagent.core.service.ServiceLoaderRPC;
import com.ist.iagent.core.service.exception.IAgentServiceException;
import com.ist.iagent.core.service.message.IAgentServiceRequest;
import com.ist.iagent.core.service.message.IAgentServiceResponse;
import com.ist.iagent.core.service.message.IAgentSessionObject;
import com.ist.iagent.core.service.util.ISuiteObjectUtil;

import flex.messaging.io.amf.ASObject;

public class HttpServiceExecutor extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger
			.getLogger(HttpServiceExecutor.class);

	private static DateFormat formatter = new SimpleDateFormat(
			"dd-MM-yyyy  HH:mm:ss");

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// resp.setContentType("xml");
		String startTimeStr = formatter.format(new Date());
		long startTime = System.currentTimeMillis();
		PrintWriter out = resp.getWriter();
		String requestXML = req.getParameter("IAgentServiceRequest");
		log.debug("----------------------\ngot requestXML : -\n" + requestXML);
		Object obj = ISuiteObjectUtil.wrap(requestXML);
		if (obj != null) {
			IAgentServiceRequest agentServiceRequest = getIAgentServiceRequest((ASObject) obj);
			try {
				/**
				 * Trying to convert String value to other primitive types if
				 * required
				 */
				agentServiceRequest = resolveArguments(agentServiceRequest);
				/** Calling execute method for given request */
				IAgentServiceResponse objResponse = new ServiceLoaderRPC()
						.execute(agentServiceRequest);
				String res = ISuiteObjectUtil.convertToXMLString(objResponse);
				log.debug("Got Result XML for service name : "
						+ agentServiceRequest.getServiceName()
						+ "\n Total time elapsed : "
						+ (System.currentTimeMillis() - startTime)
						+ " milliseconds ." + "\n start time : " + startTimeStr
						+ "\n end time : " + formatter.format(new Date()));
				out.print(res);
			} catch (Exception e) {
				log.error("Error occured to execute service ", e);
				throw new ServletException("Error occured to execute service ",
						e);
			}
		}
	}

	private IAgentServiceRequest resolveArguments(
			IAgentServiceRequest agentServiceRequest)
			throws IAgentServiceException {

		try {
			String serviceName = agentServiceRequest.getServiceName();
			IAgentService objIAgentService = ServiceLoader.getInstance()
					.getService(agentServiceRequest.getServiceName());
			if (objIAgentService == null) {
				log.error("ServiceName not found : " + serviceName);
				throw new IAgentServiceException("ServiceName not found :- "
						+ serviceName);
			}
			if (objIAgentService instanceof JavaService) {

				JavaService objJavaService = (JavaService) objIAgentService;
				List<IAgentServiceParameter> parameters = objJavaService
						.getInputType();
				Object[] arguments = agentServiceRequest.getParameters();
				arguments = resolveArguments(parameters, arguments);
				agentServiceRequest.setParameters(arguments);
			} else if (objIAgentService instanceof WSService) {

				WSService objWSService = (WSService) objIAgentService;
				List<IAgentServiceParameter> parameters = objWSService
						.getInputType();
				Object[] arguments = agentServiceRequest.getParameters();
				arguments = resolveArguments(parameters, arguments);
				agentServiceRequest.setParameters(arguments);
			} else if (objIAgentService instanceof QueryService) {
				// QueryService objQueryService = (QueryService)
				// objIAgentService;
				/**
				 * Currently no change for QueryServices to be executed using
				 * httpchannel
				 * */
			}
		} catch (Throwable ex) {
			log.error("Error occured while execute a service : " + ex, ex);
			throw new IAgentServiceException("Unable to execute a service : "
					+ ex, ex);
		}
		return agentServiceRequest;
	}

	private Object[] resolveArguments(List<IAgentServiceParameter> parameters,
			Object[] arguments) throws IAgentServiceException {
		/**
		 * verify same number of arguments are received that are needed for this
		 * service.
		 */
		int paramsRequired = parameters == null ? 0 : parameters.size();
		log.trace("Parameters required to run " + paramsRequired);
		int paramsReceived = arguments == null ? 0 : arguments.length;
		if (paramsReceived != paramsRequired) {
			String msg = "System error parameters recieved and required to run a service are not matching."
					+ paramsReceived + "," + paramsRequired;
			log.warn(msg);
			throw new IAgentServiceException(msg);
		}
		for (int i = 0; i < paramsRequired; i++) {
			IAgentServiceParameter agentServiceParameter = parameters.get(i);
			String parameterType = agentServiceParameter.getParameterType();
			if (isPrimitive(parameterType)) {
				Class<?> type = getWrapperClass(parameterType);
				String temp = arguments[i] + "";
				if (type == Boolean.TYPE) {
					arguments[i] = ((temp != null) && (temp
							.equalsIgnoreCase("true")
							|| temp.equalsIgnoreCase("yes") || temp
							.equalsIgnoreCase("ok")));
				} else if (type == Integer.TYPE) {
					arguments[i] = Integer.parseInt(temp);
				} else if (type == Float.TYPE) {
					arguments[i] = Float.parseFloat(temp);
				} else if (type == Double.TYPE) {
					arguments[i] = Double.parseDouble(temp);
				} else if (type == Short.TYPE) {
					arguments[i] = Short.parseShort(temp);
				} else if (type == Long.TYPE) {
					arguments[i] = Long.parseLong(temp);
				}
			}
		}
		return arguments;
	}

	private IAgentServiceRequest getIAgentServiceRequest(ASObject objAsObject) {

		if (objAsObject == null) {
			return null;
		}
		IAgentServiceRequest objRequest = new IAgentServiceRequest();
		if (objAsObject.get("sessionObject") != null) {
			IAgentSessionObject objSessionObject = new IAgentSessionObject();
			ASObject objASSessionObject = (ASObject) objAsObject
					.get("sessionObject");
			objSessionObject.setAgentId(objASSessionObject.get("agentId") + "");
			objSessionObject.setAgentId(objASSessionObject
					.get("customerUniqueId") + "");
			objSessionObject.setAgentId(objASSessionObject.get("callId") + "");
			objRequest.setSessionObject(objSessionObject);
		}
		if (objAsObject.get("serviceName") != null) {
			objRequest.setServiceName(objAsObject.get("serviceName") + "");
		} else {
			objRequest.setServiceName(null);
		}
		List parameters = (List) objAsObject.get("parameters");
		Object[] parameterArray = null;
		if (parameters != null && parameters.size() > 0) {
			parameterArray = new Object[parameters.size()];
			int count = 0;
			for (Object obj : parameters) {
				parameterArray[count] = obj;
				count++;
			}
		}
		objRequest.setParameters(parameterArray);
		return objRequest;
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
	 * 
	 * For testing purpose
	 * 
	 * */
	private void print(String text, String filename) {
		try {
			File file = new File(filename);
			if (!file.exists()) {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				file.createNewFile();
			}
			PrintStream printStream = new PrintStream(
					new FileOutputStream(file));
			printStream.print(text);
			printStream.close();
			log.debug("file has been written");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
