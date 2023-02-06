// 
// Decompiled by Procyon v0.5.36
// 

package com.prem.vocab.build.proj.util;

import com.prem.vocab.build.proj.VocabBuildConstants;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class XMLUtilityImpl
{
    public static final String WEBAPP_XML_KHAJANA_XML = "/home/premendra/git/MyTestPrograms/vocab-khajana/src/main/webapp/xml/khajana.xml";
    String xmlFileName;
    SimpleDateFormat sdf;
    XMLUtility utility;
    
    public XMLUtilityImpl() throws ParserConfigurationException, SAXException, IOException {
        this.xmlFileName = WEBAPP_XML_KHAJANA_XML;
        this.sdf = new SimpleDateFormat("dd-MMMM-yyyy");
        this.utility = null;
        this.utility = XMLUtility.getInstance(this.xmlFileName);
    }
    
    public String saveTicket(final String word, final String type, final List<String> meanings, final List<String> examples) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        final Element rootNode = this.utility.getRootNodeElement();
        final Node wordListElement = this.utility.getNthNodeOfType(VocabBuildConstants.WORD_LIST, rootNode, 0);
        final Element mywordListElement = this.utility.getElement(wordListElement);
        final Element myWordElement = this.utility.createElement(VocabBuildConstants.MY_WORD);
        Element wordElement = this.utility.createElement(VocabBuildConstants.Word.node);
        this.utility.appendCDATASection(wordElement, word, false);
        wordElement = this.utility.setAttribute(VocabBuildConstants.Word.TYPE, wordElement, type, true);
        myWordElement.appendChild(wordElement);
        final Element meaningsElement = this.utility.createElement(VocabBuildConstants.Meanings.node);
        for (final String meaning : meanings) {
            final Element meaningElement = this.utility.createElement(VocabBuildConstants.Meanings.MEANING);
            this.utility.appendCDATASection(meaningElement, meaning, true);
            meaningsElement.appendChild(meaningElement);
        }
        myWordElement.appendChild(meaningsElement);
        final Element examplesElement = this.utility.createElement(VocabBuildConstants.Examples.node);
        for (final String example : examples) {
            final Element exampleElement = this.utility.createElement(VocabBuildConstants.Examples.EXAMPLE);
            this.utility.appendCDATASection(exampleElement, example, true);
            examplesElement.appendChild(exampleElement);
        }
        myWordElement.appendChild(examplesElement);
        mywordListElement.appendChild(myWordElement);
        rootNode.appendChild(mywordListElement);
        this.utility.printToFile();
        return this.utility.getXMLString();
    }
    
    public List<HashMap<String, String>> getAllDescription() {
        final List<HashMap<String, String>> descSet = new ArrayList<HashMap<String, String>>();
        final Element rootNode = this.utility.getRootNodeElement();
        final Node wordListNode = this.utility.getNthNodeOfType(VocabBuildConstants.WORD_LIST, rootNode, 0);
        final Element ticketHistoryListElement = this.utility.getElement(wordListNode);
        final NodeList myWordNodeList = this.utility.getAllNodesOfType(VocabBuildConstants.MY_WORD, ticketHistoryListElement);
        for (int count = 0; count < myWordNodeList.getLength(); ++count) {
            final Node myWordNode = myWordNodeList.item(count);
            final Element myWordElement = this.utility.getElement(myWordNode);
            if (myWordElement != null) {
                final HashMap<String, String> myWordMap = new HashMap<String, String>();
                final Node wordNode = this.utility.getNthNodeOfType(VocabBuildConstants.Word.node, myWordElement, 0);
                final Element wordElement = this.utility.getElement(wordNode);
                String value = this.utility.getAttribute(VocabBuildConstants.Word.TYPE, wordElement);
                myWordMap.put(VocabBuildConstants.Word.TYPE.getName(), value);
                value = this.utility.getCharacterDataFromElement(wordElement);
                myWordMap.put(VocabBuildConstants.Word.node.getName(), value);
                final Node meaningsNode = this.utility.getNthNodeOfType(VocabBuildConstants.Meanings.node, myWordElement, 0);
                final Element meaningsElement = this.utility.getElement(meaningsNode);
                final NodeList meaningNodeList = this.utility.getAllNodesOfType(VocabBuildConstants.Meanings.MEANING, meaningsElement);
                StringBuffer sb = new StringBuffer();
                for (int count2 = 0; count2 < meaningNodeList.getLength(); ++count2) {
                    final Node meaningNode = meaningNodeList.item(count2);
                    final Element meaningElement = this.utility.getElement(meaningNode);
                    if (meaningElement != null) {
                        sb.append(this.utility.getCharacterDataFromElement(meaningElement));
                        if (count2 < meaningNodeList.getLength() - 1) {
                            sb.append("-->");
                        }
                    }
                }
                myWordMap.put(VocabBuildConstants.Meanings.node.getName(), sb.toString());
                final Node examplesNode = this.utility.getNthNodeOfType(VocabBuildConstants.Examples.node, myWordElement, 0);
                final Element examplesElement = this.utility.getElement(examplesNode);
                final NodeList exampleNodeList = this.utility.getAllNodesOfType(VocabBuildConstants.Examples.EXAMPLE, examplesElement);
                sb = new StringBuffer();
                for (int count3 = 0; count3 < exampleNodeList.getLength(); ++count3) {
                    final Node exampleNode = exampleNodeList.item(count3);
                    final Element exampleElement = this.utility.getElement(exampleNode);
                    if (exampleElement != null) {
                        sb.append(this.utility.getCharacterDataFromElement(exampleElement));
                        if (count3 < exampleNodeList.getLength() - 1) {
                            sb.append("-->");
                        }
                    }
                }
                myWordMap.put(VocabBuildConstants.Examples.node.getName(), sb.toString());
                descSet.add(myWordMap);
            }
        }
        return descSet;
    }
}
