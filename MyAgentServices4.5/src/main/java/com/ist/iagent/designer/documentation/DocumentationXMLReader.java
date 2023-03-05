package com.ist.iagent.designer.documentation;



import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.itextpdf.text.Paragraph;

public class DocumentationXMLReader {

	//private String xmlData;
	private DocumentBuilderFactory builderFactory;
	private DocumentBuilder docBuilder;
	private Document xmlDoc;
	private PdfPublisher pdfPublisher;
	
	private static final Logger log=Logger.getLogger(DocumentationXMLReader.class);
	
	public DocumentationXMLReader() throws Exception{
		builderFactory=DocumentBuilderFactory.newInstance();
		docBuilder=builderFactory.newDocumentBuilder();
	}
	public void readXML(String data) throws Exception{
		if(data!=null){
		InputSource inputStream=new InputSource(new StringReader(data));
		xmlDoc=docBuilder.parse(inputStream);
		}
	}
	
//	public String getXmlData() {
//		return xmlData;
//	}
//	public void setXmlData(String xmlData) {
//		this.xmlData = xmlData;
//	}
	
	
	public void createPdfDocument() throws Exception{
		
			
		if(xmlDoc==null){
			log.error("unable to read xml data");
			return;
		}
		
		log.debug("reading documentation xml");
		
		
		//-----------getting main module data---------------------
		log.debug("getting main module data from xml");
		Element module=(Element)xmlDoc.getElementsByTagName("module").item(0);
		if(module!=null){
		log.debug("main module data found, module name :"+module.getAttribute("id"));
		pdfPublisher.addPDFParagraph(module.getAttribute("id"),
				"Description", module.getAttribute("desc"),
				"Title",module.getAttribute("id"),null,true);
		}
		//-----------getting main module data ends---------------------
		
		
		NodeList list=xmlDoc.getElementsByTagName("containers");
		
		
		for(int i=0;i<list.getLength();i++){
			
		Element frameContainer=(Element)list.item(i);
		
		
		// ----------------------- create frames ------------------------------------
		NodeList frame=frameContainer.getElementsByTagName("container");
		log.debug("frames found "+frame.getLength());
		
		for(int j=0;j<frame.getLength();j++){
			Element frameEle=(Element)frame.item(j);
			log.debug("creating frame documentation :"+frameEle.getAttribute("label"));
			Paragraph para=pdfPublisher.addPDFParagraph(frameEle.getAttribute("id"),
							"Description", frameEle.getAttribute("desc"),
							"Title",frameEle.getAttribute("label"),null,true);
			
			
			
			
			//createParentListener(frameEle,para);
			createListenerPdf(frameEle,para);
			pdfPublisher.addParaGraphToAdd(para);
			addComponentData(frameEle);
			
		}
		// ----------------------- create frames ends------------------------------------
			
		// ----------------------- create TabBars ------------------------------------
		
		NodeList tabframe=frameContainer.getElementsByTagName("tabContainer");
		
		
		for(int j=0;j<tabframe.getLength();j++){
			Element tabFrameEle=(Element)tabframe.item(j);
			Paragraph tabContainer=pdfPublisher.addPDFParagraph(tabFrameEle.getAttribute("id"),
					"Description",tabFrameEle.getAttribute("desc"),
					"Title",tabFrameEle.getAttribute("label"),null,true);
		
			//adding tab container(tabbar) listener----------------
			createListenerPdf(tabFrameEle,tabContainer);
			pdfPublisher.addParaGraphToAdd(tabContainer);
			//adding tab container listener end----------------
			
			
			
			//adding tab element-----------------------------------------
			NodeList tabs=tabFrameEle.getElementsByTagName("tab");
			for(int k=0;k<tabs.getLength();k++){
				
				Element tabsEle=(Element)tabs.item(k);
				Paragraph tabbarPara=pdfPublisher.addPDFParagraph(tabsEle.getAttribute("id"),
						"Description",tabsEle.getAttribute("desc"),
						"Title",tabsEle.getAttribute("label"),null,true);	
				
				//createParentListener(tabsEle,tabbarPara);
				NodeList buttonBar=tabsEle.getElementsByTagName("tabInnerButton");
				
				//adding tab listeners----------------------
				createListenerPdf(tabsEle,tabbarPara);
				pdfPublisher.addParaGraphToAdd(tabbarPara);
				
				
				if(buttonBar==null || buttonBar.getLength()==0){
						addComponentData(tabsEle);
				}else{
					//adding buttonbar element-----------------------------------------
					for(int l=0;l<buttonBar.getLength();l++){
						
						Element buttonBarEle=(Element)buttonBar.item(l);
						Paragraph buttonBarPara=pdfPublisher.addPDFParagraph(buttonBarEle.getAttribute("id"),
								"Description",buttonBarEle.getAttribute("desc"),
								"Title",buttonBarEle.getAttribute("label"),null,true);	
						
						createListenerPdf(buttonBarEle,buttonBarPara);
						pdfPublisher.addParaGraphToAdd(buttonBarPara);
						addComponentData(buttonBarEle);
					}
					
					
				}
			}
			
		}
		// ----------------------- create TabBars------------------------------------
			
		
		
		}
			
		}
	
	
	
		
		
	private void createParentListener(Element ele, Paragraph para){
	
		
		NodeList container=ele.getElementsByTagName("components");
		log.debug("creating parent component listener documentation : ");
		for(int i=0;i<container.getLength();i++){
			
			Element components=(Element)container.item(i);
			
			try {
				createListenerPdf(components,para);
			} catch (Exception e) {
				
				log.error("Error in adding parent component listener documentation.",e);
			}
			
		}
	}
	
	
		
		
		private void addComponentData(Element ele) throws Exception{
		NodeList container=ele.getElementsByTagName("components");
		log.debug("creating components documentation : ");
		for(int i=0;i<container.getLength();i++){
			
			Element components=(Element)container.item(i);
			
			
			//retriving all components ------------------------------
			NodeList component=components.getElementsByTagName("component");
			for(int j=0;j<component.getLength();j++){
			
				Element comp=(Element)component.item(j);
			
				//creating and adding component data----------------------------------------------
				Paragraph para=new Paragraph();
				log.debug("creating component documentation : "+comp.getAttribute("label"));
			pdfPublisher.addPDFParagraph(comp.getAttribute("id"),
						"Description",comp.getAttribute("desc"),
						"Label",comp.getAttribute("label"),para,false);
				
			//creating and adding component events----------------------------------------------
				createEventPdf(comp,para);
				createListenerPdf(comp,para);
				pdfPublisher.addParaGraphToAdd(para);
			}
			
			
			
		}
			
		}
		
		/**
		 * @param component:org.w3c.dom.Element
		 */
		private void createEventPdf(Element component, Paragraph parentParagraph) throws Exception{
			NodeList eventsList=component.getElementsByTagName("Events");
			
			log.debug("creating compoenent Events documentation : ");
			
			for(int j=0;j<eventsList.getLength();j++){
				
				Element events=(Element)eventsList.item(j);
				
				NodeList eventList=events.getElementsByTagName("Event"); 
				
				for(int i=0;i<eventList.getLength();i++){
					Element event=(Element)eventList.item(i);
					
					log.debug("adding component event : "+event.getNodeName()+"  "+event.getAttribute("id"));
					
					addEventParagraph(event.getAttribute("id"),event.getAttribute("desc"),event.getAttribute("label"),parentParagraph);
				}
			}
		}
		
		/**
		 * @param component:org.w3c.dom.Element
		 */
		private void createListenerPdf(Element component, Paragraph parentParagraph) throws Exception{
			NodeList eventsList=component.getElementsByTagName("Listeners");
			
			
			if(eventsList==null || eventsList.getLength()==0){
				return;
			}
			//for(int j=0;j<eventsList.getLength();j++){
				
				Element events=(Element)eventsList.item(0);
				
				NodeList eventList=events.getElementsByTagName("Listener"); 
				
				for(int i=0;i<eventList.getLength();i++){
					Element event=(Element)eventList.item(i);
					
					log.debug("Listener : "+event.getNodeName());
					addListenerParagraph(event.getAttribute("id"),event.getAttribute("desc"),event.getAttribute("label"),parentParagraph);
				}
			//}
		}
		
			private void addListenerParagraph(String compId,String desc,String title,Paragraph parentParagraph) throws Exception{
		
			
			pdfPublisher.addPDFParagraph(null,"Description",desc,"Listener Name",title,parentParagraph,false);
				
			}
		
			private void addEventParagraph(String compId,String desc,String title,Paragraph parentParagraph) throws Exception{
				
				log.debug("addEvent chunk"+compId+"  "+desc+"   "+title);
				pdfPublisher.addPDFParagraph(null,"Description",desc,"Event Name",title,parentParagraph,false);
				

				
			}
			
		public PdfPublisher getPdfPublisher() {
			return pdfPublisher;
		}
		public void setPdfPublisher(PdfPublisher pdfPublisher) {
			this.pdfPublisher = pdfPublisher;
		}
	
}
