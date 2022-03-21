<%@taglib uri=" http://java.sun.com/jstl/core" prefix="c" %>
<html>
<head>
 <title>Nasty letter</title>
</head>
<body>
<%!
String name="Premendra";
int dollars=567890;
String present="Mercedege";
String appendage="Legs";
%>
<h1>Dear <c:out value="${name}"/>:</h1>
<p>
 My records show that you owe me $<c:out value="${dollars}"/>.
 I need this money now to buy myself a big 
 <c:out value="${present}"/>. If I don’t get it,
 I will break your <c:out value="${appendage}"/>.
</p>
</body>
</html>