<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>User Registration Form</title>
<s:head />
</head>
<body>
<s:form name="registerform" action="registerUser">
	<s:textfield name="username" label="User Name" required="true" />
	<s:password name="password" label="Password" required="true" />
	<s:textfield name="email" label="Email Id" required="true" />
	<s:radio label="Sex" list="sexValues" name="sex"></s:radio>
	<s:checkboxlist label="Interests" list="interestValues"
		name="interests">
	</s:checkboxlist>
	<s:select list="countryValues" name="country" label="Country"></s:select>
	<s:submit />
</s:form>
</body>
</html>