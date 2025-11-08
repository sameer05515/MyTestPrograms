<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Vocab Khajana</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 2rem;
            background-color: #f7f7fb;
            color: #222;
        }

        h1 {
            margin-bottom: 0.25rem;
        }

        .page-info {
            margin-bottom: 1.5rem;
            color: #555;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            background: #fff;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            border-radius: 6px;
            overflow: hidden;
        }

        th, td {
            padding: 0.75rem;
            vertical-align: top;
        }

        th {
            background-color: #3f51b5;
            color: #fff;
            text-align: left;
            font-weight: 600;
            width: 20%;
        }

        tr:nth-child(even) {
            background-color: #f3f4ff;
        }

        .tag {
            display: inline-block;
            padding: 0.15rem 0.45rem;
            border-radius: 4px;
            background-color: #e0e7ff;
            color: #1a237e;
            font-size: 0.75rem;
            margin-left: 0.4rem;
        }

        ul {
            margin: 0.25rem 0 0.75rem 1.25rem;
        }

        nav {
            margin-top: 1.5rem;
            display: flex;
            align-items: center;
            flex-wrap: wrap;
            gap: 0.5rem;
        }

        nav a, nav span {
            padding: 0.35rem 0.75rem;
            border-radius: 4px;
            text-decoration: none;
            color: #3f51b5;
            border: 1px solid #c5cae9;
            background-color: #fff;
        }

        nav a:hover {
            background-color: #c5cae9;
        }

        nav .active {
            background-color: #3f51b5;
            color: #fff;
        }

        .spacer {
            flex: 1 1 auto;
        }
    </style>
</head>
<body>
<h1>Vocab Khajana</h1>
<div class="page-info">
    Showing page ${wordPage.pageNumber + 1} of ${wordPage.totalPages} &mdash;
    ${wordPage.pageSize} entries per page, ${wordPage.totalElements} total words.
</div>

<c:choose>
    <c:when test="${wordPage.totalElements == 0}">
        <p>No vocabulary entries found. Ensure that <code>khajana.xml</code> is present in the classpath.</p>
    </c:when>
    <c:otherwise>
        <table>
            <c:forEach var="entry" items="${wordPage.content}">
        <tr>
            <th scope="row">
                ${entry.value}
                <c:if test="${not empty entry.type}">
                    <span class="tag">${entry.type}</span>
                </c:if>
            </th>
            <td>
                <strong>Meanings</strong>
                <ul>
                    <c:forEach var="meaning" items="${entry.meanings}">
                        <li>${meaning}</li>
                    </c:forEach>
                </ul>

                <strong>Examples</strong>
                <ul>
                    <c:forEach var="example" items="${entry.examples}">
                        <li>${example}</li>
                    </c:forEach>
                </ul>
            </td>
        </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

<c:if test="${wordPage.totalElements > 0}">
    <nav>
        <c:if test="${wordPage.hasPrevious}">
            <a href="<c:url value='/words'><c:param name='page' value='${wordPage.pageNumber - 1}'/><c:param name='size' value='${pageSize}'/></c:url>">&laquo; Previous</a>
        </c:if>

        <c:forEach var="pageNumber" items="${pageNumbers}">
            <c:choose>
                <c:when test="${pageNumber == wordPage.pageNumber}">
                    <span class="active">${pageNumber + 1}</span>
                </c:when>
                <c:otherwise>
                    <a href="<c:url value='/words'><c:param name='page' value='${pageNumber}'/><c:param name='size' value='${pageSize}'/></c:url>">
                        ${pageNumber + 1}
                    </a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <c:if test="${wordPage.hasNext}">
            <a href="<c:url value='/words'><c:param name='page' value='${wordPage.pageNumber + 1}'/><c:param name='size' value='${pageSize}'/></c:url>">Next &raquo;</a>
        </c:if>

        <span class="spacer"></span>
        <span>Jump to page:</span>
        <form method="get" action="<c:url value='/words'/>">
            <input type="hidden" name="size" value="${pageSize}"/>
            <input type="number" name="page" min="1" max="${wordPage.totalPages}" value="${wordPage.pageNumber + 1}"
                   style="width: 4rem; padding: 0.25rem;"/>
            <button type="submit" style="padding: 0.35rem 0.75rem;">Go</button>
        </form>
    </nav>
</c:if>
</body>
</html>

