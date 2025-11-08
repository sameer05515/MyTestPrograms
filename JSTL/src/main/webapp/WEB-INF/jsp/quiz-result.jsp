<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" uri="/WEB-INF/tlds/CustomTags.tld" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quiz Result</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 2rem auto;
            max-width: 560px;
            line-height: 1.6;
        }

        .result {
            padding: 1rem;
            border-radius: 4px;
            margin-bottom: 1rem;
        }

        .result--success {
            background-color: #d5f5e3;
            color: #1e8449;
        }

        .result--info {
            background-color: #ebf5fb;
            color: #21618c;
        }
    </style>
</head>
<body>
<main>
    <h1>Quiz Result</h1>
    <div class="result result--info">
        <strong>Your answer:</strong>
        <c:out value="${answer != null ? answer : 'Nothing submitted yet'}"/>
    </div>

    <tag:check paramName="opt">
        <div class="result result--success">
            Congratulations, you have answered correctly.
        </div>
    </tag:check>

    <a href="<c:url value='/'/>">Try again</a>
</main>
</body>
</html>

