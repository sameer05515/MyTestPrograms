<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>JSTL XML Parsing Demo</title>
</head>
<body>
<h1>Parsing XML with JSTL</h1>
<p>The XML file is served from <code>/data/books.xml</code> and parsed using <code>x:parse</code>.</p>
<c:import var="bookInfo" url="${pageContext.request.contextPath}/data/books.xml"/>
<x:parse xml="${bookInfo}" var="output"/>
<p><strong>First book title:</strong> <x:out select="$output/books/book[1]/name"/></p>
<p><strong>Second book price:</strong> <x:out select="$output/books/book[2]/price"/></p>
</body>
</html>
