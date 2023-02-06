package com.ist.iagent.core.service.util;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import flex.messaging.io.amf.ASObject;

public class ISuiteObjectUtil {

	public static final String VALUE = "value";

	public static final String OBJECT = "Object";

	public static final String LIST = "List";

	public static final String KEY = "key";

	public static final String MAP_VALUE = "value";

	public static final String OBJECT_FIELD = "Field";

	public static final String OBJECT_FIELD_NAME = "name";

	public static final String OBJECT_FIELD_TYPE = "type";

	private static final Logger log = Logger.getLogger(ISuiteObjectUtil.class);

	public static String convertToXMLString(Object value)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException, SecurityException,
			InvocationTargetException {

		if (value != null) {
			if (isPrimitive(value.getClass().getName())) {
				return "<" + VALUE + ">" + value + "</" + VALUE + ">";
			}
			if (value instanceof Collection) {
				return convertCollectionObjectToXMLString((Collection<?>) value);
			} else if (value.getClass().isArray()) {
				return convertCollectionObjectToXMLString(getListFromArray(value));
			} else if (value instanceof Map) {
				return convertMapObjectToXMLString((Map<?, ?>) value);
			} else {
				return convertObjectToXMLString(value);
			}
		}
		return "<" + VALUE + ">" + "null" + "</" + VALUE + ">";
	}

	private static String convertObjectToXMLString(Object obj)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException, SecurityException,
			InvocationTargetException {

		if (obj == null) {
			return "<" + VALUE + ">" + "null" + "</" + VALUE + ">";
		}
		StringBuffer sb = new StringBuffer();
		Class<?> c = obj.getClass();
		sb.append("<" + OBJECT + ">");
		Field feilds[] = c.getDeclaredFields();

		for (Field field : feilds) {

			StringBuffer sbField = new StringBuffer();
			try {
				sbField.append("<" + OBJECT_FIELD + ">");
				sbField.append("<" + OBJECT_FIELD_NAME + ">")
						.append(field.getName())
						.append("</" + OBJECT_FIELD_NAME + ">");
				if (field.getType().isArray()) {
					sbField.append("<" + OBJECT_FIELD_TYPE + ">")
							.append("array")
							.append("</" + OBJECT_FIELD_TYPE + ">");
				} else {
					sbField.append("<" + OBJECT_FIELD_TYPE + ">")
							.append(field.getType().getName())
							.append("</" + OBJECT_FIELD_TYPE + ">");
				}
				sbField.append("<" + VALUE + ">");
				if (isPrimitive((field.getType().getName()))) {
					sbField.append(BeanUtils.getProperty(obj, field.getName()));
				} else {
					Object value = BeanUtilsBean.getInstance()
							.getPropertyUtils()
							.getNestedProperty(obj, field.getName());
					if (value != null) {
						sbField.append(convertToXMLString(value));
					} else {
						// sb.append("<" + VALUE + ">" + "null" + "</" + VALUE +
						// ">");
						sbField.append("null");
					}
				}
				sbField.append("</" + VALUE + ">");
				sbField.append("</" + OBJECT_FIELD + ">");

				sb.append(sbField);
			} catch (Exception e) {
			}

		}
		sb.append("</" + OBJECT + ">");
		return sb.toString();
	}

	private static String convertMapObjectToXMLString(Map<?, ?> mapObject)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException, SecurityException,
			InvocationTargetException {

		StringBuffer sb = new StringBuffer();
		sb.append("<" + OBJECT + ">");
		if (mapObject.keySet() != null && mapObject.keySet().size() > 0) {
			for (Object key : mapObject.keySet()) {

				StringBuffer sbField = new StringBuffer();
				// sb.append("<" + KEY + ">" + key + "</" + KEY + ">");
				// sb.append("<" + MAP_VALUE + ">")
				// .append(convertToXMLString(mapObject.get(key)))
				// .append("</" + MAP_VALUE + ">");
				sbField.append("<" + OBJECT_FIELD + ">");

				sbField.append("<" + OBJECT_FIELD_NAME + ">").append(key)
						.append("</" + OBJECT_FIELD_NAME + ">");

				Object mapValueForKey = mapObject.get(key);
				if (mapValueForKey != null) {
					if (mapValueForKey.getClass().isArray()) {
						sbField.append("<" + OBJECT_FIELD_TYPE + ">")
								.append("array")
								.append("</" + OBJECT_FIELD_TYPE + ">");
					} else {
						sbField.append("<" + OBJECT_FIELD_TYPE + ">")
								.append(mapValueForKey.getClass())
								.append("</" + OBJECT_FIELD_TYPE + ">");
					}
				}

				sbField.append("<" + VALUE + ">");
				if (mapValueForKey != null
						&& isPrimitive((mapValueForKey.getClass().getName()))) {
					sbField.append(mapValueForKey + "");
				} else {
					if (mapValueForKey != null) {
						sbField.append(convertToXMLString(mapValueForKey));
					} else {
						sbField.append("null");
					}
				}
				sbField.append("</" + VALUE + ">");
				sbField.append("</" + OBJECT_FIELD + ">");

				sb.append(sbField);
			}
		}
		sb.append("</" + OBJECT + ">");
		return sb.toString();
	}

	private static String convertCollectionObjectToXMLString(
			Collection<?> collectionObject) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, InvocationTargetException {

		StringBuffer sb = new StringBuffer();
		sb.append("<" + LIST + ">");
		Iterator<?> itr = ((Collection<?>) collectionObject).iterator();
		if (itr != null) {
			while (itr.hasNext()) {
				sb.append(convertToXMLString(itr.next()));
			}
		}
		sb.append("</" + LIST + ">");
		return sb.toString();
	}

	private static List<?> getListFromArray(Object obj) {

		if (obj instanceof boolean[]) {

			boolean[] boolArr = (boolean[]) obj;
			List<Boolean> list = new ArrayList<Boolean>();
			for (boolean b : boolArr) {
				list.add(b);
			}
			return list;
		} else if (obj instanceof byte[]) {

			byte[] boolArr = (byte[]) obj;
			List<Byte> list = new ArrayList<Byte>();
			for (byte b : boolArr) {
				list.add(b);
			}
			return list;
		} else if (obj instanceof short[]) {

			short[] boolArr = (short[]) obj;
			List<Short> list = new ArrayList<Short>();
			for (short b : boolArr) {
				list.add(b);
			}
			return list;
		} else if (obj instanceof char[]) {

			char[] boolArr = (char[]) obj;
			List<Character> list = new ArrayList<Character>();
			for (char b : boolArr) {
				list.add(b);
			}
			return list;
		} else if (obj instanceof int[]) {

			int[] boolArr = (int[]) obj;
			List<Integer> list = new ArrayList<Integer>();
			for (int b : boolArr) {
				list.add(b);
			}
			return list;
		} else if (obj instanceof long[]) {

			long[] boolArr = (long[]) obj;
			List<Long> list = new ArrayList<Long>();
			for (long b : boolArr) {
				list.add(b);
			}
			return list;
		} else if (obj instanceof float[]) {

			float[] boolArr = (float[]) obj;
			List<Float> list = new ArrayList<Float>();
			for (float b : boolArr) {
				list.add(b);
			}
			return list;
		} else if (obj instanceof double[]) {

			double[] boolArr = (double[]) obj;
			List<Double> list = new ArrayList<Double>();
			for (double b : boolArr) {
				list.add(b);
			}
			return list;
		} else {

			Object[] arr = (Object[]) obj;
			List<?> list = Arrays.asList(arr);
			return list;
		}
	}

	private static boolean isPrimitive(String parameterType) {

		if ("boolean".equals(parameterType)) {
			return true;
		} else if ("byte".equals(parameterType)) {
			return true;
		} else if ("char".equals(parameterType)) {
			return true;
		} else if ("double".equals(parameterType)) {
			return true;
		} else if ("float".equals(parameterType)) {
			return true;
		} else if ("int".equals(parameterType)) {
			return true;
		} else if ("long".equals(parameterType)) {
			return true;
		} else if ("short".equals(parameterType)) {
			return true;
		} else if ("void".equals(parameterType)) {
			return true;
		} else if ("java.lang.String".equals(parameterType)) {
			return true;
		} else if ("java.lang.Boolean".equals(parameterType)) {
			return true;
		} else if ("java.lang.Byte".equals(parameterType)) {
			return true;
		} else if ("java.lang.Character".equals(parameterType)) {
			return true;
		} else if ("java.lang.Double".equals(parameterType)) {
			return true;
		} else if ("java.lang.Float".equals(parameterType)) {
			return true;
		} else if ("java.lang.Integer".equals(parameterType)) {
			return true;
		} else if ("java.lang.Long".equals(parameterType)) {
			return true;
		} else if ("java.lang.Short".equals(parameterType)) {
			return true;
		} else {
			try {
				Class<?> c = Class.forName(parameterType);
				if (java.util.Date.class.isAssignableFrom(c)) {
					return true;
				}
			} catch (ClassNotFoundException e) {
			}
			return false;
		}
	}

	/**
	 * gives Class object for a java primitive
	 * 
	 * @param parameterType
	 *            String name of parameterType
	 * @return Class object for a java primitive
	 */
	// private static Class<?> getWrapperClass(String parameterType) {
	// if (parameterType.equals("boolean")) {
	// return Boolean.class;
	// } else if (parameterType.equals("byte")) {
	// return Byte.class;
	// } else if (parameterType.equals("char")) {
	// return Character.class;
	// } else if (parameterType.equals("double")) {
	// return Double.class;
	// } else if (parameterType.equals("float")) {
	// return Float.class;
	// } else if (parameterType.equals("int")) {
	// return Integer.class;
	// } else if (parameterType.equals("long")) {
	// return Long.class;
	// } else if (parameterType.equals("short")) {
	// return Short.class;
	// } else if (parameterType.equals("void")) {
	// return Void.class;
	// }
	// return null;
	// }

	// private static Object newInstance(String className)
	// throws InstantiationException, IllegalAccessException,
	// ClassNotFoundException, NoSuchMethodException, SecurityException {
	//
	// Object returnObject = null;
	// try {
	// Class<?> test = Class.forName(className);
	// returnObject = test.newInstance();
	// } catch (InstantiationException e) {
	//
	// throw e;
	// } catch (IllegalAccessException e) {
	//
	// throw e;
	// } catch (ClassNotFoundException e) {
	//
	// throw e;
	// }
	// return returnObject;
	// }

	/**
	 * 
	 * ###############################################
	 * 
	 * Wrapping xml to object
	 * 
	 * ##############################################
	 * 
	 * **/

	public static Object wrap(String xmlString) {

		if (xmlString == null) {
			log.debug("content of given XML is null. Nothing to process.");
			return null;
		}
		if (xmlString.trim().equalsIgnoreCase("")) {
			log.debug("content of given XML is empty String. Nothing to process.");
			return null;
		}
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlString));
			Document doc = docBuilder.parse(is);
			/**
			 * ###### NORMALISE TEXT REPRESENTATION #######
			 */
			doc.getDocumentElement().normalize();
			Element rootElement = doc.getDocumentElement();
			String rootNodeName = rootElement.getNodeName();
			log.debug("Root element : " + rootNodeName);
			// System.out.println("Root element : " + rootNodeName);
			if (rootNodeName != null
					&& !(rootNodeName.trim().equalsIgnoreCase(""))) {
				if (rootNodeName.equalsIgnoreCase(VALUE)) {
					String value = rootElement.getFirstChild().getNodeValue();
					return value;
				} else if (rootNodeName.equalsIgnoreCase(LIST)) {
					List returnList = getList(rootElement);
					return returnList;
				} else if (rootNodeName.equalsIgnoreCase(OBJECT)) {
					ASObject returnObject = getObject(rootElement);
					return returnObject;
				}
			}
		} catch (ParserConfigurationException e) {
			log.error("SAXParseException : ", e);
			System.err.println("SAXParseException : " + e);
		} catch (SAXException e) {
			log.error("SAXException : ", e);
			System.err.println("SAXException : " + e);
		} catch (IOException e) {
			log.error("IOException : ", e);
			System.err.println("IOException : " + e);
		} catch (Throwable t) {
			log.error("Exception : ", t);
			System.err.println("Exception : " + t);
		}
		return null;
	}

	private static List getList(Element listElement) {

		List returnList = null;
		if (listElement == null) {
			return returnList;
		}
		NodeList nodeList = listElement.getChildNodes();

		if (nodeList != null && nodeList.getLength() > 0) {

			log.debug("List contains value tags as child nodes"
					+ "\n nodeList.getLength() === " + nodeList.getLength());

			for (int count = 0; count < nodeList.getLength(); count++) {
				Node fieldNode = nodeList.item(count);
				if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
					if (returnList == null) {

						returnList = new ArrayList();
					}
					Element fieldElemNode = (Element) fieldNode;

					if (VALUE.equalsIgnoreCase(fieldElemNode.getNodeName())) {

						if (fieldElemNode.getFirstChild() != null) {
							returnList.add(fieldElemNode.getFirstChild()
									.getNodeValue());
						} else {
							returnList.add("null");
						}

					} else if (OBJECT.equalsIgnoreCase(fieldElemNode
							.getNodeName())) {

						returnList.add(getObject(fieldElemNode));
					}
				}
			}
		} else {
			log.debug("List contains no value tags as child nodes");
		}
		return returnList;
	}

	private static ASObject getObject(Element asObjectElement) {

		ASObject retObj = null;
		NodeList nodeList = asObjectElement.getElementsByTagName(OBJECT_FIELD);

		if (nodeList != null && nodeList.getLength() > 0) {

			for (int count = 0; count < nodeList.getLength(); count++) {

				Node fieldNode = nodeList.item(count);
				if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {

					if (retObj == null) {
						retObj = new ASObject();
					}
					Element fieldElemNode = (Element) fieldNode;
					Node valueNode = getInnerElement(fieldElemNode, VALUE);
					if (valueNode.getChildNodes() == null
							|| (valueNode.getChildNodes().getLength() <= 1)) {
						String childNodeName = valueNode.getFirstChild()
								.getNodeName();
						if (childNodeName.equalsIgnoreCase(LIST)) {

							// log.debug(getTextValue(fieldElemNode,
							// OBJECT_FIELD_NAME) + "  ");
							retObj.put(
									getTextValue(fieldElemNode,
											OBJECT_FIELD_NAME),
									getList((Element) valueNode.getFirstChild()));
						} else if (childNodeName.equalsIgnoreCase(OBJECT)) {

							// log.debug(getTextValue(fieldElemNode,
							// OBJECT_FIELD_NAME) + "  ");
							retObj.put(
									getTextValue(fieldElemNode,
											OBJECT_FIELD_NAME),
									getObject((Element) valueNode
											.getFirstChild()));
						} else {
							retObj.put(
									getTextValue(fieldElemNode,
											OBJECT_FIELD_NAME),
									getTextValue(fieldElemNode, VALUE));
						}

					}

				}
			}
		} else {
			log.debug("object does not contain field tags as child nodes");
		}
		return retObj;
	}

	private static String getTextValue(Element ele, String tagName) {

		String textVal = null;
		NodeList nodeList = ele.getElementsByTagName(tagName);
		if (nodeList != null && nodeList.getLength() > 0) {
			Element element = (Element) nodeList.item(0);
			textVal = element.getFirstChild().getNodeValue();
		}
		return textVal;
	}

	private static Element getInnerElement(Element ele, String tagName) {

		Element reqElement = null;
		NodeList nodeList = ele.getChildNodes();
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int count = 0; count < nodeList.getLength(); count++) {
				Node fieldNode = nodeList.item(count);
				if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
					if (tagName.equalsIgnoreCase(fieldNode.getNodeName())) {
						return (Element) fieldNode;
					}
				}

			}
		}
		return reqElement;
	}

	private static int getCountOfInnerElement(Element ele) {

		int coun = 0;
		NodeList nodeList = ele.getChildNodes();
		if (nodeList != null && nodeList.getLength() > 0) {
			for (int count = 0; count < nodeList.getLength(); count++) {
				Node fieldNode = nodeList.item(count);
				if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
					coun++;
				}
			}
		}
		return coun;
	}

}
