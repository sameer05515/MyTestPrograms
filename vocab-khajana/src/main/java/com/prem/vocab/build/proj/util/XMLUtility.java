// 
// Decompiled by Procyon v0.5.36
// 

package com.prem.vocab.build.proj.util;

import com.prem.vocab.build.proj.VocabBuildConstants;
import com.prem.vocab.build.proj.XMLBasicMethodsRequired;
import org.apache.log4j.Logger;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class XMLUtility
{
    private static Logger log;
    private static XMLUtility thisInstance;
    private static File xmlFile;
    private Document dom;
    
    static {
        XMLUtility.log = Logger.getLogger((Class)XMLUtility.class);
        XMLUtility.thisInstance = null;
        XMLUtility.xmlFile = null;
    }
    
    private XMLUtility(final String xmlFileName) throws ParserConfigurationException, SAXException, IOException {
        this.dom = null;
        if (xmlFileName != null) {
            XMLUtility.xmlFile = new File(xmlFileName);
            this.createDomFromXMLFile();
        }
    }
    
    public static XMLUtility getInstance(final String xmlFileName) throws ParserConfigurationException, SAXException, IOException {
        if (XMLUtility.thisInstance == null || XMLUtility.xmlFile == null || (XMLUtility.xmlFile != null && !XMLUtility.xmlFile.getAbsolutePath().equals(xmlFileName))) {
            XMLUtility.thisInstance = new XMLUtility(xmlFileName);
        }
        return XMLUtility.thisInstance;
    }
    
    private void createDomFromXMLFile() throws ParserConfigurationException, SAXException, IOException {
        try {
            final InputStream inputData = new FileInputStream(XMLUtility.xmlFile);
            final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            this.dom = docBuilder.parse(inputData);
            this.dom.getDocumentElement().normalize();
        }
        catch (FileNotFoundException e) {
            XMLUtility.log.debug((Object)"FileNotFoundException : ", (Throwable)e);
            throw e;
        }
        catch (ParserConfigurationException e2) {
            XMLUtility.log.debug((Object)"ParserConfigurationException : ", (Throwable)e2);
            throw e2;
        }
        catch (SAXException e3) {
            XMLUtility.log.debug((Object)"SAXException : ", (Throwable)e3);
            throw e3;
        }
        catch (IOException e4) {
            XMLUtility.log.debug((Object)"IOException : ", (Throwable)e4);
            throw e4;
        }
    }
    
    public Element getRootNodeElement() {
        Element rootElemNode = null;
        if (this.dom != null) {
            this.dom.getDocumentElement().normalize();
            final NodeList rootNodeList1 = this.dom.getElementsByTagName(VocabBuildConstants.ROOT.getName());
            final Node rootNode = rootNodeList1.item(0);
            if (rootNode.getNodeType() == 1) {
                rootElemNode = (Element)rootNode;
                return rootElemNode;
            }
        }
        else {
            System.out.println("Document object is null. Returning null root object");
        }
        return rootElemNode;
    }
    
    public NodeList getAllNodesOfType(final XMLBasicMethodsRequired xm, final Element element) {
        NodeList nodeList = null;
        if (element != null && xm != null) {
            nodeList = element.getElementsByTagName(xm.getName());
        }
        else {
            String message = (xm != null) ? "" : "Given XMLBasicMethodsRequired object is null . ";
            message = ((message.length() > 0) ? (String.valueOf(message) + " Also , ") : "");
            message = ((element != null) ? "" : "Given Element object is null . ");
            XMLUtility.log.debug((Object)message);
        }
        return nodeList;
    }
    
    public Node getNthNodeOfType(final XMLBasicMethodsRequired xm, final Element element, final int position) {
        Node node = null;
        final NodeList nodeList = this.getAllNodesOfType(xm, element);
        if (nodeList != null && nodeList.getLength() > position) {
            node = nodeList.item(position);
        }
        else {
            String message = (xm != null) ? "" : "Given XMLBasicMethodsRequired object is null . ";
            message = ((message.length() > 0) ? (String.valueOf(message) + " Also , ") : "");
            message = ((element != null) ? "" : "Given Element object is null . ");
            XMLUtility.log.debug((Object)message);
        }
        return node;
    }
    
    public Element getElement(final Node node) {
        Element element = null;
        if (node != null && node.getNodeType() == 1) {
            element = (Element)node;
        }
        else {
            final String message = (node != null) ? "" : "Given Node object is null . ";
            XMLUtility.log.debug((Object)message);
        }
        return element;
    }
    
    public String getAttribute(final XMLBasicMethodsRequired xm, final Element element) {
        String attribute = null;
        if (element != null && xm != null) {
            attribute = element.getAttribute(xm.getName());
        }
        else {
            String message = (xm != null) ? "" : "Given XMLBasicMethodsRequired object is null . ";
            message = ((message.length() > 0) ? (String.valueOf(message) + " Also , ") : "");
            message = ((element != null) ? "" : "Given Element object is null . ");
            XMLUtility.log.debug((Object)message);
        }
        return attribute;
    }
    
    public int getIntValue(final String givenString) throws NumberFormatException {
        try {
            return (givenString != null && !givenString.trim().equalsIgnoreCase("")) ? Integer.parseInt(givenString) : 0;
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Exception in converting " + givenString + " to integer value.");
            throw e;
        }
    }
    
    public String getCharacterDataFromElement(final Element e) {
        final Node child = e.getFirstChild();
        if (child != null && child instanceof CharacterData) {
            final CharacterData cd = (CharacterData)child;
            return cd.getData();
        }
        return "";
    }
    
    public Element createElement(final XMLBasicMethodsRequired xm) {
        Element element = null;
        if (xm != null && this.dom != null) {
            element = this.dom.createElement(xm.getName());
        }
        return element;
    }
    
    public Element appendComment(final Element element, final String comment) {
        if (this.dom != null && element != null && comment != null && !comment.trim().equalsIgnoreCase("")) {
            element.appendChild(this.dom.createComment(comment.trim()));
        }
        return element;
    }
    
    public Element setAttribute(final XMLBasicMethodsRequired xm, final Element element, final String value, final boolean emptyOrNullValuesAllowed) {
        if (element != null && xm != null) {
            final boolean neitherEmptyNorNull = value != null && !value.trim().equalsIgnoreCase("");
            if (emptyOrNullValuesAllowed || neitherEmptyNorNull) {
                element.setAttribute(xm.getName(), (value != null) ? value.trim() : "null");
            }
        }
        return element;
    }
    
    public Element appendCDATASection(final Element element, final String value, final boolean emptyOrNullValuesAllowed) {
        if (this.dom != null && element != null) {
            final boolean neitherEmptyNorNull = value != null && !value.trim().equalsIgnoreCase("");
            if (emptyOrNullValuesAllowed || neitherEmptyNorNull) {
                element.appendChild(this.dom.createCDATASection((value != null) ? value.trim() : ""));
            }
        }
        return element;
    }
    
    public Element appendText(final Element element, final String value, final boolean emptyOrNullValuesAllowed) {
        if (this.dom != null && element != null) {
            final boolean neitherEmptyNorNull = value != null && !value.trim().equalsIgnoreCase("");
            if (emptyOrNullValuesAllowed || neitherEmptyNorNull) {
                element.appendChild(this.dom.createTextNode((value != null) ? value.trim() : ""));
            }
        }
        return element;
    }
    
    public Document getDom() {
        return this.dom;
    }
    
    public void printToFileOld() throws IOException {
//        try {
//            final OutputFormat format = new OutputFormat(this.dom);
//            format.setIndenting(true);
//            final OutputStream outputStream = new FileOutputStream(XMLUtility.xmlFile);
//            final XMLSerializer serializer = new XMLSerializer(outputStream, format);
//            serializer.serialize(this.dom);
//            outputStream.close();
//        }
//        catch (FileNotFoundException e) {
//            XMLUtility.log.debug((Object)"FileNotFoundException : ", (Throwable)e);
//            throw e;
//        }
//        catch (IOException e2) {
//            XMLUtility.log.debug((Object)"IOException : ", (Throwable)e2);
//            throw e2;
//        }
    }

    public void printToFile() throws IOException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(this.dom);
        final OutputStream outputStream = new FileOutputStream(XMLUtility.xmlFile);
        StreamResult result = new StreamResult(outputStream);

        transformer.transform(source, result);
    }
    
    public String getXMLString() {
        String xmlContent = "<" + VocabBuildConstants.ROOT.getName() + ">" + "</" + VocabBuildConstants.ROOT.getName() + ">";
        try {
            final FileReader fl = new FileReader(XMLUtility.xmlFile);
            final BufferedReader bf = new BufferedReader(fl);
            String str = "";
            final StringBuffer sb = new StringBuffer();
            while ((str = bf.readLine()) != null) {
                sb.append(str);
            }
            fl.close();
            xmlContent = sb.toString();
        }
        catch (Exception e) {
            XMLUtility.log.debug((Object)"Error : ", (Throwable)e);
        }
        return xmlContent;
    }
}
