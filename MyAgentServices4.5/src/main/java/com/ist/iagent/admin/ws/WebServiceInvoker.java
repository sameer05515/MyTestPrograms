package com.ist.iagent.admin.ws;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.pojo.ParameterDescDTO;
import com.ist.iagent.admin.db.pojo.WSOperationDTO;
import com.ist.iagent.admin.db.pojo.WSdlDTO;
import com.ist.iagent.admin.ws.param.split.ParameterSplitter;

public class WebServiceInvoker {

	private static Logger log = Logger.getLogger(WebServiceInvoker.class);

	public List<WSOperationDTO> getWSOperations(WSdlDTO objWSdlDTO)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException,
			InstantiationException {

		List<WSOperationDTO> objWsOperationDTOs = new ArrayList<WSOperationDTO>();
		String webServiceClassFullName = objWSdlDTO.getWsPackageName() + "."
				+ objWSdlDTO.getServiceClass();
		String getServicePortServiceName = "get"
				+ objWSdlDTO.getSoapBindingInterface();
		try {
			log.debug("webServiceStartingClassName = "
					+ webServiceClassFullName);
			log.debug("getServicePortServiceName = "
					+ getServicePortServiceName);

			/** loading class dynamically */
			Class objSeriveTestServiceClass = Class
					.forName(webServiceClassFullName);

			Object objSeriveTestService = objSeriveTestServiceClass
					.newInstance();

			/** getting method instance */
			Method m = objSeriveTestServiceClass.getMethod(
					getServicePortServiceName, (Class[]) null);

			Class<?> returnTypeClass = m.getReturnType();

			/** calling method in java using reflection */
			Method[] arrMeth = returnTypeClass.getDeclaredMethods();

			for (int i = 0; i < arrMeth.length; i++) {

				WSOperationDTO objWsOperationDTO = new WSOperationDTO();
				Method mew = arrMeth[i];
				String signature = "" + mew.getName() + " (";
				Class[] methParamTypes = mew.getParameterTypes();
				List<ParameterDescDTO> inputType = new ArrayList<ParameterDescDTO>();
				ParameterSplitter objPSN = new ParameterSplitter();
				inputType = objPSN.getParameterTypesAsList(methParamTypes);
				String parameterSchema = objPSN
						.getParameterSchema(methParamTypes);
				log.debug("\noperation name : " + mew.getName());
				log.debug("\nParameter Schema : " + parameterSchema);

				objWsOperationDTO.setParameterSchema(parameterSchema);
				objWsOperationDTO.setLinkedWSId(objWSdlDTO.getWsId());
				objWsOperationDTO.setOperationName(mew.getName());
				objWsOperationDTO.setInputType(inputType);
				objWsOperationDTOs.add(objWsOperationDTO);
			}
		} catch (InstantiationException ex) {
			log.error("Not able to create Instance of Class"
					+ webServiceClassFullName);
			throw ex;
		} catch (IllegalAccessException ex) {
			log.error("Not able to access Class" + webServiceClassFullName);
			throw ex;
		} catch (ClassNotFoundException ex) {
			log.error("Not able to find Class" + webServiceClassFullName);
			throw ex;
		} catch (NoSuchMethodException ex) {
			log.error("Not able to find Method on class"
					+ webServiceClassFullName);
			throw ex;
		} catch (SecurityException ex) {
			log.error("Security Exception raised in accesing class"
					+ webServiceClassFullName);
			throw ex;
		} catch (IllegalArgumentException ex) {
			log.error("Incorrect supplied arguments");
			throw ex;
		}
		return objWsOperationDTOs;
	}

	public Object invoke(String webServiceStartingClassName,
			String getServicePortServiceName, String methodName,
			Class[] parameterTypes, Object[] arguments)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException,
			InstantiationException {

		try {
			log.debug("webServiceStartingClassName = "
					+ webServiceStartingClassName);
			log.debug("getServicePortServiceName = "
					+ getServicePortServiceName);
			log.debug("methodName = " + methodName);
			log.debug("parameterTypes = " + parameterTypes);
			log.debug("arguments = " + arguments);

			/** loading class dynamically */
			Class objSeriveTestServiceClass = Class
					.forName(webServiceStartingClassName);
			Object objSeriveTestService = objSeriveTestServiceClass
					.newInstance();
			/** getting method instance */
			Method m = objSeriveTestServiceClass.getMethod(
					getServicePortServiceName, (Class[]) null);
			/** calling method in java using reflection */
			Object port = m.invoke(objSeriveTestService, (Object[]) null);
			Method mm = port.getClass().getMethod(methodName, parameterTypes);
			Object response = mm.invoke(port, arguments);

			log.trace(">>>>>> Got Result for service : " + response);
			return response;
		} catch (InstantiationException ex) {
			log.error("Not able to create Instance of Class", ex);
			throw ex;
		} catch (IllegalAccessException ex) {
			log.error("Not able to access Class", ex);
			throw ex;
		} catch (ClassNotFoundException ex) {
			log.error("Not able to find Class", ex);
			throw ex;
		} catch (NoSuchMethodException ex) {
			log.error("Not able to find Method on class", ex);
			throw ex;
		} catch (SecurityException ex) {
			log.error("Security Exception raised", ex);
			throw ex;
		} catch (IllegalArgumentException ex) {
			log.error("Incorrect supplied arguments", ex);
			throw ex;
		} catch (InvocationTargetException ex) {
			log.error("Not able to invoke method ", ex);
			throw ex;
		}
	}

}
