<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>XSLT Transform Demo</title>
</head>
<body>
<h1>Transforming XML with XSLT</h1>
<c:set var="xmltext">
    <books>
        <book>
            <name>Padam History</name>
            <author>ZARA</author>
            <price>100</price>
        </book>
        <book>
            <name>Great Mistry</name>
            <author>NUHA</author>
            <price>2000</price>
        </book>
    </books>
</c:set>
<c:import var="bookInfo" url="${pageContext.request.contextPath}/data/books.xml"/>
<x:parse xml="${bookInfo}" var="output"/>
<c:import url="${pageContext.request.contextPath}/data/style.xsl" var="xslt"/>
<h2>Inline XML with XSLT</h2>
<x:transform xml="${xmltext}" xslt="${xslt}"/>
<hr/>
<h2>External XML with XSLT</h2>
<x:transform xml="${output}" xslt="${xslt}"/>
</body>
</html>
