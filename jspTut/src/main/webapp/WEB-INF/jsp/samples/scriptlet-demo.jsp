<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Scriptlet Demo</title>
</head>
<body>
<h1>Legacy Scriptlet Showcase</h1>
<p>The time is now <fmt:formatDate value="${currentTime}" pattern="yyyy-MM-dd HH:mm:ss z"/></p>
<p>Your machine&#39;s address is ${remoteHost}</p>
<hr/>
<table border="1">
    <c:forEach items="${numbers}" var="i">
        <tr>
            <td>Number</td>
            <td>${i}</td>
        </tr>
    </c:forEach>
</table>
<hr/>
<c:choose>
    <c:when test="${showHello}">
        <p>Hello, world</p>
    </c:when>
    <c:otherwise>
        <p>Goodbye, world</p>
    </c:otherwise>
</c:choose>
</body>
</html>
