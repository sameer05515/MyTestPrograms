<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*,java.io.*" %>

<%
File f1=new File("");
%>
<%=f1.getAbsolutePath()%>
<%

%>
<hr/>
<c:set var="context" value="${pageContext.request.contextPath}" />
<c:out value="${context}"/>
<hr/>
Absolute Path is:<%= getServletContext().getRealPath("/") %>

<%
File f=new File(getServletContext().getRealPath("/"));
String basePath=f.getAbsolutePath().replaceAll("\"", "/")+"/";
%>
<%=traceFile(basePath,f, ".jsp") %>


<%!

private String traceFile(String basePath,File parent,String extention){
	StringBuffer sb=new StringBuffer();
	File[] children=parent.listFiles();
	if(children!=null && children.length>0){
		//System.out.println("Directory : "+parent.getName() +" Size : "+children.length);
		for(File child:children){
			if(child!=null&&child.exists()){
				if(child.isFile()&&child.getName().endsWith(extention)){
					String name=child.getAbsolutePath().replaceAll("\"", "/").substring(basePath.length());
					//System.out.println("basePath : "+basePath+" name : "+name);
					sb.append("\n<br/>").append("<a href=\""+name+"\">"+name+"</a>");			
				}else if(child.isDirectory()){
					
					sb.append(traceFile(basePath,child,extention));
				}
			}
		}
	}	
	return sb.toString();
}

%>
