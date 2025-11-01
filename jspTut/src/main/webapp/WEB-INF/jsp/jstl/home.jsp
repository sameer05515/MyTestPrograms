<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>JSTL Demo - Employee Table</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid #333;
            padding: 0.5rem 0.75rem;
            text-align: left;
        }
        th {
            background: #f0f0f0;
        }
        .section {
            margin-top: 1.5rem;
        }
    </style>
</head>
<body>
<h1>Employee List with JSTL</h1>
<p>This page reproduces the behaviour of the original <code>/jstl/home.jsp</code> sample.</p>
<section class="section">
    <h2>Looping a <code>List&lt;Employee&gt;</code></h2>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Role</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${empList}" var="emp">
            <tr>
                <td><c:out value="${emp.id}"/></td>
                <td><c:out value="${emp.name}"/></td>
                <td><c:out value="${emp.role}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
<section class="section">
    <h2>HTML escaping via <code>c:out</code></h2>
    <c:if test="${not empty htmlTagData}">
        <p><strong>Escaped:</strong> <c:out value="${htmlTagData}" escapeXml="true"/></p>
        <p><strong>Raw:</strong> <c:out value="${htmlTagData}" escapeXml="false"/></p>
    </c:if>
</section>
<section class="section">
    <h2>Variables and URLs</h2>
    <c:set var="requestId" value="${idValue}" />
    <p><strong>Request scoped value:</strong> <c:out value="${requestId}"/></p>
    <p><a href="<c:url value="${url}"/>">Return to Home</a></p>
</section>
<section class="section">
    <h2>Error Handling with <code>c:catch</code></h2>
    <c:catch var="exception">
        <c:set var="_unused" value="${1/0}" />
    </c:catch>
    <c:if test="${exception ne null}">
        <p><strong>Exception class:</strong> <c:out value="${exception['class'].name}"/></p>
        <p><strong>Message:</strong> <c:out value="${exception.message}"/></p>
    </c:if>
</section>
</body>
</html>
