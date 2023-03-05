package com.ist.iagent.admin.ws.param.split;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.ist.iagent.core.service.exception.IAgentServiceException;

//import test.SpecificCustomer;

public class ParameterAssembler {

	private static Logger log = Logger.getLogger(ParameterAssembler.class);

	public Object[] wrapArguments(List<Parameter> parameterList,
			Object[] arguments) throws IAgentServiceException,
			ClassNotFoundException, InstantiationException,
			NoSuchMethodException, InvocationTargetException,
			IllegalAccessException {

		List<Object> wrappedArguments = new ArrayList<Object>();
		argumentCounter = 0;
		if (parameterList != null && arguments != null) {
			if (arguments != null && arguments.length > 0) {
				try {
					for (int parammCoount = 0; parammCoount < parameterList
							.size(); parammCoount++) {
						Parameter objParameter = parameterList
								.get(parammCoount);
						Object p = newInstance(isPrimitive(objParameter
								.getType()) ? getWrapperClass(
								objParameter.getType()).getName()
								: objParameter.getType());
						if (objParameter.isComplex()) {
							List<Field> objFields = objParameter.getFields();
							p = wrapValuesInFields(p, objFields, arguments);
							wrappedArguments.add(p);
						} else {
							wrappedArguments.add(arguments[argumentCounter++]);
						}
						log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						log.debug(BeanUtils.describe(p));
					}
				} catch (ClassNotFoundException e) {
					log.error("ClassNotFound : ", e);
					throw e;
				} catch (IllegalAccessException e) {
					log.error("IllegalAccess : ", e);
					throw e;
				} catch (InvocationTargetException e) {
					log.error("Error in InvocationTarget : ", e);
					throw e;
				} catch (NoSuchMethodException e) {
					log.error("NoSuchMethod Error : ", e);
					throw e;
				} catch (InstantiationException e) {
					log.error("Instantiation Error : ", e);
					throw e;
				}
			}
		}
		return wrappedArguments.toArray();
	}

	private int argumentCounter = 0;

	private Object wrapValuesInFields(Object p, List<Field> paramKeFields,
			Object[] arguments) throws IllegalAccessException,
			InvocationTargetException, InstantiationException,
			ClassNotFoundException, NoSuchMethodException, SecurityException {

		if (p != null && paramKeFields != null && arguments != null) {
			for (int i = 0; i < paramKeFields.size(); i++) {
				Field f = paramKeFields.get(i);
				if (!f.isComplex()) {
					BeanUtils.setProperty(p, f.getName(),
							arguments[argumentCounter++]);
				} else {
					Object fieldObject = newInstance(isPrimitive(f.getType()) ? getWrapperClass(
							f.getType()).getName()
							: f.getType());
					fieldObject = wrapValuesInFields(fieldObject, f.getField(),
							arguments);
					BeanUtils.setProperty(p, f.getName(), fieldObject);
				}
			}
		}
		return p;
	}

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
			return Boolean.class;
		} else if (parameterType.equals("byte")) {
			return Byte.class;
		} else if (parameterType.equals("char")) {
			return Character.class;
		} else if (parameterType.equals("double")) {
			return Double.class;
		} else if (parameterType.equals("float")) {
			return Float.class;
		} else if (parameterType.equals("int")) {
			return Integer.class;
		} else if (parameterType.equals("long")) {
			return Long.class;
		} else if (parameterType.equals("short")) {
			return Short.class;
		} else if (parameterType.equals("void")) {
			return Void.class;
		}
		return null;
	}

	private static Object newInstance(String className)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, NoSuchMethodException, SecurityException {

		Object returnObject = null;
		try {
			Class<?> test = Class.forName(className);
			returnObject = test.newInstance();
		} catch (InstantiationException e) {
			log.error("Error in Instantiation : ", e);
			throw e;
		} catch (IllegalAccessException e) {
			log.error("Error in Access : ", e);
			throw e;
		} catch (ClassNotFoundException e) {
			log.error("Error in Finding Class : ", e);
			throw e;
		}
		return returnObject;
	}
}
