<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.p.xml.bo.UserDetails,com.p.xml.UserDetailsParser"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>One Touch Employee Record Maintenance Application</title>
</head>
<body>
	<%
	String userName = request.getParameter("UserName");
    String password = request.getParameter("Password");
		try {
			String filePath = "/xml/userDetails.xml";
			String real = request.getSession().getServletContext()
					.getRealPath(filePath);
			// create an instance
			UserDetailsParser dpe = new UserDetailsParser(real);

			out.println("DomParserVersion : " + dpe.getVersion()
					+ " : version " + dpe.getVersion().getVersionNumber());
			// call run example
			//dpe.runExample();
			out.println(dpe.findUserDetailsByUserName(userName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	%>

</body>
</html>