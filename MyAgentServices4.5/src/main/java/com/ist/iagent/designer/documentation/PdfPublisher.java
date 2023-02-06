package com.ist.iagent.designer.documentation;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;


public class PdfPublisher{

	private static final Logger log=Logger.getLogger(PdfPublisher.class);
	
	private File documentationSavePath;
	private Document document;
	private PdfWriter writer;
	private PdfPublishPojo publishPojo;
	private static final int FONT_SIZE=11;
	private Phrase header;
	private Phrase footerVersion;
	private Phrase footerReleaseDate;
	private Paragraph tableOfContent;
	private PageEventHandler pageEventHandler;
	private DocumentationXMLReader docXmlReader;
	private int indexCounter=1;
	private int pageNumCounter=1;
	private ByteArrayOutputStream pdfFileSave;
	private Paragraph productInformation;
	private static final BaseColor HEADING_COLOR=new BaseColor(228,120,35);//orange
	private static final int HEADING_FONT_SIZE=16;
	private String savedDocPath;
	private static final BaseColor TEXT_HEADING_COLOR=new BaseColor(31,73,125);//blue
	
	
	public PdfPublisher(File documentationSavePath)throws Exception{
	
		this.documentationSavePath=documentationSavePath;
		pageEventHandler=new PageEventHandler();
		docXmlReader=new DocumentationXMLReader();
		docXmlReader.setPdfPublisher(this);
		pageEventHandler.setPdfPublisher(this);
		createTableOfContent();

		}
	
	private void createTableOfContent(){
		tableOfContent=new Paragraph();
		tableOfContent.add(createTextHeading("Contents",null,0,0));
		tableOfContent.add(Chunk.NEWLINE);
		tableOfContent.add(Chunk.NEWLINE);
		tableOfContent.setAlignment(Element.ALIGN_LEFT);
	}
	
	/**
	 * add/create product info page in document
	 * e.g. version info, about guide, introduction etc.
	 */
	private void addProductInfo(){
		try{
			if(publishPojo.getDocInfo()==null){
				return;
			}
			
		int indexCount=1; 
		
		productInformation=new Paragraph();
		productInformation.setAlignment(Paragraph.ALIGN_JUSTIFIED);
		// adding version information-----------------------------
		
		Chunk version=createTextHeading(indexCount+". Version Information",null,0,0);
		version.setLocalDestination("Version Information");
		addTableOfContentItem("Version Information");// adding in table of contents
		
		indexCount++;
		
		productInformation.add(version);
		
		
		
		productInformation.add(Chunk.NEWLINE);
	
		PdfPTable table=new PdfPTable(6);
		
		table.addCell(createTableHeader("Version #"));
		table.addCell(createTableHeader("Section"));
		table.addCell(createTableHeader("Change"));
		table.addCell(createTableHeader("Changes Done by"));
		table.addCell(createTableHeader("Reviewer"));
		table.addCell(createTableHeader("Release Date"));
		
		
		table.addCell(publishPojo.getDocInfo().getVersion()==null?"-":publishPojo.getDocInfo().getVersion());
		table.addCell(publishPojo.getDocInfo().getSection()==null?"-":publishPojo.getDocInfo().getSection());
		table.addCell(publishPojo.getDocInfo().getChange()==null?"-":publishPojo.getDocInfo().getChange());
		table.addCell(publishPojo.getDocInfo().getDoneBy()==null?"-":publishPojo.getDocInfo().getDoneBy());
		table.addCell(publishPojo.getDocInfo().getReviewer()==null?"-":publishPojo.getDocInfo().getReviewer());
		table.addCell(publishPojo.getDocInfo().getReleaseDate()==null?"-":publishPojo.getDocInfo().getReleaseDate());
		
		
		productInformation.add(table);
		productInformation.add(Chunk.NEWLINE);
		productInformation.add(Chunk.NEWLINE);
		// adding version information end-----------------------------		
		
		
		
		// adding about this guide data-----------------------------	
		if(publishPojo.getDocInfo().getDocPurpose()!=null && publishPojo.getDocInfo().getDocPurpose().trim().length()>0 ||
				publishPojo.getDocInfo().getDocNote()!=null && publishPojo.getDocInfo().getDocNote().trim().length()>0 ||
				publishPojo.getDocInfo().getDocAudience()!=null && publishPojo.getDocInfo().getDocAudience().trim().length()>0){
		
		Chunk aboutGuide=createTextHeading(indexCount+". About this Guide",null,0,0);
		
		aboutGuide.setLocalDestination("About this Guide");
		addTableOfContentItem("About this Guide");// adding in table of contents
		
		productInformation.add(aboutGuide);
		productInformation.add(Chunk.NEWLINE);
		productInformation.add(Chunk.NEWLINE);
		int subIndex=1;
		
		if(publishPojo.getDocInfo().getDocPurpose()!=null && publishPojo.getDocInfo().getDocPurpose().trim().length()>0){
		
		Chunk docPurpose=createTextHeading(indexCount+"."+subIndex+"    Document Purpose",TEXT_HEADING_COLOR,12,Font.BOLD);
		subIndex++;
		aboutGuide.setLocalDestination("Document Purpose");
		addTableOfContentItem("Document Purpose");
		productInformation.add(docPurpose);
		productInformation.add(Chunk.NEWLINE);

		
		
		productInformation.add(createTextHeading(publishPojo.getDocInfo().getDocPurpose(),new BaseColor(0,0,0),11,Font.NORMAL));
		productInformation.add(Chunk.NEWLINE);
		productInformation.add(Chunk.NEWLINE);
		}
		
		if(publishPojo.getDocInfo().getDocAudience()!=null && publishPojo.getDocInfo().getDocAudience().trim().length()>0){
			
			Chunk docAudience=createTextHeading(indexCount+"."+subIndex+"    Document Audience",TEXT_HEADING_COLOR,12,Font.BOLD);
			subIndex++;
			docAudience.setLocalDestination("Document Audience");
			addTableOfContentItem("Document Audience");
			
			
			productInformation.add(docAudience);
			productInformation.add(Chunk.NEWLINE);
			
			productInformation.add(createTextHeading(publishPojo.getDocInfo().getDocAudience(),new BaseColor(0,0,0),11,Font.NORMAL));
			productInformation.add(Chunk.NEWLINE);
			productInformation.add(Chunk.NEWLINE);
			}
		
		
		if(publishPojo.getDocInfo().getDocNote()!=null && publishPojo.getDocInfo().getDocNote().trim().length()>0){
			
			Chunk docNote=createTextHeading(indexCount+"."+subIndex+"    Note",TEXT_HEADING_COLOR,12,Font.BOLD);
			subIndex++;
			docNote.setLocalDestination("Note");
			addTableOfContentItem("Note");
			
			
			productInformation.add(docNote);
			productInformation.add(Chunk.NEWLINE);
			
			productInformation.add(createTextHeading(publishPojo.getDocInfo().getDocNote(),new BaseColor(0,0,0),11,Font.NORMAL));
			productInformation.add(Chunk.NEWLINE);
			productInformation.add(Chunk.NEWLINE);
			}
		
		indexCount++;
		}
		if(publishPojo.getDocInfo().getIntroduction()!=null && publishPojo.getDocInfo().getIntroduction().trim().length()>0){
			Chunk intro=createTextHeading(indexCount+". Introduction",null,0,0);
			intro.setLocalDestination("Introduction");
			addTableOfContentItem("Introduction");
			productInformation.add(intro);
			productInformation.add(Chunk.NEWLINE);
			productInformation.add(Chunk.NEWLINE);
			productInformation.add(createTextHeading(publishPojo.getDocInfo().getIntroduction(),new BaseColor(0,0,0),11,Font.NORMAL));
			productInformation.add(Chunk.NEWLINE);
			productInformation.add(Chunk.NEWLINE);
			}
		// adding about this guide data end-----------------------------	
		
		
		}catch(Exception e){
			log.error("Error in adding ProductInformation data", e);
		}
		
	}
	
	/**
	 * 
	 * @param text: text to display
	 * @param textColor : textColor (default HEADING_COLOR)
	 * @param fontSize : fontSize (default HEADING_FONT_SIZE)
	 * @param fontStyle : Font.BOLD, FONT.NORMAL etc. (default Font.BOLD)
	 * @return
	 */
	private Chunk createTextHeading(String text,BaseColor textColor,int fontSize,int fontStyle){
		Chunk textHeading=new Chunk(text);
		if(textColor!=null){
		textHeading.getFont().setColor(textColor);
		}else{
			textHeading.getFont().setColor(HEADING_COLOR);
		}
		if(fontSize<=0){
		textHeading.getFont().setSize(HEADING_FONT_SIZE);
		}else{
			textHeading.getFont().setSize(fontSize);
		}
		if(fontStyle<0){
			textHeading.getFont().setStyle(Font.BOLD);
		}else{
			textHeading.getFont().setStyle(fontStyle);
		}
		
		return textHeading;
	}
	
	private PdfPCell createTableHeader(String headerTitle){
		PdfPCell header=new PdfPCell();
		Paragraph headerText=new Paragraph(headerTitle);
		headerText.getFont().setColor(255,255,255);
		headerText.getFont().setStyle(Font.BOLD);
		
		header.setBackgroundColor(TEXT_HEADING_COLOR);
		
		header.addElement(headerText);
		return header;
	}
	
	private void addTableOfContentItem(String displayText){
		Chunk item=new Chunk(indexCounter+".      ");
		item.append(displayText);
		item.setLocalGoto(displayText);
		Paragraph p=new Paragraph();
		p.add(item);
		tableOfContent.add(p);
		indexCounter++;
	}
	
	
	private void setHeaderTitle(String headerTitle) throws Exception{
		header=new Phrase();
		Paragraph headerPara=new Paragraph();
		headerPara.getFont().setSize(12);
		headerPara.getFont().setColor(166, 166, 166);
		headerPara.add(headerTitle);
		headerPara.setAlignment(Element.ALIGN_LEFT);
		header.add(headerPara);
		
	}
	
	private void setFooterNoteVersion(String projectVersion) throws Exception{
		footerVersion=new Phrase();
		Paragraph footerPara=new Paragraph();
		footerPara.getFont().setSize(10);
		footerPara.getFont().setColor(166, 166, 166);
		footerPara.add("Version ");
		footerPara.add(projectVersion);
		footerPara.setAlignment(Element.ALIGN_LEFT);
		footerVersion.add(footerPara);
	}
	private void setFooterNoteReleaseDate(String releaseDate) throws Exception{
		footerReleaseDate=new Phrase();
		Paragraph footerPara=new Paragraph();
		footerPara.getFont().setSize(10);
		footerPara.getFont().setColor(166, 166, 166);
		footerPara.add("Release Date : ");
		footerPara.add(releaseDate);
		footerPara.setAlignment(Element.ALIGN_LEFT);
		footerReleaseDate.add(footerPara);
	}
	
	public void generatePdf() throws Exception{
		
			if(documentationSavePath==null){
				log.error("publishing pdf save path not found");
				return;
			}
			log.debug("Generating Pdf");
			
			if(this.publishPojo!=null){
				log.debug("creating pages");
				
				addProductInfo();
				
				log.debug("publishing folder path :"+documentationSavePath.getAbsolutePath());
				log.debug("publishing file name:"+publishPojo.getFileSaveName());
				
				docXmlReader.readXML(this.publishPojo.getDocumentation().getFileData());
				//docXmlReader.readXML(documentationSavePath.getAbsolutePath()+"/fffff.xml");
				
				//construct page header ----------------------------------
				setHeaderTitle(publishPojo.getDocInfo().getProjectTitle());
				//construct page version footer ----------------------------------
				setFooterNoteVersion(publishPojo.getDocInfo().getVersion());
				//construct page releaseDate footer ----------------------------------
				setFooterNoteReleaseDate(publishPojo.getDocInfo().getReleaseDate());
				
			  document=new Document();//create a document
			  //writer= PdfWriter.getInstance(document,pdfFileSave=new ByteArrayOutputStream(documentationSavePath.getAbsolutePath()+"/"+pojo.getFileSaveName()));
			  writer= PdfWriter.getInstance(document,pdfFileSave=new ByteArrayOutputStream());
			  document.setMargins(36, 36,50, 36);
			  document.setMarginMirroring(true);
			  document.open();  
			
			  
		     pageEventHandler.setDocument(document);
		     pageEventHandler.setFooterReleaseDate(footerReleaseDate);
		     pageEventHandler.setFooterVersion(footerVersion);
		     pageEventHandler.setHeader(header);
		    
		     //add page event handler--------------
		     writer.setPageEvent(pageEventHandler);
		     
		     //create title page
		     createTitlePage();

		     //docXmlReader.setXmlData(pojo.getDocumentaion().fileData);
		     docXmlReader.createPdfDocument();
		    
			closeDocument();
			}
			
		
	}
	
	private Document getDocument(){
		return this.document;
	}
	
	private byte[] getComponentImage(String componentId){
		if(publishPojo!=null){
			log.debug("getting component image :"+componentId);
			if(publishPojo.getDocumentation().getImgs()!=null && publishPojo.getDocumentation().getImgs().containsKey(componentId)){
				log.debug("component image data found  :"+componentId);
				return	publishPojo.getDocumentation().getImgs().get(componentId);		
			}
		
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param compId component id to retrieve component image
	 * @param descLabel component display label
	 * @param desc description of component workflow
	 * @param titleLabel title for contain component (e.g. frame,tabbar) on left side
	 * @param title title for contain component (e.g. frame,tabbar) on right side
	 * @param parentParagraph if null create a new paragraph otherwise append 
	 * @param addToIndex true if you want to add component entry in index(Table of contents)
	 * @return Paragraph instance of paragraph object
	 * @throws Exception
	 */
	
	protected Paragraph addPDFParagraph(String compId,
		String descLabel, String desc,String titleLabel,String title,
		Paragraph parentParagraph,boolean addToIndex) throws Exception{
		
		Paragraph para;
		if(parentParagraph==null){
		para=new Paragraph();
		}else{
		para=parentParagraph;
		}
		log.debug("adding para  "+desc+"   "+title);
		para.setAlignment(Element.ALIGN_JUSTIFIED);
		para.setLeading(15);
		
		//adding image ------------------------------------------------------------------
		if(compId!=null && !compId.equals("null") && compId.trim().length()>0){
		//File imgFile=new File(getComponentImage(compId));
		//if(imgFile.exists() && imgFile.isFile()){
			byte[] imgData=getComponentImage(compId);
			if(imgData!=null){
			Image img=Image.getInstance(imgData);
			if(img!=null){
			img.scalePercent(50);
			img.setBorder(Image.BOX);
			img.setBorderColor(BaseColor.GRAY);
			img.setBorderWidth(0.5f);
			Chunk imgChunk=new Chunk(img,0,0,true);
			
			para.add(imgChunk);
			para.add("\n");
			}	
			}
		//}
		}
		
		//adding title/Label-----------------------------------------------------
		if(title!=null && !title.equals("null") && title.trim().length()>0){
		Chunk titleLabelChunk=new Chunk();
		titleLabelChunk.append(titleLabel+" : ");
		titleLabelChunk.setFont(new Font(FontFactory.getFont(FontFactory.HELVETICA_BOLD,FONT_SIZE,TEXT_HEADING_COLOR)));
		//titleLabelChunk.setUnderline(0.2f, -2f);
		
		Chunk titleChunk=new Chunk();
		titleChunk.append(title+"\n");
		titleChunk.setFont(new Font(FontFactory.getFont(FontFactory.HELVETICA,FONT_SIZE,BaseColor.BLACK)));
		
		titleChunk.setLocalDestination(title);
		
		para.add(titleLabelChunk);
		para.add(titleChunk);
		
		}
		
		//adding description -----------------------------------------------------
		if(desc!=null && !desc.equals("null") && desc.trim().length()>0){
		Chunk descParaLabel=new Chunk();
		
		
		descParaLabel.setFont(new Font(FontFactory.getFont(FontFactory.HELVETICA_BOLD,FONT_SIZE,TEXT_HEADING_COLOR)));
		//descParaLabel.setUnderline(0.2f, -2f);
		descParaLabel.append(descLabel+" : ");
		
		
		Chunk descPara=new Chunk();
		descPara.setFont(new Font(FontFactory.getFont(FontFactory.HELVETICA,FONT_SIZE,BaseColor.BLACK)));
		descPara.append(desc+"\n");
		
		para.add(descParaLabel);
		para.add(descPara);
		}
		para.add("\n");
		para.setKeepTogether(true);
		//if(parentParagraph==null){
		//document.add(para);
		//	addParaGraphToAdd(para);
			
		//}
		if(title!=null && !title.equals("null") && title.trim().length()>0 && addToIndex){
		addTableOfContentItem(title);
		}
		return para;
	}
	public void addParaGraphToAdd(Paragraph para)throws Exception{
		//paragraphsToAdd.add(para);
		document.add(para);
	}
	
	
	private void createTitlePage() throws Exception{
	PdfContentByte cb=this.writer.getDirectContent();
	PdfContentByte underCb=this.writer.getDirectContentUnder();
	document.newPage();
	
	cb.saveState();
	underCb.saveState();
	//----------------bottom background-------------------------
	underCb.moveTo(0,document.top());
	underCb.rectangle(0,0,document.getPageSize().getWidth(),document.getPageSize().getHeight());
	underCb.setColorFill(new BaseColor(31,73,125,0));
	underCb.fill();
	
	if(publishPojo.getDocInfo().getProjectImgData()!=null){
		
	
		Image titlePageImg=Image.getInstance(publishPojo.getDocInfo().getProjectImgData());
	if(titlePageImg!=null){
		//titlePageImg.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight()/2);
		titlePageImg.scaleAbsolute(document.getPageSize().getWidth(), document.getPageSize().getHeight()/2);
		log.debug("-------adding title image-----------");
		titlePageImg.setAbsolutePosition(0,(document.top()-400));
		document.add(titlePageImg);
		
	}
	}
	
	
	//---------------------adding title--------------------
	Phrase titleDisplay=new Phrase();
	Paragraph para=new Paragraph();
	para.add(publishPojo.getDocInfo().getProjectTitle());
	para.getFont().setColor(255,255,255);
	para.getFont().setSize(40);
	para.setAlignment(Element.ALIGN_LEFT);
	titleDisplay.add(para);
	
	
	
	ColumnText.showTextAligned(cb,Element.ALIGN_LEFT, titleDisplay,
			document.leftMargin()+50, document.top()-500, 0);
	
	//show client logo-------------------------------------
	if(publishPojo.getDocInfo().getClientLogoImgData()!=null){
	Image clientImg=Image.getInstance(publishPojo.getDocInfo().getClientLogoImgData());
	if(clientImg!=null){
		clientImg.scaleToFit(document.right(),100);
		clientImg.setBackgroundColor(new BaseColor(255,255,255,255));
		log.debug("-------adding client image-----------");
		clientImg.setAbsolutePosition(document.leftMargin()+50,(document.top()-670));
		document.add(clientImg);
		
	}	
	}
	// add version----------------------------------------
	Chunk versionPara=(Chunk)footerVersion.get(0);
	versionPara.getFont().setColor(255,255,255);
	versionPara.getFont().setSize(14);
	
	ColumnText.showTextAligned(cb,Element.ALIGN_LEFT, footerVersion,
			document.leftMargin()+50, document.top()-700, 0);
		
	
		
		
		
		
	//restore/set footerVersion to default for page footer
		versionPara.getFont().setColor(166,166,166);
		versionPara.getFont().setSize(10);

		//add releaseDate -----------------------------------		
		Chunk releasePara=(Chunk)footerReleaseDate.get(0);
		releasePara.getFont().setColor(255,255,255);
		releasePara.getFont().setSize(14);
			ColumnText.showTextAligned(cb,
				Element.ALIGN_LEFT, footerReleaseDate,
				document.leftMargin()+50, document.top()-720, 0);
			
			//restore/set footerReleaseData to default for page footer
			releasePara.getFont().setColor(166,166,166);
			releasePara.getFont().setSize(10);
	
	underCb.restoreState();
	cb.restoreState();
	document.newPage();
	}
	
	
	
	
	private void closeDocument() throws Exception{
		
		
		document.newPage(); //add a new blank page
		int productInfoPageNo=0;
		int productInfoPageEndNo=0;
		
		int tableOfContentPageNo=0;
		int tableOfContentPageEndNo=0;
		
		writer.setPageEvent(null); //remove pageEvents
		tableOfContentPageNo=writer.getCurrentPageNumber();//store current page number
		
		document.add(tableOfContent);//add table of contents
		
		tableOfContentPageEndNo=writer.getCurrentPageNumber();
		
		
		//ad product information page data
		if(productInformation!=null){
		document.newPage();// add a new blank page
		productInfoPageNo=writer.getCurrentPageNumber();//store current page number
		document.add(productInformation);// add product information data
		productInfoPageEndNo=writer.getCurrentPageNumber();
		}
		
		
		document.close();//close document(pdf) 
		
		//read generated document(pdf)--------
		PdfReader reader=new PdfReader(pdfFileSave.toByteArray());

		//swap pages----------------------------
		//pageNum is index page
		//pageNum2 is product information page 
		reader.selectPages("1,"+tableOfContentPageNo+"-"+tableOfContentPageEndNo+","+productInfoPageNo+"-"+productInfoPageEndNo+","+reader.getNumberOfPages()+",2-"+tableOfContentPageNo);
		
		//document save path---
		savedDocPath=documentationSavePath.getName()+"/"+publishPojo.getFileSaveName()+".pdf";
		
		
		
		String fileSavePath=documentationSavePath.getAbsolutePath()+"/"+publishPojo.getFileSaveName()+".pdf";
		
		log.debug("saved pdf doc path "+fileSavePath);
		PdfStamper stamper =new PdfStamper(reader,new FileOutputStream(fileSavePath));
		
		PdfContentByte foreground;
		//adding header and footer in doucment(pdf)
		for (int i = 2; i <=reader.getNumberOfPages(); i++) {
			foreground = stamper.getOverContent(i);
			addHeaerFooter(foreground,i);
		}
		stamper.close();//saving final document
		
	}
	/**
	 * @return String path of saved pdf document if successfull,else return null
	 */
	public String getSavedPdfPath(){
		if(savedDocPath!=null){
		return savedDocPath.replace('\\', '/');
		}
		return null;
	}
	
	private void addHeaerFooter(PdfContentByte cb,int pageNum){
		
		
				// ----------------------adding header-----------------------------
				
			drawHeaderRect(cb,0,(int)document.top()+10,(int)document.getPageSize().getWidth(),40);
				
			ColumnText.showTextAligned(cb,
			Element.ALIGN_LEFT, header,
			document.leftMargin(), document.top()+25, 0);
			// adding page number----------------------------------------
			Phrase footer=new Phrase();
			footer.setFont(new Font(FontFactory.getFont(FontFactory.HELVETICA,12)));
			footer.getFont().setColor(new BaseColor(255,255,255,0));
			footer.add(String.valueOf(pageNum));
			
			ColumnText.showTextAligned(cb,
	    			Element.ALIGN_LEFT, footer,
	    			document.right(), document.top()+25, 0);
			
			// ----------------------adding header ends-----------------------------
			
			
			// ----------------------adding footer-----------------------------
			
			ColumnText.showTextAligned(cb,
	    			Element.ALIGN_LEFT, footerVersion,
	    			document.leftMargin(), document.bottom() - 10, 0);
			
			ColumnText.showTextAligned(cb,
	    			Element.ALIGN_RIGHT, footerReleaseDate,
	    			document.right(), document.bottom() - 10, 0);
			
			
			// ----------------------adding footer end-----------------------------
			}
	
	public void drawHeaderRect(PdfContentByte cb, int x, int y, int w, int h){
		 	cb.saveState();
		 	
	     	cb.moveTo(x, y);
		 	cb.setRGBColorFill(130, 130, 130);
	        cb.setLineWidth(3);
	        cb.rectangle(0f, y,w, h);
	        cb.fill();
	       
	        cb.restoreState();
		}
	
	public static void main(String ar[]){
/*		try{
		PdfPublisher publish=new PdfPublisher(new File("d:/"));
		PdfPublishPojo pojo=new PdfPublishPojo();
		pojo.setFileSaveName("pdfTest.pdf");
		DocumentationInfoPojo infoPojo=new DocumentationInfoPojo();
		infoPojo.setDocPurpose("Document purpose is to offer iVision Genesys setup information.");
		infoPojo.setDocAudience("This document is intended for Implementation users of iVision wallboard for Genesys.");
		infoPojo.setDocNote("NovelVox retains the right to make changes to this ordering guide under the terms and conditions of your NovelVox software contract.");
		infoPojo.setIntroduction("Stat Server tracks information about customer interaction " +
				"networks (contact center, enterprise-wide, or multi-enterprise telephony " +
				"and computer networks) and converts the data accumulated for directory numbers " +
				"(DNs), agents, agent groups, and non-telephony-specific object types, such as e-mail " +
				"and chats sessions, into statistically useful information, and passes these " +
				"calculations to other software applications that request data. For example, Stat " +
				"Server sends data to Universal Routing Server (URS) to inform the URS about agent " +
				"availability. You can also use Stat Server�s numerical statistical values as routing " +
				"criteria. Stat Server provides contact center managers with a wide range of " +
				"information, allowing organizations to maximize the efficiency and flexibility of " +
				"customer interaction networks. Stat Server tracks what is happening at any DN � " +
				"whether it belongs to an agent station, an individual agent who moves between " +
				"stations, an interactive voice response (IVR), or a point in a private branch exchange " +
				"(PBX) used for queuing or routing. For example, for each DN, Stat Server tracks DN" +
				" activity, call activity on a DN, and other relevant derived states, such as how " +
				"long a phone is not in use, how long a call is on hold, how long it takes to dial a" +
				" call, how long a DN is busy with an inbound call, and so forth.");
		pojo.setDocInfo(infoPojo);
		
		publish.generatePdf(pojo);
		publish.closeDocument();
		}catch(Exception e){
			log.error("Error :", e);
		}*/
	//System.out.println("D:\\IST\\iagent4-server\\webapps\\ist\\xml\\documentation".replace('\\','/'));
	}

	public int getPageNumCounter() {
		return pageNumCounter;
	}

	public void setPageNumCounter(int pageNumCounter) {
		this.pageNumCounter = pageNumCounter;
	}

	public PdfPublishPojo getPublishPojo() {
		return publishPojo;
	}

	public void setPublishPojo(PdfPublishPojo publishPojo) {
		this.publishPojo = publishPojo;
	}
	
}
