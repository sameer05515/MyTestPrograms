
<%@page import="com.p.file.search.FileSearcher"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>

<%

	String fileName=request.getParameter("fileName"); 
	//String[] extensions = request.getParameterValues("extensions");
	File f=new File(fileName);
	if(f.delete())
	out.println("{\"status\":\"success\"}");
	else
	out.println("{\"status\":\"fail\"}");
	
%>

