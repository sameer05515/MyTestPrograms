package com.ist.iagent.designer.documentation;




import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
	 * this class is used to add header, footer and page no. etc.
	 * 
	 */
public class PageEventHandler extends PdfPageEventHelper{

	private Phrase header;
  	private Document document;
  	private Phrase footerVersion;
  	private Phrase footerReleaseDate;
  	private PdfPublisher pdfPublisher;
  
  	
  	
  	
   	 public PageEventHandler(){
   	
   	 }
   	 
   	 public void onSection(PdfWriter writer, Document document, float paragraphPosition, int depth, Paragraph title){
   		 
   	 }
   	 
   	 public void onStartPage(PdfWriter writer, Document document){
   		//pdfPublisher.setPageNumCounter(document.getPageNumber()); 
   	 }
   	 
   	 public void onEndPage(PdfWriter writer, Document document) {
   			/*PdfContentByte cb = writer.getDirectContent();
   			if (document.getPageNumber() > 1) {
   				// ----------------------adding header-----------------------------
   				
   			drawHeaderRect(cb,0,(int)document.top()+10,(int)document.getPageSize().getWidth(),40);
   				
   			ColumnText.showTextAligned(cb,
   			Element.ALIGN_LEFT, header,
   			document.leftMargin(), document.top()+25, 0);
   			// adding page number----------------------------------------
   			Phrase footer=new Phrase();
   			footer.setFont(new Font(FontFactory.getFont(FontFactory.HELVETICA,12)));
   			footer.getFont().setColor(new BaseColor(255,255,255,0));
   			footer.add(String.valueOf(document.getPageNumber()));
   			
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
   			}*/
   		
   			
   			pdfPublisher.setPageNumCounter(document.getPageNumber()+2);
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
   	 
   	 public void onParagraphEnd(PdfWriter writer, Document pdfDocument,float paragraphPosition){
   		 if (pdfDocument.getPageNumber() > 1) {
   		//log.debug("----------------paragraph end"+paragraphPosition+" "+"  "+pdfDocument.getPageSize().getHeight());
   		drawLine(writer.getDirectContent(),
                    pdfDocument.left(), pdfDocument.right(), paragraphPosition+7);
   		 }
   		//pdfPublisher.setPageNumCounter(pdfDocument.getPageNumber());
   	 }
   	 
  
   	 
   	 public void onParagraph(PdfWriter writer, Document pdfDocument,
                float paragraphPosition) {
   	//	pdfPublisher.setPageNumCounter(pdfDocument.getPageNumber());   
        }
   	 public void drawLine(PdfContentByte cb, float x1, float x2, float y) {
            cb.moveTo(x1, y);
            cb.lineTo(x2, y);
            cb.setColorStroke(new BaseColor(200,200,200,1));
            cb.stroke();
        }

	public Phrase getHeader() {
		return header;
	}

	public void setHeader(Phrase header) {
		this.header = header;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Phrase getFooterVersion() {
		return footerVersion;
	}

	public void setFooterVersion(Phrase footerVersion) {
		this.footerVersion = footerVersion;
	}

	public Phrase getFooterReleaseDate() {
		return footerReleaseDate;
	}

	public void setFooterReleaseDate(Phrase footerReleaseDate) {
		this.footerReleaseDate = footerReleaseDate;
	}

	public PdfPublisher getPdfPublisher() {
		return pdfPublisher;
	}

	public void setPdfPublisher(PdfPublisher pdfPublisher) {
		this.pdfPublisher = pdfPublisher;
	}

	
   
	
}

