<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Spring Boot JSP Demo Index</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 2rem auto;
            max-width: 960px;
            line-height: 1.6;
        }
        h1 {
            margin-bottom: 0.5rem;
        }
        p.lead {
            margin-top: 0;
            color: #555;
        }
        ul.demos {
            list-style: none;
            padding: 0;
        }
        ul.demos li {
            margin-bottom: 1rem;
            border: 1px solid #ddd;
            border-radius: 6px;
            padding: 1rem;
        }
        ul.demos li h2 {
            margin: 0 0 0.25rem;
            font-size: 1.1rem;
        }
        a.demo-link {
            text-decoration: none;
            color: #0b5ed7;
        }
        a.demo-link:hover {
            text-decoration: underline;
        }
        code {
            background: #f7f7f7;
            padding: 0.1rem 0.3rem;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<h1>Spring Boot JSP Samples</h1>
<p class="lead">Examples migrated from the legacy JSP tutorial and now running on Spring Boot.</p>
<ul class="demos">
    <c:forEach items="${demos}" var="demo">
        <li>
            <h2><a class="demo-link" href="${pageContext.request.contextPath}${demo.path}">${demo.title}</a></h2>
            <p>${demo.description}</p>
            <c:if test="${not empty demo.notes}">
                <p><strong>Notes:</strong> ${demo.notes}</p>
            </c:if>
        </li>
    </c:forEach>
</ul>
</body>
</html>
