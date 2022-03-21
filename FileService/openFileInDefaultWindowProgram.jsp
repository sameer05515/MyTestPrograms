
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.nio.file.*"%>
<%@page import="java.awt.*"%>

<%
String documentId=request.getParameter("documentId");
	System.out.println(documentId);
try{
	
	if(documentId!=null && documentId.trim().length()>0){
		File file = new File(documentId);
		if(!file.exists()){
			throw new IOException("File not exists!");
        }
        
        Desktop.getDesktop().open(file);
		//if(file.isDirectory()){
			//File[] children=file.listFiles();
			//int count=((children!=null)?children.length:0);
			//out.println("<b>Total file found : - "+count+"</b>");
			//out.println("<ul>");
			//for(File child:children){
				//out.println("<li>"+"<a href=\"?documentId="+child.getAbsolutePath()+"\" >"+child.getName()+"</a>"+"</li>");
			//}
			//out.println("</ul>");
		//}
			//response.setHeader("Content-Type", getServletContext().getMimeType(file.getName()));
			//response.setHeader("Content-Length", String.valueOf(file.length()));
			//response.setHeader("Content-Disposition", "inline; filename=\"foo.pdf\"");
			//Files.copy(file.toPath(), response.getOutputStream());
			//System.out.println(file);
	}
}catch(IOException e){
	e.printStackTrace();
	out.println("No File found : "+documentId);
}catch(Exception e){
	e.printStackTrace();
}

	




%>

