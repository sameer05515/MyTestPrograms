
<%@page import="com.p.file.search.FileSearcher"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>

<%

	String fileName=request.getParameter("fileName");
	String targetPath=request.getParameter("targetPath"); 
	String newName=request.getParameter("newName"); 
	//String[] extensions = request.getParameterValues("extensions");
	File f=new File(fileName);
	File tDir = new File(targetPath);
	
	
	boolean valid=false;
	if(tDir.exists()){
		File fnew=new File(tDir.getAbsolutePath()+File.separator+newName);
		
		if(!fnew.exists()){
			if(f.renameTo(fnew)){
	            out.println("{\"status\":\"success\"}");;
	        }else{
	            out.println("{\"status\":\"fail\"}");
	        }
		}else{
			out.println("{\"status\":\"fail filename : "+newName+" already exists in  targetpath : "+targetPath+" \"}");
		}
		
		
	}else{
		out.println("{\"status\":\"fail targetpath does not exists : "+targetPath+" \"}");
	}
	
	
	
	
%>

