<%@page import="java.io.File"%>
<%@page import="java.io.FileFilter"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>



<% String base=getServletContext().getRealPath("/");

	List<String> fileList = new ArrayList<String>(); %>
	
<%
	StringBuffer sb = new StringBuffer();
	if (base != null) {
		fileList = new ArrayList<String>();
		serarch(fileList, base,base);

		for (int i = 0; i < fileList.size(); i++) {
			
			%>

			<a href="<%=fileList.get(i)%>"><%=fileList.get(i)%></a><br/>
			
			<%

			
		}
	}

%>

<%!

private static String[] allowedExtentions = { ".html", ".htm",".pdf",".exe",".mp3",".mp4" };
//private static String[] allowedExtentions = { ".pdf",".mp3",".mp4" };

private void serarch(List<String> fileList, String fileName,String base) {

		File f = new File(fileName);
		File[] children = f.listFiles(new MyFileFilter());
		for (File child : children) {
			if (child.isDirectory()) {
				serarch(fileList, child.getAbsolutePath(),base);
			}
			if (child.isFile()) {

				fileList.add(child.getAbsolutePath().trim().replace("\\", "/")
						.substring(base.length()));
			}
		}

	}

	private static class MyFileFilter implements FileFilter {

		@Override
		public boolean accept(File file) {

			if (file == null) {
				return false;
			}
			if (!file.exists()) {
				return false;
			}
			if (file.isDirectory()) {
				return true;
			}

			String fileNameee = (file.getName() != null) ? file.getName()
					.trim().toLowerCase() : null;			

			for (String exts : allowedExtentions) {

				if (fileNameee != null && fileNameee.endsWith(exts)) {
					return true;
				}
			}

			return false;
		}

	}


%>