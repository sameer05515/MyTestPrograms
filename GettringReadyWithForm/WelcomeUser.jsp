<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
Hi <s:property value="username"/><br/>
You are a: <s:property  value="sex" /><br/>
Your Email Id: <s:property value="email"/><br/>
You Belong to: <s:property value="country"/> <br/>
Your Interests are: <s:property value="interests"/>
</body>
</html>