<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>JSTL Samples</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 2rem auto;
            max-width: 560px;
            line-height: 1.6;
        }

        label {
            display: block;
            margin-bottom: 0.5rem;
            color: #1f3a93;
            font-weight: bold;
        }

        input[type="text"] {
            padding: 0.4rem;
            width: 100%;
            box-sizing: border-box;
            margin-bottom: 0.75rem;
        }

        input[type="submit"] {
            padding: 0.5rem 1rem;
            background-color: #1f3a93;
            color: #fff;
            border: none;
            cursor: pointer;
        }

        nav a {
            display: inline-block;
            margin-right: 1rem;
            color: #1f3a93;
        }
    </style>
</head>
<body>
<main>
    <h1>Predict and win!</h1>
    <form action="<c:url value='/quiz/result'/>" method="get">
        <label for="opt">The country which is known as "Land of rising sun"</label>
        <input type="text" id="opt" name="opt" placeholder="Enter your answer"/>
        <input type="submit" value="Check"/>
    </form>

    <nav>
        <a href="<c:url value='/quiz/result'/>">Quiz Result</a>
        <a href="<c:url value='/numbers'/>">Number Formatter</a>
        <a href="<c:url value='/colorbox'/>">Colorbox Notes</a>
        <a href="<c:url value='/legacy'/>">Legacy Samples</a>
    </nav>
</main>
</body>
</html>

