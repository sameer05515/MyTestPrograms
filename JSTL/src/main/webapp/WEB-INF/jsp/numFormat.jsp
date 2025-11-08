<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/tlds/numberformatter.tld" prefix="mytags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Custom Tag Example</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 2rem auto;
            max-width: 560px;
            line-height: 1.8;
        }

        code {
            background-color: #f2f2f2;
            padding: 0.2rem 0.4rem;
            border-radius: 3px;
        }
    </style>
</head>
<body>
<main>
    <h1>Number Formatting Example</h1>

    <p>
        Pattern <code>#,&nbsp;###.00</code> ⇒
        <strong><mytags:formatNumber number="100050.574" format="#,###.00"/></strong>
    </p>

    <p>
        Pattern <code>$# ###.00</code> ⇒
        <strong><mytags:formatNumber number="1234.567" format="$# ###.00"/></strong>
    </p>

    <a href="<c:url value='/'/>">Back to home</a>
</main>
</body>
</html>

