
<%@page import="com.p.file.search.FileSearcher"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>

<%

	String fileName=request.getParameter("fileName");
	String newName=request.getParameter("newName"); 
	//String[] extensions = request.getParameterValues("extensions");
	File f=new File(fileName);
	File fnew=new File(f.getParentFile().getAbsolutePath()+"/"+newName);
	
	//out.println("{\"status\":"+"\"success "+" fileName : "+fileName +" newName : "+newName+" \"}");
	//System.out.println("{\"status\":"+"\"success "+" fileName : "+fileName +" newName : "+newName+" \"}");
	
	//if(f.delete())
	//out.println("{\"status\":\"success\"}");
	//else
	//out.println("{\"status\":\"fail\"}");
	
	if(f.renameTo(fnew)){
            out.println("{\"status\":\"success\"}");;
        }else{
            out.println("{\"status\":\"fail\"}");
        }
	
%>

