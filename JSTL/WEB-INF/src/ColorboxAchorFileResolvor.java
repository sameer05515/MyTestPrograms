package com.prem.tags;
 
import java.io.IOException;
 
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.*;
import java.sql.*;
import java.io.*;
 
public class ColorboxAchorFileResolvor extends TagSupport {
    private String base;
    private String traverseSubFolder;
    private String allowedExtentions;
	private String classname;
	private String baseURLPrefix;
     
    @Override
    public int doStartTag() throws JspException {
         
        try {
		
		/////////////////
		String docID = base;//request.getParameter("catId");
		String traverseSubFolder = this.traverseSubFolder;//request.getParameter("travSubFolder");
		String allowedExtentions=this.allowedExtentions;//request.getParameter("allowedExtentions");
		//String classname=this.classname;//request.getParameter("classname");
		//String baseURLPrefix=this.baseURLPrefix;//request.getParameter("baseURLPrefix");
		allowedExtentions=(allowedExtentions!=null)?allowedExtentions.trim().toLowerCase():"";
		classname=(classname!=null)?classname.trim().toLowerCase():"";		
		baseURLPrefix=(baseURLPrefix!=null)?baseURLPrefix.trim():"";
		
		String exts[]= allowedExtentions.split(",");
		listAllowedExtensionsForIndexing=new HashSet<String>();
		for (int i = 0; i < exts.length; i++){
			if (exts[i] != null
						&& !exts[i].trim().equals(""))
					listAllowedExtensionsForIndexing
							.add(exts[i].trim()
									.toLowerCase());
		}
		
		boolean trav=(traverseSubFolder!=null && traverseSubFolder.toLowerCase().equals("false"))?false:true;
		
		///////////////////////
            //Get the writer object for output.
            JspWriter out = pageContext.getOut();
 
            //Perform substr operation on string.
            //out.println(base.substring(traverseSubFolder, allowedExtentions));
			String str=createFileElement(new File(docID),true,trav);
			//out.println("<div class=\"homecontent\">"+"<h3 class=\"h3home\">"+docID.toUpperCase() +"files</h3>");
			//out.println("<br/>#####################################<br/>");			
			//out.println("The "+base+" folder has following files : <br/>"+str);
			out.println(str);
			//out.println("base == "+base+"<br/>");
			//out.println("traverseSubFolder == "+traverseSubFolder+"<br/>");
			//out.println("allowedExtentions == "+allowedExtentions+"<br/>");
			//out.println("<br/>#####################################<br/>");
			//out.println("</div>");
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }
	
	//################################
	Set<String> listAllowedExtensionsForIndexing=new HashSet<String>();
	
	private String createFileElement(File b,boolean traverse,boolean trav) {
		StringBuffer sb = new StringBuffer();		
		if (b.exists()) {			
			if (b.isFile()) {	
				if(validExtention(getExtention(b.getName()))){				
				sb.append("\n<p>" + "<a class=\""+classname+"\" " 
						+" title=\""+b.getName()+"\" "
						+"href=\""+baseURLPrefix+ b.getAbsolutePath().replace("\"", "/") 
						+ "\">"
						+ b.getName() + "</a></p>");
				}
			} else if (b.isDirectory()&&traverse) {
				//sb.append("\n<ul>");
				//sb.append("\n<li>" + "<a href=\""
						//+ b.getAbsolutePath().replace("\"", "/") + "\">"
						//+ b.getName() + "</a>");
				File[] bKeChilddren = b.listFiles();
				if (bKeChilddren != null && bKeChilddren.length > 0) {
					//sb.append("\n<ul>");
					for (File bKeCh : bKeChilddren) {
						String authEle = createFileElement(bKeCh,trav,trav);
						if (authEle != null) {
							sb.append("\n"+authEle);
						}
					}
					//sb.append("\n</ul>");
				}
				//sb.append("</li>");
				//sb.append("\n</ul>");
			}
		}else{
			sb.append(b.getAbsolutePath()+" not exists");
		}
		return sb.toString();
	}
	
	
	private String getExtention(String fileName) {
		String extention = "NA";

		if (fileName != null && fileName.contains(".")) {
			extention = fileName.substring(fileName.lastIndexOf("."));
		}

		return extention;
	}

	private boolean validExtention(String extention) {	
		boolean valid=true;
		for(String extn:listAllowedExtensionsForIndexing){
		
			if (extn.equalsIgnoreCase(extention)) {
				 valid= true;
				 break;
			} else {
				valid= false;
			}
		}		
		return valid;
	}
	//#################################
	
    public String getBase() {
        return base;
    }
    public void setBase(String base) {
        this.base = base;
    }
    public String getTraverseSubFolder() {
        return traverseSubFolder;
    }
    public void setTraverseSubFolder(String traverseSubFolder) {
        this.traverseSubFolder = traverseSubFolder;
    }
    public String getAllowedExtentions() {
        return allowedExtentions;
    }
    public void setAllowedExtentions(String allowedExtentions) {
        this.allowedExtentions = allowedExtentions;
    }
	
	public String getClassname() {
        return classname;
    }
    public void setClassname(String classname) {
        this.classname = classname;
    }
	
	public String getBaseURLPrefix() {
        return baseURLPrefix;
    }
    public void setBaseURLPrefix(String baseURLPrefix) {
        this.baseURLPrefix = baseURLPrefix;
    }
}