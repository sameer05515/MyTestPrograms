
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.nio.file.*"%>

<%

String documentId=request.getParameter("documentId");
if(documentId!=null && documentId.trim().length()>0){
	File file = new File("C:/Users/premendra.kumar1/Desktop/KT-Documents/references/The Nature of Software Development.pdf");
        response.setHeader("Content-Type", getServletContext().getMimeType(file.getName()));
        response.setHeader("Content-Length", String.valueOf(file.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"foo.pdf\"");
        Files.copy(file.toPath(), response.getOutputStream());
}
	




%>

