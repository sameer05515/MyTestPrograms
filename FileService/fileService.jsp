
<%@page import="com.p.file.search.FileSearcher"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%

	String fileName=request.getParameter("fileName"); 
	String[] extensions = request.getParameterValues("extensions");
	
	List<String> extensionList=new ArrayList<String>();	
	if(extensions!=null){
		for (int i = 0; i < extensions.length; i++) {
			System.out.println("got extensions list : "+extensions[i]);
			extensionList.add(extensions[i]);
		}
	}
	
%>

<%System.out.println("Starting check for : "+fileName);%>

<%=new FileSearcher().startSearch(fileName,extensionList)%>