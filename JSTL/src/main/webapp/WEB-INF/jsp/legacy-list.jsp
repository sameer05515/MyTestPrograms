<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Legacy JSP Samples</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 2rem auto;
            max-width: 720px;
            line-height: 1.7;
        }

        h1, h2 {
            color: #1f3a93;
        }

        ul {
            list-style: none;
            padding-left: 0;
        }

        li + li {
            margin-top: 0.35rem;
        }

        a {
            color: #1f3a93;
        }

        .note {
            color: #555;
            font-size: 0.95rem;
        }
    </style>
</head>
<body>
<main>
    <h1>Legacy JSP Samples</h1>
    <p class="note">
        These pages are the original demos that shipped with the legacy servlet container. They may reference
        hard-coded file system paths or external resources, so expect to tweak values before running them in production.
    </p>

    <h2>Primary Samples</h2>
    <ul>
        <li><a href="<c:url value='/legacy/subscript'/>">Subscript Tag Demo</a></li>
        <li><a href="<c:url value='/legacy/test-page'/>">Quiz Result Tag Demo</a></li>
        <li><a href="<c:url value='/legacy/gg'/>">AJAX Loader (gg.jsp)</a></li>
        <li><a href="<c:url value='/legacy/letter'/>">EL Letter Template</a></li>
        <li><a href="<c:url value='/legacy/number-format'/>">Custom Number Formatter</a></li>
        <li><a href="<c:url value='/legacy/colorbox'/>">Colorbox Gallery (filesystem based)</a></li>
    </ul>

    <h2>Test Variants</h2>
    <ul>
        <li><a href="<c:url value='/legacy/test/colorbox'/>">Colorbox Test Gallery</a></li>
        <li><a href="<c:url value='/legacy/test/colorbox-explorer'/>">Colorbox Explorer</a></li>
        <li><a href="<c:url value='/legacy/test/file-explorer'/>">File Explorer</a></li>
        <li><a href="<c:url value='/legacy/test/file-explorer-div'/>">File Explorer (DIV layout)</a></li>
        <li><a href="<c:url value='/legacy/test/subscript'/>">Subscript Test</a></li>
        <li><a href="<c:url value='/legacy/test/subscript-div'/>">Subscript Test (DIV layout)</a></li>
    </ul>

    <p>
        <a href="<c:url value='/'/>">Back to home</a>
    </p>
</main>
</body>
</html>

