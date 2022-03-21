
<%@page import="com.p.file.search.FileSearcher"%>
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.nio.file.*"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<%

	//File file = new File("C:/Users/796412/Desktop/21-dec-2015/01-july-2016/Agile Metrics in Action.pdf");
	String str=request.getParameter("documentId");
	File file = new File(str);
	
	if(file.isFile()){
		response.setHeader("Content-Type",    getServletContext().getMimeType(file.getName()));
		response.setHeader("Content-Length", String.valueOf(file.length()));
		response.setHeader("Content-Disposition", "inline; filename=\""+file.getName()+"\"");
		Files.copy(file.toPath(), response.getOutputStream());
		
		FileInputStream in = new FileInputStream(file);
		ServletOutputStream outs = response.getOutputStream();
		response.setContentLength(in.available());
		byte[] buf = new byte[8192];
		int c = 0;
		try {
			while ((c = in.read(buf, 0, buf.length)) > 0) {
				//System.out.println("size:"+c);
				outs.write(buf, 0, c);
				out.write(outs.toString());
			}

		} catch (IOException ioe) {
			ioe.printStackTrace(System.out);
		} finally {
			outs.flush();
			outs.close();
			in.close();
		}
		
	}else if(file.isDirectory()){
		response.setHeader("Content-Type",    "application/json");
		
		

			String fileName=request.getParameter("documentId"); 
			String[] extensions = request.getParameterValues("extensions");
			
			List<String> extensionList=new ArrayList<String>();	
			if(extensions!=null){
				for (int i = 0; i < extensions.length; i++) {
					System.out.println("got extensions list : "+extensions[i]);
					extensionList.add(extensions[i]);
				}
			}
			
		System.out.println("Starting check for : "+fileName);%>

		<%=new FileSearcher().startSearch(fileName,extensionList)%>
		
		
		
		<%
		
	}

%>