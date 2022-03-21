
<%@page import="java.io.*"%>
<%@page import="java.util.*"%>
<%@page import="java.nio.file.*"%>

<%

	File file = new File("C:/Users/796412/Desktop/21-dec-2015/01-july-2016/Agile Metrics in Action.pdf");
    response.setHeader("Content-Type",    getServletContext().getMimeType(file.getName()));
    response.setHeader("Content-Length", String.valueOf(file.length()));
    response.setHeader("Content-Disposition", "inline; filename=\""+file.getName()+"\"");
    //Files.copy(file.toPath(), response.getOutputStream());
	
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




%>

