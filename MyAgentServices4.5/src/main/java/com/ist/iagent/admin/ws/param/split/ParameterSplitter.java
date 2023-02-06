package com.ist.iagent.admin.ws.param.split;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.admin.db.pojo.ParameterDescDTO;

public class ParameterSplitter {

	private static Logger log = Logger.getLogger(ParameterSplitter.class);

	private List<ParameterDescDTO> inputType = new ArrayList<ParameterDescDTO>();

	public List<ParameterDescDTO> getParameterTypesAsList(
			Class<?>[] parameterTypes) {

		inputType = new ArrayList<ParameterDescDTO>();
		for (int j = 0; j < parameterTypes.length; j++) {
			Class<?> parameterType = parameterTypes[j];
			boolean splittingRequired = splittingRequired(parameterType, 0);
			if (!splittingRequired) {
				ParameterDescDTO pDescDTO = new ParameterDescDTO();
				pDescDTO.setParameterType(parameterType.getName());
				inputType.add(pDescDTO);
			} else {
				splitParametersToAddItToList(parameterType, 0);
			}
		}
		return inputType;
	}

	private void splitParametersToAddItToList(Class<?> parameterClassName,
			int increment) {

		Field[] fields = parameterClassName.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			boolean splittingRequired = splittingRequired(fields[i].getType(),
					increment + 1);
			if (!splittingRequired) {
				ParameterDescDTO pDescDTO = new ParameterDescDTO();
				pDescDTO.setParameterType(fields[i].getType().getName());
				pDescDTO.setParameterName(fields[i].getName());
				inputType.add(pDescDTO);
			} else {
				splitParametersToAddItToList(fields[i].getType(), increment + 1);
			}
		}
	}

	public String getParameterSchema(Class<?>[] parameterTypes) {

		StringBuffer sb = new StringBuffer();
		sb.append(getTabs(1) + "<" + ParameterSchemaReader.PARAMETER_SCHEMA
				+ ">");
		for (int j = 0; j < parameterTypes.length; j++) {
			Class<?> parameterType = parameterTypes[j];
			sb.append("\n" + getTabs(2) + "<" + ParameterSchemaReader.PARAMETER
					+ ">");
			sb.append("\n" + getTabs(3) + "<"
					+ ParameterSchemaReader.PARAMETER_TYPE + ">"
					+ parameterType.getName() + "</"
					+ ParameterSchemaReader.PARAMETER_TYPE + ">");
			boolean splittingRequired = splittingRequired(parameterType, 0);
			sb.append("\n" + getTabs(3) + "<"
					+ ParameterSchemaReader.PARAMETER_COMPLEX + ">"
					+ splittingRequired + "</"
					+ ParameterSchemaReader.PARAMETER_COMPLEX + ">");
			if (splittingRequired) {
				splitParameters(parameterType, 0, sb);
			}
			sb.append("\n" + getTabs(2) + "</"
					+ ParameterSchemaReader.PARAMETER + ">");
		}
		sb.append("\n" + getTabs(1) + "</"
				+ ParameterSchemaReader.PARAMETER_SCHEMA + ">");
		return sb.toString();
	}

	private void splitParameters(Class<?> parameterClassName, int increment,
			StringBuffer sb) {
		Field[] fields = parameterClassName.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			sb.append("\n" + getTabs(4 + increment) + "<"
					+ ParameterSchemaReader.FIELDS + ">");
			boolean splittingRequired = splittingRequired(fields[i].getType(),
					increment + 1);
			sb.append("\n" + getTabs(5 + increment) + "<"
					+ ParameterSchemaReader.FIELD_NAME + ">"
					+ fields[i].getName() + "</"
					+ ParameterSchemaReader.FIELD_NAME + ">");
			sb.append("\n" + getTabs(5 + increment) + "<"
					+ ParameterSchemaReader.FIELD_TYPE + ">"
					+ fields[i].getType().getName() + "</"
					+ ParameterSchemaReader.FIELD_TYPE + ">");
			sb.append("\n" + getTabs(5 + increment) + "<"
					+ ParameterSchemaReader.FIELD_COMPLEX + ">"
					+ splittingRequired + "</"
					+ ParameterSchemaReader.FIELD_COMPLEX + ">");
			if (splittingRequired) {
				splitParameters(fields[i].getType(), increment + 1, sb);
			}
			sb.append("\n" + getTabs(4 + increment) + "</"
					+ ParameterSchemaReader.FIELDS + ">");
		}
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

	private boolean splittingRequired(Class<?> name, int increment) {

		boolean splittingRequired = true;
		/** String name = parameterClassName.getName(); */
		boolean isPrimitive = isPrimitive(name.getName());
		if (isPrimitive) {
			name = getWrapperClass(name.getName());
		}
		if (name == String.class) {
			splittingRequired = false;
		} else if (name == Object.class) {
			splittingRequired = false;
		} else if (name == flex.messaging.io.amf.ASObject.class) {
			splittingRequired = false;
		} else if (name == List.class) {
			splittingRequired = false;
		} else if (name == ArrayList.class) {
			splittingRequired = false;
		} else if (name == Boolean.class) {
			splittingRequired = false;
		} else if (name == Byte.class) {
			splittingRequired = false;
		} else if (name == Character.class) {
			splittingRequired = false;
		} else if (name == Double.class) {
			splittingRequired = false;
		} else if (name == Float.class) {
			splittingRequired = false;
		} else if (name == Integer.class) {
			splittingRequired = false;
		} else if (name == Long.class) {
			splittingRequired = false;
		} else if (name == Short.class) {
			splittingRequired = false;
		} else if (name == Object[].class) {
			splittingRequired = false;
		}
		return splittingRequired;
	}

	private String getTabs(int count) {
		String result = "";
		if (count > 0) {
			while (count > 0) {
				result += "\t";
				count--;
			}
		}
		return result;
	}

	// public static void main(String[] args) {
	//
	// String className = "com.ist.iagent.core.service.ServiceLoaderRPC";
	// try {
	// new ParameterSplitter().analyze(className);
	// } catch (ClassNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public void analyze(String className) throws IOException,
	// ClassNotFoundException {
	//
	// File file = new File(
	// "C:/Documents and Settings/Admin/Desktop/asm/ParameterSplitter.txt");
	// /** if file doesnt exists, then create it */
	// if (!file.exists()) {
	// file.createNewFile();
	// }
	// PrintStream printStream = new PrintStream(new FileOutputStream(file));
	// System.setOut(printStream);
	// System.setErr(printStream);
	// analyzeClass(className);
	// }
	//
	// private void analyzeClass(String className) throws ClassNotFoundException
	// {
	// try {
	//
	// log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>");
	// Class<?> c = Class.forName(className);
	// log.debug("Class == " + c);
	//
	// /** ####---CONSTRUCTORS--##### */
	// log.debug(getTabs(1) + "Constructor List : ");
	// Constructor<?>[] cons = c.getConstructors();
	// for (int i = 0; i < cons.length; i++) {
	// Constructor<?> constructor = cons[i];
	// log.debug(getTabs(2) + "Constructor[" + (i + 1) + "] == "
	// + constructor.getName() + "(");
	// int count = 0;
	// for (Class<?> conss : constructor.getParameterTypes()) {
	// log.debug(conss.getName()
	// + ((count < constructor.getParameterTypes().length - 1) ? " , "
	// : ""));
	// count++;
	// }
	// log.debug(")\n");
	// }
	//
	// /** ####---METHODS--##### */
	// log.debug("\n\n" + getTabs(1) + "Method List");
	// // System.out.println("\n\n" + getTabs(1) + "Method List");
	// Method[] meths = c.getDeclaredMethods();
	// for (int i = 0; i < meths.length; i++) {
	// Method meth = meths[i];
	// int mod = meth.getModifiers();
	// log.debug("\n"
	// /* + getTabs(2) */
	// +
	// "##################################################################################"
	// + "\n" + getTabs(2) + "Method[" + (i + 1) + "] == "
	// + meth.getName() + " $$ is Public == "
	// + Modifier.isPublic(mod));
	// Class<?>[] parameterTypes = meth.getParameterTypes();
	// String parameterSchema = getParameterSchema(parameterTypes);
	//
	// List<ParameterDescDTO> list = getParameterTypesAsList(parameterTypes);
	//
	// log.debug("parameterSchema ==" + " \n" + parameterSchema);
	// log.debug("{");
	// for (ParameterDescDTO pdto : list) {
	// log.debug(pdto.getParameterType() + " , ");
	// }
	// log.debug("}" + "\n Total Length : " + list.size());
	// }
	// log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>");
	// } catch (ClassNotFoundException e) {
	// log.error("ClassNotFound : " + className + " : ", e);
	// throw e;
	// }
	// }
}
