<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en" xmlns="http://www.w3.org/1999/xhtml" xmlns:jsp="http://java.sun.com/JSP/Page">
<head>
    <meta charset="UTF-8">
    <title>JSP XML Element Demo</title>
</head>
<body>
<h1>Building an XML Fragment with <code>jsp:element</code></h1>
<jsp:element name="xmlElement">
    <jsp:attribute name="xmlElementAttr">Value for the attribute</jsp:attribute>
    <jsp:body>Body for XML element</jsp:body>
</jsp:element>
</body>
</html>
