<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
 
<html>
<head>
  <title>JSTL x:transform Tags</title>
</head>
<body>
<h3>Books Info:</h3>
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

<c:import var="bookInfo" url="books.xml"/>
 
<x:parse xml="${bookInfo}" var="output"/>
 
<c:import url="style.xsl" var="xslt"/>
<x:transform xml="${xmltext}" xslt="${xslt}"/>
<hr/>

<x:transform xml="${output}" xslt="${xslt}"/>
 
</body>
</html>