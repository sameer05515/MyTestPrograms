package com.p.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.p.xml.bo.Link;
import com.p.xml.bo.LinkGroup;

public class DomParserExampleNewVersion implements DomParserVersion {

	// No generics
	// List myEmpls;
	Document dom;

	// private final static String XML_FILE_NAME="employees.xml";
	private static String XML_FILE_NAME = "src/xml/links1.1.xml";

	public DomParserExampleNewVersion(String xml_file_name) throws IOException,
			SAXException, ParserConfigurationException {
		// create a list to hold the employee objects
		// myEmpls = new ArrayList();

		// parse the xml file and get the dom object
		this.XML_FILE_NAME = xml_file_name;
		parseXmlFile();
	}

	public void runExample() {

		// get each employee element and create a Employee object
		// parseDocument();
		List<LinkGroup> linkGroups = fetchAllLinkGroups();

		for (LinkGroup lg : linkGroups) {
			System.out.println(lg);
		}

		// Iterate through the list and print the data
		// printData();

	}

	private Document parseXmlFile() throws IOException, SAXException,
			ParserConfigurationException {
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// try {
		dbf.setValidating(true);
		// Using factory get an instance of document builder
		DocumentBuilder db = dbf.newDocumentBuilder();
		// ////////////////Added code for dtd validation
		db.setErrorHandler(new ErrorHandler() {
			@Override
			public void error(SAXParseException exception) throws SAXException {
				// do something more useful in each of these handlers
				System.err.println("Given xml file "
						+ new File(XML_FILE_NAME).getAbsolutePath()
						+ " is NOT OK for " + "DomParserVersion : "
						+ getVersion() + " : version "
						+ getVersion().getVersionNumber());
				exception.printStackTrace();
				throw exception;
			}

			@Override
			public void fatalError(SAXParseException exception)
					throws SAXException {
				System.err.println("Given xml file "
						+ new File(XML_FILE_NAME).getAbsolutePath()
						+ " is NOT OK for " + "DomParserVersion : "
						+ getVersion() + " : version "
						+ getVersion().getVersionNumber());
				exception.printStackTrace();
				throw exception;
			}

			@Override
			public void warning(SAXParseException exception)
					throws SAXException {
				System.err.println("Given xml file "
						+ new File(XML_FILE_NAME).getAbsolutePath()
						+ " is NOT OK for " + "DomParserVersion : "
						+ getVersion() + " : version "
						+ getVersion().getVersionNumber());
				exception.printStackTrace();
				throw exception;
			}
		});

		// ///////////////

		// parse using builder to get DOM representation of the XML file
		dom = db.parse(XML_FILE_NAME);
		System.out.println("Given xml file "
				+ new File(XML_FILE_NAME).getAbsolutePath() + " is OK for "
				+ "DomParserVersion : " + this.getVersion() + " : version "
				+ this.getVersion().getVersionNumber());
		return dom;

		// } catch (ParserConfigurationException pce) {
		// pce.printStackTrace();
		// throw pce;
		// } catch (SAXException se) {
		// se.printStackTrace();
		// throw se;
		// } catch (IOException ioe) {
		// ioe.printStackTrace();
		// throw ioe;
		// }
	}

	public List<LinkGroup> fetchAllLinkGroups() {
		// get the root elememt

		Element docEle = dom.getDocumentElement();

		// fetchAllLinkGroups(docEle);

		// NodeList nl =
		// docEle.getElementsByTagName(XMLConstatnts.LinkGroup.ROOT
		// .getName());
		/** List of Link groups */
		List<Element> children = getChildElements(XMLConstatnts.LinkGroup.ROOT,
				docEle);
		List<LinkGroup> linkGroups = new ArrayList<LinkGroup>();
		for (Element linkGroupElement : children) {
			LinkGroup linkGroup = getLinkGroup(linkGroupElement);
			linkGroups.add(linkGroup);
		}

		//
		// if (nl != null && nl.getLength() > 0) {
		// for (int i = 0; i < nl.getLength(); i++) {
		// // get the employee element
		// Element el = (Element) nl.item(i);
		// LinkGroup linkGroup = getLinkGroup(el);
		// linkGroups.add(linkGroup);
		//
		// }
		//
		// }
		return linkGroups;
	}

	private LinkGroup getLinkGroup(Element linkGroupElement) {

		String name = linkGroupElement
				.getAttribute(XMLConstatnts.LinkGroup.LINK_GROUP_NAME.getName());
		List<Link> links = getAllLinks(linkGroupElement);
		LinkGroup linkgrp = new LinkGroup(name, links);
		return linkgrp;
	}

	private List<Link> getAllLinks(Element linkGroupElement) {

		String textVal = null;
		List<Link> links = new ArrayList<Link>();
		// /////////
		List<Element> linkElementList = getChildElements(
				XMLConstatnts.LinkGroup.Link.ROOT, linkGroupElement);
		for (Element linkElement : linkElementList) {

			Element urlElement = getChildElements(
					XMLConstatnts.LinkGroup.Link.Url.URL, linkElement).get(0);

			textVal = getCharacterDataFromElement((Element) urlElement);// .getNodeValue();
			Link link = new Link(textVal);
			links.add(link);
		}
		// /////////
		// NodeList nl = linkGroupElement
		// .getElementsByTagName(XMLConstatnts.LinkGroup.Link.ROOT
		// .getName());
		// if (nl != null && nl.getLength() > 0) {
		// for (int i = 0; i < nl.getLength(); i++) {
		// Element el = (Element) nl.item(i);
		// // textVal = el.getFirstChild().getNodeValue();
		// textVal = getCharacterDataFromElement((Element) el
		// .getFirstChild());// .getNodeValue();
		// Link link = new Link(textVal);
		// links.add(link);
		// }
		// }
		return links;
	}

	public List<Element> getChildElements(
			BasicXMLMethodRequired childElementConstant, Element parentElement) {
		List<Element> children = new ArrayList<Element>();
		NodeList nl = parentElement.getElementsByTagName(childElementConstant
				.getName());
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Node node = nl.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element el = (Element) nl.item(i);
					children.add(el);
				}
			}
		}
		return children;
	}

	/**
	 * I take a xml element and the tag name, look for the tag and get the text
	 * content i.e for <employee><name>John</name></employee> xml snippet if the
	 * Element points to employee node and tagName is name I will return John
	 * 
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	/**
	 * Calls getTextValue and returns a int value
	 * 
	 * @param ele
	 * @param tagName
	 * @return
	 */
	private int getIntValue(Element ele, String tagName) {
		// in production application you would catch the exception
		return Integer.parseInt(getTextValue(ele, tagName));
	}

	private String getCharacterDataFromElement(Element f) {

		NodeList list = f.getChildNodes();
		String data;

		for (int index = 0; index < list.getLength(); index++) {
			if (list.item(index) instanceof CharacterData) {
				CharacterData child = (CharacterData) list.item(index);
				data = child.getData();

				if (data != null && data.trim().length() > 0)
					return child.getData();
			}
		}
		return "";
	}

	/**
	 * Iterate through the list and print the content to console
	 */
	// private void printData() {
	//
	// System.out.println("No of Employees '" + myEmpls.size() + "'.");
	//
	// Iterator it = myEmpls.iterator();
	// while (it.hasNext()) {
	// System.out.println(it.next().toString());
	// }
	// }

	public static void main(String[] args) {
		try {
			// create an instance
			DomParserExampleNewVersion dpe = new DomParserExampleNewVersion(
					XML_FILE_NAME);

			System.out.println("DomParserVersion : " + dpe.getVersion()
					+ " : version " + dpe.getVersion().getVersionNumber());
			// call run example
			dpe.runExample();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 
	 * */
	private static enum XMLConstatnts implements BasicXMLMethodRequired {

		ROOT("root", Type.NODE);

		private String name;
		private Type type;

		private XMLConstatnts(String name, Type type) {
			this.name = name;
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public Type getType() {
			return type;
		}

		public static enum LinkGroup implements BasicXMLMethodRequired {

			ROOT("link-group", Type.NODE), LINK_GROUP_NAME("name",
					Type.ATTRIBUTE);

			private String name;
			private Type type;

			private LinkGroup(String name, Type type) {
				this.name = name;
				this.type = type;
			}

			public String getName() {
				return name;
			}

			public Type getType() {
				return type;
			}

			public static enum Link implements BasicXMLMethodRequired {
				ROOT("link", Type.NODE);

				private String name;
				private Type type;

				private Link(String name, Type type) {
					this.name = name;
					this.type = type;
				}

				public String getName() {
					return name;
				}

				public Type getType() {
					return type;
				}

				public static enum Url implements BasicXMLMethodRequired {
					URL("url", Type.CDATA);

					private String name;
					private Type type;

					private Url(String name, Type type) {
						this.name = name;
						this.type = type;
					}

					public String getName() {
						return name;
					}

					public Type getType() {
						return type;
					}
				}

			}
		}
	}

	@Override
	public Version getVersion() {
		// TODO Auto-generated method stub
		return Version.Dracula;
	}

}
