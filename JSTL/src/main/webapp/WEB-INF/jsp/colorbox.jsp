<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Colorbox Demo (placeholder)</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 2rem auto;
            max-width: 720px;
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
    <h1>Colorbox Demo</h1>
    <p>
        The legacy <code>colorbox.jsp</code> relied on absolute file-system paths and a locally hosted jQuery Colorbox
        build. For portability, the Spring Boot port leaves the custom tag implementation in place but does not attempt
        to render the original gallery by default.
    </p>
    <p>
        To enable the demo, configure a valid directory on the server host and update the JSP to pass
        <code>base</code>, <code>baseURLPrefix</code>, and
        <code>allowedExtentions</code> values that resolve against your deployment environment.
    </p>
</main>
</body>
</html>

